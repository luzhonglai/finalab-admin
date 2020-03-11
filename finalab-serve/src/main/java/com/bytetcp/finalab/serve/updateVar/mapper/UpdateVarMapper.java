package com.bytetcp.finalab.serve.updateVar.mapper;

import com.bytetcp.finalab.serve.updateVar.domain.UpdateVar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 变量更新 数据层
 *
 * @author finalab
 * @date 2019-08-17
 */
public interface UpdateVarMapper
{
	/**
     * 查询变量更新信息
     *
     * @param id 变量更新ID
     * @return 变量更新信息
     */
	public UpdateVar selectUpdateVarById(Long id);

	/**
     * 查询变量更新列表
     *
     * @param updateVar 变量更新信息
     * @return 变量更新集合
     */
	public List<UpdateVar> selectUpdateVarList(UpdateVar updateVar);

	/**
     * 新增变量更新
     *
     * @param updateVar 变量更新信息
     * @return 结果
     */
	public int insertUpdateVar(UpdateVar updateVar);

	/**
     * 修改变量更新
     *
     * @param updateVar 变量更新信息
     * @return 结果
     */
	public int updateUpdateVar(UpdateVar updateVar);

	/**
     * 删除变量更新
     *
     * @param id 变量更新ID
     * @return 结果
     */
	public int deleteUpdateVarById(Long id);

	/**
     * 批量删除变量更新
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUpdateVarByIds(String[] ids);

    boolean insertUpdateVarList(@Param("updateVarList")List<UpdateVar> updateVarList);
}