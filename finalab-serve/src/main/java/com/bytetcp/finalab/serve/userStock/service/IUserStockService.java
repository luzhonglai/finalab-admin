package com.bytetcp.finalab.serve.userStock.service;

import com.bytetcp.finalab.serve.userStock.domain.UserStock;

import java.util.List;

/**
 * 用户持仓明细 服务层
 * 
 * @author finalab
 * @date 2019-05-27
 */
public interface IUserStockService 
{
	/**
     * 查询用户持仓明细信息
     * 
     * @param id 用户持仓明细ID
     * @return 用户持仓明细信息
     */
	public UserStock selectUserStockById(Long id);
	
	/**
     * 查询用户持仓明细列表
     * 
     * @param userStock 用户持仓明细信息
     * @return 用户持仓明细集合
     */
	public List<UserStock> selectUserStockList(UserStock userStock);
	
	/**
     * 新增用户持仓明细
     * 
     * @param userStock 用户持仓明细信息
     * @return 结果
     */
	public int insertUserStock(UserStock userStock);
	
	/**
     * 修改用户持仓明细
     * 
     * @param userStock 用户持仓明细信息
     * @return 结果
     */
	public int updateUserStock(UserStock userStock);
		
	/**
     * 删除用户持仓明细信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserStockByIds(String ids);

	public List<UserStock> selectStockSpace(long courseId, String instanceId);
}
