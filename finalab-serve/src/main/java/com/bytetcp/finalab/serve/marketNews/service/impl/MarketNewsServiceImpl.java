package com.bytetcp.finalab.serve.marketNews.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.marketNews.domain.MarketNews;
import com.bytetcp.finalab.serve.marketNews.mapper.MarketNewsMapper;
import com.bytetcp.finalab.serve.marketNews.service.IMarketNewsService;
import org.apache.poi.ss.usermodel.*;
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
 * 案例市场新闻 服务层实现
 *
 * @author finalab
 * @date 2019-05-25
 */
@Service
public class MarketNewsServiceImpl implements IMarketNewsService {
    @Autowired(required = false)
    private MarketNewsMapper marketNewsMapper;

    private static Logger logger = LoggerFactory.getLogger(MarketNewsServiceImpl.class);

    /**
     * 查询案例市场新闻信息
     *
     * @param id 案例市场新闻ID
     * @return 案例市场新闻信息
     */
    @Override
    public MarketNews selectMarketNewsById(Long id) {
        return marketNewsMapper.selectMarketNewsById(id);
    }

    /**
     * 查询案例市场新闻列表
     *
     * @param marketNews 案例市场新闻信息
     * @return 案例市场新闻集合
     */
    @Override
    public List<MarketNews> selectMarketNewsList(MarketNews marketNews) {
        return marketNewsMapper.selectMarketNewsList(marketNews);
    }

    /**
     * 新增案例市场新闻
     *
     * @param marketNews 案例市场新闻信息
     * @return 结果
     */
    @Override
    public int insertMarketNews(MarketNews marketNews) {
        return marketNewsMapper.insertMarketNews(marketNews);
    }

    /**
     * 修改案例市场新闻
     *
     * @param marketNews 案例市场新闻信息
     * @return 结果
     */
    @Override
    public int updateMarketNews(MarketNews marketNews) {
        return marketNewsMapper.updateMarketNews(marketNews);
    }

    /**
     * 删除案例市场新闻对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMarketNewsByIds(String ids) {
        return marketNewsMapper.deleteMarketNewsByIds(Convert.toStrArray(ids));
    }


    @Override
    public boolean handlExcelSheet(Sheet marketNewsSheet, Long caseId) {
        if (Objects.isNull(marketNewsSheet)) {
            return false;
        }
        int skipRows = 1;//跳过sheet的前两行数据(一般是跳过表头)
        int rownums = marketNewsSheet.getPhysicalNumberOfRows();
        List<MarketNews> marketNewsList = new ArrayList<>();
//        int afterHasColumn = afterHasColumn(marketNewsSheet, 0, 7);
//        for (int i = skipRows; i < rownums; i++) {
//            Row row = marketNewsSheet.getRow(i);
//            if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)))) {
//                break;
//            }
        for (int j = 1; j < rownums; j++) {
            Row row = marketNewsSheet.getRow(j);
            MarketNews marketNews = new MarketNews();
            marketNews.setCaseId(caseId);
            // 以下两行，是由于，案例excel模板中，市场新闻，无数据行被公式处理过，poi将空行识别出来，cell的value转换成Integer会报错。
            boolean empty = StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)));
            if (empty) break;
            marketNews.setGroupNum(Integer.valueOf(String.valueOf(ExcelUtil.getCellValue(row, 0))));
            marketNews.setPhaseNum(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 1)).trim()));
            marketNews.setTimeNum(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 2)).trim()));
            marketNews.setSendAim(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 3)).trim()));
            marketNews.setIsCompel(Integer.parseInt(String.valueOf(ExcelUtil.getCellValue(row, 4)).trim()));
            marketNews.setAction(String.valueOf(ExcelUtil.getCellValue(row, 5)).trim());
            marketNews.setCompelStockId(String.valueOf(ExcelUtil.getCellValue(row, 6)));
            marketNews.setNewsTitle(String.valueOf(ExcelUtil.getCellValue(row, 7)));

            //判断这个字段是否公式
            Boolean isFormula = false;
            String content = String.valueOf(ExcelUtil.getCellValue(row, 8));
            try{
                isFormula = row.getCell(8).getCellTypeEnum() == CellType.FORMULA;
            } catch (Exception e) {

            }

            if (isFormula) {
                FormulaEvaluator evaluator = marketNewsSheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue evaluate = evaluator.evaluate(row.getCell(8));
                content = evaluate.formatAsString();
            }
            marketNews.setContent(content);

            String tragetString = loadTragetString(row, 9, new StringBuffer(""));
            marketNews.setTargetString(tragetString);
            marketNewsList.add(marketNews);
        }
//        }

        // 检查数据是否已存在
        MarketNews marketNews = new MarketNews();
        marketNews.setCaseId(caseId);
        List<MarketNews> targetParams = marketNewsMapper.selectMarketNewsList(marketNews);
        if (!CollectionUtils.isEmpty(targetParams) && targetParams.size() > 0) {
            List<String> collect = targetParams.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
            String[] ids = collect.toArray(new String[collect.size()]);
            logger.info("案例 id 为 {} 的 '交易约束' 已存在，将删除 用户新闻数据 ids : {}", caseId, ids);
            marketNewsMapper.deleteMarketNewsByIds(ids);
        }
        if (marketNewsList.size() == 0) return true;
        return marketNewsMapper.insertMarketNewsList(marketNewsList) > 0;

    }

    private String loadTragetString(Row row, Integer currIndex, StringBuffer targetString) {
        StringBuffer currentTargetString = new StringBuffer(String.valueOf(ExcelUtil.getCellValue(row, currIndex)));
        if (StringUtils.isEmpty(currentTargetString.toString())) {
            return targetString.toString();
        }
        if (StringUtils.isEmpty(targetString)) {
            targetString = currentTargetString;
        } else {
            targetString = targetString.append(",").append(currentTargetString);
        }
        return this.loadTragetString(row, currIndex + 1, targetString);
    }
}
