package com.bytetcp.finalab.serve.cases.controller;

import com.alibaba.fastjson.JSON;
import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.config.Global;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.enums.SheetName;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.file.FileUploadUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.common.utils.spring.SpringUtils;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.caseParameters.domain.CaseParameters;
import com.bytetcp.finalab.serve.caseParameters.service.ICaseParametersService;
import com.bytetcp.finalab.serve.cases.domain.Cases;
import com.bytetcp.finalab.serve.cases.mapper.CasesMapper;
import com.bytetcp.finalab.serve.cases.service.ICasesService;
import com.bytetcp.finalab.serve.cases.service.IExcelHandler;
import com.bytetcp.finalab.serve.targetParam.service.ITargetParamService;
import com.bytetcp.finalab.serve.tradingTarget.service.ITradingTargetService;
import com.bytetcp.finalab.system.domain.SysRole;
import com.bytetcp.finalab.system.domain.SysUser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 案例 信息操作处理
 *
 * @author finalab
 * @date 2019-03-09
 */
@Controller
@RequestMapping("/serve/cases")
public class CasesController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CasesController.class);

    private String prefix = "cases";

    @Value("${finalab.profile}")
    private String imgPath;

    @Value("${default.role.teacher}")
    private String teacherRoleSign;

    @Autowired
    private ICasesService casesService;

    @Autowired
    private ICaseParametersService caseParametersService;

    @Autowired
    private ITargetParamService targetParamService;

    @Autowired
    private ITradingTargetService tradingTargetService;
    @Autowired
    private CasesMapper casesMapper;

    @RequiresPermissions("serve:cases:view")
    @GetMapping()
    public String cases() {
        return prefix + "/cases";
    }

    //TODO 测试页面上时需要删除
    @GetMapping("test")
    public String test() {
        return "/tradingManagement/Trading";
    }

    @GetMapping("test1")
    public String test1() {
        return "/transaction/mline";
    }

    @ResponseBody
    @PostMapping("testTrade")
    public TestVo testTrade(@RequestBody TestVo testVo) {
        logger.info("交易下单 入参 ：{}", JSON.toJSONString(testVo));
        return testVo;
    }

    @RequiresPermissions("serve:cases:startCase")
    @GetMapping("run/{param1}")
    public String run(@PathVariable("param1") long caseId, ModelMap mmap) {
        Cases cases = casesService.selectCasesById(caseId);
        mmap.addAttribute("cases", cases);
        return prefix + "/run";
    }

    @GetMapping("/getCaseNum")
    @ResponseBody
    public Map<String, Integer> getCaseNum() {
        Map<String, Integer> map = new HashMap<>();
        SysUser sysUser = getSysUser();
        Long teacherId = null;
        List<SysRole> roles = sysUser.getRoles();
        int caseNum = casesMapper.getCaseNum();
        List<SysRole> collect = roles.stream()
                .filter(sysRole -> sysRole.getRoleKey().equals(teacherRoleSign))
                .collect(Collectors.toList());
        if (collect.size() != 0 && roles.size() == 1) { teacherId = sysUser.getUserId(); }
        int courseNum = casesMapper.getCourseNumByTeachId(teacherId);
        map.put("caseNum", caseNum);
        map.put("courseNum", courseNum);
        return map;
    }

    @RequiresPermissions("serve:cases:remove")
    @Log(title = "案例图片上传", businessType = BusinessType.IMPORT)
    @PostMapping("/addCaseImg")
    @ResponseBody
    public AjaxResult addCaseInfo(@RequestParam(value = "file", required = false) MultipartFile file,
                                  @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
                                  @RequestParam("caseName") String caseName,
                                  @RequestParam("caseType") String caseType,
                                  String caseDesc) {
        Cases cases = new Cases();
        try {
            if (Objects.nonNull(file) && file.getSize() > 0) {
                // 转存文件
                String upload = FileUploadUtils.upload(Global.getCaseIconPath(), file);
                cases.setCaseIcon(upload);
            } else {
                String defaultImg = "default/browser.png";
                String absPath = Global.getCaseIconPath() + defaultImg;
                if (!FileUploadUtils.exits(absPath)) {
                    InputStream in = getClass().getClassLoader().getResourceAsStream("static/img/browser.png");
                    FileUtils.copyToFile(in, new File(absPath));
                }
                cases.setCaseIcon(defaultImg);
            }
            //上传pdf
            if (Objects.nonNull(pdfFile) && pdfFile.getSize() > 0) {
                String pdfupload = FileUploadUtils.upload(Global.getCasePdfPath(), pdfFile, ".pdf");
                cases.setPdfPath(pdfupload);
                cases.setPdfName(pdfFile.getOriginalFilename());
            }
            cases.setCaseName(caseName);
            cases.setCaseType(NumberUtils.toInt(caseType, -1));
            cases.setCaseDesc(caseDesc);
            SysUser sysUser = getSysUser();
            cases.setCreateName(sysUser.getUserName());
            cases.setCreateId(sysUser.getUserId());
            casesService.insertCases(cases);
        } catch (Exception e) {
            logger.error("案例创建失败 {}", e.getMessage(), e);
            e.printStackTrace();
            return AjaxResult.error("创建案例失败");
        }
        return AjaxResult.success().put("caseId", cases.getId());
    }

    @PostMapping("canDownload/{casesId}")
    @ResponseBody
    public AjaxResult canDownloadPdf(@PathVariable("casesId") Long casesId, HttpServletRequest request) throws Exception {
        Cases cases = casesService.selectCasesById(casesId);
        if (cases != null && StringUtils.isEmpty(cases.getPdfPath())) {
            return AjaxResult.error();
        }
        String realPath = Global.getCasePdfPath() + cases.getPdfPath();//得到文件所在位置
        File file = new File(realPath);
        if (!file.exists()) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @RequiresPermissions("serve:cases:download:pdf")
    @GetMapping("download/{casesId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable("casesId") Long casesId, HttpServletRequest request) throws Exception {
        Cases cases = casesService.selectCasesById(casesId);
        ServletContext servletContext = request.getServletContext();
        String realPath = Global.getCasePdfPath() + cases.getPdfPath();//得到文件所在位置
        File file = new File(realPath);
        if(!file.exists()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "text/html");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        InputStream in = new FileInputStream(new File(realPath));//将该文件加入到输入流之中
        byte[] body = null;
        body = new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
        in.read(body);//读入到输入流里面

        String fileName = new String(cases.getPdfName().getBytes("gbk"), "iso8859-1");//防止中文乱码
        HttpHeaders headers = new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + fileName);
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }


    @RequiresPermissions("serve:cases:caseUpload")
    @GetMapping("caseUpload")
    public String caseUpload(ModelMap mmap) {
        mmap.put("showStep", "1");//展示第一步
        mmap.put("caseId", "");//为了兼容展示第二步
        mmap.put("sourceFrom", "1");//页面来源标志
        return prefix + "/caseUpload";
    }

    /**
     * 查询案例列表
     */
    @RequiresPermissions("serve:cases:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Cases cases) {
        startPage();
        List<Cases> list = casesService.selectCasesList(cases);
        return getDataTable(list);
    }


    /**
     * 导出案例列表
     */
    @RequiresPermissions("serve:cases:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Cases cases) {
        List<Cases> list = casesService.selectCasesList(cases);
        ExcelUtil<Cases> util = new ExcelUtil<Cases>(Cases.class);
        return util.exportExcel(list, "cases");
    }

    /**
     * 新增案例
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存案例
     */
    @RequiresPermissions("serve:cases:add")
    @Log(title = "案例", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Cases cases) {
        return toAjax(casesService.insertCases(cases));
    }

    /**
     * 修改案例
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Cases cases = casesService.selectCasesById(id);
        mmap.put("cases", cases);
        return prefix + "/edit";
    }

    /**
     * 修改保存案例
     */
    @RequiresPermissions("serve:cases:edit")
    @Log(title = "案例", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Cases cases) {
        return toAjax(casesService.updateCases(cases));
    }

    /**
     * 删除案例
     */
    @RequiresPermissions("serve:cases:remove")
    @Log(title = "案例", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(casesService.deleteCasesByIds(ids));
    }


    @RequiresPermissions("serve:cases:remove")
    @Log(title = "案例数据导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importExcel")
    @ResponseBody
    @Transactional
    public AjaxResult importExcel(@RequestParam("file") MultipartFile file, Long caseId) throws InterruptedException, InvalidFormatException {
        CaseParameters caseParameters = new CaseParameters();
        caseParameters.setCaseId(caseId);
        List<CaseParameters> parameters = caseParametersService.selectCaseParametersList(caseParameters);

        if (parameters.size() > 0) {
            return AjaxResult.error("已有案例数据，请勿重复导入");
        }
        try {
            if (!StringUtils.equals("xlsx", com.bytetcp.finalab.common.utils.file.FileUtils.fileType(file.getOriginalFilename()))) {
                return AjaxResult.error("导入Excel失败,仅支持Excel2007版");
            }
            ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
            String fileName = FileUploadUtils.uploadExcel(Global.getUploadPath(), file);
            fileName = Global.getUploadPath() + fileName;
            logger.info("update excel[{}]", fileName);
            Workbook workbook = WorkbookFactory.create(inputStream);
            AjaxResult ajaxResult = checkSheet(workbook);
            if (Objects.nonNull(ajaxResult)) {
                return ajaxResult;
            }
            saveExcelData(workbook, caseId);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error("导入Excel失败");
        }
        return AjaxResult.success().put("caseId", caseId);
    }

    private AjaxResult checkSheet(Workbook workbook) {
        for (SheetName sheetName : SheetName.values()) {
            Sheet sheet = workbook.getSheet(sheetName.getSheetName());
            if (Objects.isNull(sheet)) {
//                return AjaxResult.error(String.format("此Excel没有 '%s' sheet", sheetName.getSheetName()));
                logger.warn("此Excel没有 '{}' sheet", sheetName.getSheetName());
            }
        }
        return null;
    }

    /**
     * 保存 excel 数据
     *
     * @param workbook
     * @param caseId
     * @return
     */
    private AjaxResult saveExcelData(Workbook workbook, Long caseId) {
        IExcelHandler excelHandler;
        for (SheetName sheetName : SheetName.values()) {
            Sheet sheet = workbook.getSheet(sheetName.getSheetName());
            if (Objects.nonNull(sheet)) {
                excelHandler = SpringUtils.getBean(sheetName.getHandlerBeanName());
                boolean b = excelHandler.handlExcelSheet(sheet, caseId);
                logger.info("'{}' 保存 - {}", sheetName.getSheetName(), b ? "成功" : "失败");
            } else {
                logger.info("该excel 未找到名为 ： {} 的sheet。", sheetName.getSheetName());
            }
        }
        return null;
    }

    @Log(title = "案例图标", businessType = BusinessType.UPDATE)
    @GetMapping("/caseIcon/{caseId}")
    public String caseIcon(@PathVariable("caseId") Long caseId, ModelMap modelMap) {
        Cases cases = casesService.selectCasesById(caseId);
        modelMap.put("case", cases);
        return prefix + "/caseIcon";
    }

    @Log(title = "案例图标修改", businessType = BusinessType.UPDATE)
    @PostMapping("/updateCaseIcon")
    @ResponseBody
    public AjaxResult updateCaseIcon(MultipartFile file, Long caseId) {
        if (Objects.isNull(file)) {
            return AjaxResult.error("请选择案例图标");
        }
        try {
            String caseIcon = "";
            if (!file.isEmpty()) {
                caseIcon = FileUploadUtils.upload(Global.getCaseIconPath(), file);
                Cases cases = new Cases();
                cases.setId(caseId);
                cases.setCaseIcon(caseIcon);
                casesService.updateCases(cases);
            }
            return success().put("caseIcon", caseIcon);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/reUpPdf")
    @ResponseBody
    public AjaxResult reUpPdf(@RequestParam("caseId") Long caseId, @RequestParam("reUpPdf") MultipartFile pdf) {
        Cases cases = new Cases();
        if (Objects.nonNull(pdf) && pdf.getSize() > 0) {
            try {
                String pdfupload = FileUploadUtils.upload(Global.getCasePdfPath(), pdf, ".pdf");
                cases.setPdfPath(pdfupload);
                cases.setPdfName(pdf.getOriginalFilename());
                cases.setId(caseId);
            } catch(IOException e) {
                logger.error("上传pdf文件失败，失败原因：{}", e.getCause());
                return AjaxResult.error("文件上传失败！");
            }
        }
        int i = casesService.updateCases(cases);
        return i > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
