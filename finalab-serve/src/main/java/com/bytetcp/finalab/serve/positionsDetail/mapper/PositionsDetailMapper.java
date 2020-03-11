package com.bytetcp.finalab.serve.positionsDetail.mapper;

import com.bytetcp.finalab.serve.positionsDetail.domain.PositionsDetail;
import java.util.List;

/**
 * 用户持仓明细 数据层
 *
 * @author finalab
 * @date 2019-08-07
 */
public interface PositionsDetailMapper
{
	/**
     * 查询持仓明细
     */
	 PositionsDetail selectPositionsDetailById(Long id);

	/**
     * 查询持仓明细
     *
     * @param positionsDetail
     * @return 持仓明细
     */
	 List<PositionsDetail> selectPositionsDetailList(PositionsDetail positionsDetail);

	/**
     * 新增用户持仓明细
     *
     * @param positionsDetail 持仓明细
     * @return 结果
     */
	 int insertPositionsDetail(PositionsDetail positionsDetail);

	/**
     * 修改用户持仓明细
     *
     * @param positionsDetail 持仓明细
     * @return 结果
     */
	 int updatePositionsDetail(PositionsDetail positionsDetail);

	/**
     * 删除用户持仓明细
     *
     * @param id 持仓明细
     * @return 结果
     */
	 int deletePositionsDetailById(Long id);

	/**
     * 批量删除用户持仓明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	 int deletePositionsDetailByIds(String[] ids);

	List<PositionsDetail> selectPositionsDetailListForCourse(PositionsDetail positionsDetail);
}