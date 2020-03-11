package com.bytetcp.finalab.serve.courseStudent.mapper;

import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  学生参与课件 数据层
 *
 * @author finalab
 * @date 2019-03-15
 */
public interface CourseStudentMapper
{
	/**
     * 查询 学生参与课件信息
     *
     * @param id  学生参与课件ID
     * @return  学生参与课件信息
     */
	public CourseStudent selectCourseStudentById(Long id);


	public CourseStudent selectCourseStudentByStudentIdAndCourseId(@Param("studentId") Long studentId,
																   @Param("courseId") Long courseId);

	/**
     * 查询 学生参与课件列表
     *
     * @param courseStudent  学生参与课件信息
     * @return  学生参与课件集合
     */
	public List<CourseStudent> selectCourseStudentList(CourseStudent courseStudent);


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

	/**
     * 新增 学生参与课件
     *
     * @param courseStudent  学生参与课件信息
     * @return 结果
     */
	public int insertCourseStudent(CourseStudent courseStudent);

	/**
     * 修改 学生参与课件
     *
     * @param courseStudent  学生参与课件信息
     * @return 结果
     */
	public int updateCourseStudent(CourseStudent courseStudent);

	public int updateCourseStudentByStudIdAndCourseId(CourseStudent courseStudent);

	/**
     * 删除 学生参与课件
     *
     * @param id  学生参与课件ID
     * @return 结果
     */
	public int deleteCourseStudentById(Long id);

	/**
     * 批量删除 学生参与课件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseStudentByIds(String[] ids);

	List<CourseStudent> getStuIdByCourseId(Long courseId);

}