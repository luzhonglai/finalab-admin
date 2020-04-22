package com.bytetcp.finalab.serve.course.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bytetcp.finalab.common.annotation.Log;
import com.bytetcp.finalab.common.base.AjaxResult;
import com.bytetcp.finalab.common.base.HttpResult;
import com.bytetcp.finalab.common.enums.BusinessType;
import com.bytetcp.finalab.common.enums.FinancialType;
import com.bytetcp.finalab.common.enums.InstanceStatus;
import com.bytetcp.finalab.common.enums.TradeType;
import com.bytetcp.finalab.common.page.TableDataInfo;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.framework.util.ShiroUtils;
import com.bytetcp.finalab.framework.web.base.BaseController;
import com.bytetcp.finalab.serve.cases.domain.Cases;
import com.bytetcp.finalab.serve.cases.service.ICasesService;
import com.bytetcp.finalab.serve.course.domain.*;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.course.service.IInstanceTradingService;
import com.bytetcp.finalab.serve.course.vo.CourseVo;
import com.bytetcp.finalab.serve.courseCaseParamters.domain.CourseCaseParamters;
import com.bytetcp.finalab.serve.courseCaseParamters.service.ICourseCaseParamtersService;
import com.bytetcp.finalab.serve.courseDerivedVar.service.ICourseDerivedVarService;
import com.bytetcp.finalab.serve.courseLiquidation.service.ICourseLiquidationService;
import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;
import com.bytetcp.finalab.serve.courseStudent.service.ICourseStudentService;
import com.bytetcp.finalab.serve.courseTargetParam.domain.CourseTargetParam;
import com.bytetcp.finalab.serve.courseTargetParam.service.ICourseTargetParamService;
import com.bytetcp.finalab.serve.courseUpdateVar.domain.CourseUpdateVar;
import com.bytetcp.finalab.serve.courseUpdateVar.service.ICourseUpdateVarService;
import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotal;
import com.bytetcp.finalab.serve.positionsTotal.service.IPositionsTotalService;
import com.bytetcp.finalab.serve.userMoneyDetail.service.IUserMoneyDetailService;
import com.bytetcp.finalab.system.domain.SysRole;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * 课件 信息操作处理
 *
 * @author finalab
 * @date 2019-03-09
 */
