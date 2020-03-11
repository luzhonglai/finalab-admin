package com.bytetcp.finalab.serve.tradingConstraint.mapper;

import com.bytetcp.finalab.serve.tradingConstraint.domain.TradingConstraint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交易约束 数据层
 *
 * @author finalab
 * @date 2019-05-01
 */
public interface TradingConstraintMapper
{
	/**
     * 查询交易约束信息
     *
     * @param id 交易约束ID
     * @return 交易约束信息
     */
	public TradingConstraint selectTradingConstraintById(Long id);

	/**
     * 查询交易约束列表
     *
     * @param tradingConstraint 交易约束信息
     * @return 交易约束集合
     */
	public List<TradingConstraint> selectTradingConstraintList(TradingConstraint tradingConstraint);

	/**
     * 新增交易约束
     *
     * @param tradingConstraint 交易约束信息
     * @return 结果
     */
	public int insertTradingConstraint(TradingConstraint tradingConstraint);

	/**
     * 修改交易约束
     *
     * @param tradingConstraint 交易约束信息
     * @return 结果
     */
	public int updateTradingConstraint(TradingConstraint tradingConstraint);

	/**
     * 删除交易约束
     *
     * @param id 交易约束ID
     * @return 结果
     */
	public int deleteTradingConstraintById(Long id);

	/**
     * 批量删除交易约束
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTradingConstraintByIds(String[] ids);


	public int insertTradingConstraintList(@Param("tradingConstraints") List<TradingConstraint> tradingConstraints);

}