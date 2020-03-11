package com.bytetcp.finalab.serve.courseStudent.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.course.service.ICourseService;
import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;
import com.bytetcp.finalab.serve.courseStudent.mapper.CourseStudentMapper;
import com.bytetcp.finalab.serve.courseStudent.service.ICourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *  学生参与课件 服务层实现
 * 
 * @author finalab
 * @date 2019-03-15
 */
@Service
public class CourseStudentServiceImpl implements ICourseStudentService 
{
	@Autowired
	private CourseStudentMapper courseStudentMapper;

	@Autowired
	private ICourseService courseService;

	/**
     * 查询 学生参与课件信息
     * 
     * @param id  学生参与课件ID
     * @return  学生参与课件信息
     */
    @Override
	public CourseStudent selectCourseStudentById(Long id)
	{
	    return courseStudentMapper.selectCourseStudentById(id);
	}

	@Override
	public CourseStudent selectCourseStudentByStudentIdAndCourseId(Long studengId, Long courseId)
	{
	    return courseStudentMapper.selectCourseStudentByStudentIdAndCourseId(studengId, courseId);
	}
	
	/**
     * 查询 学生参与课件列表
     * 
     * @param courseStudent  学生参与课件信息
     * @return  学生参与课件集合
     */
	@Override
	public List<CourseStudent> selectCourseStudentList(CourseStudent courseStudent)
	{
	    return courseStudentMapper.selectCourseStudentList(courseStudent);
	}

	@Override
	public List<CourseStudent> selectCourseStudentListByCourseId(Long courseId) {
		return courseStudentMapper.selectCourseStudentListByCourseId(courseId);
	}

	@Override
	public Integer countOnLineStudent(Long courseId) {
		return courseStudentMapper.countOnLineStudent(courseId);
	}

	@Override
	public Integer countStudent(Long courseId) {
		return courseStudentMapper.countStudent(courseId);
	}

	@Override
	public String membersProportion(Long courseId) {
		Integer a = courseStudentMapper.countOnLineStudent(courseId);
		Integer b = courseStudentMapper.countStudent(courseId);
		return "(" + a.toString() + "/" + b.toString() + ")";
	}

	@Override
	public int insertCourseStudent(CourseStudent courseStudent){
		return courseStudentMapper.insertCourseStudent(courseStudent);
	}

	/**
     * 新增 学生参与课件
     * 
     * @param courseStudent  学生参与课件信息
     * @return 结果
     */
	@Override
	public int signUp(CourseStudent courseStudent)
	{
		int result  = 0;
		Long courseId = courseStudent.getCourseId();
		Long studentId = courseStudent.getStudentId();
		CourseStudent findCourseStudent = courseStudentMapper.selectCourseStudentByStudentIdAndCourseId(studentId, courseId);
		if (Objects.isNull(findCourseStudent)) {
			courseService.incrementAddNum(courseId);
			result  = courseStudentMapper.insertCourseStudent(courseStudent);
		} else {
			result  = courseStudentMapper.updateCourseStudentByStudIdAndCourseId(courseStudent);
		}
		return result;
	}
	
	/**
     * 修改 学生参与课件
     * 
     * @param courseStudent  学生参与课件信息
     * @return 结果
     */
	@Override
	public int updateCourseStudent(CourseStudent courseStudent)
	{
	    return courseStudentMapper.updateCourseStudent(courseStudent);
	}

	@Override
	public int updateCourseStudentByStudIdAndCourseId(CourseStudent courseStudent)
	{
	    return courseStudentMapper.updateCourseStudentByStudIdAndCourseId(courseStudent);
	}

	/**
     * 删除 学生参与课件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseStudentByIds(String ids)
	{
		String[] idArr = Convert.toStrArray(ids);
		CourseStudent courseStudent = courseStudentMapper.selectCourseStudentById(new Long(idArr[0]));
		courseService.decrementAddNum(courseStudent.getCourseId());
		return courseStudentMapper.deleteCourseStudentByIds(idArr);
	}

	@Override
	public List<CourseStudent> getStuIdByCourseId(Long courseId) {
		return courseStudentMapper.getStuIdByCourseId(courseId);
	}

}
