package com.bytetcp.finalab.serve.caseParameters.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.caseParameters.domain.CaseParameters;
import com.bytetcp.finalab.serve.caseParameters.mapper.CaseParametersMapper;
import com.bytetcp.finalab.serve.caseParameters.service.ICaseParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 案例参数 服务层实现
 * 
 * @author finalab
 * @date 2019-03-09
 */
@Service
public class CaseParametersServiceImpl implements ICaseParametersService
{
	@Autowired
	private CaseParametersMapper caseParametersMapper;

	/**
     * 查询案例参数信息
     * 
     * @param caseParamId 案例参数ID
     * @return 案例参数信息
     */
    @Override
	public CaseParameters selectCaseParametersById(Long caseParamId)
	{
	    return caseParametersMapper.selectCaseParametersById(caseParamId);
	}
	
	/**
     * 查询案例参数列表
     * 
     * @param caseParameters 案例参数信息
     * @return 案例参数集合
     */
	@Override
	public List<CaseParameters> selectCaseParametersList(CaseParameters caseParameters)
	{
	    return caseParametersMapper.selectCaseParametersList(caseParameters);
	}
	
    /**
     * 新增案例参数
     * 
     * @param caseParameters 案例参数信息
     * @return 结果
     */
	@Override
	public int insertCaseParameters(CaseParameters caseParameters)
	{
	    return caseParametersMapper.insertCaseParameters(caseParameters);
	}
	
	/**
     * 修改案例参数
     * 
     * @param caseParameters 案例参数信息
     * @return 结果
     */
	@Override
	public int updateCaseParameters(CaseParameters caseParameters)
	{
	    return caseParametersMapper.updateCaseParameters(caseParameters);
	}

	/**
     * 删除案例参数对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCaseParametersByIds(String ids)
	{
		return caseParametersMapper.deleteCaseParametersByIds(Convert.toStrArray(ids));
	}

	@Override
	public int deleteCaseParametersById(Long id) {

		return caseParametersMapper.deleteCaseParametersById(id);
	}
}
