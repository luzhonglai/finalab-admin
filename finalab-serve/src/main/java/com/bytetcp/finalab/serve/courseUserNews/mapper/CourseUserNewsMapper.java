package com.bytetcp.finalab.serve.courseUserNews.mapper;

import com.bytetcp.finalab.serve.courseUserNews.domain.CourseUserNews;

import java.util.List;

/**
 * 课件用户新闻 数据层
 *
 * @author finalab
 * @date 2019-05-25
 */
public interface CourseUserNewsMapper
{
	/**
     * 查询课件用户新闻信息
     *
     * @param id 课件用户新闻ID
     * @return 课件用户新闻信息
     */
	public CourseUserNews selectCourseUserNewsById(Long id);

	/**
     * 查询课件用户新闻列表
     *
     * @param courseUserNews 课件用户新闻信息
     * @return 课件用户新闻集合
     */
	public List<CourseUserNews> selectCourseUserNewsList(CourseUserNews courseUserNews);

	/**
     * 新增课件用户新闻
     *
     * @param courseUserNews 课件用户新闻信息
     * @return 结果
     */
	public int insertCourseUserNews(CourseUserNews courseUserNews);

	/**
     * 修改课件用户新闻
     *
     * @param courseUserNews 课件用户新闻信息
     * @return 结果
     */
	public int updateCourseUserNews(CourseUserNews courseUserNews);

	/**
     * 删除课件用户新闻
     *
     * @param id 课件用户新闻ID
     * @return 结果
     */
	public int deleteCourseUserNewsById(Long id);

	/**
     * 批量删除课件用户新闻
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCourseUserNewsByIds(String[] ids);

}