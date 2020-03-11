package com.bytetcp.finalab.serve.courseDerivedVar.mapper;

import com.bytetcp.finalab.serve.courseDerivedVar.domain.CourseDerivedVar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课件-衍生变量 数据层
 *
 * @author finalab
 * @date 2019-09-24
 */
public interface CourseDerivedVarMapper
{
	/**
     * 查询课件-衍生变量信息
     *
     * @param id 课件-衍生变量ID
     * @return 课件-衍生变量信息
     */
	public CourseDerivedVar selectCourseDerivedVarById(Long id);

	/**
     * 查询课件-衍生变量列表
     *
     * @param courseDerivedVar 课件-衍生变量信息
     * @return 课件-衍生变量集合
     */
	public List<CourseDerivedVar> selectCourseDerivedVarList(CourseDerivedVar courseDerivedVar);

	/**
     * 新增课件-衍生变量
     *
     * @param courseDerivedVar 课件-衍生变量信息
     * @return 结果
     */
	public int insertCourseDerivedVar(CourseDerivedVar courseDerivedVar);

	/**
     * 修改课件-衍生变量
     *
     * @param courseDerivedVar 课件-衍生变量信息
     * @return 结果
     */
	public int updateCourseDerivedVar(CourseDerivedVar courseDerivedVar);

	/**
     * 删除课件-衍生变量
     *
     * @param id 课件-衍生变量ID
     * @return 结果
     */
	public int deleteCourseDerivedVarById(Long id);

	/**
     * 批量删除课件-衍生变量
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseDerivedVarByIds(String[] ids);

	/**
	 * 复制案例衍生变量到课件表
	 */
	int copyCourseDerivedVars(@Param("caseId") Long caseId, @Param("courseId") Long courseId);

}