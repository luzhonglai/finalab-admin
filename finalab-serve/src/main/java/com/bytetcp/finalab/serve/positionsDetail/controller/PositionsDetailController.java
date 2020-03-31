package com.bytetcp.finalab.serve.positionsDetail.controller;

import java.util.ArrayList;
import java.util.List;

import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.service.ICourseService;
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
import com.bytetcp.finalab.serve.positionsDetail.domain.PositionsDetail;
import com.bytetcp.finalab.serve.positionsDetail.service.IPositionsDetailService;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;

/**
 * 用户持仓明细
 *
 * @author finalab
 * @date 2019-08-07
 */
@Controller
@RequestMapping("/serve/positionsDetail")
public class PositionsDetailController extends BaseController {
    private String prefix = "positionsDetail";

    @Autowired
    ICourseService courseService;

    @Autowired
    private IPositionsDetailService positionsDetailService;

    @RequiresPermissions("serve:positionsDetail:view")
    @GetMapping()
    public String positionsDetail() {
        return prefix + "/positionsDetail";
    }

    /**
     * 用户持仓明细
     */
    @RequiresPermissions("serve:positionsDetail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PositionsDetail positionsDetail) {
        startPage();
        List<PositionsDetail> list = positionsDetailService.selectPositionsDetailList(positionsDetail);
        return getDataTable(list);
    }

    /**
     * 课件详情页面成交明细
     */
    @RequiresPermissions("serve:positionsDetail:list")
    @PostMapping("/listForCourse")
    @ResponseBody
    public TableDataInfo listForCourse(Long courseId, String traderName, Integer timeLine, Integer pageOffset, Integer pageLimit, String sort, String order) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        List<PositionsDetail> list = new ArrayList<>(0);

        if (instanceRunRecode != null) {
            PositionsDetail positionsDetail = new PositionsDetail();
            positionsDetail.setTraderName(traderName);
            positionsDetail.setTimeLine(timeLine);
            positionsDetail.setInstanceId(instanceRunRecode.getInstanceId());
            divPage(pageOffset, pageLimit, sort, order);
            list = positionsDetailService.selectPositionsDetailListForCourse(positionsDetail);
        }

        return getDataTable(list);
    }

    /**
     * 课件详情页面成交明细 导出
     */
    @RequiresPermissions("serve:positionsDetail:list")
    @PostMapping("/exportForCourse")
    @ResponseBody
    public AjaxResult exportForCourse(Long courseId, String traderName, Integer timeLine, String sort, String order) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        List<PositionsDetail> list = new ArrayList<>(0);
        if (instanceRunRecode != null) {
            PositionsDetail positionsDetail = PositionsDetail.builder()
                                                             .traderName(traderName)
                                                             .timeLine(timeLine)
                                                             .instanceId(instanceRunRecode.getInstanceId()).build();
            divPageByOrder(sort, order);
            list = positionsDetailService.selectPositionsDetailListForCourse(positionsDetail);
            for (PositionsDetail p: list) {
                p.fixed();
                p.setCostAndAvg();
            }
        }
        ExcelUtil<PositionsDetail> util = new ExcelUtil<PositionsDetail>(PositionsDetail.class);
        return util.exportExcel(list, "DealDetail");
    }


    /**
     * 导出用户持仓明细
     */
    @RequiresPermissions("serve:positionsDetail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PositionsDetail positionsDetail) {
        List<PositionsDetail> list = positionsDetailService.selectPositionsDetailList(positionsDetail);
        ExcelUtil<PositionsDetail> util = new ExcelUtil<PositionsDetail>(PositionsDetail.class);
        return util.exportExcel(list, "positionsDetail");
    }

    /**
     * 新增用户持仓明细
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户持仓明细
     */
    @RequiresPermissions("serve:positionsDetail:add")
    @Log(title = "持仓明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PositionsDetail positionsDetail) {
        return toAjax(positionsDetailService.insertPositionsDetail(positionsDetail));
    }

    /**
     * 修改用户持仓明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        PositionsDetail positionsDetail = positionsDetailService.selectPositionsDetailById(id);
        mmap.put("positionsDetail", positionsDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户持仓明细
     */
    @RequiresPermissions("serve:positionsDetail:edit")
    @Log(title = "持仓明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PositionsDetail positionsDetail) {
        return toAjax(positionsDetailService.updatePositionsDetail(positionsDetail));
    }

    /**
     * 删除用户持仓明细
     */
    @RequiresPermissions("serve:positionsDetail:remove")
    @Log(title = "持仓明细", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(positionsDetailService.deletePositionsDetailByIds(ids));
    }

}
