package com.bytetcp.finalab.serve.priceMove.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.priceMove.domain.PriceMove;
import com.bytetcp.finalab.serve.priceMove.service.IPriceMoveService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 价格走势 信息操作处理
 *
 * @author finalab
 * @date 2019-05-02
 */
@Controller
@RequestMapping("/serve/priceMove")
public class PriceMoveController extends BaseController
{
    private String prefix = "priceMove";

	@Autowired
	private IPriceMoveService priceMoveService;

	@RequiresPermissions("serve:priceMove:view")
	@GetMapping()
	public String priceMove()
	{
	    return prefix + "/priceMove";
	}

	/**
	 * 查询价格走势列表
	 */
	@RequiresPermissions("serve:priceMove:list")
	@PostMapping("/list/{caseId}")
	@ResponseBody
	public TableDataInfo list(PriceMove priceMove,@PathVariable("caseId") long caseId)
	{
		startPage();
		priceMove.setCaseId(caseId);
        List<PriceMove> list = priceMoveService.selectPriceMoveList(priceMove);
		return getDataTable(list);
	}


	/**
	 * 导出价格走势列表
	 */
	@RequiresPermissions("serve:priceMove:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PriceMove priceMove)
    {
    	List<PriceMove> list = priceMoveService.selectPriceMoveList(priceMove);
        ExcelUtil<PriceMove> util = new ExcelUtil<PriceMove>(PriceMove.class);
        return util.exportExcel(list, "priceMove");
    }

	/**
	 * 新增价格走势
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}

	/**
	 * 新增保存价格走势
	 */
	@RequiresPermissions("serve:priceMove:add")
	@Log(title = "价格走势", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(PriceMove priceMove)
	{
		return toAjax(priceMoveService.insertPriceMove(priceMove));
	}

	/**
	 * 修改价格走势
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		PriceMove priceMove = priceMoveService.selectPriceMoveById(id);
		mmap.put("priceMove", priceMove);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存价格走势
	 */
	@RequiresPermissions("serve:priceMove:edit")
	@Log(title = "价格走势", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(PriceMove priceMove)
	{
		return toAjax(priceMoveService.updatePriceMove(priceMove));
	}

	/**
	 * 删除价格走势
	 */
	@RequiresPermissions("serve:priceMove:remove")
	@Log(title = "价格走势", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(priceMoveService.deletePriceMoveByIds(ids));
	}

}
