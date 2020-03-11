package com.bytetcp.finalab.serve.targetParam.mapper;

import com.bytetcp.finalab.serve.targetParam.domain.TargetParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标的参数 数据层
 *
 * @author finalab
 * @date 2019-05-01
 */
public interface TargetParamMapper
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
     * 删除标的参数
     *
     * @param id 标的参数ID
     * @return 结果
     */
	public int deleteTargetParamById(Long id);

	/**
     * 批量删除标的参数
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTargetParamByIds(String[] ids);



	public int deleteTargetParamByActive(TargetParam targetParam);

	/**
	 * 批量插入
	 * @param targetParams
	 * @return
	 */
	public int insertTargetParamList(@Param("targetParams") List<TargetParam> targetParams);

	/**
	 * 根据课件ID查询标的参数
	 * @param courseId
	 * @return
	 */
	public List<TargetParam> selectCourseTargetParambyCourseId(@Param("courseId")Long courseId);
}