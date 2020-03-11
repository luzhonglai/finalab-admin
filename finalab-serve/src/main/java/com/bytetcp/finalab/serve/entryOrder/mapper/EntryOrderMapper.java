package com.bytetcp.finalab.serve.entryOrder.mapper;

import com.bytetcp.finalab.serve.entryOrder.domain.EntryOrder;
import java.util.List;

/**
 * 挂单 数据层
 *
 * @author finalab
 * @date 2019-07-28
 */
public interface EntryOrderMapper
{
	/**
     * 查询挂单信息
     *
     * @param id 挂单ID
     * @return 挂单信息
     */
	public EntryOrder selectEntryOrderById(Long id);

	/**
     * 查询挂单列表
     *
     * @param entryOrder 挂单信息
     * @return 挂单集合
     */
	public List<EntryOrder> selectEntryOrderList(EntryOrder entryOrder);

	/**
     * 新增挂单
     *
     * @param entryOrder 挂单信息
     * @return 结果
     */
	public int insertEntryOrder(EntryOrder entryOrder);

	/**
     * 修改挂单
     *
     * @param entryOrder 挂单信息
     * @return 结果
     */
	public int updateEntryOrder(EntryOrder entryOrder);

	/**
     * 删除挂单
     *
     * @param id 挂单ID
     * @return 结果
     */
	public int deleteEntryOrderById(Long id);

	/**
     * 批量删除挂单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEntryOrderByIds(String[] ids);

}