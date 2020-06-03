package com.bytetcp.finalab.serve.userMoneyDetail.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.userMoneyDetail.domain.UserMoneyDetail;
import com.bytetcp.finalab.serve.userMoneyDetail.domain.UserMoneyDetailInCourse;
import com.bytetcp.finalab.serve.userMoneyDetail.service.IUserMoneyDetailService;
import com.github.pagehelper.PageHelper;
import java.util.Collections;
import java.util.Comparator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户金额明细 信息操作处理
 *
 * @author finalab
 * @date 2019-08-02
 */
@Controller
@RequestMapping("/serve/userMoneyDetail")
public class UserMoneyDetailController extends BaseController {
    private String prefix = "userMoneyDetail";

    @Autowired
    private IUserMoneyDetailService userMoneyDetailService;

    @Autowired
    private ICourseService courseService;

    @GetMapping("/allDealDetail/{courseId}")
    public String allDealDetail(@PathVariable("courseId") Long courseId, ModelMap modelMap) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        modelMap.put("traderId", getUserId());
        modelMap.put("courseId", courseId);
        modelMap.put("instanceId", instanceRunRecode == null ? null : instanceRunRecode.getInstanceId());
        return prefix + "/allDealDetail";
    }

    @PostMapping("/getLastLoopDetail")
    @ResponseBody
    public TableDataInfo getLastloopDetail(UserMoneyDetail userMoneyDetail,Integer pageOffset, Integer pageLimit, String sort, String order) {
        divPageByOrder(sort, order);
        List<UserMoneyDetail> list = userMoneyDetailService.getLastLoopDetail(userMoneyDetail);
        return getDataTable(list);
    }

    @PostMapping("/profitInCourse")
    @ResponseBody
    public TableDataInfo profitInCourseDetail(@RequestParam("courseId") Long courseId, Integer pageOffset, Integer pageLimit, String sort, String order) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        List<UserMoneyDetailInCourse> list = new ArrayList<>();
        List<UserMoneyDetailInCourse> userMoneyDetailInCourses = new ArrayList<>();
        if (instanceRunRecode != null) {
            divPage(pageOffset, pageLimit, sort, order);
            list = userMoneyDetailService.profitInCourseDetail(instanceRunRecode.getInstanceId());
            userMoneyDetailInCourses = userMoneyDetailService.profitInCourseDetailExt(list, instanceRunRecode.getInstanceId());
        }
        for (UserMoneyDetailInCourse m : userMoneyDetailInCourses) {
            m.setDealPrice(m.getTotalPrice());
            m.setTotalPrice(m.getTotalPrice().subtract(m.getTransactionFee()).subtract(m.getTotalFine()));
        }
        return getDataTable(userMoneyDetailInCourses);
    }

    @RequiresPermissions("serve:userMoneyDetail:view")
    @GetMapping()
    public String userMoneyDetail() {
        return prefix + "/userMoneyDetail";
    }

    /**
     * 查询用户金额明细列表
     */
    @RequiresPermissions("serve:userMoneyDetail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserMoneyDetail userMoneyDetail, Integer pageOffset, Integer pageLimit, String sort, String order) {
        divPage(pageOffset, pageLimit, sort, order);
        List<UserMoneyDetail> list = userMoneyDetailService.selectUserMoneyDetailList(userMoneyDetail);
        Collections.sort(list, new Comparator<UserMoneyDetail>() {
            @Override
            public int compare(UserMoneyDetail o1, UserMoneyDetail o2) {
                return o2.getId().intValue() - o1.getId().intValue(); //降序
            }
        });
        return getDataTable(list);
    }


    /**
     * 导出用户金额明细列表
     */
    @RequiresPermissions("serve:userMoneyDetail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UserMoneyDetail userMoneyDetail) {
        List<UserMoneyDetail> list = userMoneyDetailService.selectUserMoneyDetailList(userMoneyDetail);
        ExcelUtil<UserMoneyDetail> util = new ExcelUtil<UserMoneyDetail>(UserMoneyDetail.class);
        return util.exportExcel(list, "userMoneyDetail");
    }

    /**
     * 新增用户金额明细
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户金额明细
     */
    @RequiresPermissions("serve:userMoneyDetail:add")
    @Log(title = "用户金额明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UserMoneyDetail userMoneyDetail) {
        return toAjax(userMoneyDetailService.insertUserMoneyDetail(userMoneyDetail));
    }

    /**
     * 修改用户金额明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        UserMoneyDetail userMoneyDetail = userMoneyDetailService.selectUserMoneyDetailById(id);
        mmap.put("userMoneyDetail", userMoneyDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户金额明细
     */
    @RequiresPermissions("serve:userMoneyDetail:edit")
    @Log(title = "用户金额明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UserMoneyDetail userMoneyDetail) {
        return toAjax(userMoneyDetailService.updateUserMoneyDetail(userMoneyDetail));
    }

    /**
     * 删除用户金额明细
     */
    @RequiresPermissions("serve:userMoneyDetail:remove")
    @Log(title = "用户金额明细", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(userMoneyDetailService.deleteUserMoneyDetailByIds(ids));
    }

}
