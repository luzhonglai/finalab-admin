package com.bytetcp.finalab.serve.positionsDetail.service;

import com.bytetcp.finalab.serve.positionsDetail.domain.PositionsDetail;
import java.util.List;

/**
 * 用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数 服务层
 * 
 * @author finalab
 * @date 2019-08-07
 */
public interface IPositionsDetailService 
{
	/**
     * 查询用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数信息
     * 
     * @param id 用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数ID
     * @return 用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数信息
     */
	public PositionsDetail selectPositionsDetailById(Long id);
	
	/**
     * 查询用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数列表
     * 
     * @param positionsDetail 用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数信息
     * @return 用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数集合
     */
	public List<PositionsDetail> selectPositionsDetailList(PositionsDetail positionsDetail);
	
	/**
     * 新增用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数
     * 
     * @param positionsDetail 用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数信息
     * @return 结果
     */
	public int insertPositionsDetail(PositionsDetail positionsDetail);
	
	/**
     * 修改用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数
     * 
     * @param positionsDetail 用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数信息
     * @return 结果
     */
	public int updatePositionsDetail(PositionsDetail positionsDetail);
		
	/**
     * 删除用户持仓明细，实例-用户-股票，记录每次加/减仓位
eg：买单成交：则+成交数
eg：卖单成交：则-成交数信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePositionsDetailByIds(String ids);

    List<PositionsDetail> selectPositionsDetailListForCourse(PositionsDetail positionsDetail);

    List<PositionsDetail> selectPositionsDetailListForFinancialType(PositionsDetail positionsDetail);
}
