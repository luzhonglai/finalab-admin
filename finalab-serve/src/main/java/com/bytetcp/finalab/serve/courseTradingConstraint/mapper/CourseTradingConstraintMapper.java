package com.bytetcp.finalab.serve.courseTradingConstraint.mapper;

import com.bytetcp.finalab.serve.courseTradingConstraint.domain.CourseTradingConstraint;

import java.util.List;

/**
 * 课件交易约束 数据层
 *
 * @author finalab
 * @date 2019-05-03
 */
public interface CourseTradingConstraintMapper
{
	/**
     * 查询课件交易约束信息
     *
     * @param id 课件交易约束ID
     * @return 课件交易约束信息
     */
	public CourseTradingConstraint selectCourseTradingConstraintById(Long id);

	/**
     * 查询课件交易约束列表
     *
     * @param courseTradingConstraint 课件交易约束信息
     * @return 课件交易约束集合
     */
	public List<CourseTradingConstraint> selectCourseTradingConstraintList(CourseTradingConstraint courseTradingConstraint);

	/**
     * 新增课件交易约束
     *
     * @param courseTradingConstraint 课件交易约束信息
     * @return 结果
     */
	public int insertCourseTradingConstraint(CourseTradingConstraint courseTradingConstraint);

	/**
     * 修改课件交易约束
     *
     * @param courseTradingConstraint 课件交易约束信息
     * @return 结果
     */
	public int updateCourseTradingConstraint(CourseTradingConstraint courseTradingConstraint);

	/**
     * 删除课件交易约束
     *
     * @param id 课件交易约束ID
     * @return 结果
     */
	public int deleteCourseTradingConstraintById(Long id);

	/**
     * 批量删除课件交易约束
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseTradingConstraintByIds(String[] ids);

}