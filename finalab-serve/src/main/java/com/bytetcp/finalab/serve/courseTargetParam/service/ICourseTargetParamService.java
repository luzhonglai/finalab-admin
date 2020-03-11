package com.bytetcp.finalab.serve.courseTargetParam.service;

import com.bytetcp.finalab.serve.courseTargetParam.domain.CourseTargetParam;

import java.util.List;

/**
 * 课件标的参数 服务层
 * 
 * @author finalab
 * @date 2019-05-03
 */
public interface ICourseTargetParamService 
{
	/**
     * 查询课件标的参数信息
     * 
     * @param id 课件标的参数ID
     * @return 课件标的参数信息
     */
	public CourseTargetParam selectCourseTargetParamById(Long id);

	public CourseTargetParam selectCourseTargetParam(CourseTargetParam courseTargetParam);

	/**
     * 查询课件标的参数列表
     * 
     * @param courseTargetParam 课件标的参数信息
     * @return 课件标的参数集合
     */
	public List<CourseTargetParam> selectCourseTargetParamList(CourseTargetParam courseTargetParam);
	
	/**
     * 新增课件标的参数
     * 
     * @param courseTargetParam 课件标的参数信息
     * @return 结果
     */
	public int insertCourseTargetParam(CourseTargetParam courseTargetParam);
	
	/**
     * 修改课件标的参数
     * 
     * @param courseTargetParam 课件标的参数信息
     * @return 结果
     */
	public int updateCourseTargetParam(CourseTargetParam courseTargetParam);
		
	/**
     * 删除课件标的参数信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseTargetParamByIds(String ids);
	
}
