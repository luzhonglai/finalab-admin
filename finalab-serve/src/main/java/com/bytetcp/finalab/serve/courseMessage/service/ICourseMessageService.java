package com.bytetcp.finalab.serve.courseMessage.service;

import com.bytetcp.finalab.serve.courseMessage.domain.CourseMessage;
import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;

import java.util.List;

/**
 *  学生参与课件 服务层
 * 
 * @author finalab
 * @date 2020-05-28
 */
public interface ICourseMessageService
{
	/**
     * 发送消息广播给指定学生
     */
    int insertCourseMessage(CourseMessage courseMessage);

    List<CourseMessage> selectCourseMessage(CourseMessage courseMessage);

    int deleteCourseMessage(CourseMessage courseMessage);

    int updateCourseMessage(CourseMessage courseMessage);

    int deleteAllCourseMessage(CourseMessage courseMessage);
}
