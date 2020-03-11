package com.bytetcp.finalab.serve.priceMove.service;

import com.bytetcp.finalab.serve.cases.service.IExcelHandler;
import com.bytetcp.finalab.serve.priceMove.domain.PriceMove;

import java.util.List;

/**
 * 价格走势 服务层
 * 
 * @author finalab
 * @date 2019-05-02
 */
public interface IPriceMoveService extends IExcelHandler
{
	/**
     * 查询价格走势信息
     * 
     * @param id 价格走势ID
     * @return 价格走势信息
     */
	public PriceMove selectPriceMoveById(Long id);
	
	/**
     * 查询价格走势列表
     * 
     * @param priceMove 价格走势信息
     * @return 价格走势集合
     */
	public List<PriceMove> selectPriceMoveList(PriceMove priceMove);
	
	/**
     * 新增价格走势
     * 
     * @param priceMove 价格走势信息
     * @return 结果
     */
	public int insertPriceMove(PriceMove priceMove);
	
	/**
     * 修改价格走势
     * 
     * @param priceMove 价格走势信息
     * @return 结果
     */
	public int updatePriceMove(PriceMove priceMove);
		
	/**
     * 删除价格走势信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePriceMoveByIds(String ids);
	
}
