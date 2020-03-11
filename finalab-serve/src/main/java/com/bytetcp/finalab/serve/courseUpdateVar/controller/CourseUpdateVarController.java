package com.bytetcp.finalab.serve.courseUpdateVar.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.serve.courseUpdateVar.domain.CourseUpdateVar;
import com.bytetcp.finalab.serve.courseUpdateVar.service.ICourseUpdateVarService;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;

/**
 * 课件变量更新 信息操作处理
 *
 * @author finalab
 * @date 2019-08-17
 */
@Controller
@RequestMapping("/serve/courseUpdateVar")
public class CourseUpdateVarController extends BaseController
{
    private String prefix = "courseUpdateVar";

	@Autowired
	private ICourseUpdateVarService courseUpdateVarService;

	@RequiresPermissions("serve:courseUpdateVar:view")
	@GetMapping()
	public String courseUpdateVar()
	{
	    return prefix + "/courseUpdateVar";
	}

	/**
	 * 查询课件变量更新列表
	 */
	@RequiresPermissions("serve:courseUpdateVar:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo list(CourseUpdateVar courseUpdateVar, @PathVariable("courseId")Long courseId)
	{
		startPage();
		courseUpdateVar.setCourseId(courseId);
        List<CourseUpdateVar> list = courseUpdateVarService.selectCourseUpdateVarList(courseUpdateVar);
		return getDataTable(list);
	}


	/**
	 * 导出课件变量更新列表
	 */
	@RequiresPermissions("serve:courseUpdateVar:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseUpdateVar courseUpdateVar)
    {
    	List<CourseUpdateVar> list = courseUpdateVarService.selectCourseUpdateVarList(courseUpdateVar);
        ExcelUtil<CourseUpdateVar> util = new ExcelUtil<CourseUpdateVar>(CourseUpdateVar.class);
        return util.exportExcel(list, "courseUpdateVar");
    }

	/**
	 * 新增课件变量更新
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存课件变量更新
	 */
	@RequiresPermissions("serve:courseUpdateVar:add")
	@Log(title = "课件变量更新", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CourseUpdateVar courseUpdateVar)
	{
		return toAjax(courseUpdateVarService.insertCourseUpdateVar(courseUpdateVar));
	}

	/**
	 * 修改课件变量更新
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		CourseUpdateVar courseUpdateVar = courseUpdateVarService.selectCourseUpdateVarById(id);
		mmap.put("courseUpdateVar", courseUpdateVar);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存课件变量更新
	 */
	@RequiresPermissions("serve:courseUpdateVar:edit")
	@Log(title = "课件变量更新", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CourseUpdateVar courseUpdateVar)
	{
		return toAjax(courseUpdateVarService.updateCourseUpdateVar(courseUpdateVar));
	}

	/**
	 * 删除课件变量更新
	 */
	@RequiresPermissions("serve:courseUpdateVar:remove")
	@Log(title = "课件变量更新", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(courseUpdateVarService.deleteCourseUpdateVarByIds(ids));
	}

}
