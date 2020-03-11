package com.bytetcp.finalab.serve.targetParam.service;

import com.bytetcp.finalab.serve.cases.service.IExcelHandler;
import com.bytetcp.finalab.serve.targetParam.domain.TargetParam;

import java.util.List;

/**
 * 标的参数 服务层
 * 
 * @author finalab
 * @date 2019-05-01
 */
public interface ITargetParamService extends IExcelHandler
{
	/**
     * 查询标的参数信息
     * 
     * @param id 标的参数ID
     * @return 标的参数信息
     */
	public TargetParam selectTargetParamById(Long id);
	
	/**
     * 查询标的参数列表
     * 
     * @param targetParam 标的参数信息
     * @return 标的参数集合
     */
	public List<TargetParam> selectTargetParamList(TargetParam targetParam);

	/**
	 * 查询课程标的参数列表
	 * @param courseId
	 * @return
	 */
	public List<TargetParam> selectCourseTargetParambyCourseId(Long courseId);


	/**
     * 新增标的参数
     * 
     * @param targetParam 标的参数信息
     * @return 结果
     */
	public int insertTargetParam(TargetParam targetParam);
	
	/**
     * 修改标的参数
     * 
     * @param targetParam 标的参数信息
     * @return 结果
     */
	public int updateTargetParam(TargetParam targetParam);
		
	/**
     * 删除标的参数信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTargetParamByIds(String ids);


	
}
