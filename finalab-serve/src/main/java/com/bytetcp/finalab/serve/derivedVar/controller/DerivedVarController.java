package com.bytetcp.finalab.serve.derivedVar.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.derivedVar.domain.DerivedVar;
import com.bytetcp.finalab.serve.derivedVar.service.IDerivedVarService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 衍生变量 信息操作处理
 *
 * @author finalab
 * @date 2019-09-24
 */
@Controller
@RequestMapping("/serve/derivedVar")
public class DerivedVarController extends BaseController
{
    private String prefix = "derivedVar";

	@Autowired
	private IDerivedVarService derivedVarService;

	@RequiresPermissions("serve:derivedVar:view")
	@GetMapping()
	public String derivedVar()
	{
	    return prefix + "/derivedVar";
	}

	/**
	 * 查询衍生变量列表
	 */
	@RequiresPermissions("serve:derivedVar:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(DerivedVar derivedVar)
	{
		startPage();
        List<DerivedVar> list = derivedVarService.selectDerivedVarList(derivedVar);
		return getDataTable(list);
	}

	@PostMapping("/listForCase/{caseId}")
	@ResponseBody
	public TableDataInfo listForCase(@PathVariable("caseId") Long caseId) {
		startPage();
		DerivedVar derivedVar = new DerivedVar();
		derivedVar.setCaseId(caseId);
		List<DerivedVar> list = derivedVarService.selectDerivedVarList(derivedVar);
		return getDataTable(list);
	}

	/**
	 * 导出衍生变量列表
	 */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DerivedVar derivedVar)
    {
    	List<DerivedVar> list = derivedVarService.selectDerivedVarList(derivedVar);
        ExcelUtil<DerivedVar> util = new ExcelUtil<DerivedVar>(DerivedVar.class);
        return util.exportExcel(list, "derivedVar");
    }

	/**
	 * 新增衍生变量
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存衍生变量
	 */
	@Log(title = "衍生变量", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(DerivedVar derivedVar)
	{
		return toAjax(derivedVarService.insertDerivedVar(derivedVar));
	}

	/**
	 * 修改衍生变量
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		DerivedVar derivedVar = derivedVarService.selectDerivedVarById(id);
		mmap.put("derivedVar", derivedVar);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存衍生变量
	 */
	@Log(title = "衍生变量", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(DerivedVar derivedVar)
	{
		return toAjax(derivedVarService.updateDerivedVar(derivedVar));
	}

	/**
	 * 删除衍生变量
	 */
	@Log(title = "衍生变量", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(derivedVarService.deleteDerivedVarByIds(ids));
	}

	@PostMapping("/fixColumns")
	@ResponseBody
	public List<String> getAllDerivedNames(DerivedVar derivedVar) {
		List<String> tradeNames = derivedVarService.fixColumns(derivedVar);
		if (!CollectionUtils.isEmpty(tradeNames)) {
			tradeNames.add(0, "TargetName");
		}
		return tradeNames;
	}
}
