package com.bytetcp.finalab.serve.courseLiquidation.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.courseLiquidation.domain.CourseLiquidation;
import com.bytetcp.finalab.serve.courseLiquidation.service.ICourseLiquidationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 清算 信息操作处理
 *
 * @author finalab
 * @date 2019-05-03
 */
@Controller
@RequestMapping("/serve/courseLiquidation")
public class CourseLiquidationController extends BaseController
{
    private String prefix = "courseLiquidation";

	@Autowired
	private ICourseLiquidationService courseLiquidationService;

	@RequiresPermissions("serve:courseLiquidation:view")
	@GetMapping()
	public String courseLiquidation()
	{
	    return prefix + "/courseLiquidation";
	}

	/**
	 * 查询清算列表
	 */
	@RequiresPermissions("serve:courseLiquidation:list")
	@PostMapping("/list/{courseId}")
	@ResponseBody
	public TableDataInfo list(CourseLiquidation courseLiquidation ,@PathVariable("courseId") Long courseId )
	{
		courseLiquidation.setCourseId(courseId);
		startPage();
        List<CourseLiquidation> list = courseLiquidationService.selectCourseLiquidationList(courseLiquidation);
		return getDataTable(list);
	}


	/**
	 * 导出清算列表
	 */
	@RequiresPermissions("serve:courseLiquidation:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseLiquidation courseLiquidation)
    {
    	List<CourseLiquidation> list = courseLiquidationService.selectCourseLiquidationList(courseLiquidation);
        ExcelUtil<CourseLiquidation> util = new ExcelUtil<CourseLiquidation>(CourseLiquidation.class);
        return util.exportExcel(list, "courseLiquidation");
    }

	/**
	 * 新增清算
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存清算
	 */
	@RequiresPermissions("serve:courseLiquidation:add")
	@Log(title = "清算", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CourseLiquidation courseLiquidation)
	{
		return toAjax(courseLiquidationService.insertCourseLiquidation(courseLiquidation));
	}

	/**
	 * 修改清算
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		CourseLiquidation courseLiquidation = courseLiquidationService.selectCourseLiquidationById(id);
		mmap.put("courseLiquidation", courseLiquidation);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存清算
	 */
	@RequiresPermissions("serve:courseLiquidation:edit")
	@Log(title = "清算", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CourseLiquidation courseLiquidation)
	{
		return toAjax(courseLiquidationService.updateCourseLiquidation(courseLiquidation));
	}

	/**
	 * 删除清算
	 */
	@RequiresPermissions("serve:courseLiquidation:remove")
	@Log(title = "清算", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(courseLiquidationService.deleteCourseLiquidationByIds(ids));
	}

}
