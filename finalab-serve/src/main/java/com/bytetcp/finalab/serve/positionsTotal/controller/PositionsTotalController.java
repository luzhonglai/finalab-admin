package com.bytetcp.finalab.serve.positionsTotal.controller;

import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.base.HttpResult;
import com.bytetcp.finalab.common.base.ResultStatus;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.config.HttpMethod;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.domain.Order;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.courseTargetParam.domain.CourseTargetParam;
import com.bytetcp.finalab.serve.courseTradingConstraint.domain.CourseTradingConstraint;
import com.bytetcp.finalab.serve.courseTradingConstraint.service.ICourseTradingConstraintService;
import com.bytetcp.finalab.serve.positionsDetail.domain.PositionsDetail;
import com.bytetcp.finalab.serve.positionsDetail.service.IPositionsDetailService;
import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotal;
import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotalInCourse;
import com.bytetcp.finalab.serve.positionsTotal.service.IPositionsTotalService;
import com.bytetcp.finalab.serve.userMoneyDetail.domain.UserMoneyDetail;
import com.bytetcp.finalab.serve.userMoneyDetail.service.IUserMoneyDetailService;
import com.bytetcp.finalab.serve.userStock.domain.UserStock;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 持仓汇总，实例-用户-股票，一只股票一个持仓
 * 当平仓时，删除改记录 信息操作处理
 *
 * @author finalab
 * @date 2019-07-31
 */
@Controller
@RequestMapping("/serve/positionsTotal")
public class PositionsTotalController extends BaseController {
    private String prefix = "positionsTotal";

    @Value("${finalab.server.closePosition}")
    private String closePositionUrl;

    @Autowired
    HttpMethod httpMethod;

    @Autowired
    ICourseService courseService;

    @Autowired
    private IPositionsDetailService positionsDetailService;

    @Autowired
    private IPositionsTotalService positionsTotalService;

    @Autowired
    private ICourseTradingConstraintService courseTradingConstraintService;

    @Autowired
    private IUserMoneyDetailService userMoneyDetailService;

    @RequiresPermissions("serve:positionsTotal:view")
    @GetMapping()
    public String positionsTotal() {
        return prefix + "/positionsTotal";
    }