@Controller
@RequestMapping("/serve/course")
public class CourseController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    public static Map<String, Set> mapForSet = new ConcurrentHashMap<>();

    private String prefix = "course";

    @Value("${finalab.server.webSocketUrl}")
    private String webSocketUrl;

    @Value("${finalab.admin.userStoreUrl}")
    private String userStoreUrl;

    @Value("${finalab.admin.orderUrl}")
    private String orderUrl;

    @Value("${finalab.admin.quickOrderUrl}")
    private String quickOrderUrl;

    @Value("${default.role.teacher}")
    private String teacherRoleSign;

    @Value("${finalab.admin.maxRunning}")
    private Integer maxRunning;

    @Value("${finalab.server.autoSocketUrl}")
    private String autoSocketUrl;

    @Value("${finalab.server.instanceInfo}")
    private String instanceInfoUrl;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICourseCaseParamtersService courseCaseParamtersService;

    @Autowired
    private ICourseStudentService courseStudentService;

    @Autowired
    private ICasesService casesService;

    @Autowired
    private ICourseLiquidationService courseLiquidationService;

    @Autowired
    private ICourseTargetParamService courseTargetParamService;

    @Autowired
    private IInstanceTradingService iInstanceTradingService;

    @Autowired
    private IUserMoneyDetailService userMoneyDetailService;

    @Autowired
    private IPositionsTotalService positionsTotalService;

    @Autowired
    private ICourseUpdateVarService courseUpdateVarService;

    @Autowired
    private ICourseDerivedVarService courseDerivedVarService;

    /***
     * 列出当前用户有关系的课件
     * @param mmap
     * @return
     */
    @RequiresPermissions("serve:course:control")
    @GetMapping()
    public String course(ModelMap mmap) {
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setStudentId(getUserId());
        List<CourseStudent> courseStudents = courseStudentService.selectCourseStudentList(courseStudent);
        Map<Long, Object> courseStudentMap = new HashMap<>();
        courseStudents.forEach(e -> courseStudentMap.put(e.getCourseId(), e));
        mmap.put("courseStudentMap", courseStudentMap);
        return prefix + "/course";
    }

    /**
     * 查询课件列表
     */
    @RequiresPermissions("serve:course:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Course course) {
        course = ifRoleTeacher(course);
        startPage();
        List<CourseVo> list = courseService.selectCourseVoList(course);
        return getDataTable(list);
    }

    /**
     * 判断当前用户角色是否为老师，若角色是“老师”，
     * 则给 course 的 teacherId 设置为当前系统用户 userId ，
     * 使当前用户只能查看到自己创建的“课件”
     *
     * @param course
     * @return
     */
    private Course ifRoleTeacher(Course course) {
        List<SysRole> roles = ShiroUtils.getSysUser().getRoles();
        List<SysRole> collect = roles.stream()
                .filter(sysRole -> sysRole.getRoleKey().equals(teacherRoleSign))
                .collect(Collectors.toList());
        //if (collect.size() != 0) course.setTeacherId(getUserId());
        if (collect.size() != 0 && roles.size() == 1)
            course.setTeacherId(getUserId()); //为只有老师角色的人展示他自己创建的课件，如果拥有其他角色，则展示全部课件
        return course;
    }

    @RequiresPermissions("serve:course:view")
    @GetMapping("/{courseId}")
    public String courseDetail(@PathVariable("courseId") String courseId, Model model) {
        if ("undefined".equals(courseId) || "null".equals(courseId)) {
            courseId = "0";
        }
        Long courseID = Long.parseLong(courseId);
        CourseVo courseVo = courseService.selectCourseById(courseID);

        CourseTargetParam targetParam = new CourseTargetParam();
        targetParam.setCourseId(courseID);
        targetParam.setParamName("QuotedDecimals");
        List<CourseTargetParam> courseTargetParams = courseTargetParamService.selectCourseTargetParamList(targetParam);
        Map<String, String> stockQuotedDecimals = new HashMap<>();
        if (courseTargetParams.size() != 0) {
            courseTargetParams.forEach(c -> stockQuotedDecimals.put(c.getTargetName(), c.getTargetValue()));
            model.addAttribute("stockQuotedDecimals", stockQuotedDecimals);
        }
        model.addAttribute("courseVo", courseVo);
        Long userId = getSysUser().getUserId();
        model.addAttribute("showSignUp", showSignUp(userId, courseID));
        String membersProportion = courseStudentService.membersProportion(courseID);
        model.addAttribute("membersProportion", membersProportion);
        String instanceStatus = courseService.selectInstanceByLastStatus(courseID);
        String selectPeriods = courseService.selectPeriods(courseID);
        String ticksPerPeriod = courseService.selectTicksPerPeriod(courseID);

        model.addAttribute("instanceInfoUrl", instanceInfoUrl);
        if (selectPeriods != null) {
            model.addAttribute("Periods", selectPeriods);
        }
        if (ticksPerPeriod != null) {
            model.addAttribute("TicksPerPeriod", selectPeriods);
        }
        if (StringUtils.isNotEmpty(instanceStatus)) {
            model.addAttribute("instanceStatus", instanceStatus);
        } else {
            model.addAttribute("instanceStatus", "default".toUpperCase());
        }
        return prefix + "/course-detail";
    }

    /**
     * 已报名返回true
     *
     * @param userId
     * @param courseId
     * @return
     */
    private boolean showSignUp(Long userId, Long courseId) {
        CourseStudent courseStudent = courseStudentService.selectCourseStudentByStudentIdAndCourseId(userId, courseId);
        return null != courseStudent && 1 == courseStudent.getSignUp();
    }


    /**
     * 导出课件列表
     */
    @RequiresPermissions("serve:course:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Course course) {
        List<Course> list = courseService.selectCourseList(course);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        return util.exportExcel(list, "course");
    }

    /**
     * 新增课件
     */
    @GetMapping("/add")
    public String add(Model model) {
        Map<Long, String> caseMap = new HashedMap();
        List<Cases> list = casesService.selectCasesList(new Cases());
        for (Cases cases : list) {
            caseMap.put(cases.getId(), cases.getCaseName());
        }
        model.addAttribute("case", caseMap);
        model.addAttribute("sysUser", getSysUser());

        Map<String, String> teacherMap = new HashedMap();
        teacherMap.put("1", "若依");
        teacherMap.put("2", "教师2");
        teacherMap.put("3", "教师3");
        model.addAttribute("teacher", teacherMap);

        return prefix + "/add";
    }

    /**
     * 新增保存课件
     */
    @RequiresPermissions("serve:course:add")
    @Log(title = "课件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @Transactional
    public AjaxResult addSave(Course course) {
        course.setTeacherId(getUserId());
        courseService.insertCourse(course);
        //新增课程案例参数
        Long caseId = course.getCaseId();
        Long courseId = course.getId();

        //开课全部copy
        courseCaseParamtersService.copyCaseParamsByCaseId(caseId, courseId);
        courseCaseParamtersService.copyTradingTargetByCaseId(caseId, courseId);


//        Integer maxGroupNum = courseCaseParamtersService.selectMaxGroupNumInPriceMove(caseId);
//        //随机数
//        int MaxNum = maxGroupNum.intValue();
//        int min = 1;
//        Random random = new Random();
//        int randomGroupNum = random.nextInt(MaxNum) + min;

        //筛选
//        if (maxGroupNum != null && maxGroupNum == 1) {
        courseCaseParamtersService.copyMarketNewsByCaseId(caseId, courseId);
        courseCaseParamtersService.copyLiquidationByCaseId(caseId, courseId);
        courseCaseParamtersService.copyPriceMoveByCaseId(caseId, courseId);
//        } else {
////            courseCaseParamtersService.copyMarketNewsByCaseIdAndGroupNum(caseId, courseId, randomGroupNum);
////            courseCaseParamtersService.copyLiquidationByCaseIdAndGroupNum(caseId, courseId, randomGroupNum);
////            courseCaseParamtersService.copyPriceMoveByCaseIdAndGroupNum(caseId, courseId, randomGroupNum);
////        }

        courseCaseParamtersService.copyTargetParamByCaseId(caseId, courseId);
        courseCaseParamtersService.copyUserNewsByCaseId(caseId, courseId);
        courseCaseParamtersService.copyTradingConstraintByCaseId(caseId, courseId);
        courseCaseParamtersService.copyUpdateVarByCaseId(caseId, courseId);
        courseDerivedVarService.copyCourseDerivedVars(caseId, courseId);

        //案例课件数修改
        Cases cases = casesService.selectCasesById(course.getCaseId());

        int courseNum = null == cases.getCourseNum() ? 0 : cases.getCourseNum();

        cases.setCourseNum(courseNum + 1);
        casesService.updateCases(cases);

        try {
            sleep(1000);
        } catch (InterruptedException ie) {
            return AjaxResult.error("加载失败");
        }
        return toAjax(true);
    }

    /**
     * 修改课件
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Course course = courseService.selectCourseById(id);
        model.addAttribute("course", course);
        Map<String, String> caseMap = new HashedMap();
        caseMap.put("1", "案例1");
        caseMap.put("2", "案例2");
        caseMap.put("3", "案例3");
        model.addAttribute("case", caseMap);
        Map<String, String> teacherMap = new HashedMap();
        teacherMap.put("1", "若依");
        teacherMap.put("2", "教师2");
        teacherMap.put("3", "教师3");
        model.addAttribute("teacher", teacherMap);
        return prefix + "/edit";
    }

    @RequestMapping("/op/{id}")
    @Log(title = "课件开启状态", businessType = BusinessType.UPDATE)
    @ResponseBody
    public AjaxResult changeCourseStatus(@PathVariable("id") Long courseId, String status) {

        if (status.equals("START") && courseService.countRunningCourse() >= maxRunning) {
            return AjaxResult.error("运行中课件达到" + maxRunning + "个,此课件无法启动");
        }

        Course course = new Course();
        course.setId(courseId);
        Integer changeStatusResult = courseService.changeInstanceStatus(courseId, status, true);

        if (changeStatusResult == 0) {
            switch (status) {
                case "START": {
                    course.setStatus(1);
                    break;
                }
                case "RESUME": {
                    course.setStatus(1);
                    break;
                }
                case "STOP": {
                    course.setStatus(2);
                    break;
                }
                default: {
                    course.setStatus(0);
                }
            }
            return AjaxResult.success(course);
        }
        return AjaxResult.error();
    }

    @PostMapping("/changeCycleIn")
    @Log(title = "改变循环开关", businessType = BusinessType.UPDATE)
    @ResponseBody
    public AjaxResult changeCycleIn(@RequestBody Course course) {
        Integer result = courseService.changeInstanceStatus(course.getId(), InstanceStatus.LOOPSTART.name(), false);
        if (result == 0) {
            courseService.updateCourse(course);
            return AjaxResult.success(course);
        } else {
            return AjaxResult.error(course);
        }
    }

    /**
     * 修改保存课件
     */
    @RequiresPermissions("serve:course:edit")
    @Log(title = "课件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Course course) {
        return toAjax(courseService.updateCourse(course));
    }

    /**
     * 删除课件
     */
    @RequiresPermissions("serve:course:remove")
    @Log(title = "课件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(courseService.deleteCourseByIds(ids));
    }


    //下单接口
    @PostMapping("/order")
    @ResponseBody
    public AjaxResult order(@RequestBody Order order) throws Exception {
        //分配用户
        Long userId = getSysUser().getUserId();
        String userName = getSysUser().getLoginName();
        order.setTraderId(userId);
        order.setTraderName(userName);
        logger.info(order.getPrice()+"闪电下单价格");
        if (order.getTradeType() == TradeType.ASK) {
            CourseTargetParam param = new CourseTargetParam();
            param.setCourseId(order.getCourseId());
            param.setTargetName(order.getStockId());
            param.setParamName("IsShortAllowed");
            String targetValue = courseTargetParamService.selectCourseTargetParam(param).getTargetValue();
            if (!targetValue.equals("1")) {
                PositionsTotal pTotalParam = new PositionsTotal();
                pTotalParam.setInstanceId(order.getInstanceId());
                pTotalParam.setStockId(order.getStockId());
                pTotalParam.setTraderId(order.getTraderId());
                PositionsTotal positionTotal = positionsTotalService.findPositionTotal(pTotalParam);
                if (positionTotal == null || positionTotal.getNowQuantity() - Integer.valueOf(order.getQuantity()) < 0) {
                    return AjaxResult.error().put("msg", "持仓数量不足，禁止交易").put("code", 1);
                }
            }
        }

        //执行下单
        HttpResult results = courseService.sendHttp(order);
        //返回下单状态
        if (200 == results.getStatus()) {
            return AjaxResult.success().put("msg", results.getMsg()).put("code", results.getCode());
        } else {
            return AjaxResult.error().put("msg", results.getMsg()).put("code", results.getCode());
        }
    }

    @PostMapping("instanceStatus")
    @ResponseBody
    public AjaxResult instanceStatus(Long courseId) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        boolean canJoin = Objects.nonNull(instanceRunRecode) && !instanceRunRecode.getStatus().equals(InstanceStatus.STOP.name());
        return canJoin ? AjaxResult.success() : AjaxResult.error();
    }

    @PostMapping("/instanceId")
    @ResponseBody
    public String instanceId(Long courseId) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        return instanceRunRecode == null ? null : instanceRunRecode.getInstanceId();
    }

    //进入交易页面
    @RequiresPermissions("serve:course:gotrad")
    @GetMapping("/transaction/{courseId}")
    public String transaction(@PathVariable("courseId") Long courseId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        AtomicReference<Boolean> showDerived = new AtomicReference<>(false);//是否显示衍生变量Tab false:不显示 true：显示

        model.put("courseId", courseId);
        model.put("webSocketUrl", webSocketUrl);
        model.put("autoSocketUrl", autoSocketUrl);
        model.put("userStoreUrl", userStoreUrl);
        model.put("quickOrderUrl", quickOrderUrl);
        logger.info("quickOrderUrl"+quickOrderUrl);
        model.put("orderUrl", orderUrl);
        model.put("userId", getUserId());
        model.put("userName", getSysUser().getUserName());
        CourseVo courseVo = courseService.selectCourseById(courseId);
        model.put("course", courseVo);

        //根据课件id拿实例id
        InstanceRunRecode Instance = courseService.selectInstance(courseId);
        if (Objects.nonNull(Instance) && StringUtils.isNotEmpty(Instance.getInstanceId())) {
            model.put("instanceId", Instance.getInstanceId());

            //查出标的参数
            CourseTargetParam courseTargetParam = new CourseTargetParam();
            courseTargetParam.setCourseId(courseId);
            List<CourseTargetParam> stockList = courseTargetParamService.selectCourseTargetParamList(courseTargetParam);
            Map<String, List<CourseTargetParam>> store = stockList.stream().collect(Collectors.groupingBy(CourseTargetParam::getTargetName));


            HashMap<String, Object> stockHashMap = new HashMap<>();
            for (Map.Entry<String, List<CourseTargetParam>> entry : store.entrySet()) {
                String key = entry.getKey();
                List<CourseTargetParam> value = entry.getValue();
                HashMap<String, String> stockMap = new HashMap<>();

                value.forEach(e -> {
                    stockMap.put(e.getParamName(), e.getTargetValue());
                    if (e.getParamName().equals("Type") && e.getTargetValue().toLowerCase().equals(FinancialType.option.name())) {
                        showDerived.set(true);
                    }
                });
                stockHashMap.put(key, stockMap);
            }

            model.put("showDerived", showDerived.get());
            model.put("stockMap", stockHashMap);
            Map<String, String> courseCasesParam = convertCase2map(courseId);
            model.put("courseCasesParam", courseCasesParam);

            logger.info("交易页面参数 - stockMap - {}, courseCasesParam - {}",
                    JSON.toJSONString(stockHashMap), JSON.toJSONString(courseCasesParam));
        } else {
            model.put("instanceId", "");
        }
        return "transaction/mline";
    }

    @PostMapping("/getUpdateVar")
    @ResponseBody
    public List<CourseUpdateVar> readVar(Long courseId) {
        //读取变量更新
        CourseUpdateVar courseUpdateVar = new CourseUpdateVar();
        courseUpdateVar.setCourseId(courseId);
        return courseUpdateVarService.selectCourseUpdateVarList(courseUpdateVar);
    }

    private Map<String, String> convertCase2map(Long courseId) {
        CourseCaseParamters courseCaseParamters = new CourseCaseParamters();
        courseCaseParamters.setCourseId(courseId);
        List<CourseCaseParamters> casesList = courseCaseParamtersService.selectCourseCaseParamtersList(courseCaseParamters);
        HashMap<String, String> courseCaseParmaMap = new HashMap<>();
        for (CourseCaseParamters c : casesList) {
            courseCaseParmaMap.put(c.getParaName(), c.getParaValue());
        }
        return courseCaseParmaMap;
    }


    //交易明细数据，每秒请求一次
    @PostMapping("/instanceTradingT/{instanceId}")
    @ResponseBody
    public List<InstanceTrading> instanceTradingT(@PathVariable("instanceId") String instanceId) {
        InstanceTrading instanceTrading = new InstanceTrading();
        instanceTrading.setUserId(getUserId().intValue());
        instanceTrading.setInstanceId(instanceId);

        //startPage();
        List<InstanceTrading> list = iInstanceTradingService.selectInstanceTradingList(instanceTrading);
        return list;
    }


    //交易明细数据，每秒请求一次
    @PostMapping("/instanceTrading/{instanceId}")
    @ResponseBody
    public TableDataInfo instanceTradingR(@PathVariable("instanceId") String instanceId) {
        InstanceTrading instanceTrading = new InstanceTrading();
        instanceTrading.setUserId(getUserId().intValue());
        instanceTrading.setInstanceId(instanceId);

        startPage();
        List<InstanceTrading> list = iInstanceTradingService.selectInstanceTradingList(instanceTrading);
        return getDataTable(list);
    }

    @PostMapping("/profitRanking")
    @ResponseBody
    public Map<String, JSONArray> profitRanking(Long courseId) {
        InstanceRunRecode instanceRunRecode = courseService.selectInstance(courseId);
        if (instanceRunRecode == null) {
            HashMap<String, JSONArray> resultParam = new HashMap();
            resultParam.put("plus", new JSONArray());
            resultParam.put("minus", new JSONArray());
            resultParam.put("yAxis", new JSONArray());
            return resultParam;
        }
        return userMoneyDetailService.getRanking(instanceRunRecode.getInstanceId());
    }
}
