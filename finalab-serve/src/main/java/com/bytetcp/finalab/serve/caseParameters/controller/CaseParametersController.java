package com.bytetcp.finalab.serve.caseParameters.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.UUIDUtil;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.caseParameters.domain.CaseParameters;
import com.bytetcp.finalab.serve.caseParameters.mapper.CaseParametersMapper;
import com.bytetcp.finalab.serve.caseParameters.service.ICaseParametersService;
import com.bytetcp.finalab.serve.cases.domain.Cases;
import com.bytetcp.finalab.serve.cases.service.ICasesService;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.course.vo.CourseVo;
import com.bytetcp.finalab.serve.courseCaseParamters.service.ICourseCaseParamtersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 案例参数 信息操作处理
 *
 * @author finalab
 * @date 2019-03-09
 */
@Controller
@RequestMapping("/serve/caseParameters")
public class CaseParametersController extends BaseController {
    private String prefix = "caseParameters";
    private String casesPrefix = "cases";


    @Autowired
    private ICaseParametersService caseParametersService;

    @Autowired
    private ICasesService casesService;

    @Autowired
    private CaseParametersMapper caseParametersMapper;

    @Autowired
    private ICourseCaseParamtersService courseCaseParamtersService;

    @Autowired
    private ICourseService courseService;

    @RequiresPermissions("serve:caseParameters:view")
    @GetMapping()
    public String caseParameters() {
        return prefix + "/caseParameters";
    }

    /**
     * 查询案例参数列表
     */
    @RequiresPermissions("serve:caseParameters:list")
    @PostMapping("/list/{caseId}")
    @ResponseBody
    public TableDataInfo list(CaseParameters caseParameters,@PathVariable("caseId") long caseId ) {
        caseParameters.setCaseId(caseId);
        startPage();
        List<CaseParameters> list = caseParametersService.selectCaseParametersList(caseParameters);
        return getDataTable(list);
    }


    /**
     * 导出案例参数列表
     */
    @RequiresPermissions("serve:caseParameters:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CaseParameters caseParameters) {
        List<CaseParameters> list = caseParametersService.selectCaseParametersList(caseParameters);
        ExcelUtil<CaseParameters> util = new ExcelUtil<CaseParameters>(CaseParameters.class);
        return util.exportExcel(list, "caseParameters");
    }

    /**
     * 新增案例参数
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存案例参数
     */
    @RequiresPermissions("serve:caseParameters:add")
    @Log(title = "案例参数", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CaseParameters caseParameters) {
        return toAjax(caseParametersService.insertCaseParameters(caseParameters));
    }

    /**
     * 修改案例参数
     */
    @GetMapping("/edit/{caseParamId}")
    public String edit(@PathVariable("caseParamId") Long caseParamId, ModelMap mmap) {
        CaseParameters caseParameters = caseParametersService.selectCaseParametersById(caseParamId);
        mmap.put("caseParameters", caseParameters);
        return prefix + "/edit";
    }

    @PostMapping("/editSave")
    @ResponseBody
    public AjaxResult editSave(@RequestParam("caseParamId") long caseParamId,
     @RequestParam("paraValue") String paraValue, @RequestParam("paraDesc") String paraDesc){

        CaseParameters cps = new CaseParameters();
        cps.setCaseParamId(caseParamId);
        cps.setParaValue(paraValue);
        cps.setParaDesc(paraDesc);
        int status = caseParametersService.updateCaseParameters(cps);
        if(status == 0){
            return AjaxResult.error("失败");
        }
        return AjaxResult.success("成功");
    }

    @PostMapping("/updateCaseType")
    @ResponseBody
    public AjaxResult updateCaseType(@RequestParam("caseId") long caseId,@RequestParam("caseType") Integer caseType){
        Cases cases = new Cases();
        cases.setId(caseId);
        cases.setCaseType(caseType);
        int result= casesService.updateCases(cases);
        if(result == 0 ){
            System.out.println(1);
            return AjaxResult.error();
        }else {
            System.out.println(2);
            return AjaxResult.success();
        }
    }

    //编辑案例参数
    @GetMapping("/parEdit/{caseId}")
    public String parEdit(@PathVariable("caseId") Long caseId, ModelMap mmap) {
        Cases cases = casesService.selectCasesById(caseId);
        if (cases == null) {
            cases = new Cases();
            String code = "500";
            mmap.addAttribute("code", code);
            mmap.addAttribute("msg", "案例不存在");
        }
        CaseParameters caseParameters = new CaseParameters();
        caseParameters.setCaseId(caseId);

        List<CaseParameters> cpList = caseParametersService.selectCaseParametersList(caseParameters);
        //如果创建案例，未上传案例参数则跳转上传案例参数页面,并且展示第 2 步
        if (CollectionUtils.isEmpty(cpList)) {
            mmap.put("caseId", caseId);
            mmap.put("showStep", "2");
            mmap.put("sourceFrom", "2");//页面来源标志
            return casesPrefix + "/caseUpload";
        }

        mmap.addAttribute("cases", cases);
        mmap.put("cplist", cpList);
        CourseVo cv =new CourseVo();
        cv.setCourseName("isNotCoursePage");
        mmap.put("courseVo",cv);
        return prefix + "/parEdit";
    }


