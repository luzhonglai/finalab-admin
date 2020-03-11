package com.bytetcp.finalab.serve.courseStudent.service;

import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;

import java.util.List;

/**
 *  学生参与课件 服务层
 * 
 * @author finalab
 * @date 2019-03-15
 */
public interface ICourseStudentService 
{
	/**
     * 查询 学生参与课件信息
     * 
     * @param id  学生参与课件ID
     * @return  学生参与课件信息
     */
	public CourseStudent selectCourseStudentById(Long id);

	public CourseStudent selectCourseStudentByStudentIdAndCourseId(Long studengId, Long courseId);

	/**
     * 查询 学生参与课件列表
     * 
     * @param courseStudent  学生参与课件信息
     * @return  学生参与课件集合
     */
	public List<CourseStudent> selectCourseStudentList(CourseStudent courseStudent);


	/**
	 * 查询 参与课件的学生列表
	 *
	 * @param courseId  课件Id
	 * @return  参与课件学生的集合
	 */
	public List<CourseStudent> selectCourseStudentListByCourseId(Long courseId);

	/**
	 * 在线学生人数
	 * @param courseId
	 * @return
	 */
	Integer countOnLineStudent(Long courseId);

	/**
	 * 已报名学生人数
	 * @param courseId
	 * @return
	 */
	Integer countStudent(Long courseId);

	String membersProportion(Long courseId);

	/**
     * 新增 学生参与课件
     * 
     * @param courseStudent  学生参与课件信息
     * @return 结果
     */
	public int insertCourseStudent(CourseStudent courseStudent);
	public int signUp(CourseStudent courseStudent);

	/**
     * 修改 学生参与课件
     * 
     * @param courseStudent  学生参与课件信息
     * @return 结果
     */
	public int updateCourseStudent(CourseStudent courseStudent);

	public int updateCourseStudentByStudIdAndCourseId(CourseStudent courseStudent);
		
	/**
     * 删除 学生参与课件信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseStudentByIds(String ids);

	List<CourseStudent> getStuIdByCourseId(Long courseId);
	
}
