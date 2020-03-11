package com.bytetcp.finalab.serve.clearActionDetail.mapper;

import com.bytetcp.finalab.serve.clearActionDetail.domain.ClearActionDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏 数据层
 *
 * @author finalab
 * @date 2019-08-02
 */
public interface ClearActionDetailMapper {
    /**
     * 查询记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     *
     * @param id 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏ID
     * @return 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     */
    public ClearActionDetail selectClearActionDetailById(Long id);

    /**
     * 查询记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏列表
     *
     * @param clearActionDetail 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     * @return 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏集合
     */
    public List<ClearActionDetail> selectClearActionDetailList(ClearActionDetail clearActionDetail);

    /**
     * 新增记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     *
     * @param clearActionDetail 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     * @return 结果
     */
    public int insertClearActionDetail(ClearActionDetail clearActionDetail);

    /**
     * 修改记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     *
     * @param clearActionDetail 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     * @return 结果
     */
    public int updateClearActionDetail(ClearActionDetail clearActionDetail);

    /**
     * 删除记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     *
     * @param id 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏ID
     * @return 结果
     */
    public int deleteClearActionDetailById(Long id);

    /**
     * 批量删除记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteClearActionDetailByIds(String[] ids);

    Double countProfit(ClearActionDetail clearActionDetail);
}