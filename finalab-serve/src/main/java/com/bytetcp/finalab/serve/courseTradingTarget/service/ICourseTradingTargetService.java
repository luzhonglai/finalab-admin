package com.bytetcp.finalab.serve.courseTradingTarget.service;

import com.bytetcp.finalab.serve.courseTradingTarget.domain.CourseTradingTarget;

import java.util.List;

/**
 * 标的 服务层
 * 
 * @author finalab
 * @date 2019-05-03
 */
public interface ICourseTradingTargetService 
{
	/**
     * 查询标的信息
     * 
     * @param id 标的ID
     * @return 标的信息
     */
	public CourseTradingTarget selectCourseTradingTargetById(Long id);
	
	/**
     * 查询标的列表
     * 
     * @param courseTradingTarget 标的信息
     * @return 标的集合
     */
	public List<CourseTradingTarget> selectCourseTradingTargetList(CourseTradingTarget courseTradingTarget);
	
	/**
     * 新增标的
     * 
     * @param courseTradingTarget 标的信息
     * @return 结果
     */
	public int insertCourseTradingTarget(CourseTradingTarget courseTradingTarget);
	
	/**
     * 修改标的
     * 
     * @param courseTradingTarget 标的信息
     * @return 结果
     */
	public int updateCourseTradingTarget(CourseTradingTarget courseTradingTarget);
		
	/**
     * 删除标的信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseTradingTargetByIds(String ids);
	
}
