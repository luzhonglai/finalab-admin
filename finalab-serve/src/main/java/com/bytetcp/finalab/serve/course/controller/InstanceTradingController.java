package com.bytetcp.finalab.serve.course.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.domain.InstanceTrading;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.course.service.IInstanceTradingService;
import com.bytetcp.finalab.serve.courseTargetParam.service.ICourseTargetParamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易明细 信息操作处理
 *
 * @author finalab
 * @date 2019-05-22
 */
@Controller
@RequestMapping("/serve/instanceTrading")
public class InstanceTradingController extends BaseController {
    private String prefix = "instanceTrading";

    @Autowired
    private IInstanceTradingService instanceTradingService;

    @Autowired
    private ICourseTargetParamService courseTargetParamService;

    @Autowired
    private ICourseService iCourseService;

    @RequiresPermissions("serve:instanceTrading:view")
    @GetMapping()
    public String instanceTrading() {
        return prefix + "/instanceTrading";
    }

    /**
     * 查询交易明细列表
     */
    @RequiresPermissions("serve:instanceTrading:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(InstanceTrading instanceTrading) {
        startPage();
        List<InstanceTrading> list = instanceTradingService.selectInstanceTradingList(instanceTrading);
        return getDataTable(list);
    }

    @PostMapping("/TradingRecord/{courseId}")
    @ResponseBody
    public TableDataInfo showTradingRecord(InstanceTrading instanceTrading, @PathVariable("courseId") long courseId) {

        instanceTrading.setCourseId(courseId);
        InstanceRunRecode selectInstance = iCourseService.selectInstance(courseId);
        if (selectInstance != null){
            instanceTrading.setInstanceId(selectInstance.getInstanceId());
            startPage();
            List<InstanceTrading> list = instanceTradingService.selectInstanceTradingList(instanceTrading);
            return getDataTable(list);
        }
        else {
            startPage();
            List<InstanceTrading> list1 = new ArrayList<>();
            return getDataTable(list1);
        }

    }


    @RequiresPermissions("serve:instanceTrading:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InstanceTrading instanceTrading) {
        List<InstanceTrading> list = instanceTradingService.selectInstanceTradingList(instanceTrading);
        ExcelUtil<InstanceTrading> util = new ExcelUtil<InstanceTrading>(InstanceTrading.class);
        return util.exportExcel(list, "instanceTrading");
    }

    /**
     * 新增交易明细
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存交易明细
     */
    @RequiresPermissions("serve:instanceTrading:add")
    @Log(title = "交易明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(InstanceTrading instanceTrading) {
        return toAjax(instanceTradingService.insertInstanceTrading(instanceTrading));
    }

    /**
     * 修改交易明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        InstanceTrading instanceTrading = instanceTradingService.selectInstanceTradingById(id);
        mmap.put("instanceTrading", instanceTrading);
        return prefix + "/edit";
    }

    /**
     * 修改保存交易明细
     */
    @RequiresPermissions("serve:instanceTrading:edit")
    @Log(title = "交易明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(InstanceTrading instanceTrading) {
        return toAjax(instanceTradingService.updateInstanceTrading(instanceTrading));
    }

    /**
     * 删除交易明细
     */
    @RequiresPermissions("serve:instanceTrading:remove")
    @Log(title = "交易明细", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(instanceTradingService.deleteInstanceTradingByIds(ids));
    }


}
