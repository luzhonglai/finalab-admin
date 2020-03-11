package com.bytetcp.finalab.serve.cases.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.common.utils.StringUtils;
import com.bytetcp.finalab.common.utils.poi.ExcelUtil;
import com.bytetcp.finalab.serve.caseParameters.domain.CaseParameters;
import com.bytetcp.finalab.serve.caseParameters.mapper.CaseParametersMapper;
import com.bytetcp.finalab.serve.cases.domain.Cases;
import com.bytetcp.finalab.serve.cases.mapper.CasesMapper;
import com.bytetcp.finalab.serve.cases.service.ICasesService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 案例 服务层实现
 *
 * @author finalab
 * @date 2019-03-09
 */
@Service
public class CasesServiceImpl implements ICasesService {
    @Autowired
    private CasesMapper casesMapper;

    @Autowired
    private CaseParametersMapper caseParametersMapper;

    public String test(String  params) {
        return "123456 : " + params;
    }

    /**
     * 查询案例信息
     *
     * @param id 案例ID
     * @return 案例信息
     */
    @Override
    public Cases selectCasesById(Long id) {
        return casesMapper.selectCasesById(id);
    }

    /**
     * 查询案例列表
     *
     * @param cases 案例信息
     * @return 案例集合
     */
    @Override
    public List<Cases> selectCasesList(Cases cases) {
        return casesMapper.selectCasesList(cases);
    }

    /**
     * 新增案例
     *
     * @param cases 案例信息
     * @return 结果
     */
    @Override
    public int insertCases(Cases cases) {
        return casesMapper.insertCases(cases);
    }

    /**
     * 修改案例
     *
     * @param cases 案例信息
     * @return 结果
     */
    @Override
    public int updateCases(Cases cases) {
        return casesMapper.updateCases(cases);
    }

    @Override
    public int deleteCasesById(Long id){
        return casesMapper.deleteCasesById(id);
    }

    /**
     * 删除案例对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCasesByIds(String ids) {
        return casesMapper.deleteCasesByIds(Convert.toStrArray(ids));
    }

    @Override
    public Map<String, Object> checkExcel(Workbook workbook) {
        HashMap<String, Object> handleResult = new HashMap<>();
        return handleResult;
    }

    @Override
    public boolean handlExcelSheet(Sheet parameterSheet, Long caseId) {
        if (Objects.isNull(parameterSheet)) {
            return false;
        }
        int skipRows = 2;//跳过sheet的前两行数据(一般是跳过表头)
        int rownums = parameterSheet.getPhysicalNumberOfRows();
        List<CaseParameters> caseParameters = new ArrayList<>();

        for (int i = skipRows; i < rownums; i++) {
            CaseParameters caseParameter = new CaseParameters();
            Row row = parameterSheet.getRow(i);
            caseParameter.setCaseId(caseId);
            if (StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, 0)))) {
                break;
            }
            caseParameter.setParaName(String.valueOf(ExcelUtil.getCellValue(row, 0)));
            caseParameter.setParaTypeId(String.valueOf(ExcelUtil.getCellValue(row, 1)));
            caseParameter.setParaValue(String.valueOf(ExcelUtil.getCellValue(row, 3)));
            caseParameter.setParaDesc(String.valueOf(ExcelUtil.getCellValue(row, 4)));
            caseParameters.add(caseParameter);
        }
        return caseParametersMapper.insertCaseParametersList(caseParameters) > 0;

    }


}