    @PostMapping("tranDetailInCourse")
    @ResponseBody
    public TableDataInfo listToCourse(@RequestParam("courseId")Long courseId) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        List<PositionsTotal> list = new ArrayList<>();
        if (instanceRunRecode != null) {
            startPage();
            list = positionsTotalService.selectPTListForCourse(instanceRunRecode.getInstanceId());
        }
        return getDataTable(list);
    }

    /**
     * 查询持仓汇总
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PositionsTotal positionsTotal, Integer pageOffset, Integer pageLimit) {
        PageHelper.offsetPage(pageOffset, pageLimit);
        boolean flag = false;
        //List<PositionsTotal> list = positionsTotalService.selectPositionsTotalList(positionsTotal);
        List<PositionsTotal> list = positionsTotalService.selectPositionsTotalListNew(positionsTotal);
        //期货和其他展示类型作区分
        for (PositionsTotal p:list) {
            if(p.getNowQuantity()==0){
                p.setTotalPrice(new BigDecimal(0));
            }
            if(p.getProfit().toString().equals("0E-8")){
                p.setProfit(new BigDecimal(0));
            }
            if(!flag){
                //判断是否为期货
                PositionsDetail PositionsDetail = new PositionsDetail();
                PositionsDetail.setInstanceId(p.getInstanceId());
                List<PositionsDetail> positionsDetails = positionsDetailService.selectPositionsDetailListForFinancialType(PositionsDetail);
                for (PositionsDetail position:positionsDetails) {
                    if(position.getFinancialType().equals("期货")){
                        flag = true;
                    }
                }
            }
        }
        if(flag){
            List<PositionsTotal> listMarkToMarket = positionsTotalService.selectPositionsTotalListWithoutZeroNewMarkToMarket(positionsTotal);
            list.clear();
            for (PositionsTotal p:listMarkToMarket) {
                if(p.getNowQuantity()==0){
                    p.setTotalPrice(new BigDecimal(0));
                }
                if(p.getProfit().toString().equals("0E-8")){
                    p.setProfit(new BigDecimal(0));
                }
                UserMoneyDetail userMoneyDetail = new UserMoneyDetail();
                userMoneyDetail.setLoopNum(positionsTotal.getLoopNum());
                userMoneyDetail.setInstanceId(positionsTotal.getInstanceId());
                userMoneyDetail.setCourseId(positionsTotal.getCourseId());
                userMoneyDetail.setTraderId(positionsTotal.getTraderId());
                userMoneyDetail.setStockId(positionsTotal.getStockId());
                List<UserMoneyDetail> userMoneyDetailList = userMoneyDetailService.selectUserMoneyDetailList(userMoneyDetail);
                BigDecimal profit = new BigDecimal(0);
                for (UserMoneyDetail user:userMoneyDetailList) {
                    if(p.getStockId().equals(user.getStockId())){
                        profit = profit.add(user.getCostPrice());
                    }
                }
                p.setProfit(profit);
                list.add(p);
            }
        }
        return getDataTable(list);
    }

    /**
     * 课件详情查询持仓量
     */
    @PostMapping("/listInCourse")
    @ResponseBody
    public TableDataInfo listInCourse(@RequestParam("courseId")Long courseId,Integer pageOffset, Integer pageLimit, String sort, String order) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        List<PositionsTotalInCourse> list = new ArrayList<>();
        if (instanceRunRecode != null) {
            divPage(pageOffset, pageLimit, sort, order);
            list = positionsTotalService.selectPositionsTotalListInCourse(instanceRunRecode.getInstanceId());
        }
        return getDataTable(list);
    }


    /**
     * 导出持仓汇总
     */
    @RequiresPermissions("serve:positionsTotal:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PositionsTotal positionsTotal) {
        List<PositionsTotal> list = positionsTotalService.selectPositionsTotalList(positionsTotal);
        ExcelUtil<PositionsTotal> util = new ExcelUtil<PositionsTotal>(PositionsTotal.class);
        return util.exportExcel(list, "positionsTotal");
    }

    /**
     * 跳转到新增持仓汇总
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存持仓汇总
     */
    @RequiresPermissions("serve:positionsTotal:add")
    @Log(title = "持仓汇总", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PositionsTotal positionsTotal) {
        return toAjax(positionsTotalService.insertPositionsTotal(positionsTotal));
    }

    /**
     * 修改持仓汇总
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        PositionsTotal positionsTotal = positionsTotalService.selectPositionsTotalById(id);
        mmap.put("positionsTotal", positionsTotal);
        return prefix + "/edit";
    }

    /**
     * 修改保存持仓汇总
     */
    @RequiresPermissions("serve:positionsTotal:edit")
    @Log(title = "持仓汇总-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PositionsTotal positionsTotal) {
        return toAjax(positionsTotalService.updatePositionsTotal(positionsTotal));
    }

    /**
     * 删除持仓汇总，实例-用户-股票，一只股票一个持仓
     * 当平仓时，删除改记录
     */
    @RequiresPermissions("serve:positionsTotal:remove")
    @Log(title = "持仓汇总", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(positionsTotalService.deletePositionsTotalByIds(ids));
    }

    @PostMapping("/closePosition")
    @ResponseBody
    public AjaxResult closePosition(Order order) {

        HttpResult send = httpMethod.send(order, closePositionUrl);
        if (send.getStatus() == 200) {
            if (send.getCode() != 0) {
                return AjaxResult.error(send.getMsg());
            }
            return new AjaxResult().toSuccess();
        }
        return AjaxResult.error("平仓失败");
    }

    @PostMapping("/refresh")
    @ResponseBody
    public AjaxResult getLastPositionsTotalByStockId(@RequestBody PositionsTotal positionsTotal) {
        positionsTotal.setTraderId(getUserId());
        PositionsTotal lastPt = positionsTotalService.findPositionTotal(positionsTotal);
        return lastPt == null ? AjaxResult.error(new PositionsTotal()) : AjaxResult.success(lastPt);
    }

    @PostMapping("/allQuantity")
    @ResponseBody
    public AjaxResult getAllQuantity(@RequestBody PositionsTotal param) {
        HashMap<String, Integer> quantityMap = new HashMap<>();
        List<PositionsTotal> pList = positionsTotalService.selectPositionsTotalList(param);
        for (PositionsTotal p: pList) {
            quantityMap.put(p.getStockId(), p.getNowQuantity());
        }
        return AjaxResult.success(quantityMap);
    }

    @PostMapping("/judgeConstraints")
    @ResponseBody
    public AjaxResult getJudgeConstraints(@RequestBody PositionsTotal positionsTotal) {
        boolean flag = true;
        CourseTradingConstraint courseTradingConstraint = new CourseTradingConstraint();
        courseTradingConstraint.setCourseId(positionsTotal.getCourseId());
        List<CourseTradingConstraint> tradingConstraint = courseTradingConstraintService.selectCourseTradingConstraintList(courseTradingConstraint);
        if (Objects.isNull(tradingConstraint)) {
            return AjaxResult.success("操作成功");
        }
        Map<String, List<CourseTradingConstraint>> store = tradingConstraint.stream().collect(Collectors.groupingBy(CourseTradingConstraint::getTradingTarget));
        List<CourseTradingConstraint> courseTradingConstraints = store.get(positionsTotal.getStockId());
        if(Objects.isNull(courseTradingConstraints)){
            return AjaxResult.success("操作成功");
        }
        //交易约束  毛
        int grossQuantity = courseTradingConstraints.get(0).getGrossQuantity();
        int grossUnitFines = courseTradingConstraints.get(0).getGrossUnitFines();

        //交易约束  净
        int netPosition = courseTradingConstraints.get(0).getNetPosition();
        Integer netUnitFines = courseTradingConstraints.get(0).getNetUnitFines();


        PositionsTotal positionTotal = positionsTotalService.findPositionTotal(positionsTotal);
        if(Objects.isNull(positionTotal)){
            return AjaxResult.success("操作成功");
        }
        int totalQuantity = positionTotal.getTotalQuantity();
        int nowQuantity = positionTotal.getNowQuantity();
        /*if (totalQuantity >= grossQuantity && grossUnitFines == 0) {
            flag = false;
        }*/
        int quantity = positionsTotal.getQuantity();//本次交易数量
        /*if(quantity>0)
        {
            quantity = quantity-1;

        }else {
            quantity = quantity+1;
        }*/
/*
        nowQuantity = Math.abs(nowQuantity+quantity);
*/
        int nowQuantity1 = Math.abs(nowQuantity+quantity);
        if(nowQuantity1 > netPosition && netUnitFines == 0)
        {
            if(nowQuantity>0 && quantity<0 && quantity<= Math.abs(nowQuantity)+Math.abs(netPosition))
            {
                flag = true;
            }else if(nowQuantity<0 && quantity>0 && quantity<= Math.abs(nowQuantity)+Math.abs(netPosition))
            {
                flag = true;
            } else{
                flag = false;
            }

        }

        if (!flag) {
            return AjaxResult.error(ResultStatus.FAIL);
        }
        return AjaxResult.success("操作成功");
    }
}
