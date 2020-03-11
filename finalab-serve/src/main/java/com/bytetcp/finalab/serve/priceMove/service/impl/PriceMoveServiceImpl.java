package com.bytetcp.finalab.serve.priceMove.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.priceMove.domain.PriceMove;
import com.bytetcp.finalab.serve.priceMove.mapper.PriceMoveMapper;
import com.bytetcp.finalab.serve.priceMove.service.IPriceMoveService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
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
 * 价格走势 服务层实现
 *
 * @author finalab
 * @date 2019-05-02
 */
@Service
public class PriceMoveServiceImpl implements IPriceMoveService {
    private static Logger logger = LoggerFactory.getLogger(PriceMoveServiceImpl.class);

    @Autowired(required = false)
    private PriceMoveMapper priceMoveMapper;

    /**
     * 查询价格走势信息
     *
     * @param id 价格走势ID
     * @return 价格走势信息
     */
    @Override
    public PriceMove selectPriceMoveById(Long id) {
        return priceMoveMapper.selectPriceMoveById(id);
    }

    /**
     * 查询价格走势列表
     *
     * @param priceMove 价格走势信息
     * @return 价格走势集合
     */
    @Override
    public List<PriceMove> selectPriceMoveList(PriceMove priceMove) {
        return priceMoveMapper.selectPriceMoveList(priceMove);
    }

    /**
     * 新增价格走势
     *
     * @param priceMove 价格走势信息
     * @return 结果
     */
    @Override
    public int insertPriceMove(PriceMove priceMove) {
        return priceMoveMapper.insertPriceMove(priceMove);
    }

    /**
     * 修改价格走势
     *
     * @param priceMove 价格走势信息
     * @return 结果
     */
    @Override
    public int updatePriceMove(PriceMove priceMove) {
        return priceMoveMapper.updatePriceMove(priceMove);
    }

    /**
     * 删除价格走势对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePriceMoveByIds(String ids) {
        return priceMoveMapper.deletePriceMoveByIds(Convert.toStrArray(ids));
    }

    @Override
    public boolean handlExcelSheet(Sheet sheet, Long caseId) {
        if (Objects.isNull(sheet)) {
            return false;
        }
        int skipRows = 1;//跳过sheet的前两行数据(一般是跳过表头)
        int rownums = sheet.getPhysicalNumberOfRows();
        List<PriceMove> priceMoves = new ArrayList<>();
        int afterHasColumn = afterHasColumn(sheet, 1, 3);
        for (int i = skipRows; i < rownums; i++) {
            Row row = sheet.getRow(i);
            if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 1)))) {
                break;
            }
            for (int j = 0; j < afterHasColumn; j++) {
                PriceMove priceMove = new PriceMove();
                priceMove.setCaseId(caseId);
                priceMove.setTargetName(String.valueOf(ExcelUtil.getCellValue(row, 0)));
                priceMove.setStage(Integer.valueOf(String.valueOf(ExcelUtil.getCellValue(row, 1))));
                priceMove.setDateNum(Long.parseLong(String.valueOf(ExcelUtil.getCellValue(row, 2))));
                priceMove.setGroupNum(j + 1);
                priceMove.setPrice(new Double(String.valueOf(ExcelUtil.getCellValue(row, 3 + j, 10))));
                priceMoves.add(priceMove);
            }
        }
        priceMoves.sort(Comparator.comparingInt(PriceMove::getGroupNum));
        // 检查数据是否已存在
        PriceMove priceMove = new PriceMove();
        priceMove.setCaseId(caseId);
        List<PriceMove> targetParams = priceMoveMapper.selectPriceMoveList(priceMove);
        if (!CollectionUtils.isEmpty(targetParams) && targetParams.size() > 0) {
            List<String> collect = targetParams.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
            String[] ids = collect.toArray(new String[collect.size()]);
            logger.info("案例 id 为 {} 价格走势已存在，将删除 价格走势数据 ids : {}", caseId, ids);
            priceMoveMapper.deletePriceMoveByIds(ids);
        }
        if (priceMoves.size() == 0) return true;
        return priceMoveMapper.insertPriceMoveList(priceMoves) > 0;
    }
}
