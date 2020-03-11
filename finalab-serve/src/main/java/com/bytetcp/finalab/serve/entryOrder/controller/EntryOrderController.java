package com.bytetcp.finalab.serve.entryOrder.controller;

import java.util.List;

import com.bytetcp.finalab.common.base.HttpResult;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.serve.entryOrder.domain.EntryOrder;
import com.bytetcp.finalab.serve.entryOrder.service.IEntryOrderService;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;

/**
 * 挂单 信息操作处理
 *
 * @author finalab
 * @date 2019-07-28
 */
@Controller
@RequestMapping("/serve/entryOrder")
public class EntryOrderController extends BaseController {
    private String prefix = "entryOrder";

    @Autowired
    private IEntryOrderService entryOrderService;

    @RequiresPermissions("serve:entryOrder:view")
    @GetMapping()
    public String entryOrder() {
        return prefix + "/entryOrder";
    }

    /**
     * 查询挂单列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(EntryOrder entryOrder, Integer pageOffset, Integer pageLimit) {
        PageHelper.offsetPage(pageOffset,pageLimit);
        List<EntryOrder> list = entryOrderService.selectEntryOrderList(entryOrder);
        return getDataTable(list);
    }


    /**
     * 导出挂单列表
     */
    @RequiresPermissions("serve:entryOrder:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EntryOrder entryOrder) {
        List<EntryOrder> list = entryOrderService.selectEntryOrderList(entryOrder);
        ExcelUtil<EntryOrder> util = new ExcelUtil<EntryOrder>(EntryOrder.class);
        return util.exportExcel(list, "entryOrder");
    }

    /**
     * 新增挂单
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存挂单
     */
    @RequiresPermissions("serve:entryOrder:add")
    @Log(title = "挂单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(EntryOrder entryOrder) {
        return toAjax(entryOrderService.insertEntryOrder(entryOrder));
    }

    /**
     * 修改挂单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        EntryOrder entryOrder = entryOrderService.selectEntryOrderById(id);
        mmap.put("entryOrder", entryOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存挂单
     */
    @RequiresPermissions("serve:entryOrder:edit")
    @Log(title = "挂单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(EntryOrder entryOrder) {
        return toAjax(entryOrderService.updateEntryOrder(entryOrder));
    }

    /**
     * 删除挂单
     */
    @RequiresPermissions("serve:entryOrder:remove")
    @Log(title = "挂单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(entryOrderService.deleteEntryOrderByIds(ids));
    }


    @PostMapping("/cancel")
    @ResponseBody
    public AjaxResult order(@RequestBody EntryOrder entryOrder) {
        entryOrder.setTraderId(getSysUser().getUserId());
        entryOrder.setTraderName(getSysUser().getLoginName());
        //执行下单
        HttpResult results = entryOrderService.sendHttp(entryOrder);
        //返回下单状态
        if (200 == results.getStatus()) {
            return AjaxResult.success().put("msg", results.getMsg()).put("code", results.getCode());
        } else {
            return AjaxResult.error().put("msg", results.getMsg()).put("code", results.getCode());
        }
    }
}
