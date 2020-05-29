package com.bytetcp.finalab.serve.courseMessage.service.impl;

import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.courseMessage.domain.CourseMessage;
import com.bytetcp.finalab.serve.courseMessage.mapper.CourseMessageMapper;
import com.bytetcp.finalab.serve.courseMessage.service.ICourseMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  学生参与课件 服务层实现
 * 
 * @author finalab
 * @date 2020-05-28
 */
@Service
public class CourseMessageServiceImpl implements ICourseMessageService
{
	@Autowired
	private CourseMessageMapper courseMessageMapper;

	@Override
	public int insertCourseMessage(CourseMessage courseMessage) {
		return courseMessageMapper.insertCourseMessage(courseMessage);
	}

	@Override
	public List<CourseMessage> selectCourseMessage(CourseMessage courseMessage) {
		return courseMessageMapper.selectCourseMessage(courseMessage);
	}

	@Override
	public int deleteCourseMessage(CourseMessage courseMessage) {
		return courseMessageMapper.deleteCourseMessage(courseMessage);
	}

	@Override
	public int updateCourseMessage(CourseMessage courseMessage) {
		return courseMessageMapper.updateCourseMessage(courseMessage);
	}

	@Override
	public int deleteAllCourseMessage(CourseMessage courseMessage) {
		return courseMessageMapper.deleteAllCourseMessage(courseMessage);
	}
}
