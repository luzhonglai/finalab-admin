package com.bytetcp.finalab.serve.course.service;

import com.bytetcp.finalab.common.base.HttpResult;
import com.bytetcp.finalab.serve.course.domain.Course;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.domain.Order;
import com.bytetcp.finalab.serve.course.vo.CourseVo;

import java.util.List;

/**
 * 课件 服务层
 *
 * @author finalab
 * @date 2019-03-09
 */
public interface ICourseService {
    /**
     * 查询课件信息
     *
     * @param id 课件ID
     * @return 课件信息
     */
    CourseVo selectCourseById(Long id);

    /**
     * 查询课件列表
     *
     * @param course 课件信息
     * @return 课件集合
     */
    List<Course> selectCourseList(Course course);

    List<CourseVo> selectCourseVoList(Course course);

    /**
     * 新增课件
     *
     * @param course 课件信息
     * @return 结果
     */
    int insertCourse(Course course);

    /**
     * 学生报名时增加学生人数
     *
     * @param courseId
     * @return
     */
    int incrementAddNum(Long courseId);

    /**
     * 学生退出时学生人数 减 1
     *
     * @param courseId
     * @return
     */
    int decrementAddNum(Long courseId);

    /**
     * 修改课件
     *
     * @param course 课件信息
     * @return 结果
     */
    int updateCourse(Course course);

    /**
     * 删除课件信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCourseByIds(String ids);

    int deleteCourseByCaseId(long caseId);

    //启动新实例
//    Integer instanceStart(long courseId, String status);

    //实例运行状态插入流水表
    int insertInstanceRunRecord(InstanceRunRecode instanceRunRecode);

    //改变实例运行状态
//    Integer instanceChange(long courseId, String status);

    //查询实例id
    InstanceRunRecode selectInstance(long courseId);
    //下单

    HttpResult sendHttp(Order order);

    /**
     * 循环开启与关闭操作
     */
//    Integer changeCycleIn(long courseId);

    //根据课件id查询当前最新的实例状态
    String selectInstanceByLastStatus(long courseId);

    //查询阶段数是否显示循环开启
    String selectPeriods(long courseId);
    String selectTicksPerPeriod(long courseId);

    Integer changeInstanceStatus(Long courseId, String status, Boolean isRecord);

    Integer countRunningCourse();

    InstanceRunRecode getRunStatus(Long courseId, String instanceId);
}
