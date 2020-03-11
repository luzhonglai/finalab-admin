package com.bytetcp.finalab.serve.derivedVar.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.derivedVar.domain.DerivedVar;
import com.bytetcp.finalab.serve.derivedVar.mapper.DerivedVarMapper;
import com.bytetcp.finalab.serve.derivedVar.service.IDerivedVarService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 衍生变量 服务层实现
 *
 * @author finalab
 * @date 2019-09-24
 */
@Service
public class DerivedVarServiceImpl implements IDerivedVarService {

    private static Logger logger = LoggerFactory.getLogger(DerivedVarServiceImpl.class);

    @Autowired
    private DerivedVarMapper derivedVarMapper;

    /**
     * 查询衍生变量信息
     *
     * @param id 衍生变量ID
     * @return 衍生变量信息
     */
    @Override
    public DerivedVar selectDerivedVarById(Long id) {
        return derivedVarMapper.selectDerivedVarById(id);
    }

    /**
     * 查询衍生变量列表
     *
     * @param derivedVar 衍生变量信息
     * @return 衍生变量集合
     */
    @Override
    public List<DerivedVar> selectDerivedVarList(DerivedVar derivedVar) {
        return derivedVarMapper.selectDerivedVarList(derivedVar);
    }

    /**
     * 新增衍生变量
     *
     * @param derivedVar 衍生变量信息
     * @return 结果
     */
    @Override
    public int insertDerivedVar(DerivedVar derivedVar) {
        return derivedVarMapper.insertDerivedVar(derivedVar);
    }

    /**
     * 修改衍生变量
     *
     * @param derivedVar 衍生变量信息
     * @return 结果
     */
    @Override
    public int updateDerivedVar(DerivedVar derivedVar) {
        return derivedVarMapper.updateDerivedVar(derivedVar);
    }

    /**
     * 删除衍生变量对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDerivedVarByIds(String ids) {
        return derivedVarMapper.deleteDerivedVarByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<String> fixColumns(DerivedVar derivedVar) {
        return derivedVarMapper.fixColumns(derivedVar);
    }

    @Override
    public boolean handlExcelSheet(Sheet sheet, Long caseId) {
        if (Objects.isNull(sheet)) {
            return false;
        }
        int skipRows = 1;//跳过sheet的前两行数据(一般是跳过表头)
        int rownums = sheet.getPhysicalNumberOfRows();
        List<DerivedVar> derivedVars = new ArrayList<>();
        int afterHasColumn = afterHasColumn(sheet, 1, 4);
        for (int i = skipRows; i < rownums; i++) {
            Row row = sheet.getRow(i);
            if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 1)))) {
                break;
            }
            for (int j = 0; j < afterHasColumn; j++) {
                DerivedVar derivedVar = new DerivedVar();
                derivedVar.setCaseId(caseId);
                derivedVar.setVarName(String.valueOf(ExcelUtil.getCellValue(row, 0)));
                derivedVar.setTargetName(String.valueOf(ExcelUtil.getCellValue(row, 1)));
                derivedVar.setPeriod(Integer.valueOf(String.valueOf(ExcelUtil.getCellValue(row, 2))));
                derivedVar.setTimeNum(Integer.valueOf(String.valueOf(ExcelUtil.getCellValue(row, 3))));
                derivedVar.setVarValue(new Double(String.valueOf(ExcelUtil.getCellValue(row, 4 + j, 10))));
                derivedVar.setGroupNum(j + 1);
                derivedVar.setCreateTime(new Date());
                derivedVars.add(derivedVar);
            }
        }
        derivedVars.sort(Comparator.comparingInt(DerivedVar::getGroupNum));
        // 检查数据是否已存在
        DerivedVar derivedVar = new DerivedVar();
        derivedVar.setCaseId(caseId);
        List<DerivedVar> fDderivedVars = derivedVarMapper.selectDerivedVarList(derivedVar);
        if (!CollectionUtils.isEmpty(fDderivedVars) && fDderivedVars.size() > 0) {
            List<String> collect = fDderivedVars.stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
            String[] ids = collect.toArray(new String[collect.size()]);
            logger.info("案例 id 为 {} 衍生变量已存在，将删除 价格走势数据 ids : {}", caseId, ids);
            derivedVarMapper.deleteDerivedVarByIds(ids);
        }
        if (derivedVars.size() == 0) return true;
        return derivedVarMapper.insertDerivedVarList(derivedVars) > 0;
    }

}
