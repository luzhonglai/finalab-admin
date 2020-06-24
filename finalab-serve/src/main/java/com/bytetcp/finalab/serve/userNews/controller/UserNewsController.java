package com.bytetcp.finalab.serve.userNews.controller;

import com.alibaba.fastjson.JSON;
import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.base.HttpResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.ParamsUtil;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.config.HttpMethod;
import com.bytetcp.finalab.serve.course.domain.MustAdd;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.courseMarketNews.domain.CourseMarketNews;
import com.bytetcp.finalab.serve.courseMarketNews.service.ICourseMarketNewsService;
import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;
import com.bytetcp.finalab.serve.courseStudent.service.ICourseStudentService;
import com.bytetcp.finalab.serve.courseUserNews.domain.CourseUserNews;
import com.bytetcp.finalab.serve.courseUserNews.service.ICourseUserNewsService;
import com.bytetcp.finalab.serve.userNews.domain.UserNews;
import com.bytetcp.finalab.serve.userNews.domain.UserNewsDetail;
import com.bytetcp.finalab.serve.userNews.domain.UserNewsReq;
import com.bytetcp.finalab.serve.userNews.mapper.UserNewsDetailMapper;
import com.bytetcp.finalab.serve.userNews.service.IUserNewsService;
import com.bytetcp.finalab.system.domain.SysUser;
import com.bytetcp.finalab.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * user_news用户新闻 信息操作处理
 *
 * @author finalab
 * @date 2019-05-25
 */
