package com.bytetcp.finalab.serve.tradingConstraint.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.targetParam.service.impl.TargetParamServiceImpl;
import com.bytetcp.finalab.serve.tradingConstraint.domain.TradingConstraint;
import com.bytetcp.finalab.serve.tradingConstraint.mapper.TradingConstraintMapper;
import com.bytetcp.finalab.serve.tradingConstraint.service.ITradingConstraintService;
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
 * 交易约束 服务层实现
 * 
 * @author finalab
 * @date 2019-05-01
 */
@Service
public class TradingConstraintServiceImpl implements ITradingConstraintService 
{
	private static Logger logger = LoggerFactory.getLogger(TargetParamServiceImpl.class);

	@Autowired(required = false)
	private TradingConstraintMapper tradingConstraintMapper;

	/**
     * 查询交易约束信息
     * 
     * @param id 交易约束ID
     * @return 交易约束信息
     */
    @Override
	public TradingConstraint selectTradingConstraintById(Long id)
	{
	    return tradingConstraintMapper.selectTradingConstraintById(id);
	}
	
	/**
     * 查询交易约束列表
     * 
     * @param tradingConstraint 交易约束信息
     * @return 交易约束集合
     */
	@Override
	public List<TradingConstraint> selectTradingConstraintList(TradingConstraint tradingConstraint)
	{
	    return tradingConstraintMapper.selectTradingConstraintList(tradingConstraint);
	}
	
    /**
     * 新增交易约束
     * 
     * @param tradingConstraint 交易约束信息
     * @return 结果
     */
	@Override
	public int insertTradingConstraint(TradingConstraint tradingConstraint)
	{
	    return tradingConstraintMapper.insertTradingConstraint(tradingConstraint);
	}
	
	/**
     * 修改交易约束
     * 
     * @param tradingConstraint 交易约束信息
     * @return 结果
     */
	@Override
	public int updateTradingConstraint(TradingConstraint tradingConstraint)
	{
	    return tradingConstraintMapper.updateTradingConstraint(tradingConstraint);
	}

	/**
     * 删除交易约束对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTradingConstraintByIds(String ids)
	{
		return tradingConstraintMapper.deleteTradingConstraintByIds(Convert.toStrArray(ids));
	}

	@Override
	public boolean handlExcelSheet(Sheet tradingConstraintSheet, Long caseId) {
		if (Objects.isNull(tradingConstraintSheet)) {
			return false;
		}
		int skipRows = 1;//跳过sheet的前两行数据(一般是跳过表头)
		int rownums = tradingConstraintSheet.getPhysicalNumberOfRows();
		List<TradingConstraint> tradingConstraints = new ArrayList<>();

		for (int i = skipRows; i < rownums; i++) {
			TradingConstraint tradingConstraint = new TradingConstraint();
			Row row = tradingConstraintSheet.getRow(i);
			if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)))) {
				break;
			}
			tradingConstraint.setCaseId(caseId);
			tradingConstraint.setConstraintName(String.valueOf(ExcelUtil.getCellValue(row, 0)));
			tradingConstraint.setGrossQuantity(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 1))));
			tradingConstraint.setGrossUnitFines(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 2))));
			tradingConstraint.setNetPosition(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 3))));
			tradingConstraint.setNetUnitFines(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 4))));
			tradingConstraint.setTradingTarget(String.valueOf(ExcelUtil.getCellValue(row, 5)));
			tradingConstraints.add(tradingConstraint);
		}
		// 检查数据是否已存在
		TradingConstraint tradingConstraint = new TradingConstraint();
		tradingConstraint.setCaseId(caseId);
		List<TradingConstraint> targetParams = tradingConstraintMapper.selectTradingConstraintList(tradingConstraint);
		if (!CollectionUtils.isEmpty(targetParams) && targetParams.size() > 0) {
			List<String> collect = targetParams.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
			String[] ids = collect.toArray(new String[collect.size()]);
			logger.info("案例 id 为 {} 的 '交易约束' 已存在，将删除 交易约束数据 ids : {}", caseId, ids);
			tradingConstraintMapper.deleteTradingConstraintByIds(ids);
		}
		if (tradingConstraints.size() == 0) return true;
		return tradingConstraintMapper.insertTradingConstraintList(tradingConstraints) > 0;
	}

}
