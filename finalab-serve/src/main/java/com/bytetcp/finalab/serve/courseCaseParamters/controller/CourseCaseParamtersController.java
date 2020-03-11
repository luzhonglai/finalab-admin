package com.bytetcp.finalab.serve.courseCaseParamters.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.UUIDUtil;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.caseParameters.mapper.CaseParametersMapper;
import com.bytetcp.finalab.serve.caseParameters.service.ICaseParametersService;
import com.bytetcp.finalab.serve.cases.domain.Cases;
import com.bytetcp.finalab.serve.cases.service.ICasesService;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.course.vo.CourseVo;
import com.bytetcp.finalab.serve.courseCaseParamters.domain.CourseCaseParamters;
import com.bytetcp.finalab.serve.courseCaseParamters.service.ICourseCaseParamtersService;
import com.bytetcp.finalab.serve.targetParam.domain.TargetParam;
import com.bytetcp.finalab.serve.targetParam.service.ITargetParamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程案例参数 信息操作处理
 *
 * @author finalab
 * @date 2019-04-02
 */
@Controller
@RequestMapping("/serve/courseCaseParamters")
public class CourseCaseParamtersController extends BaseController
{
    private String prefix = "courseParameters";

	@Autowired
	private ICourseCaseParamtersService courseCaseParamtersService;
	@Autowired
	private CaseParametersMapper caseParametersMapper;
	@Autowired
	private ITargetParamService targetParamService;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private ICasesService casesService;

	@Autowired
	private ICaseParametersService caseParametersService;

	@Autowired
	private com.bytetcp.finalab.serve.courseCaseParamters.mapper.CourseCaseParamtersMapper CourseCaseParamtersMapper;

	@RequiresPermissions("serve:courseCaseParamters:view")
	@GetMapping()
	public String courseCaseParamters()
	{
	    return prefix + "/courseCaseParamters";
	}

