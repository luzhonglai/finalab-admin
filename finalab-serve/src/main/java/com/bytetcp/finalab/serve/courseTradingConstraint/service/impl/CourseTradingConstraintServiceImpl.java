package com.bytetcp.finalab.serve.courseTradingConstraint.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.courseTradingConstraint.domain.CourseTradingConstraint;
import com.bytetcp.finalab.serve.courseTradingConstraint.mapper.CourseTradingConstraintMapper;
import com.bytetcp.finalab.serve.courseTradingConstraint.service.ICourseTradingConstraintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课件交易约束 服务层实现
 * 
 * @author finalab
 * @date 2019-05-03
 */
@Service
public class CourseTradingConstraintServiceImpl implements ICourseTradingConstraintService 
{
	@Autowired
	private CourseTradingConstraintMapper courseTradingConstraintMapper;

	/**
     * 查询课件交易约束信息
     * 
     * @param id 课件交易约束ID
     * @return 课件交易约束信息
     */
    @Override
	public CourseTradingConstraint selectCourseTradingConstraintById(Long id)
	{
	    return courseTradingConstraintMapper.selectCourseTradingConstraintById(id);
	}
	
	/**
     * 查询课件交易约束列表
     * 
     * @param courseTradingConstraint 课件交易约束信息
     * @return 课件交易约束集合
     */
	@Override
	public List<CourseTradingConstraint> selectCourseTradingConstraintList(CourseTradingConstraint courseTradingConstraint)
	{
	    return courseTradingConstraintMapper.selectCourseTradingConstraintList(courseTradingConstraint);
	}
	
    /**
     * 新增课件交易约束
     * 
     * @param courseTradingConstraint 课件交易约束信息
     * @return 结果
     */
	@Override
	public int insertCourseTradingConstraint(CourseTradingConstraint courseTradingConstraint)
	{
	    return courseTradingConstraintMapper.insertCourseTradingConstraint(courseTradingConstraint);
	}
	
	/**
     * 修改课件交易约束
     * 
     * @param courseTradingConstraint 课件交易约束信息
     * @return 结果
     */
	@Override
	public int updateCourseTradingConstraint(CourseTradingConstraint courseTradingConstraint)
	{
	    return courseTradingConstraintMapper.updateCourseTradingConstraint(courseTradingConstraint);
	}

	/**
     * 删除课件交易约束对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseTradingConstraintByIds(String ids)
	{
		return courseTradingConstraintMapper.deleteCourseTradingConstraintByIds(Convert.toStrArray(ids));
	}
	
}
