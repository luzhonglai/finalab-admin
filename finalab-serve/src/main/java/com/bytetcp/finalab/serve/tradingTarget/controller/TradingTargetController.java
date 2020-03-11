package com.bytetcp.finalab.serve.tradingTarget.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.cases.service.ICasesService;
import com.bytetcp.finalab.serve.tradingTarget.domain.TradingTarget;
import com.bytetcp.finalab.serve.tradingTarget.service.ITradingTargetService;
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
 * @date 2019-05-01
 */
@Controller
@RequestMapping("/serve/tradingTarget")
public class TradingTargetController extends BaseController
{
    private String prefix = "tradingTarget";

	@Autowired
	private ITradingTargetService tradingTargetService;

	@Autowired
	private ICasesService iCasesService;

	@RequiresPermissions("serve:tradingTarget:view")
	@GetMapping()
	public String tradingTarget()
	{
	    return prefix + "/tradingTarget";
	}

	/**
	 * 查询标的列表
	 */
	@RequiresPermissions("serve:tradingTarget:list")
	@PostMapping("/list/{caseId}")
	@ResponseBody
	public TableDataInfo list(TradingTarget tradingTarget,@PathVariable("caseId") long caseId)
	{
		tradingTarget.setCaseId(caseId);
		startPage();
        List<TradingTarget> list = tradingTargetService.selectTradingTargetList(tradingTarget);
		return getDataTable(list);
	}


	/**
	 * 导出标的列表
	 */
	@RequiresPermissions("serve:tradingTarget:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TradingTarget tradingTarget)
    {
    	List<TradingTarget> list = tradingTargetService.selectTradingTargetList(tradingTarget);
        ExcelUtil<TradingTarget> util = new ExcelUtil<TradingTarget>(TradingTarget.class);
        return util.exportExcel(list, "tradingTarget");
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
	@RequiresPermissions("serve:tradingTarget:add")
	@Log(title = "标的", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TradingTarget tradingTarget)
	{
		return toAjax(tradingTargetService.insertTradingTarget(tradingTarget));
	}

	/**
	 * 修改标的
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		TradingTarget tradingTarget = tradingTargetService.selectTradingTargetById(id);
		mmap.put("tradingTarget", tradingTarget);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存标的
	 */
	@RequiresPermissions("serve:tradingTarget:edit")
	@Log(title = "标的", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TradingTarget tradingTarget)
	{
		return toAjax(tradingTargetService.updateTradingTarget(tradingTarget));
	}

	/**
	 * 删除标的
	 */
	@RequiresPermissions("serve:tradingTarget:remove")
	@Log(title = "标的", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(tradingTargetService.deleteTradingTargetByIds(ids));
	}

}
