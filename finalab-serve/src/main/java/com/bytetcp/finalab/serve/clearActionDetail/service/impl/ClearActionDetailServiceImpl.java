package com.bytetcp.finalab.serve.clearActionDetail.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bytetcp.finalab.serve.clearActionDetail.mapper.ClearActionDetailMapper;
import com.bytetcp.finalab.serve.clearActionDetail.domain.ClearActionDetail;
import com.bytetcp.finalab.serve.clearActionDetail.service.IClearActionDetailService;
import com.bytetcp.finalab.common.support.Convert;

/**
 * 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏 服务层实现
 *
 * @author finalab
 * @date 2019-08-02
 */
@Service
public class ClearActionDetailServiceImpl implements IClearActionDetailService {

    @Autowired(required = false)
    private ClearActionDetailMapper clearActionDetailMapper;

    /**
     * 查询记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     *
     * @param id 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏ID
     * @return 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     */
    @Override
    public ClearActionDetail selectClearActionDetailById(Long id) {
        return clearActionDetailMapper.selectClearActionDetailById(id);
    }

    /**
     * 查询记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏列表
     *
     * @param clearActionDetail 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     * @return 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏集合
     */
    @Override
    public List<ClearActionDetail> selectClearActionDetailList(ClearActionDetail clearActionDetail) {
        return clearActionDetailMapper.selectClearActionDetailList(clearActionDetail);
    }

    /**
     * 新增记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     *
     * @param clearActionDetail 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     * @return 结果
     */
    @Override
    public int insertClearActionDetail(ClearActionDetail clearActionDetail) {
        return clearActionDetailMapper.insertClearActionDetail(clearActionDetail);
    }

    /**
     * 修改记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏
     *
     * @param clearActionDetail 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏信息
     * @return 结果
     */
    @Override
    public int updateClearActionDetail(ClearActionDetail clearActionDetail) {
        return clearActionDetailMapper.updateClearActionDetail(clearActionDetail);
    }

    /**
     * 删除记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteClearActionDetailByIds(String ids) {
        return clearActionDetailMapper.deleteClearActionDetailByIds(Convert.toStrArray(ids));
    }

    @Override
    public Double countProfit(ClearActionDetail clearActionDetail) {
        return clearActionDetailMapper.countProfit(clearActionDetail);
    }

}
