package com.bytetcp.finalab.serve.courseDerivedVar.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bytetcp.finalab.serve.courseDerivedVar.mapper.CourseDerivedVarMapper;
import com.bytetcp.finalab.serve.courseDerivedVar.domain.CourseDerivedVar;
import com.bytetcp.finalab.serve.courseDerivedVar.service.ICourseDerivedVarService;
import com.bytetcp.finalab.common.support.Convert;

/**
 * 课件-衍生变量 服务层实现
 * 
 * @author finalab
 * @date 2019-09-24
 */
@Service
public class CourseDerivedVarServiceImpl implements ICourseDerivedVarService 
{
	@Autowired
	private CourseDerivedVarMapper courseDerivedVarMapper;

	/**
     * 查询课件-衍生变量信息
     * 
     * @param id 课件-衍生变量ID
     * @return 课件-衍生变量信息
     */
    @Override
	public CourseDerivedVar selectCourseDerivedVarById(Long id)
	{
	    return courseDerivedVarMapper.selectCourseDerivedVarById(id);
	}
	
	/**
     * 查询课件-衍生变量列表
     * 
     * @param courseDerivedVar 课件-衍生变量信息
     * @return 课件-衍生变量集合
     */
	@Override
	public List<CourseDerivedVar> selectCourseDerivedVarList(CourseDerivedVar courseDerivedVar)
	{
	    return courseDerivedVarMapper.selectCourseDerivedVarList(courseDerivedVar);
	}
	
    /**
     * 新增课件-衍生变量
     * 
     * @param courseDerivedVar 课件-衍生变量信息
     * @return 结果
     */
	@Override
	public int insertCourseDerivedVar(CourseDerivedVar courseDerivedVar)
	{
	    return courseDerivedVarMapper.insertCourseDerivedVar(courseDerivedVar);
	}
	
	/**
     * 修改课件-衍生变量
     * 
     * @param courseDerivedVar 课件-衍生变量信息
     * @return 结果
     */
	@Override
	public int updateCourseDerivedVar(CourseDerivedVar courseDerivedVar)
	{
	    return courseDerivedVarMapper.updateCourseDerivedVar(courseDerivedVar);
	}

	/**
     * 删除课件-衍生变量对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseDerivedVarByIds(String ids)
	{
		return courseDerivedVarMapper.deleteCourseDerivedVarByIds(Convert.toStrArray(ids));
	}

	@Override
	public int copyCourseDerivedVars(Long caseId, Long courseId) {
		return courseDerivedVarMapper.copyCourseDerivedVars(caseId, courseId);
	}

}
