package com.bytetcp.finalab.serve.courseUpdateVar.mapper;

import com.bytetcp.finalab.serve.courseUpdateVar.domain.CourseUpdateVar;
import java.util.List;

/**
 * 课件变量更新 数据层
 *
 * @author finalab
 * @date 2019-08-17
 */
public interface CourseUpdateVarMapper
{
	/**
     * 查询课件变量更新信息
     *
     * @param id 课件变量更新ID
     * @return 课件变量更新信息
     */
	public CourseUpdateVar selectCourseUpdateVarById(Long id);

	/**
     * 查询课件变量更新列表
     *
     * @param courseUpdateVar 课件变量更新信息
     * @return 课件变量更新集合
     */
	public List<CourseUpdateVar> selectCourseUpdateVarList(CourseUpdateVar courseUpdateVar);

	/**
     * 新增课件变量更新
     *
     * @param courseUpdateVar 课件变量更新信息
     * @return 结果
     */
	public int insertCourseUpdateVar(CourseUpdateVar courseUpdateVar);

	/**
     * 修改课件变量更新
     *
     * @param courseUpdateVar 课件变量更新信息
     * @return 结果
     */
	public int updateCourseUpdateVar(CourseUpdateVar courseUpdateVar);

	/**
     * 删除课件变量更新
     *
     * @param id 课件变量更新ID
     * @return 结果
     */
	public int deleteCourseUpdateVarById(Long id);

	/**
     * 批量删除课件变量更新
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseUpdateVarByIds(String[] ids);

}