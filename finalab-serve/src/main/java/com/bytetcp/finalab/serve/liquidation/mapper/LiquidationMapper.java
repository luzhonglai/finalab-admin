package com.bytetcp.finalab.serve.liquidation.mapper;

import com.bytetcp.finalab.serve.liquidation.domain.Liquidation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 清算 数据层
 *
 * @author finalab
 * @date 2019-05-01
 */
public interface LiquidationMapper
{
	/**
     * 查询清算信息
     *
     * @param id 清算ID
     * @return 清算信息
     */
	public Liquidation selectLiquidationById(Long id);

	/**
     * 查询清算列表
     *
     * @param liquidation 清算信息
     * @return 清算集合
     */
	public List<Liquidation> selectLiquidationList(Liquidation liquidation);

	/**
     * 新增清算
     *
     * @param liquidation 清算信息
     * @return 结果
     */
	public int insertLiquidation(Liquidation liquidation);

	/**
     * 修改清算
     *
     * @param liquidation 清算信息
     * @return 结果
     */
	public int updateLiquidation(Liquidation liquidation);

	/**
     * 删除清算
     *
     * @param id 清算ID
     * @return 结果
     */
	public int deleteLiquidationById(Long id);

	/**
     * 批量删除清算
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteLiquidationByIds(String[] ids);



	int insertLiquidationList(@Param("liquidations") List<Liquidation> liquidations);

}
