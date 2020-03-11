package com.bytetcp.finalab.serve.courseTradingConstraint.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.courseTradingConstraint.domain.CourseTradingConstraint;
import com.bytetcp.finalab.serve.courseTradingConstraint.service.ICourseTradingConstraintService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课件交易约束 信息操作处理
 *
 * @author finalab
 * @date 2019-05-03
 */
@Controller
@RequestMapping("/serve/courseTradingConstraint")
public class CourseTradingConstraintController extends BaseController
{
    private String prefix = "courseTradingConstraint";

	@Autowired
	private ICourseTradingConstraintService courseTradingConstraintService;

	@RequiresPermissions("serve:courseTradingConstraint:view")
	@GetMapping()
	public String courseTradingConstraint()
	{
	    return prefix + "/courseTradingConstraint";
	}

	/**
	 * 查询课件交易约束列表
	 */
	@RequiresPermissions("serve:courseTradingConstraint:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo list(CourseTradingConstraint courseTradingConstraint,@PathVariable("courseId") long courseId)
	{
		courseTradingConstraint.setCourseId(courseId);
		startPage();
        List<CourseTradingConstraint> list = courseTradingConstraintService.selectCourseTradingConstraintList(courseTradingConstraint);
		return getDataTable(list);
	}


	/**
	 * 导出课件交易约束列表
	 */
	@RequiresPermissions("serve:courseTradingConstraint:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseTradingConstraint courseTradingConstraint)
    {
    	List<CourseTradingConstraint> list = courseTradingConstraintService.selectCourseTradingConstraintList(courseTradingConstraint);
        ExcelUtil<CourseTradingConstraint> util = new ExcelUtil<CourseTradingConstraint>(CourseTradingConstraint.class);
        return util.exportExcel(list, "courseTradingConstraint");
    }

	/**
	 * 新增课件交易约束
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存课件交易约束
	 */
	@RequiresPermissions("serve:courseTradingConstraint:add")
	@Log(title = "课件交易约束", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CourseTradingConstraint courseTradingConstraint)
	{
		return toAjax(courseTradingConstraintService.insertCourseTradingConstraint(courseTradingConstraint));
	}

	/**
	 * 修改课件交易约束
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		CourseTradingConstraint courseTradingConstraint = courseTradingConstraintService.selectCourseTradingConstraintById(id);
		mmap.put("courseTradingConstraint", courseTradingConstraint);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存课件交易约束
	 */
	@RequiresPermissions("serve:courseTradingConstraint:edit")
	@Log(title = "课件交易约束", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CourseTradingConstraint courseTradingConstraint)
	{
		return toAjax(courseTradingConstraintService.updateCourseTradingConstraint(courseTradingConstraint));
	}

	/**
	 * 删除课件交易约束
	 */
	@RequiresPermissions("serve:courseTradingConstraint:remove")
	@Log(title = "课件交易约束", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(courseTradingConstraintService.deleteCourseTradingConstraintByIds(ids));
	}

}