@Controller
@RequestMapping("/serve/userNews")
public class UserNewsController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UserNewsController.class);

    private String prefix = "userNews";

    @Value("${finalab.server.scaleIn}")
    private String compelScaleInUrl;

    @Autowired
    private IUserNewsService userNewsService;

    @Autowired
    private ICourseUserNewsService courseUserNewsService;

    @Autowired
    private ICourseMarketNewsService courseMarketNewsService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICourseStudentService courseStudentService;

    @Autowired
    private UserNewsDetailMapper userNewsDetailMapper;

    @Autowired
    private HttpMethod httpMethod;


    @RequiresPermissions("serve:userNews:view")
    @GetMapping()
    public String userNews() {
        return prefix + "/userNews";
    }

    /**
     * 查询user_news用户新闻列表
     */
    @RequiresPermissions("serve:userNews:list")
    @PostMapping("/list/{caseId}")
    @ResponseBody
    public TableDataInfo list(@PathVariable("caseId") long caseId, UserNews userNews) {
        userNews.setCaseId(caseId);
        startPage();
        List<UserNews> list = userNewsService.selectUserNewsList(userNews);
        return getDataTable(list);
    }

    @PostMapping("/news")
    @ResponseBody
    public AjaxResult userNews(@RequestBody UserNewsReq userNewsReq) {
        AjaxResult result = AjaxResult.error(1, "无新闻数据");
        Long userId = getSysUser().getUserId();
        CourseUserNews userNews = courseUserNewsService.getUserNews(userNewsReq, userId);
        if (Objects.nonNull(userNews)) {
            HashMap<String, Object> userNewsMap = new HashMap<>();
            userNewsMap.put("price", userNews.getPrice());
            userNewsMap.put("quantity", userNews.getNumber());
            userNewsMap.put("stockId", userNews.getTargetName());
            userNewsMap.put("timeout", userNews.getContinueTime());
            result.toSuccess().put("userNews", userNewsMap);
        }
        CourseMarketNews marketNews = courseMarketNewsService.selectCourseMarketNewsByTimeNum(userNewsReq.getTimeNum(), userNewsReq.getCourseId(), userNewsReq.getThePeriod(), userNewsReq.getGroupNum());
        if (Objects.nonNull(marketNews)) {
            HashMap<String, Object> marketNewsMap = new HashMap<>();
            marketNewsMap.put("title", marketNews.getNewsTitle());
            String targetString = marketNews.getTargetString();
            if (StringUtils.isNotEmpty(targetString)) {
                String[] formatEle = targetString.split(",");
                marketNewsMap.put("content", MessageFormat.format(marketNews.getContent(), formatEle));
                marketNewsMap.put("price", formatEle[0]);
                if(marketNews.getAction().contains("-")){
                    marketNewsMap.put("quantity", "-"+formatEle[1]);
                }else {
                    marketNewsMap.put("quantity", formatEle[1]);
                }
            } else {
                marketNewsMap.put("content", marketNews.getContent());
                marketNewsMap.put("price", "");
                marketNewsMap.put("quantity", "");
            }
            marketNewsMap.put("isCompel", marketNews.getIsCompel());
            marketNewsMap.put("stockId", marketNews.getCompelStockId());
            marketNewsMap.put("timeout", marketNews.getTimeNum());
            result.toSuccess().put("marketNews", marketNewsMap);
        }
        //userId username  userNews.getcaseId 案例id    result.getUserNews  userNewsReq.getTimeNum 时间  userNewsReq.getCourseId 课件id
        UserNewsDetail userNewsDetail = new UserNewsDetail();
        if (result.get("code").equals(0)) {
            SysUser sysUser = userService.selectUserById(userId);
            userNewsDetail.setUserId(userId);
            userNewsDetail.setUserName(sysUser.getUserName());
            userNewsDetail.setTimeNum(userNewsReq.getTimeNum());
            userNewsDetail.setCourseId(userNewsReq.getCourseId());
            userNewsDetail.setInstanceId(userNewsReq.getInstanceId());
            if (Objects.nonNull(userNews)) {
                String type = userNews.getNumber() > 0 ? "卖出" : "买入";
                userNewsDetail.setCaseId(userNews.getCaseId());
                String usernew = /*userNews.getTargetName()*/"你公司的某位客户" + "准备以￥" + ParamsUtil.priceExact(userNews.getPrice(), 2)
                        + type + Math.abs(userNews.getNumber()) + "支" + userNews.getTargetName();
                userNewsDetail.setUserNews(usernew);
            }
            if (Objects.nonNull(marketNews)) {
                userNewsDetail.setCaseId(marketNews.getCaseId());
                String targetString = marketNews.getTargetString();
                if (StringUtils.isNotEmpty(targetString)) {
                    String[] formatEle = targetString.split(",");
                    userNewsDetail.setUserNews(MessageFormat.format(marketNews.getContent(), formatEle));
                }else {
                    String usernew = marketNews.getContent() + " " + marketNews.getTargetString();
                    userNewsDetail.setUserNews(usernew);
                }
            }
            userNewsDetailMapper.insert(userNewsDetail);
        }
        return result;
    }

    static class Trader {

        static List<Trader> convertTraderList(List<CourseStudent> list) {
            List<Trader> result = new ArrayList<>();
            for (CourseStudent stu : list) {
                Trader trader = new Trader();
                trader.setTraderId(stu.getStudentId());
                trader.setTraderName(stu.getUserName());
                result.add(trader);
            }
            return result;
        }

        private Long traderId;
        private String traderName;

        public Long getTraderId() {
            return traderId;
        }

        public void setTraderId(Long traderId) {
            this.traderId = traderId;
        }

        public String getTraderName() {
            return traderName;
        }

        public void setTraderName(String traderName) {
            this.traderName = traderName;
        }
    }


    public static void main(String[] args) {
//		ArrayList<String> strings = new ArrayList<>();
//		strings.add("111");
//		String s = strings.get(2);
//		System.out.println(s);
        Double aDouble = new Double(12.36);
        HashMap<String, Double> stringDoubleHashMap = new HashMap<>();
        stringDoubleHashMap.put("dddd", aDouble);
        System.out.println(JSON.toJSONString(stringDoubleHashMap));
        System.out.println(new Double(-20).intValue());
    }


    /**
     * 导出user_news用户新闻列表
     */
    @RequiresPermissions("serve:userNews:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UserNews userNews) {
        List<UserNews> list = userNewsService.selectUserNewsList(userNews);
        ExcelUtil<UserNews> util = new ExcelUtil<UserNews>(UserNews.class);
        return util.exportExcel(list, "userNews");
    }

    /**
     * 新增user_news用户新闻
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存user_news用户新闻
     */
    @RequiresPermissions("serve:userNews:add")
    @Log(title = "user_news用户新闻", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UserNews userNews) {
        return toAjax(userNewsService.insertUserNews(userNews));
    }

    /**
     * 修改user_news用户新闻
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        UserNews userNews = userNewsService.selectUserNewsById(id);
        mmap.put("userNews", userNews);
        return prefix + "/edit";
    }

    /**
     * 修改保存user_news用户新闻
     */
    @RequiresPermissions("serve:userNews:edit")
    @Log(title = "user_news用户新闻", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UserNews userNews) {
        return toAjax(userNewsService.updateUserNews(userNews));
    }

    /**
     * 删除user_news用户新闻
     */
    @RequiresPermissions("serve:userNews:remove")
    @Log(title = "user_news用户新闻", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(userNewsService.deleteUserNewsByIds(ids));
    }

    @ResponseBody
    @PostMapping("/mustAdd")
    public AjaxResult mustAdd(@RequestBody MustAdd mustAdd) {
        HttpResult httpResult = httpMethod.send(mustAdd, compelScaleInUrl);
        if (200 == httpResult.getStatus()) {
            logger.info("用户新闻强制加仓结果：{}", httpResult);
            return AjaxResult.success().put("msg", httpResult.getMsg()).put("code", httpResult.getCode());
        } else {
            return AjaxResult.error().put("msg", httpResult.getMsg()).put("code", httpResult.getCode());
        }
    }

}
