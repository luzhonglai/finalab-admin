package com.bytetcp.finalab.serve.tradingTarget.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.tradingTarget.domain.TradingTarget;
import com.bytetcp.finalab.serve.tradingTarget.mapper.TradingTargetMapper;
import com.bytetcp.finalab.serve.tradingTarget.service.ITradingTargetService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 标的 服务层实现
 * 
 * @author finalab
 * @date 2019-05-01
 */
@Service
public class TradingTargetServiceImpl implements ITradingTargetService 
{
	private static Logger logger = LoggerFactory.getLogger(TradingTargetServiceImpl.class);

	@Autowired
	private TradingTargetMapper tradingTargetMapper;

	/**
     * 查询标的信息
     * 
     * @param id 标的ID
     * @return 标的信息
     */
    @Override
	public TradingTarget selectTradingTargetById(Long id)
	{
	    return tradingTargetMapper.selectTradingTargetById(id);
	}
	
	/**
     * 查询标的列表
     * 
     * @param tradingTarget 标的信息
     * @return 标的集合
     */
	@Override
	public List<TradingTarget> selectTradingTargetList(TradingTarget tradingTarget)
	{
	    return tradingTargetMapper.selectTradingTargetList(tradingTarget);
	}
	
    /**
     * 新增标的
     * 
     * @param tradingTarget 标的信息
     * @return 结果
     */
	@Override
	public int insertTradingTarget(TradingTarget tradingTarget)
	{
	    return tradingTargetMapper.insertTradingTarget(tradingTarget);
	}
	
	/**
     * 修改标的
     * 
     * @param tradingTarget 标的信息
     * @return 结果
     */
	@Override
	public int updateTradingTarget(TradingTarget tradingTarget)
	{
	    return tradingTargetMapper.updateTradingTarget(tradingTarget);
	}

	/**
     * 删除标的对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTradingTargetByIds(String ids)
	{
		return tradingTargetMapper.deleteTradingTargetByIds(Convert.toStrArray(ids));
	}

	@Override
	public boolean handlExcelSheet(Sheet TradingTargetSheet, Long caseId) {
		if (Objects.isNull(TradingTargetSheet)) {
			return false;
		}
		int skipRows = 2;//跳过sheet的前两行数据( )
		int rownums = TradingTargetSheet.getPhysicalNumberOfRows();
		List<TradingTarget> tradingTargets = new ArrayList<>();

		for (int i = skipRows; i < rownums; i++) {
			TradingTarget tradingTarget = new TradingTarget();
			Row row = TradingTargetSheet.getRow(i);
			tradingTarget.setCaseId(caseId);
			if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)))) {
				break;
			}
			tradingTarget.setTargetNum(String.valueOf(ExcelUtil.getCellValue(row, 0)));
			tradingTarget.setTargetType(String.valueOf(ExcelUtil.getCellValue(row, 1)));
			tradingTarget.setTargetName(String.valueOf(ExcelUtil.getCellValue(row, 2)));
			tradingTargets.add(tradingTarget);
		}
		// 检查改案例id下是否已经有 ’标的‘， 如果有 先删除在插入
		TradingTarget tradingTarget = new TradingTarget();
		tradingTarget.setCaseId(caseId);
		List<TradingTarget> findTradingTargets = tradingTargetMapper.selectTradingTargetList(tradingTarget);
		if (!CollectionUtils.isEmpty(findTradingTargets) && findTradingTargets.size() > 0) {
			List<String> collect = findTradingTargets.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
			String[] ids = collect.toArray(new String[collect.size()]);
			logger.info("案例 id 为 {} 的交易标的已存在，将删除‘交易标的’ ids ：{}", caseId, ids);
			tradingTargetMapper.deleteTradingTargetByIds((String[]) ids);
		}
		if (tradingTargets.size() == 0) return true;
		return tradingTargetMapper.insertTradingTargetList(tradingTargets) > 0;
	}

}
