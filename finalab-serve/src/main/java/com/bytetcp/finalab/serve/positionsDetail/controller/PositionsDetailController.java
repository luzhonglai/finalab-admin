package com.bytetcp.finalab.serve.positionsDetail.controller;

import java.util.ArrayList;
import java.util.List;

import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExpExcelUtil;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.userMoneyDetail.domain.UserMoneyDetailInCourse;
import com.bytetcp.finalab.serve.userMoneyDetail.service.IUserMoneyDetailService;
import com.bytetcp.finalab.serve.userNews.domain.UserNewsDetail;
import com.bytetcp.finalab.serve.userNews.mapper.UserNewsDetailMapper;
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

    @Autowired
    private IUserMoneyDetailService userMoneyDetailService;

    @Autowired
    private UserNewsDetailMapper userNewsDetailMapper;

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
        List<UserMoneyDetailInCourse> list2 = new ArrayList<>();
        List<UserNewsDetail> list3 = new ArrayList<>();
        List<UserMoneyDetailInCourse> moneyDetaillist = new ArrayList<>(0);
        if (instanceRunRecode != null) {
            PositionsDetail positionsDetail = PositionsDetail.builder()
                    .traderName(traderName)
                    .timeLine(timeLine)
                    .instanceId(instanceRunRecode.getInstanceId()).build();
            divPageByOrder(sort, order);
            list = positionsDetailService.selectPositionsDetailListForCourse(positionsDetail);
            moneyDetaillist = userMoneyDetailService.profitInCourseDetail(instanceRunRecode.getInstanceId());
            list2 = userMoneyDetailService.profitInCourseDetailExt(moneyDetaillist, instanceRunRecode.getInstanceId());
            //新闻数据  先清除重复数据
            userNewsDetailMapper.deleteRepeat();
            list3 = userNewsDetailMapper.selectByInstanceId(instanceRunRecode.getInstanceId());
            for (PositionsDetail p : list) {
                p.fixed();
                p.setCostAndAvg();
            }
            for (UserMoneyDetailInCourse m : list2) {
                m.setDealPrice(m.getTotalPrice().subtract(m.getTransactionFee().abs()).subtract(m.getTotalFine()));
            }
        }
        //数据
        List<String[]> dataset = new ArrayList<String[]>();
        for (int i = 0; i < list.size(); i++) {
            String[] arr = new String[9];
            arr[0] = list.get(i).getTraderId() == null ? "" : list.get(i).getTraderId().toString();
            arr[1] = list.get(i).getThePeriod() == null ? "" : list.get(i).getThePeriod().toString();
            arr[2] = list.get(i).getTimeLine() == null ? "" : list.get(i).getTimeLine().toString();
            arr[3] = list.get(i).getTraderName() == null ? "" : list.get(i).getTraderName().toString();
            arr[4] = list.get(i).getStockName() == null ? "" : list.get(i).getStockName().toString();
            arr[5] = list.get(i).getDealPrice() == null ? "" : list.get(i).getDealPrice().toString();
            arr[6] = list.get(i).getDealQuantity() == null ? "" : list.get(i).getDealQuantity().toString();
            arr[7] = list.get(i).getCost() == null ? "" : list.get(i).getCost().toString();
            arr[8] = list.get(i).getTradeType() == null ? "" : list.get(i).getTradeType().toString();
            dataset.add(arr);
        }
        //数据
        List<String[]> dataset2 = new ArrayList<String[]>();
        for (int i = 0; i < list2.size(); i++) {
            String[] arr2 = new String[7];
            arr2[0] = list2.get(i).getTraderId() == null ? "" : list2.get(i).getTraderId().toString();
            arr2[1] = list2.get(i).getTraderName() == null ? "" : list2.get(i).getTraderName().toString();
            arr2[2] = list2.get(i).getTotalPrice() == null ? "" : list2.get(i).getTotalPrice().toString();
            arr2[3] = list2.get(i).getUnrealizedProfitandLoss() == null ? "" : list2.get(i).getUnrealizedProfitandLoss().toString();
            arr2[4] = list2.get(i).getTransactionFee() == null ? "" : list2.get(i).getTransactionFee().toString();
            arr2[5] = list2.get(i).getTotalFine() == null ? "" : list2.get(i).getTotalFine().toString();
            arr2[6] = list2.get(i).getDealPrice() == null ? "" : list2.get(i).getDealPrice().toString();
            if(arr2[3].equals("0E-8")){
                arr2[3] = "0";
            }
            if(arr2[5].equals("0E-8")){
                arr2[5] = "0";
            }
            dataset2.add(arr2);
        }
        //数据
        List<String[]> dataset3 = new ArrayList<String[]>();
        for (int i = 0; i < list3.size(); i++) {
            String[] arr3 = new String[4];
            arr3[0] = list3.get(i).getUserId() == null ? "" : list3.get(i).getUserId().toString();
            arr3[1] = list3.get(i).getUserName() == null ? "" : list3.get(i).getUserName().toString();
            arr3[2] = list3.get(i).getUserNews() == null ? "" : list3.get(i).getUserNews().toString();
            arr3[3] = list3.get(i).getTimeNum() == null ? "" : list3.get(i).getTimeNum().toString();
            dataset3.add(arr3);
        }
        return ExpExcelUtil.expExcel(dataset, dataset2,dataset3);
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
