package com.bytetcp.finalab.serve.positionsTotal.service.impl;

import java.util.List;

import com.bytetcp.finalab.serve.positionsDetail.domain.PositionsDetail;
import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotalInCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bytetcp.finalab.serve.positionsTotal.mapper.PositionsTotalMapper;
import com.bytetcp.finalab.serve.positionsTotal.domain.PositionsTotal;
import com.bytetcp.finalab.serve.positionsTotal.service.IPositionsTotalService;
import com.bytetcp.finalab.common.support.Convert;

/**
 * 持仓汇总，实例-用户-股票，一只股票一个持仓
 * 当平仓时，删除改记录 服务层实现
 *
 * @author finalab
 * @date 2019-07-31
 */
@Service
public class PositionsTotalServiceImpl implements IPositionsTotalService {

    @Autowired(required = false)
    private PositionsTotalMapper positionsTotalMapper;

    /**
     * 查询持仓汇总
     */
    @Override
    public PositionsTotal selectPositionsTotalById(Long id) {
        return positionsTotalMapper.selectPositionsTotalById(id);
    }

    /**
     * 查询持仓汇总
     */
    @Override
    public List<PositionsTotal> selectPositionsTotalList(PositionsTotal positionsTotal) {
        return positionsTotalMapper.selectPositionsTotalListWithoutZero(positionsTotal);
    }
    @Override
    public List<PositionsTotal> selectPositionsTotalListNew(PositionsTotal positionsTotal) {
        return positionsTotalMapper.selectPositionsTotalListWithoutZeroNew(positionsTotal);
    }
    /**
     * 查询持仓汇总
     */
    @Override
    public List<PositionsTotal> selectPositionsTotalListWithoutZeroNewMarkToMarket(PositionsTotal positionsTotal) {
        return positionsTotalMapper.selectPositionsTotalListWithoutZeroNewMarkToMarket(positionsTotal);
    }

    /**
     * 新增持仓汇总
     */
    @Override
    public int insertPositionsTotal(PositionsTotal positionsTotal) {
        return positionsTotalMapper.insertPositionsTotal(positionsTotal);
    }

    /**
     * 修改持仓汇总
     */
    @Override
    public int updatePositionsTotal(PositionsTotal positionsTotal) {
        return positionsTotalMapper.updatePositionsTotal(positionsTotal);
    }

    /**
     * 删除持仓汇总
     */
    @Override
    public int deletePositionsTotalByIds(String ids) {
        return positionsTotalMapper.deletePositionsTotalByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<PositionsTotalInCourse> selectPositionsTotalListInCourse(String instanceId) {
        return positionsTotalMapper.selectPositionsTotalListInCourse(instanceId);
    }

    @Override
    public List<PositionsTotal> selectPTListForCourse(String instanceId) {
        return positionsTotalMapper.selectPTListForCourse(instanceId);
    }

    @Override
    public PositionsTotal findPositionTotal(PositionsTotal pTotalParam) {
        return positionsTotalMapper.findPositionTotal(pTotalParam);
    }

    @Override
    public List<PositionsTotal> selectPosiTotalForDerived(PositionsTotal pTotalParam) {
        return positionsTotalMapper.selectPositionsTotalList(pTotalParam);
    }

}
