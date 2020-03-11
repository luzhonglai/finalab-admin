package com.bytetcp.finalab.serve.liquidation.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.liquidation.domain.Liquidation;
import com.bytetcp.finalab.serve.liquidation.service.ILiquidationService;
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
 * @date 2019-05-01
 */
@Controller
@RequestMapping("/serve/liquidation")
public class LiquidationController extends BaseController {
    private String prefix = "liquidation";

    @Autowired
    private ILiquidationService liquidationService;

    @RequiresPermissions("serve:liquidation:view")
    @GetMapping()
    public String liquidation() {
        return prefix + "/liquidation";
    }

    /**
     * 查询清算列表
     */
    @RequiresPermissions("serve:liquidation:list")
    @PostMapping("/list/{caseId}")
    @ResponseBody
    public TableDataInfo list(Liquidation liquidation, @PathVariable("caseId") long caseId) {
        liquidation.setCaseId(caseId);
        startPage();
        List<Liquidation> list = liquidationService.selectLiquidationList(liquidation);
        return getDataTable(list);
    }


    /**
     * 导出清算列表
     */
    @RequiresPermissions("serve:liquidation:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Liquidation liquidation) {
        List<Liquidation> list = liquidationService.selectLiquidationList(liquidation);
        ExcelUtil<Liquidation> util = new ExcelUtil<Liquidation>(Liquidation.class);
        return util.exportExcel(list, "liquidation");
    }

    /**
     * 新增清算
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存清算
     */
    @RequiresPermissions("serve:liquidation:add")
    @Log(title = "清算", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Liquidation liquidation) {
        return toAjax(liquidationService.insertLiquidation(liquidation));
    }

    /**
     * 修改清算
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Liquidation liquidation = liquidationService.selectLiquidationById(id);
        mmap.put("liquidation", liquidation);
        return prefix + "/edit";
    }

    /**
     * 修改保存清算
     */
    @RequiresPermissions("serve:liquidation:edit")
    @Log(title = "清算", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Liquidation liquidation) {
        return toAjax(liquidationService.updateLiquidation(liquidation));
    }

    /**
     * 删除清算
     */
    @RequiresPermissions("serve:liquidation:remove")
    @Log(title = "清算", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(liquidationService.deleteLiquidationByIds(ids));
    }

}
