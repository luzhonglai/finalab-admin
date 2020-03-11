package com.bytetcp.finalab.serve.courseLiquidation.service;

import com.bytetcp.finalab.serve.courseLiquidation.domain.CourseLiquidation;

import java.util.List;

/**
 * 清算 服务层
 * 
 * @author finalab
 * @date 2019-05-03
 */
public interface ICourseLiquidationService 
{
	/**
     * 查询清算信息
     * 
     * @param id 清算ID
     * @return 清算信息
     */
	public CourseLiquidation selectCourseLiquidationById(Long id);

	public CourseLiquidation selectCourseLiquidation(CourseLiquidation courseLiquidation);

	/**
     * 查询清算列表
     * 
     * @param courseLiquidation 清算信息
     * @return 清算集合
     */
	public List<CourseLiquidation> selectCourseLiquidationList(CourseLiquidation courseLiquidation);
	
	/**
     * 新增清算
     * 
     * @param courseLiquidation 清算信息
     * @return 结果
     */
	public int insertCourseLiquidation(CourseLiquidation courseLiquidation);
	
	/**
     * 修改清算
     * 
     * @param courseLiquidation 清算信息
     * @return 结果
     */
	public int updateCourseLiquidation(CourseLiquidation courseLiquidation);
		
	/**
     * 删除清算信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseLiquidationByIds(String ids);
	
}
