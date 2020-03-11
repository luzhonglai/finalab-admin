package com.bytetcp.finalab.serve.positionsDetail.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bytetcp.finalab.serve.positionsDetail.mapper.PositionsDetailMapper;
import com.bytetcp.finalab.serve.positionsDetail.domain.PositionsDetail;
import com.bytetcp.finalab.serve.positionsDetail.service.IPositionsDetailService;
import com.bytetcp.finalab.common.support.Convert;

/**
 * 用户持仓明细
 *
 * @author finalab
 * @date 2019-08-07
 */
@Service
public class PositionsDetailServiceImpl implements IPositionsDetailService {
    @Autowired
    private PositionsDetailMapper positionsDetailMapper;

    /**
     * 查询用户持仓明细
     */
    @Override
    public PositionsDetail selectPositionsDetailById(Long id) {
        return positionsDetailMapper.selectPositionsDetailById(id);
    }

    /**
     * 查询用户持仓明细
     */
    @Override
    public List<PositionsDetail> selectPositionsDetailList(PositionsDetail positionsDetail) {
        return positionsDetailMapper.selectPositionsDetailList(positionsDetail);
    }

    /**
     * 新增用户持仓明细
     */
    @Override
    public int insertPositionsDetail(PositionsDetail positionsDetail) {
        return positionsDetailMapper.insertPositionsDetail(positionsDetail);
    }

    /**
     * 修改用户持仓明细
     */
    @Override
    public int updatePositionsDetail(PositionsDetail positionsDetail) {
        return positionsDetailMapper.updatePositionsDetail(positionsDetail);
    }

    /**
     * 删除用户持仓明细
     */
    @Override
    public int deletePositionsDetailByIds(String ids) {
        return positionsDetailMapper.deletePositionsDetailByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<PositionsDetail> selectPositionsDetailListForCourse(PositionsDetail positionsDetail) {
        return positionsDetailMapper.selectPositionsDetailListForCourse(positionsDetail);
    }

}
