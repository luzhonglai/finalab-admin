package com.bytetcp.finalab.serve.course.mapper;

import com.bytetcp.finalab.serve.course.domain.Course;
import com.bytetcp.finalab.serve.course.domain.InstanceRunRecode;
import com.bytetcp.finalab.serve.course.vo.CourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课件 数据层
 *
 * @author finalab
 * @date 2019-03-09
 */
public interface CourseMapper {
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
     * @param courseId
     * @return
     */
    int incrementAddNum(Long courseId);

    /**
     * 学生退出时学生人数 减 1
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
     * 删除课件
     *
     * @param id 课件ID
     * @return 结果
     */
     int deleteCourseById(Long id);

    /**
     * 批量删除课件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteCourseByIds(String[] ids);

     int deleteCourseByCaseId(long caseId);

     int insertInstanceRunRecord(InstanceRunRecode instanceRunRecode);

    //根据courseId查询InstanceId
     InstanceRunRecode selectInstance(long courseId);

    //根据课件id查询当前最新的实例状态
     String selectInstanceByLastStatus(long courseId);

    //查询阶段数是否显示循环开启
     String selectPeriods(long courseId);

     Integer countRunningCourse();

     InstanceRunRecode getRunStatus(@Param("courseId") Long courseId, @Param("instanceId") String instanceId);

    String selectTicksPerPeriod(Long courseId);
}