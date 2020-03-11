package com.bytetcp.finalab.serve.clearActionDetail.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
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
import com.bytetcp.finalab.serve.clearActionDetail.domain.ClearActionDetail;
import com.bytetcp.finalab.serve.clearActionDetail.service.IClearActionDetailService;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;

/**
 * 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏 信息操作处理
 *
 * @author finalab
 * @date 2019-08-02
 */
@Controller
@RequestMapping("/serve/clearActionDetail")
public class ClearActionDetailController extends BaseController {
    private String prefix = "clearActionDetail";

    @Autowired
    private IClearActionDetailService clearActionDetailService;

    @RequiresPermissions("serve:clearActionDetail:view")
    @GetMapping()
    public String clearActionDetail() {
        return prefix + "/clearActionDetail";
    }

    /**
     * 查询记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ClearActionDetail clearActionDetail, Integer pageOffset, Integer pageLimit) {
        PageHelper.offsetPage(pageOffset, pageLimit);
        List<ClearActionDetail> list = clearActionDetailService.selectClearActionDetailList(clearActionDetail);
        return getDataTable(list);
    }


    /**
     * 导出记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏列表
     */
    @RequiresPermissions("serve:clearActionDetail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ClearActionDetail clearActionDetail) {
        List<ClearActionDetail> list = clearActionDetailService.selectClearActionDetailList(clearActionDetail);
        ExcelUtil<ClearActionDetail> util = new ExcelUtil<ClearActionDetail>(ClearActionDetail.class);
        return util.exportExcel(list, "clearActionDetail");
    }

    /**
     * 新增记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     */
    @RequiresPermissions("serve:clearActionDetail:add")
    @Log(title = "记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ClearActionDetail clearActionDetail) {
        return toAjax(clearActionDetailService.insertClearActionDetail(clearActionDetail));
    }

    /**
     * 修改记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ClearActionDetail clearActionDetail = clearActionDetailService.selectClearActionDetailById(id);
        mmap.put("clearActionDetail", clearActionDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     */
    @RequiresPermissions("serve:clearActionDetail:edit")
    @Log(title = "记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ClearActionDetail clearActionDetail) {
        return toAjax(clearActionDetailService.updateClearActionDetail(clearActionDetail));
    }

    /**
     * 删除记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     */
    @RequiresPermissions("serve:clearActionDetail:remove")
    @Log(title = "记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(clearActionDetailService.deleteClearActionDetailByIds(ids));
    }


    @PostMapping("/countProfit")
    @ResponseBody
    public Double countProfit(ClearActionDetail clearActionDetail) {
        Double count = clearActionDetailService.countProfit(clearActionDetail);
        return count == null ? 0D : count;
    }
}
