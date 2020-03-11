package com.bytetcp.finalab.serve.liquidation.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.liquidation.domain.Liquidation;
import com.bytetcp.finalab.serve.liquidation.mapper.LiquidationMapper;
import com.bytetcp.finalab.serve.liquidation.service.ILiquidationService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 清算 服务层实现
 *
 * @author finalab
 * @date 2019-05-01
 */
@Service
public class LiquidationServiceImpl implements ILiquidationService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LiquidationServiceImpl.class);

    @Autowired
    private LiquidationMapper liquidationMapper;

    /**
     * 查询清算信息
     *
     * @param id 清算ID
     * @return 清算信息
     */
    @Override
    public Liquidation selectLiquidationById(Long id) {
        return liquidationMapper.selectLiquidationById(id);
    }

    /**
     * 查询清算列表
     *
     * @param liquidation 清算信息
     * @return 清算集合
     */
    @Override
    public List<Liquidation> selectLiquidationList(Liquidation liquidation) {
        return liquidationMapper.selectLiquidationList(liquidation);
    }

    /**
     * 新增清算
     *
     * @param liquidation 清算信息
     * @return 结果
     */
    @Override
    public int insertLiquidation(Liquidation liquidation) {
        return liquidationMapper.insertLiquidation(liquidation);
    }

    /**
     * 修改清算
     *
     * @param liquidation 清算信息
     * @return 结果
     */
    @Override
    public int updateLiquidation(Liquidation liquidation) {
        return liquidationMapper.updateLiquidation(liquidation);
    }

    /**
     * 删除清算对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLiquidationByIds(String ids) {
        return liquidationMapper.deleteLiquidationByIds(Convert.toStrArray(ids));
    }

    @Override
    public boolean handlExcelSheet(Sheet liquidationSheet, Long caseId) {
        if (Objects.isNull(liquidationSheet)) {
            return false;
        }
        int skipRows = 1;//跳过sheet的前两行数据(一般是跳过表头)
        int rownums = liquidationSheet.getPhysicalNumberOfRows();
        List<Liquidation> liquidationParameters = new ArrayList<>();
        int afterHasColumn = afterHasColumn(liquidationSheet, 1, 3);
        for (int i = skipRows; i < rownums; i++) {
            Row row = liquidationSheet.getRow(i);
            if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)))) {
                break;
            }
            for (int j = 0; j < afterHasColumn; j++) {
                Liquidation Liquidation = new Liquidation();
                Liquidation.setCaseId(caseId);
                Liquidation.setTargetName(String.valueOf(ExcelUtil.getCellValue(row, 0)));
                Liquidation.setPhaseNum(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 1))));
                Liquidation.setLiquidationType(String.valueOf(ExcelUtil.getCellValue(row, 2)));
                Liquidation.setGroupNum(j + 1);
                //获取清算值
                int cellIndex = 3 + j;
                String value = String.valueOf(ExcelUtil.getCellValue(row, cellIndex));

                //判断这个字段是否公式
                Boolean isFormula = false;
                try{
                    isFormula = row.getCell(cellIndex).getCellTypeEnum() == CellType.FORMULA;
                } catch (Exception e) {

                }

                if (isFormula) {
                    FormulaEvaluator evaluator = liquidationSheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue evaluate = evaluator.evaluate(row.getCell(cellIndex));
                    value = evaluate.formatAsString();
                }
                Liquidation.setLiquidationValue(value);
                liquidationParameters.add(Liquidation);
            }
        }
        liquidationParameters.sort(Comparator.comparingInt(Liquidation::getGroupNum));
        // 检查数据是否已存在
        Liquidation liquidation = new Liquidation();
        liquidation.setCaseId(caseId);
        List<Liquidation> targetParams = liquidationMapper.selectLiquidationList(liquidation);
        if (!CollectionUtils.isEmpty(targetParams) && targetParams.size() > 0) {
            List<String> collect = targetParams.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
            String[] ids = collect.toArray(new String[collect.size()]);
            logger.info("案例 id 为 {} 的 '清算表' 已存在，将删除 清算表数据 ids : {}", caseId, ids);
            liquidationMapper.deleteLiquidationByIds(ids);
        }
        if (liquidationParameters.size() == 0) return true;
        return liquidationMapper.insertLiquidationList(liquidationParameters) > 0;
    }


}
