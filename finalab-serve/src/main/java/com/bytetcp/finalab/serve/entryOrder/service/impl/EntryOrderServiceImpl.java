package com.bytetcp.finalab.serve.entryOrder.service.impl;

import java.util.List;

import com.bytetcp.finalab.common.base.HttpResult;
import com.bytetcp.finalab.serve.config.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bytetcp.finalab.serve.entryOrder.mapper.EntryOrderMapper;
import com.bytetcp.finalab.serve.entryOrder.domain.EntryOrder;
import com.bytetcp.finalab.serve.entryOrder.service.IEntryOrderService;
import com.bytetcp.finalab.common.support.Convert;
import org.springframework.web.client.RestTemplate;

/**
 * 挂单 服务层实现
 *
 * @author finalab
 * @date 2019-07-28
 */
@Service
public class EntryOrderServiceImpl implements IEntryOrderService {

    @Value("${finalab.server.cancelUrl}")
    private String cancelUrl;


    @Autowired(required = false)
    private EntryOrderMapper entryOrderMapper;

    @Autowired
    private HttpMethod httpMethod;

    /**
     * 查询挂单信息
     *
     * @param id 挂单ID
     * @return 挂单信息
     */
    @Override
    public EntryOrder selectEntryOrderById(Long id) {
        return entryOrderMapper.selectEntryOrderById(id);
    }

    /**
     * 查询挂单列表
     *
     * @param entryOrder 挂单信息
     * @return 挂单集合
     */
    @Override
    public List<EntryOrder> selectEntryOrderList(EntryOrder entryOrder) {
        return entryOrderMapper.selectEntryOrderList(entryOrder);
    }

    /**
     * 新增挂单
     *
     * @param entryOrder 挂单信息
     * @return 结果
     */
    @Override
    public int insertEntryOrder(EntryOrder entryOrder) {
        return entryOrderMapper.insertEntryOrder(entryOrder);
    }

    /**
     * 修改挂单
     *
     * @param entryOrder 挂单信息
     * @return 结果
     */
    @Override
    public int updateEntryOrder(EntryOrder entryOrder) {
        return entryOrderMapper.updateEntryOrder(entryOrder);
    }

    /**
     * 删除挂单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteEntryOrderByIds(String ids) {
        return entryOrderMapper.deleteEntryOrderByIds(Convert.toStrArray(ids));
    }

    @Override
    public HttpResult sendHttp(EntryOrder entryOrder) {
        return httpMethod.send(entryOrder, cancelUrl);
    }

}
