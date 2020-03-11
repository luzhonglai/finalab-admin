package com.bytetcp.finalab.serve.cases.mapper;

import com.bytetcp.finalab.serve.cases.domain.Cases;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 案例 数据层
 *
 * @author finalab
 * @date 2019-03-09
 */
public interface CasesMapper
{
	/**
     * 查询案例信息
     *
     * @param id 案例ID
     * @return 案例信息
     */
	public Cases selectCasesById(Long id);

	/**
     * 查询案例列表
     *
     * @param cases 案例信息
     * @return 案例集合
     */
	public List<Cases> selectCasesList(Cases cases);

	/**
     * 新增案例
     *
     * @param cases 案例信息
     * @return 结果
     */
	public int insertCases(Cases cases);

	/**
     * 修改案例
     *
     * @param cases 案例信息
     * @return 结果
     */
	public int updateCases(Cases cases);

	/**
     * 删除案例
     *
     * @param id 案例ID
     * @return 结果
     */
	public int deleteCasesById(Long id);

	/**
     * 批量删除案例
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCasesByIds(String[] ids);

	public int getCaseNum();

	public int getCourseNum();

	int getCourseNumByTeachId(@Param("teacherId")Long teacherId);

}