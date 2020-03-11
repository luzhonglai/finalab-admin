package com.bytetcp.finalab.serve.updateVar.controller;

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
import com.bytetcp.finalab.serve.updateVar.domain.UpdateVar;
import com.bytetcp.finalab.serve.updateVar.service.IUpdateVarService;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;

/**
 * 变量更新 信息操作处理
 *
 * @author finalab
 * @date 2019-08-17
 */
@Controller
@RequestMapping("/serve/updateVar")
public class UpdateVarController extends BaseController
{
    private String prefix = "updateVar";

	@Autowired
	private IUpdateVarService updateVarService;

	@RequiresPermissions("serve:updateVar:view")
	@GetMapping()
	public String updateVar()
	{
	    return prefix + "/updateVar";
	}

	/**
	 * 查询变量更新列表
	 */
	@RequiresPermissions("serve:updateVar:list")
	@PostMapping("/list/{caseId}")
	@ResponseBody
	public TableDataInfo list(UpdateVar updateVar, @PathVariable("caseId")Long caseId)
	{
		startPage();
		updateVar.setCaseId(caseId);
        List<UpdateVar> list = updateVarService.selectUpdateVarList(updateVar);
		return getDataTable(list);
	}


	/**
	 * 导出变量更新列表
	 */
	@RequiresPermissions("serve:updateVar:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UpdateVar updateVar)
    {
    	List<UpdateVar> list = updateVarService.selectUpdateVarList(updateVar);
        ExcelUtil<UpdateVar> util = new ExcelUtil<UpdateVar>(UpdateVar.class);
        return util.exportExcel(list, "updateVar");
    }

	/**
	 * 新增变量更新
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存变量更新
	 */
	@RequiresPermissions("serve:updateVar:add")
	@Log(title = "变量更新", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(UpdateVar updateVar)
	{
		return toAjax(updateVarService.insertUpdateVar(updateVar));
	}

	/**
	 * 修改变量更新
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		UpdateVar updateVar = updateVarService.selectUpdateVarById(id);
		mmap.put("updateVar", updateVar);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存变量更新
	 */
	@RequiresPermissions("serve:updateVar:edit")
	@Log(title = "变量更新", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(UpdateVar updateVar)
	{
		return toAjax(updateVarService.updateUpdateVar(updateVar));
	}

	/**
	 * 删除变量更新
	 */
	@RequiresPermissions("serve:updateVar:remove")
	@Log(title = "变量更新", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(updateVarService.deleteUpdateVarByIds(ids));
	}

}
