package com.bytetcp.finalab.serve.tradingConstraint.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.tradingConstraint.domain.TradingConstraint;
import com.bytetcp.finalab.serve.tradingConstraint.service.ITradingConstraintService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 交易约束 信息操作处理
 *
 * @author finalab
 * @date 2019-05-01
 */
@Controller
@RequestMapping("/serve/tradingConstraint")
public class TradingConstraintController extends BaseController
{
    private String prefix = "tradingConstraint";

	@Autowired
	private ITradingConstraintService tradingConstraintService;

	@RequiresPermissions("serve:tradingConstraint:view")
	@GetMapping()
	public String tradingConstraint()
	{
	    return prefix + "/tradingConstraint";
	}

	/**
	 * 查询交易约束列表
	 */
	@RequiresPermissions("serve:tradingConstraint:list")
	@PostMapping("/list/{caseId}")
	@ResponseBody
	public TableDataInfo list(TradingConstraint tradingConstraint,@PathVariable("caseId") long caseId)
	{
		tradingConstraint.setCaseId(caseId);
		startPage();
        List<TradingConstraint> list = tradingConstraintService.selectTradingConstraintList(tradingConstraint);
		return getDataTable(list);
	}


	/**
	 * 导出交易约束列表
	 */
	@RequiresPermissions("serve:tradingConstraint:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TradingConstraint tradingConstraint)
    {
    	List<TradingConstraint> list = tradingConstraintService.selectTradingConstraintList(tradingConstraint);
        ExcelUtil<TradingConstraint> util = new ExcelUtil<TradingConstraint>(TradingConstraint.class);
        return util.exportExcel(list, "tradingConstraint");
    }

	/**
	 * 新增交易约束
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存交易约束
	 */
	@RequiresPermissions("serve:tradingConstraint:add")
	@Log(title = "交易约束", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TradingConstraint tradingConstraint)
	{
		return toAjax(tradingConstraintService.insertTradingConstraint(tradingConstraint));
	}

	/**
	 * 修改交易约束
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		TradingConstraint tradingConstraint = tradingConstraintService.selectTradingConstraintById(id);
		mmap.put("tradingConstraint", tradingConstraint);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存交易约束
	 */
	@RequiresPermissions("serve:tradingConstraint:edit")
	@Log(title = "交易约束", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TradingConstraint tradingConstraint)
	{
		return toAjax(tradingConstraintService.updateTradingConstraint(tradingConstraint));
	}

	/**
	 * 删除交易约束
	 */
	@RequiresPermissions("serve:tradingConstraint:remove")
	@Log(title = "交易约束", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(tradingConstraintService.deleteTradingConstraintByIds(ids));
	}

}
