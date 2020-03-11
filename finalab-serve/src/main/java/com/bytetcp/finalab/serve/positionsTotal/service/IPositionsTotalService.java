package com.bytetcp.finalab.serve.positionsTotal.service;

import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotal;
import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotalInCourse;

import java.util.List;

/**
 * 持仓汇总，实例-用户-股票，一只股票一个持仓
 * 当平仓时，删除改记录 服务层
 *
 * @author finalab
 * @date 2019-07-31
 */
public interface IPositionsTotalService {
    /**
     * 查询持仓汇总
     */
    PositionsTotal selectPositionsTotalById(Long id);

    /**
     * 查询持仓汇总
     */
    List<PositionsTotal> selectPositionsTotalList(PositionsTotal positionsTotal);

    List<PositionsTotal> selectPositionsTotalOrigin(PositionsTotal positionsTotal);

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
    int deletePositionsTotalByIds(String ids);

    List<PositionsTotalInCourse> selectPositionsTotalListInCourse(String instanceId);

    /**
     * 成交明细查询（老师页面）
     * @param instanceId
     * @return
     */
    List<PositionsTotal> selectPTListForCourse(String instanceId);

    PositionsTotal findPositionTotal(PositionsTotal pTotalParam);

    List<PositionsTotal> selectPosiTotalForDerived(PositionsTotal pTotalParam);
}
