package com.bytetcp.finalab.serve.courseLiquidation.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.courseLiquidation.domain.CourseLiquidation;
import com.bytetcp.finalab.serve.courseLiquidation.mapper.CourseLiquidationMapper;
import com.bytetcp.finalab.serve.courseLiquidation.service.ICourseLiquidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 清算 服务层实现
 * 
 * @author finalab
 * @date 2019-05-03
 */
@Service
public class CourseLiquidationServiceImpl implements ICourseLiquidationService 
{
	@Autowired
	private CourseLiquidationMapper courseLiquidationMapper;

	/**
     * 查询清算信息
     * 
     * @param id 清算ID
     * @return 清算信息
     */
    @Override
	public CourseLiquidation selectCourseLiquidationById(Long id)
	{
	    return courseLiquidationMapper.selectCourseLiquidationById(id);
	}

	@Override
	public CourseLiquidation selectCourseLiquidation(CourseLiquidation courseLiquidation) {
		return courseLiquidationMapper.selectCourseLiquidation(courseLiquidation);
	}

	/**
     * 查询清算列表
     * 
     * @param courseLiquidation 清算信息
     * @return 清算集合
     */
	@Override
	public List<CourseLiquidation> selectCourseLiquidationList(CourseLiquidation courseLiquidation)
	{
	    return courseLiquidationMapper.selectCourseLiquidationList(courseLiquidation);
	}
	
    /**
     * 新增清算
     * 
     * @param courseLiquidation 清算信息
     * @return 结果
     */
	@Override
	public int insertCourseLiquidation(CourseLiquidation courseLiquidation)
	{
	    return courseLiquidationMapper.insertCourseLiquidation(courseLiquidation);
	}
	
	/**
     * 修改清算
     * 
     * @param courseLiquidation 清算信息
     * @return 结果
     */
	@Override
	public int updateCourseLiquidation(CourseLiquidation courseLiquidation)
	{
	    return courseLiquidationMapper.updateCourseLiquidation(courseLiquidation);
	}

	/**
     * 删除清算对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseLiquidationByIds(String ids)
	{
		return courseLiquidationMapper.deleteCourseLiquidationByIds(Convert.toStrArray(ids));
	}
	
}
