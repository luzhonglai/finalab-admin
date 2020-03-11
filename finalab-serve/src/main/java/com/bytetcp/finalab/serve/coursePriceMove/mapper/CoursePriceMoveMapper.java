package com.bytetcp.finalab.serve.coursePriceMove.mapper;

import com.bytetcp.finalab.serve.coursePriceMove.domain.CoursePriceMove;

import java.util.List;

/**
 * 价格走势 数据层
 *
 * @author finalab
 * @date 2019-05-03
 */
public interface CoursePriceMoveMapper
{
	/**
     * 查询价格走势信息
     *
     * @param id 价格走势ID
     * @return 价格走势信息
     */
	public CoursePriceMove selectCoursePriceMoveById(Long id);

	/**
     * 查询价格走势列表
     *
     * @param coursePriceMove 价格走势信息
     * @return 价格走势集合
     */
	public List<CoursePriceMove> selectCoursePriceMoveList(CoursePriceMove coursePriceMove);

	/**
     * 新增价格走势
     *
     * @param coursePriceMove 价格走势信息
     * @return 结果
     */
	public int insertCoursePriceMove(CoursePriceMove coursePriceMove);

	/**
     * 修改价格走势
     *
     * @param coursePriceMove 价格走势信息
     * @return 结果
     */
	public int updateCoursePriceMove(CoursePriceMove coursePriceMove);

	/**
     * 删除价格走势
     *
     * @param id 价格走势ID
     * @return 结果
     */
	public int deleteCoursePriceMoveById(Long id);

	/**
     * 批量删除价格走势
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCoursePriceMoveByIds(String[] ids);

}