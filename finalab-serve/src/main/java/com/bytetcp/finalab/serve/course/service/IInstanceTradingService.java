package com.bytetcp.finalab.serve.course.service;

import com.bytetcp.finalab.serve.course.domain.InstanceTrading;

import java.util.List;

/**
 * 交易明细 服务层
 * 
 * @author finalab
 * @date 2019-05-22
 */
public interface IInstanceTradingService 
{
	/**
     * 查询交易明细信息
     * 
     * @param id 交易明细ID
     * @return 交易明细信息
     */
	public InstanceTrading selectInstanceTradingById(Long id);
	
	/**
     * 查询交易明细列表
     * 
     * @param instanceTrading 交易明细信息
     * @return 交易明细集合
     */
	public List<InstanceTrading> selectInstanceTradingList(InstanceTrading instanceTrading);
	
	/**
     * 新增交易明细
     * 
     * @param instanceTrading 交易明细信息
     * @return 结果
     */
	public int insertInstanceTrading(InstanceTrading instanceTrading);
	
	/**
     * 修改交易明细
     * 
     * @param instanceTrading 交易明细信息
     * @return 结果
     */
	public int updateInstanceTrading(InstanceTrading instanceTrading);
		
	/**
     * 删除交易明细信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteInstanceTradingByIds(String ids);
	
}
