package com.bytetcp.finalab.serve.courseMessage.mapper;

import com.bytetcp.finalab.serve.courseMessage.domain.CourseMessage;
import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;
import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  老师学生消息广播 数据层
 *
 * @author finalab
 * @date 2020-05-28
 */
public interface CourseMessageMapper
{
	/**
     * 发送消息给指定学生  消息广播
     */
	int insertCourseMessage(CourseMessage courseMessage);

	/**
     * 查询 学生参与课件的消息
     *
     * @param courseMessage  学生参与课件消息
     * @return  学生参与课件消息集合
     */
	List<CourseMessage> selectCourseMessage(CourseMessage courseMessage);

	/**
	 * 删除 学生参与课件的消息
	 */
	int deleteCourseMessage(CourseMessage courseMessage);

	/**
	 * 删除所有 学生参与课件的消息
	 */
	int deleteAllCourseMessage(CourseMessage courseMessage);

	int updateCourseMessage(CourseMessage courseMessage);

}