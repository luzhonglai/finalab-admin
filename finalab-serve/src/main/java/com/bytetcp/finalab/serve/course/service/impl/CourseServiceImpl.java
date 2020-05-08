package com.bytetcp.finalab.serve.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bytetcp.finalab.common.base.HttpResult;
import com.bytetcp.finalab.common.enums.InstanceStatus;
import com.bytetcp.finalab.common.enums.ServerStatus;
import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.UUIDUtil;
import com.bytetcp.finalab.serve.config.HttpMethod;
import com.bytetcp.finalab.serve.course.controller.CourseController;
import com.bytetcp.finalab.serve.course.domain.Course;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.domain.Order;
import com.bytetcp.finalab.serve.course.mapper.CourseMapper;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.course.vo.CourseVo;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 课件 服务层实现
 *
 * @author finalab
 * @date 2019-03-09
 */
@Service
public class CourseServiceImpl implements ICourseService {

    Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);


    @Value("${finalab.server.orderUrl}")
    private String orderUrl;

    @Value("${finalab.server.instanceUrl}")
    private String instanceUrl;

    @Autowired
    private HttpMethod httpMethod;

    @Autowired(required = false)
    private CourseMapper courseMapper;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询课件信息
     *
     * @param id 课件ID
     * @return 课件信息
     */
    @Override
    public CourseVo selectCourseById(Long id) {
        return courseMapper.selectCourseById(id);
    }

    /**
     * 查询课件列表
     *
     * @param course 课件信息
     * @return 课件集合
     */
    @Override
    public List<Course> selectCourseList(Course course) {
        return courseMapper.selectCourseList(course);
    }

    public List<CourseVo> selectCourseVoList(Course course) {
        return courseMapper.selectCourseVoList(course);
    }


    /**
     * 新增课件
     *
     * @param course 课件信息
     * @return 结果
     */
    @Override
    public int insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    /**
     * 学生报名时增加学生人数
     *
     * @param courseId
     * @return
     */
    public int incrementAddNum(Long courseId) {
        return courseMapper.incrementAddNum(courseId);
    }

    /**
     * 学生退出时学生人数 减 1
     *
     * @param courseId
     * @return
     */
    public int decrementAddNum(Long courseId) {
        return courseMapper.decrementAddNum(courseId);
    }

    /**
     * 修改课件
     *
     * @param course 课件信息
     * @return 结果
     */
    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    /**
     * 删除课件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseByIds(String ids) {
        return courseMapper.deleteCourseByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteCourseByCaseId(long caseId) {
        return courseMapper.deleteCourseByCaseId(caseId);
    }

    @Override
    public Integer changeInstanceStatus(Long courseId, String status,String speedValue,Boolean isRecord) {
        Boolean isStart = status.equals(InstanceStatus.START.name());
        String instanceId = isStart ? UUIDUtil.getAccountIdByUUId() : selectInstance(courseId).getInstanceId();

        CourseVo courseVo = courseMapper.selectCourseById(courseId);
        JSONObject paramsToSendServer = new JSONObject();
        paramsToSendServer.put("instanceId", instanceId);
        paramsToSendServer.put("courseId", String.valueOf(courseId));
        paramsToSendServer.put("instanceMethod", status);
        paramsToSendServer.put("financialType",  courseVo.getCaseType().toString());
        paramsToSendServer.put("speedValue", String.valueOf(speedValue));

        HttpResult httpResult = httpMethod.send(paramsToSendServer, instanceUrl);
        logger.info("请求地址：{}， 请求内容：{}, 返回结果：{}", instanceUrl,paramsToSendServer.toString(), httpResult.getMsg());
        return httpResult.getCode();
    }

    @Override
    public HttpResult sendHttp(Order order) {

        return httpMethod.send(order, orderUrl);

    }

    @Override
    public int insertInstanceRunRecord(InstanceRunRecode instanceRunRecode) {
        return courseMapper.insertInstanceRunRecord(instanceRunRecode);
    }

    @Override
    public InstanceRunRecode selectInstance(long courseId) {
        return courseMapper.selectInstance(courseId);
    }

    @Override
    public String selectInstanceByLastStatus(long courseId) {
        return courseMapper.selectInstanceByLastStatus(courseId);
    }

    @Override
    public String selectPeriods(long courseId) {
        return courseMapper.selectPeriods(courseId);
    }

    @Override
    public String selectTicksPerPeriod(long courseId) {
        return courseMapper.selectTicksPerPeriod(courseId);
    }

    @Override
    public Integer countRunningCourse() {
        return courseMapper.countRunningCourse();
    }

    @Override
    public InstanceRunRecode getRunStatus(Long courseId, String instanceId){
        return courseMapper.getRunStatus(courseId, instanceId);
    }

}
