package com.bytetcp.finalab.serve.userStock.mapper;

import com.bytetcp.finalab.serve.userStock.domain.UserStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户持仓明细 数据层
 *
 * @author finalab
 * @date 2019-05-27
 */
public interface UserStockMapper
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
     * 删除用户持仓明细
     *
     * @param id 用户持仓明细ID
     * @return 结果
     */
	public int deleteUserStockById(Long id);

	/**
     * 批量删除用户持仓明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserStockByIds(String[] ids);

	public List<UserStock> selectStockSpace(@Param("courseId") long courseId, @Param("instanceId") String instanceId);
}