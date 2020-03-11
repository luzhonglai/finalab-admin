package com.bytetcp.finalab.serve.caseParameters.service;

import com.bytetcp.finalab.serve.caseParameters.domain.CaseParameters;

import java.util.List;

/**
 * 案例参数 服务层
 * 
 * @author finalab
 * @date 2019-03-09
 */
public interface ICaseParametersService 
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
	
	/**
     * 修改案例参数
     * 
     * @param caseParameters 案例参数信息
     * @return 结果
     */
	public int updateCaseParameters(CaseParameters caseParameters);
		
	/**
     * 删除案例参数信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCaseParametersByIds(String ids);


	//根据案例参数id删除
	public int deleteCaseParametersById(Long id);

	
}
