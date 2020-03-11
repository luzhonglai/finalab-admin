package com.bytetcp.finalab.serve.derivedVar.service;

import com.bytetcp.finalab.serve.cases.service.IExcelHandler;
import com.bytetcp.finalab.serve.derivedVar.domain.DerivedVar;

import java.util.List;

/**
 * 衍生变量 服务层
 *
 * @author finalab
 * @date 2019-09-24
 */
public interface IDerivedVarService extends IExcelHandler {
    /**
     * 查询衍生变量信息
     *
     * @param id 衍生变量ID
     * @return 衍生变量信息
     */
    public DerivedVar selectDerivedVarById(Long id);

    /**
     * 查询衍生变量列表
     *
     * @param derivedVar 衍生变量信息
     * @return 衍生变量集合
     */
    public List<DerivedVar> selectDerivedVarList(DerivedVar derivedVar);

    /**
     * 新增衍生变量
     *
     * @param derivedVar 衍生变量信息
     * @return 结果
     */
    public int insertDerivedVar(DerivedVar derivedVar);

    /**
     * 修改衍生变量
     *
     * @param derivedVar 衍生变量信息
     * @return 结果
     */
    public int updateDerivedVar(DerivedVar derivedVar);

    /**
     * 删除衍生变量信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDerivedVarByIds(String ids);

    List<String> fixColumns(DerivedVar derivedVar);
}
