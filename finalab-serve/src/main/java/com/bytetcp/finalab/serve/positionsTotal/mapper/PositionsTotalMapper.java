package com.bytetcp.finalab.serve.positionsTotal.mapper;

import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotal;
import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotalInCourse;

import java.util.List;

/**
 * 持仓汇总，实例-用户-股票，一只股票一个持仓
 * 当平仓时，删除改记录 数据层
 *
 * @author finalab
 * @date 2019-07-31
 */
public interface PositionsTotalMapper {
    /**
     * 查询持仓汇总
     */
    PositionsTotal selectPositionsTotalById(Long id);

    /**
     * 查询持仓汇总
     */
    List<PositionsTotal> selectPositionsTotalList(PositionsTotal positionsTotal);

    /***
     * 过滤掉持仓量为0的
     * @param positionsTotal
     * @return
     */
    List<PositionsTotal> selectPositionsTotalListWithoutZero(PositionsTotal positionsTotal);

    /**
     * 新增持仓汇总
     */
    int insertPositionsTotal(PositionsTotal positionsTotal);

    /**
     * 修改持仓汇总
     */
    int updatePositionsTotal(PositionsTotal positionsTotal);

    /**
     * 删除持仓汇总
     */
    int deletePositionsTotalById(Long id);

    /**
     * 批量删除持仓汇总
     */
    int deletePositionsTotalByIds(String[] ids);

    /**
     * 课件详情持仓量列表
     */
    List<PositionsTotalInCourse> selectPositionsTotalListInCourse(String instanceId);

    List<PositionsTotal> selectPTListForCourse(String instanceId);

    PositionsTotal findPositionTotal(PositionsTotal pTotalParam);
}