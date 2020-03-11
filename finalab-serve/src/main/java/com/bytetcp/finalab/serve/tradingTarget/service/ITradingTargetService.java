package com.bytetcp.finalab.serve.tradingTarget.service;

import com.bytetcp.finalab.serve.cases.service.IExcelHandler;
import com.bytetcp.finalab.serve.tradingTarget.domain.TradingTarget;

import java.util.List;

/**
 * 标的 服务层
 * 
 * @author finalab
 * @date 2019-05-01
 */
public interface ITradingTargetService extends IExcelHandler
{
	/**
     * 查询标的信息
     * 
     * @param id 标的ID
     * @return 标的信息
     */
	public TradingTarget selectTradingTargetById(Long id);
	
	/**
     * 查询标的列表
     * 
     * @param tradingTarget 标的信息
     * @return 标的集合
     */
	public List<TradingTarget> selectTradingTargetList(TradingTarget tradingTarget);
	
	/**
     * 新增标的
     * 
     * @param tradingTarget 标的信息
     * @return 结果
     */
	public int insertTradingTarget(TradingTarget tradingTarget);
	
	/**
     * 修改标的
     * 
     * @param tradingTarget 标的信息
     * @return 结果
     */
	public int updateTradingTarget(TradingTarget tradingTarget);
		
	/**
     * 删除标的信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTradingTargetByIds(String ids);


}
