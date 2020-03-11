package com.bytetcp.finalab.serve.userStock.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.userStock.domain.UserStock;
import com.bytetcp.finalab.serve.userStock.mapper.UserStockMapper;
import com.bytetcp.finalab.serve.userStock.service.IUserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户持仓明细 服务层实现
 * 
 * @author finalab
 * @date 2019-05-27
 */
@Service
public class UserStockServiceImpl implements IUserStockService 
{
	@Autowired
	private UserStockMapper userStockMapper;

	/**
     * 查询用户持仓明细信息
     * 
     * @param id 用户持仓明细ID
     * @return 用户持仓明细信息
     */
    @Override
	public UserStock selectUserStockById(Long id)
	{
	    return userStockMapper.selectUserStockById(id);
	}
	
	/**
     * 查询用户持仓明细列表
     * 
     * @param userStock 用户持仓明细信息
     * @return 用户持仓明细集合
     */
	@Override
	public List<UserStock> selectUserStockList(UserStock userStock)
	{
	    return userStockMapper.selectUserStockList(userStock);
	}
	
    /**
     * 新增用户持仓明细
     * 
     * @param userStock 用户持仓明细信息
     * @return 结果
     */
	@Override
	public int insertUserStock(UserStock userStock)
	{
	    return userStockMapper.insertUserStock(userStock);
	}
	
	/**
     * 修改用户持仓明细
     * 
     * @param userStock 用户持仓明细信息
     * @return 结果
     */
	@Override
	public int updateUserStock(UserStock userStock)
	{
	    return userStockMapper.updateUserStock(userStock);
	}

	/**
     * 删除用户持仓明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserStockByIds(String ids)
	{
		return userStockMapper.deleteUserStockByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<UserStock> selectStockSpace(long courseId , String instanceId){
		return userStockMapper.selectStockSpace(courseId, instanceId);
	}
	
}
