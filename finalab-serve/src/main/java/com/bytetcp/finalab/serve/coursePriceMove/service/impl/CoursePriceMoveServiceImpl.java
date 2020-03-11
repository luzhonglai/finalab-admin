package com.bytetcp.finalab.serve.coursePriceMove.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.coursePriceMove.domain.CoursePriceMove;
import com.bytetcp.finalab.serve.coursePriceMove.mapper.CoursePriceMoveMapper;
import com.bytetcp.finalab.serve.coursePriceMove.service.ICoursePriceMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 价格走势 服务层实现
 * 
 * @author finalab
 * @date 2019-05-03
 */
@Service
public class CoursePriceMoveServiceImpl implements ICoursePriceMoveService 
{
	@Autowired
	private CoursePriceMoveMapper coursePriceMoveMapper;

	/**
     * 查询价格走势信息
     * 
     * @param id 价格走势ID
     * @return 价格走势信息
     */
    @Override
	public CoursePriceMove selectCoursePriceMoveById(Long id)
	{
	    return coursePriceMoveMapper.selectCoursePriceMoveById(id);
	}
	
	/**
     * 查询价格走势列表
     * 
     * @param coursePriceMove 价格走势信息
     * @return 价格走势集合
     */
	@Override
	public List<CoursePriceMove> selectCoursePriceMoveList(CoursePriceMove coursePriceMove)
	{
	    return coursePriceMoveMapper.selectCoursePriceMoveList(coursePriceMove);
	}
	
    /**
     * 新增价格走势
     * 
     * @param coursePriceMove 价格走势信息
     * @return 结果
     */
	@Override
	public int insertCoursePriceMove(CoursePriceMove coursePriceMove)
	{
	    return coursePriceMoveMapper.insertCoursePriceMove(coursePriceMove);
	}
	
	/**
     * 修改价格走势
     * 
     * @param coursePriceMove 价格走势信息
     * @return 结果
     */
	@Override
	public int updateCoursePriceMove(CoursePriceMove coursePriceMove)
	{
	    return coursePriceMoveMapper.updateCoursePriceMove(coursePriceMove);
	}

	/**
     * 删除价格走势对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCoursePriceMoveByIds(String ids)
	{
		return coursePriceMoveMapper.deleteCoursePriceMoveByIds(Convert.toStrArray(ids));
	}
	
}
