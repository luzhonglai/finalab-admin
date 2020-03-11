package com.bytetcp.finalab.serve.courseTradingTarget.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.courseTradingTarget.domain.CourseTradingTarget;
import com.bytetcp.finalab.serve.courseTradingTarget.service.ICourseTradingTargetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标的 信息操作处理
 *
 * @author finalab
 * @date 2019-05-03
 */
@Controller
@RequestMapping("/serve/courseTradingTarget")
public class CourseTradingTargetController extends BaseController
{
    private String prefix = "courseTradingTarget";

	@Autowired
	private ICourseTradingTargetService courseTradingTargetService;

	@RequiresPermissions("serve:courseTradingTarget:view")
	@GetMapping()
	public String courseTradingTarget()
	{
	    return prefix + "/courseTradingTarget";
	}

	/**
	 * 查询标的列表
	 */
	@RequiresPermissions("serve:courseTradingTarget:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo list(CourseTradingTarget courseTradingTarget,@PathVariable("courseId") long courseId)
	{
		courseTradingTarget.setCourseId(courseId);
		startPage();
        List<CourseTradingTarget> list = courseTradingTargetService.selectCourseTradingTargetList(courseTradingTarget);
		return getDataTable(list);
	}


	/**
	 * 导出标的列表
	 */
	@RequiresPermissions("serve:courseTradingTarget:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseTradingTarget courseTradingTarget)
    {
    	List<CourseTradingTarget> list = courseTradingTargetService.selectCourseTradingTargetList(courseTradingTarget);
        ExcelUtil<CourseTradingTarget> util = new ExcelUtil<CourseTradingTarget>(CourseTradingTarget.class);
        return util.exportExcel(list, "courseTradingTarget");
    }

	/**
	 * 新增标的
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存标的
	 */
	@RequiresPermissions("serve:courseTradingTarget:add")
	@Log(title = "标的", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CourseTradingTarget courseTradingTarget)
	{
		return toAjax(courseTradingTargetService.insertCourseTradingTarget(courseTradingTarget));
	}

	/**
	 * 修改标的
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		CourseTradingTarget courseTradingTarget = courseTradingTargetService.selectCourseTradingTargetById(id);
		mmap.put("courseTradingTarget", courseTradingTarget);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存标的
	 */
	@RequiresPermissions("serve:courseTradingTarget:edit")
	@Log(title = "标的", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CourseTradingTarget courseTradingTarget)
	{
		return toAjax(courseTradingTargetService.updateCourseTradingTarget(courseTradingTarget));
	}

	/**
	 * 删除标的
	 */
	@RequiresPermissions("serve:courseTradingTarget:remove")
	@Log(title = "标的", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(courseTradingTargetService.deleteCourseTradingTargetByIds(ids));
	}

}