	/**
	 * 查询课程案例参数列表
	 */
	@RequiresPermissions("serve:courseCaseParamters:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo list(@PathVariable("courseId") long courseId ,CourseCaseParamters courseCaseParamters)
	{
		courseCaseParamters.setCourseId(courseId);
		startPage();
        List<CourseCaseParamters> list = courseCaseParamtersService.selectCourseCaseParamtersList(courseCaseParamters);
		return getDataTable(list);
	}

	@RequiresPermissions("serve:courseCaseParamters:list")
	@PostMapping("/targetParamlist/{courseId}")
	@ResponseBody
	public TableDataInfo targetParamlist(@PathVariable("courseId") Long courseId)
	{
		startPage();
		List<TargetParam> list = targetParamService.selectCourseTargetParambyCourseId(courseId);
		return getDataTable(list);
	}

	//编辑课件参数
	@RequiresPermissions("serve:courseCaseParamters:edit")
	@GetMapping("/parCourseEdit/{courseId}")
	public String parCourseEdit(@PathVariable("courseId") Long courseId, ModelMap mmap) {
		CourseVo courseVo = courseService.selectCourseById(courseId);

		Cases cases = casesService.selectCasesById(courseVo.getCaseId());


		//mmap.put("cplist", cpList);
		mmap.put("cases" , cases);
		mmap.put("courseVo" , courseVo);
		return "courseParameters/courseParaEdit" ;
	}

	//物理删除全部数据参数、案例数据
	//课件案例参数删除，是否删除案例？？？待确认
	@Log(title = "案例参数", businessType = BusinessType.DELETE)
	@PostMapping("/delAll")
	@ResponseBody
	public AjaxResult delAll(@RequestParam("caseId") Long caseId) {

		//删除全部课件参数
		courseCaseParamtersService.deleteCourseCaseParamtersByCaseId(caseId);
		CourseCaseParamtersMapper.deleteCourseLiquidationByCaseId(caseId);
		CourseCaseParamtersMapper.deleteCourseMarketNewsByCaseId(caseId);
		CourseCaseParamtersMapper.deleteCourseTargetParamByCaseId(caseId);
		CourseCaseParamtersMapper.deleteCoursePriceMoveByCaseId(caseId);
		CourseCaseParamtersMapper.deleteCourseTradingConstraintByCaseId(caseId);
		CourseCaseParamtersMapper.deleteCourseUserNewsByCaseId(caseId);
		//删除全部案例参数
		caseParametersMapper.deleteCaseParametersByCaseId(caseId);
		caseParametersMapper.deleteCaseLiquidationByCaseId(caseId);
		caseParametersMapper.deleteCaseMarketNewsByCaseId(caseId);
		caseParametersMapper.deleteCaseTargetParamByCaseId(caseId);
		caseParametersMapper.deleteCasePriceMoveByCaseId(caseId);
		caseParametersMapper.deleteCaseTradingConstraintByCaseId(caseId);
		caseParametersMapper.deleteCaseUserNewsByCaseId(caseId);

		courseService.deleteCourseByCaseId(caseId);
		casesService.deleteCasesById(caseId);
		return AjaxResult.success();
	}

	@PostMapping("/updateCaseType")
	@ResponseBody
	public AjaxResult updateCaseType(@RequestParam("caseId") long caseId,@RequestParam("caseType") Integer caseType,@RequestParam("courseId") long courseId){
		Cases cases = new Cases();
		cases.setId(caseId);
		cases.setCaseType(caseType);
		int result= casesService.updateCases(cases);
		if(result == 0 ){
			return AjaxResult.error();
		}else {
			return AjaxResult.success();
		}
	}

	@PostMapping("/editSave")
	@ResponseBody
	public AjaxResult editSave(@RequestParam("caseParamId") long caseParamId,
		@RequestParam("paraValue") String paraValue, @RequestParam("paraDesc") String paraDesc ,@RequestParam("courseId") long courseId,
    @RequestParam("caseId") long caseId){
		CourseCaseParamters ccp = new CourseCaseParamters();
		ccp.setCaseParamId(caseParamId);
		ccp.setCaseId(caseId);
		ccp.setCourseId(courseId);
		//ccp.setParaName(paraName);
		ccp.setParaValue(paraValue);
		ccp.setParaDesc(paraDesc);
		int status = courseCaseParamtersService.EditCourseCaseParamters(ccp);
		if(status == 0){
			return AjaxResult.error("失败");
		}
		return AjaxResult.success("成功");
	}

	@Log(title = "案例参数", businessType = BusinessType.INSERT)
	@PostMapping("/addPara")
	@ResponseBody
	public List<CourseCaseParamters> addPara(@RequestParam("paraName") String paraName, @RequestParam("paraValue") String paraValue,
			@RequestParam("caseId") Long caseId , @RequestParam("courseId") Long courseId) {
		System.out.println("执行了案例参数添加");
		List<CourseCaseParamters> cplist = new ArrayList<>();
		if (paraName != "" && paraValue != "" && caseId != null) {
			CourseCaseParamters courseCaseParamters = new CourseCaseParamters();
			courseCaseParamters.setCaseId(caseId);
			courseCaseParamters.setParaValue(paraValue);
			courseCaseParamters.setParaName(paraName);
			courseCaseParamters.setCourseId(courseId);
			long caseParametersId = UUIDUtil.getRandom();
			System.out.println("caseParamId" + caseParametersId);
			courseCaseParamters.setCaseParamId(caseParametersId);
			int inster = courseCaseParamtersService.insertCourseCaseParamters(courseCaseParamters);
			//拿出数据
			if (inster == 1) {
				CourseCaseParamters ccp = new CourseCaseParamters();
				ccp.setCourseId(courseId);
				cplist = courseCaseParamtersService.selectCourseCaseParamtersList(ccp);
			}
			return cplist;
		} else {
			return null;
		}
	}

	@Log(title = "案例参数", businessType = BusinessType.DELETE)
	@PostMapping("/delPara")
	@ResponseBody
	public List<CourseCaseParamters> delPara(@RequestParam("caseParamId") Long caseParamId, @RequestParam("caseId") Long caseId,
											 @RequestParam("courseId") long courseId) {
		System.out.println("进来del物理单条" + " caseParamId+" + caseParamId + " caseId+" + caseId);
		List<CourseCaseParamters> cplist = new ArrayList<>();
		CourseCaseParamters courseCaseParamters = new CourseCaseParamters();
		courseCaseParamters.setCaseId(caseId);
		courseCaseParamters.setCourseId(courseId);
		courseCaseParamters.setCaseParamId(caseParamId);
		cplist = courseCaseParamtersService.selectCourseCaseParamtersList(courseCaseParamters);
		if(cplist.size()==0){
			long delId = cplist.get(0).getId();
			int deleteStatus = courseCaseParamtersService.deleteCourseCaseParamtersById(delId);
			List<CourseCaseParamters> cplist1 = new ArrayList<>();
			CourseCaseParamters ccp = new CourseCaseParamters();
			ccp.setCourseId(courseId);
			cplist1 = courseCaseParamtersService.selectCourseCaseParamtersList(ccp);
			return cplist1;
		}else {
			return null;
		}

	}

	/**
	 * 导出课程案例参数列表
	 */
	@RequiresPermissions("serve:courseCaseParamters:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseCaseParamters courseCaseParamters)
    {
    	List<CourseCaseParamters> list = courseCaseParamtersService.selectCourseCaseParamtersList(courseCaseParamters);
        ExcelUtil<CourseCaseParamters> util = new ExcelUtil<CourseCaseParamters>(CourseCaseParamters.class);
        return util.exportExcel(list, "courseCaseParamters");
    }

	/**
	 * 新增课程案例参数
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存课程案例参数
	 */
	@RequiresPermissions("serve:courseCaseParamters:add")
	@Log(title = "课程案例参数", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CourseCaseParamters courseCaseParamters)
	{
		return toAjax(courseCaseParamtersService.insertCourseCaseParamters(courseCaseParamters));
	}

	/**
	 * 修改课程案例参数
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{

		CourseCaseParamters courseCaseParamters = courseCaseParamtersService.selectCourseCaseParamtersById(id);
		mmap.put("courseCaseParamters", courseCaseParamters);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存课程案例参数
	 */
	@RequiresPermissions("serve:courseCaseParamters:edit")
	@Log(title = "课程案例参数", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CourseCaseParamters courseCaseParamters)
	{
		return toAjax(courseCaseParamtersService.updateCourseCaseParamters(courseCaseParamters));
	}

	/**
	 * 删除课程案例参数
	 */
	@RequiresPermissions("serve:courseCaseParamters:remove")
	@Log(title = "课程案例参数", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(courseCaseParamtersService.deleteCourseCaseParamtersByIds(ids));
	}

}
