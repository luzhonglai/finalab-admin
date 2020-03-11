package com.bytetcp.finalab.serve.targetParam.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.targetParam.domain.TargetParam;
import com.bytetcp.finalab.serve.targetParam.service.ITargetParamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标的参数 信息操作处理
 *
 * @author finalab
 * @date 2019-05-01
 */
@Controller
@RequestMapping("/serve/targetParam")
public class TargetParamController extends BaseController
{
    private String prefix = "targetParam";

	@Autowired
	private ITargetParamService targetParamService;

	@RequiresPermissions("serve:targetParam:view")
	@GetMapping()
	public String targetParam()
	{
	    return prefix + "/targetParam";
	}

	/**
	 * 查询标的参数列表
	 */
	@RequiresPermissions("serve:targetParam:list")
	@PostMapping("/list/{caseId}")
	@ResponseBody
	public TableDataInfo list(@PathVariable("caseId") Long caseId, TargetParam targetParam)
	{
		startPage();
		targetParam.setCaseId(caseId);
		List<TargetParam> list = targetParamService.selectTargetParamList(targetParam);
		return getDataTable(list);
	}


	/**
	 * 导出标的参数列表
	 */
	@RequiresPermissions("serve:targetParam:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TargetParam targetParam)
    {
    	List<TargetParam> list = targetParamService.selectTargetParamList(targetParam);
        ExcelUtil<TargetParam> util = new ExcelUtil<TargetParam>(TargetParam.class);
        return util.exportExcel(list, "targetParam");
    }

	/**
	 * 新增标的参数
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存标的参数
	 */
	@RequiresPermissions("serve:targetParam:add")
	@Log(title = "标的参数", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TargetParam targetParam)
	{
		return toAjax(targetParamService.insertTargetParam(targetParam));
	}

	/**
	 * 修改标的参数
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		TargetParam targetParam = targetParamService.selectTargetParamById(id);
		mmap.put("targetParam", targetParam);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存标的参数
	 */
	@RequiresPermissions("serve:targetParam:edit")
	@Log(title = "标的参数", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TargetParam targetParam)
	{
		return toAjax(targetParamService.updateTargetParam(targetParam));
	}

	/**
	 * 删除标的参数
	 */
	@RequiresPermissions("serve:targetParam:remove")
	@Log(title = "标的参数", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(targetParamService.deleteTargetParamByIds(ids));
	}

}
