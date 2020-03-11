package com.bytetcp.finalab.serve.courseTargetParam.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.courseTargetParam.domain.CourseTargetParam;
import com.bytetcp.finalab.serve.courseTargetParam.service.ICourseTargetParamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课件标的参数 信息操作处理
 *
 * @author finalab
 * @date 2019-05-03
 */
@Controller
@RequestMapping("/serve/courseTargetParam")
public class CourseTargetParamController extends BaseController
{
    private String prefix = "courseTargetParam";

	@Autowired
	private ICourseTargetParamService courseTargetParamService;

	@RequiresPermissions("serve:courseTargetParam:view")
	@GetMapping()
	public String courseTargetParam()
	{
	    return prefix + "/courseTargetParam";
	}

	/**
	 * 查询课件标的参数列表
	 */
	@RequiresPermissions("serve:courseTargetParam:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo list(CourseTargetParam courseTargetParam,@PathVariable("courseId") long courseId)
	{
		startPage();
		courseTargetParam.setCourseId(courseId);
        List<CourseTargetParam> list = courseTargetParamService.selectCourseTargetParamList(courseTargetParam);
		return getDataTable(list);
	}


	/**
	 * 导出课件标的参数列表
	 */
	@RequiresPermissions("serve:courseTargetParam:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseTargetParam courseTargetParam)
    {
    	List<CourseTargetParam> list = courseTargetParamService.selectCourseTargetParamList(courseTargetParam);
        ExcelUtil<CourseTargetParam> util = new ExcelUtil<CourseTargetParam>(CourseTargetParam.class);
        return util.exportExcel(list, "courseTargetParam");
    }

	/**
	 * 新增课件标的参数
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存课件标的参数
	 */
	@RequiresPermissions("serve:courseTargetParam:add")
	@Log(title = "课件标的参数", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CourseTargetParam courseTargetParam)
	{
		return toAjax(courseTargetParamService.insertCourseTargetParam(courseTargetParam));
	}

	/**
	 * 修改课件标的参数
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		CourseTargetParam courseTargetParam = courseTargetParamService.selectCourseTargetParamById(id);
		mmap.put("courseTargetParam", courseTargetParam);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存课件标的参数
	 */
	@RequiresPermissions("serve:courseTargetParam:edit")
	@Log(title = "课件标的参数", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CourseTargetParam courseTargetParam)
	{
		return toAjax(courseTargetParamService.updateCourseTargetParam(courseTargetParam));
	}

	/**
	 * 删除课件标的参数
	 */
	@RequiresPermissions("serve:courseTargetParam:remove")
	@Log(title = "课件标的参数", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(courseTargetParamService.deleteCourseTargetParamByIds(ids));
	}

}
