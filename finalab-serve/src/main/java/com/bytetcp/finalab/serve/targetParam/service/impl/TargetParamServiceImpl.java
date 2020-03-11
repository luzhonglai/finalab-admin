package com.bytetcp.finalab.serve.targetParam.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.targetParam.domain.TargetParam;
import com.bytetcp.finalab.serve.targetParam.mapper.TargetParamMapper;
import com.bytetcp.finalab.serve.targetParam.service.ITargetParamService;
import org.apache.poi.ss.usermodel.Cell;
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
 * 标的参数 服务层实现
 *
 * @author finalab
 * @date 2019-05-01
 */
@Service
public class TargetParamServiceImpl implements ITargetParamService {
    private static Logger logger = LoggerFactory.getLogger(TargetParamServiceImpl.class);

    @Autowired
    private TargetParamMapper targetParamMapper;

    /**
     * 查询标的参数信息
     *
     * @param id 标的参数ID
     * @return 标的参数信息
     */
    @Override
    public TargetParam selectTargetParamById(Long id) {
        return targetParamMapper.selectTargetParamById(id);
    }

    /**
     * 查询标的参数列表
     *
     * @param targetParam 标的参数信息
     * @return 标的参数集合
     */
    @Override
    public List<TargetParam> selectTargetParamList(TargetParam targetParam) {
        return targetParamMapper.selectTargetParamList(targetParam);
    }

    /**
     * 新增标的参数
     *
     * @param targetParam 标的参数信息
     * @return 结果
     */
    @Override
    public int insertTargetParam(TargetParam targetParam) {
        return targetParamMapper.insertTargetParam(targetParam);
    }

    /**
     * 修改标的参数
     *
     * @param targetParam 标的参数信息
     * @return 结果
     */
    @Override
    public int updateTargetParam(TargetParam targetParam) {
        return targetParamMapper.updateTargetParam(targetParam);
    }

    /**
     * 删除标的参数对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTargetParamByIds(String ids) {
        return targetParamMapper.deleteTargetParamByIds(Convert.toStrArray(ids));
    }

    @Override
    public boolean handlExcelSheet(Sheet parameterSheet, Long caseId) {
        if (Objects.isNull(parameterSheet)) {
            return false;
        }
        int skipRows = 1;//跳过sheet的前两行数据(一般是跳过表头)
        int rownums = parameterSheet.getPhysicalNumberOfRows();
        int groupCount = afterHasColumn(parameterSheet, 1, 3);
        List<TargetParam> targetParams = new ArrayList<>();

        for (int i = skipRows; i < rownums; i++) {
            Row row = parameterSheet.getRow(i);
            if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)))) {
                break;
            }
            for (int j = 0; j < groupCount; j++) {
                TargetParam targetParam = new TargetParam();
                targetParam.setCaseId(caseId);
                targetParam.setParamName(String.valueOf(ExcelUtil.getCellValue(row, 0)));
                targetParam.setTargetName(String.valueOf(ExcelUtil.getCellValue(parameterSheet.getRow(skipRows), 3 + j)));
                targetParam.setTargetType(String.valueOf(ExcelUtil.getCellValue(row, 1)));
                targetParam.setDescription(String.valueOf(ExcelUtil.getCellValue(row, 2)));
                targetParam.setTargetValue(String.valueOf(ExcelUtil.getCellValue(row, 3 + j)));
                targetParam.setGroupNum(j + 1);
                targetParams.add(targetParam);
            }
        }
        targetParams.sort(Comparator.comparingInt(TargetParam::getGroupNum));
        // 检查改案例id下是否已经有 ’标的参数‘， 如果有 先删除在插入
        TargetParam targetParam = new TargetParam();
        targetParam.setCaseId(caseId);
        List<TargetParam> oldTargetParams = targetParamMapper.selectTargetParamList(targetParam);
        if (!CollectionUtils.isEmpty(oldTargetParams) && oldTargetParams.size() > 0) {
            List<String> collect = oldTargetParams.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
            String[] ids = collect.toArray(new String[collect.size()]);
            logger.info("案例 id 为 {} 的标的参数已存在，将删除 标的参数 ids : {}", caseId, ids);
            targetParamMapper.deleteTargetParamByIds(ids);
        }
        if (targetParams.size() == 0) return true;
        return targetParamMapper.insertTargetParamList(targetParams) > 0;
    }

    /**
     * 获取 “标的参数sheet 的取值  到  说明之间的列数”
     *
     * @param sheet
     * @return
     */
    private static int getGroupCount(Sheet sheet) {
        Row row = sheet.getRow(0);
        int count = 0;
        short lastCellNum = row.getLastCellNum();
        a:
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            Object cellValue = ExcelUtil.getCellValue(row, i);
            String s = cellValue.toString();
            if ("取值".equals(s)) {
                for (int j = i; j < lastCellNum; j++) {
                    cellValue = ExcelUtil.getCellValue(row, j);
                    s = cellValue.toString();
                    if ("说明".equals(s)) {
                        break a;
                    }
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public List<TargetParam> selectCourseTargetParambyCourseId(Long courseId) {
        return targetParamMapper.selectCourseTargetParambyCourseId(courseId);
    }

}
