package com.bytetcp.finalab.serve.caseParameters.mapper;

import com.bytetcp.finalab.serve.caseParameters.domain.CaseParameters;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 案例参数 数据层
 *
 * @author finalab
 * @date 2019-03-09
 */
public interface CaseParametersMapper
{
	/**
     * 查询案例参数信息
     *
     * @param caseParamId 案例参数ID
     * @return 案例参数信息
     */
	public CaseParameters selectCaseParametersById(Long caseParamId);

	/**
     * 查询案例参数列表
     *
     * @param caseParameters 案例参数信息
     * @return 案例参数集合
     */
	public List<CaseParameters> selectCaseParametersList(CaseParameters caseParameters);

	/**
     * 新增案例参数
     *
     * @param caseParameters 案例参数信息
     * @return 结果
     */
	public int insertCaseParameters(CaseParameters caseParameters);


	public int insertCaseParametersList(@Param("caseParameters") List<CaseParameters> caseParameters);


	/**
     * 修改案例参数
     *
     * @param caseParameters 案例参数信息
     * @return 结果
     */
	public int updateCaseParameters(CaseParameters caseParameters);

	/**
     * 删除案例参数
     *
     * @param caseParamId 案例参数ID
     * @return 结果
     */
	public int deleteCaseParametersById(Long caseParamId);

	/**
     * 批量删除案例参数
     *
     * @param caseParamIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteCaseParametersByIds(String[] caseParamIds);

	public int deleteCaseParametersByCaseId(@Param("caseId") Long caseId);
	public int deleteCasePriceMoveByCaseId(Long caseId);
	public int deleteCaseLiquidationByCaseId(Long caseId);
	public int deleteCaseMarketNewsByCaseId(Long caseId);
	public int deleteCaseTargetParamByCaseId(Long caseId);
	public int deleteCaseTradingConstraintByCaseId(Long caseId);
	public int deleteCaseUserNewsByCaseId(Long caseId);
}