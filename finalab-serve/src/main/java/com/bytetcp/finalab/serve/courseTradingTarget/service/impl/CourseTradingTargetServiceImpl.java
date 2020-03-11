package com.bytetcp.finalab.serve.courseTradingTarget.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.courseTradingTarget.domain.CourseTradingTarget;
import com.bytetcp.finalab.serve.courseTradingTarget.mapper.CourseTradingTargetMapper;
import com.bytetcp.finalab.serve.courseTradingTarget.service.ICourseTradingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标的 服务层实现
 * 
 * @author finalab
 * @date 2019-05-03
 */
@Service
public class CourseTradingTargetServiceImpl implements ICourseTradingTargetService 
{
	@Autowired
	private CourseTradingTargetMapper courseTradingTargetMapper;

	/**
     * 查询标的信息
     * 
     * @param id 标的ID
     * @return 标的信息
     */
    @Override
	public CourseTradingTarget selectCourseTradingTargetById(Long id)
	{
	    return courseTradingTargetMapper.selectCourseTradingTargetById(id);
	}
	
	/**
     * 查询标的列表
     * 
     * @param courseTradingTarget 标的信息
     * @return 标的集合
     */
	@Override
	public List<CourseTradingTarget> selectCourseTradingTargetList(CourseTradingTarget courseTradingTarget)
	{
	    return courseTradingTargetMapper.selectCourseTradingTargetList(courseTradingTarget);
	}
	
    /**
     * 新增标的
     * 
     * @param courseTradingTarget 标的信息
     * @return 结果
     */
	@Override
	public int insertCourseTradingTarget(CourseTradingTarget courseTradingTarget)
	{
	    return courseTradingTargetMapper.insertCourseTradingTarget(courseTradingTarget);
	}
	
	/**
     * 修改标的
     * 
     * @param courseTradingTarget 标的信息
     * @return 结果
     */
	@Override
	public int updateCourseTradingTarget(CourseTradingTarget courseTradingTarget)
	{
	    return courseTradingTargetMapper.updateCourseTradingTarget(courseTradingTarget);
	}

	/**
     * 删除标的对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseTradingTargetByIds(String ids)
	{
		return courseTradingTargetMapper.deleteCourseTradingTargetByIds(Convert.toStrArray(ids));
	}
	
}
