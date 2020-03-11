package com.bytetcp.finalab.serve.courseMarketNews.service;

import com.bytetcp.finalab.serve.courseMarketNews.domain.CourseMarketNews;

import java.util.List;

/**
 * 课件市场新闻 服务层
 * 
 * @author finalab
 * @date 2019-05-25
 */
public interface ICourseMarketNewsService 
{
	/**
     * 查询课件市场新闻信息
     * 
     * @param id 课件市场新闻ID
     * @return 课件市场新闻信息
     */
	public CourseMarketNews selectCourseMarketNewsById(Long id);

	public CourseMarketNews selectCourseMarketNewsByTimeNum(Integer timeNum, Long courseId, Integer thePeriod, Integer groupNum);

	/**
     * 查询课件市场新闻列表
     * 
     * @param courseMarketNews 课件市场新闻信息
     * @return 课件市场新闻集合
     */
	public List<CourseMarketNews> selectCourseMarketNewsList(CourseMarketNews courseMarketNews);
	
	/**
     * 新增课件市场新闻
     * 
     * @param courseMarketNews 课件市场新闻信息
     * @return 结果
     */
	public int insertCourseMarketNews(CourseMarketNews courseMarketNews);
	
	/**
     * 修改课件市场新闻
     * 
     * @param courseMarketNews 课件市场新闻信息
     * @return 结果
     */
	public int updateCourseMarketNews(CourseMarketNews courseMarketNews);
		
	/**
     * 删除课件市场新闻信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseMarketNewsByIds(String ids);
	
}
