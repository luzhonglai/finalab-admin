package com.bytetcp.finalab.serve.courseUpdateVar.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bytetcp.finalab.serve.courseUpdateVar.mapper.CourseUpdateVarMapper;
import com.bytetcp.finalab.serve.courseUpdateVar.domain.CourseUpdateVar;
import com.bytetcp.finalab.serve.courseUpdateVar.service.ICourseUpdateVarService;
import com.bytetcp.finalab.common.support.Convert;

/**
 * 课件变量更新 服务层实现
 * 
 * @author finalab
 * @date 2019-08-17
 */
@Service
public class CourseUpdateVarServiceImpl implements ICourseUpdateVarService 
{
	@Autowired
	private CourseUpdateVarMapper courseUpdateVarMapper;

	/**
     * 查询课件变量更新信息
     * 
     * @param id 课件变量更新ID
     * @return 课件变量更新信息
     */
    @Override
	public CourseUpdateVar selectCourseUpdateVarById(Long id)
	{
	    return courseUpdateVarMapper.selectCourseUpdateVarById(id);
	}
	
	/**
     * 查询课件变量更新列表
     * 
     * @param courseUpdateVar 课件变量更新信息
     * @return 课件变量更新集合
     */
	@Override
	public List<CourseUpdateVar> selectCourseUpdateVarList(CourseUpdateVar courseUpdateVar)
	{
	    return courseUpdateVarMapper.selectCourseUpdateVarList(courseUpdateVar);
	}
	
    /**
     * 新增课件变量更新
     * 
     * @param courseUpdateVar 课件变量更新信息
     * @return 结果
     */
	@Override
	public int insertCourseUpdateVar(CourseUpdateVar courseUpdateVar)
	{
	    return courseUpdateVarMapper.insertCourseUpdateVar(courseUpdateVar);
	}
	
	/**
     * 修改课件变量更新
     * 
     * @param courseUpdateVar 课件变量更新信息
     * @return 结果
     */
	@Override
	public int updateCourseUpdateVar(CourseUpdateVar courseUpdateVar)
	{
	    return courseUpdateVarMapper.updateCourseUpdateVar(courseUpdateVar);
	}

	/**
     * 删除课件变量更新对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseUpdateVarByIds(String ids)
	{
		return courseUpdateVarMapper.deleteCourseUpdateVarByIds(Convert.toStrArray(ids));
	}
	
}
