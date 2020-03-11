package com.bytetcp.finalab.serve.course.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.course.domain.InstanceTrading;
import com.bytetcp.finalab.serve.course.mapper.InstanceTradingMapper;
import com.bytetcp.finalab.serve.course.service.IInstanceTradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 交易明细 服务层实现
 * 
 * @author finalab
 * @date 2019-05-22
 */
@Service
public class InstanceTradingServiceImpl implements IInstanceTradingService 
{
	@Autowired
	private InstanceTradingMapper instanceTradingMapper;

	/**
     * 查询交易明细信息
     * 
     * @param id 交易明细ID
     * @return 交易明细信息
     */
    @Override
	public InstanceTrading selectInstanceTradingById(Long id)
	{
	    return instanceTradingMapper.selectInstanceTradingById(id);
	}
	
	/**
     * 查询交易明细列表
     * 
     * @param instanceTrading 交易明细信息
     * @return 交易明细集合
     */
	@Override
	public List<InstanceTrading> selectInstanceTradingList(InstanceTrading instanceTrading)
	{
	    return instanceTradingMapper.selectInstanceTradingList(instanceTrading);
	}
	
    /**
     * 新增交易明细
     * 
     * @param instanceTrading 交易明细信息
     * @return 结果
     */
	@Override
	public int insertInstanceTrading(InstanceTrading instanceTrading)
	{
	    return instanceTradingMapper.insertInstanceTrading(instanceTrading);
	}
	
	/**
     * 修改交易明细
     * 
     * @param instanceTrading 交易明细信息
     * @return 结果
     */
	@Override
	public int updateInstanceTrading(InstanceTrading instanceTrading)
	{
	    return instanceTradingMapper.updateInstanceTrading(instanceTrading);
	}

	/**
     * 删除交易明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteInstanceTradingByIds(String ids)
	{
		return instanceTradingMapper.deleteInstanceTradingByIds(Convert.toStrArray(ids));
	}
	
}
