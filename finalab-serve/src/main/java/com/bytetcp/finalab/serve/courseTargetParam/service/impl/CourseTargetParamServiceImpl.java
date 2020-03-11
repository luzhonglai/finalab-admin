package com.bytetcp.finalab.serve.courseTargetParam.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.courseTargetParam.domain.CourseTargetParam;
import com.bytetcp.finalab.serve.courseTargetParam.mapper.CourseTargetParamMapper;
import com.bytetcp.finalab.serve.courseTargetParam.service.ICourseTargetParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课件标的参数 服务层实现
 * 
 * @author finalab
 * @date 2019-05-03
 */
@Service
public class CourseTargetParamServiceImpl implements ICourseTargetParamService 
{
	@Autowired
	private CourseTargetParamMapper courseTargetParamMapper;

	/**
     * 查询课件标的参数信息
     * 
     * @param id 课件标的参数ID
     * @return 课件标的参数信息
     */
    @Override
	public CourseTargetParam selectCourseTargetParamById(Long id)
	{
	    return courseTargetParamMapper.selectCourseTargetParamById(id);
	}

	@Override
	public CourseTargetParam selectCourseTargetParam(CourseTargetParam courseTargetParam) {
		return courseTargetParamMapper.selectCourseTargetParam(courseTargetParam);
	}

	/**
     * 查询课件标的参数列表
     * 
     * @param courseTargetParam 课件标的参数信息
     * @return 课件标的参数集合
     */
	@Override
	public List<CourseTargetParam> selectCourseTargetParamList(CourseTargetParam courseTargetParam)
	{
	    return courseTargetParamMapper.selectCourseTargetParamList(courseTargetParam);
	}
	
    /**
     * 新增课件标的参数
     * 
     * @param courseTargetParam 课件标的参数信息
     * @return 结果
     */
	@Override
	public int insertCourseTargetParam(CourseTargetParam courseTargetParam)
	{
	    return courseTargetParamMapper.insertCourseTargetParam(courseTargetParam);
	}
	
	/**
     * 修改课件标的参数
     * 
     * @param courseTargetParam 课件标的参数信息
     * @return 结果
     */
	@Override
	public int updateCourseTargetParam(CourseTargetParam courseTargetParam)
	{
	    return courseTargetParamMapper.updateCourseTargetParam(courseTargetParam);
	}

	/**
     * 删除课件标的参数对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseTargetParamByIds(String ids)
	{
		return courseTargetParamMapper.deleteCourseTargetParamByIds(Convert.toStrArray(ids));
	}
	
}