    @RequiresPermissions("serve:caseParameters:edit")
    @Log(title = "案例参数", businessType = BusinessType.INSERT)
    @PostMapping("/addPara")
    @ResponseBody
    public List<CaseParameters> addPara(@RequestParam("paraName") String paraName, @RequestParam("paraValue") String paraValue, @RequestParam("caseId") Long caseId) {
        System.out.println("执行了案例参数添加");
        List<CaseParameters> cplist = new ArrayList<>();
        if (paraName != "" && paraValue != "" && caseId != null) {
            CaseParameters caseParameters = new CaseParameters();
            caseParameters.setCaseId(caseId);
            caseParameters.setParaValue(paraValue);
            caseParameters.setParaName(paraName);
            //String asd = UUIDUtil.getRandomNo();
            //9997201903200003273
            //caseParameters.setCaseParamId(Long.parseLong(asd));
            long caseParametersId = UUIDUtil.getRandom();
            System.out.println("caseParamId" + caseParametersId);
            caseParameters.setCaseParamId(caseParametersId);
            int inster = caseParametersService.insertCaseParameters(caseParameters);
            //拿出数据
            if (inster == 1) {
                caseParameters.setParaName(null);
                caseParameters.setParaValue(null);
                caseParameters.setCaseParamId(null);
                cplist = caseParametersService.selectCaseParametersList(caseParameters);
            }
        } else {
            return cplist;
        }
        return cplist;
    }

    @RequiresPermissions("serve:caseParameters:edit")
    @Log(title = "案例参数", businessType = BusinessType.DELETE)
    @PostMapping("/delPara")
    @ResponseBody
    public List<CaseParameters> delPara(@RequestParam("caseParamId") Long caseParamId, @RequestParam("caseId") Long caseId) {
        System.out.println("进来del物理单条" + " caseParamId+" + caseParamId + " caseId+" + caseId);
        List<CaseParameters> cplist = new ArrayList<>();
        CaseParameters caseParameters1 = new CaseParameters();
        caseParameters1.setCaseId(caseId);
        //删除数据
        int deleteStatus = caseParametersService.deleteCaseParametersById(caseParamId);
        //拿到删除后的结果
        cplist = caseParametersService.selectCaseParametersList(caseParameters1);
        return cplist;
    }

    //物理删除全部数据参数、案例数据
    @RequiresPermissions("serve:caseParameters:edit")
    @Log(title = "案例参数", businessType = BusinessType.DELETE)
    @PostMapping("/delAll")
    @ResponseBody
    public AjaxResult delAll(@RequestParam("caseId") Long caseId) {
        CaseParameters caseParameters = new CaseParameters();
        caseParameters.setCaseId(caseId);
        List<CaseParameters> cplist = caseParametersService.selectCaseParametersList(caseParameters);
        String[] ids;
        String strId = "";
        String tepId = "";
        for (int i = 0; i < cplist.size(); i++) {
            tepId = String.valueOf(cplist.get(i).getCaseParamId());
            strId = tepId + "," + strId;
        }
        System.out.println("strId=" + strId);
        caseParametersService.deleteCaseParametersByIds(strId);
        courseCaseParamtersService.deleteCourseCaseParamtersByCaseId(caseId);
        courseService.deleteCourseByCaseId(caseId);
        casesService.deleteCasesById(caseId);
        return AjaxResult.success();

    }


    //物理删除全部数据参数、案例数据, 并从新上传
    @RequiresPermissions("serve:caseParameters:edit")
    @GetMapping("/reload/{caseId}")
    public String reload(@PathVariable("caseId") Long caseId, ModelMap mmap) {
        CaseParameters caseParameters = new CaseParameters();
        caseParameters.setCaseId(caseId);
        List<CaseParameters> cplist = caseParametersService.selectCaseParametersList(caseParameters);
        String[] ids;
        String strId = "";
        String tepId = "";
        for (int i = 0; i < cplist.size(); i++) {
            tepId = String.valueOf(cplist.get(i).getCaseParamId());
            strId = tepId + "," + strId;
        }
        System.out.println("strId=" + strId);
        caseParametersService.deleteCaseParametersByIds(strId);
       /* courseCaseParamtersService.deleteCourseCaseParamtersByCaseId(caseId);
        courseService.deleteCourseByCaseId(caseId);*/
        mmap.put("caseId", caseId);
        mmap.put("showStep", "2");
        mmap.put("sourceFrom", "3");//页面来源标志
        return casesPrefix + "/caseUpload";

    }

    /**
     * 修改保存案例参数
     */
    @RequiresPermissions("serve:caseParameters:edit")
    @Log(title = "案例参数", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CaseParameters caseParameters) {
        return toAjax(caseParametersService.updateCaseParameters(caseParameters));
    }

    /**
     * 删除案例参数
     */
    @RequiresPermissions("serve:caseParameters:remove")
    @Log(title = "案例参数", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(caseParametersService.deleteCaseParametersByIds(ids));
    }

}
