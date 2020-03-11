package com.bytetcp.finalab.serve.userNews.mapper;

import com.bytetcp.finalab.serve.userNews.domain.UserNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * user_news用户新闻 数据层
 *
 * @author finalab
 * @date 2019-05-25
 */
public interface UserNewsMapper
{
	/**
     * 查询user_news用户新闻信息
     *
     * @param id user_news用户新闻ID
     * @return user_news用户新闻信息
     */
	public UserNews selectUserNewsById(Long id);

	/**
     * 查询user_news用户新闻列表
     *
     * @param userNews user_news用户新闻信息
     * @return user_news用户新闻集合
     */
	public List<UserNews> selectUserNewsList(UserNews userNews);

	/**
     * 新增user_news用户新闻
     *
     * @param userNews user_news用户新闻信息
     * @return 结果
     */
	public int insertUserNews(UserNews userNews);

	/**
     * 修改user_news用户新闻
     *
     * @param userNews user_news用户新闻信息
     * @return 结果
     */
	public int updateUserNews(UserNews userNews);

	/**
     * 删除user_news用户新闻
     *
     * @param id user_news用户新闻ID
     * @return 结果
     */
	public int deleteUserNewsById(Long id);

	/**
     * 批量删除user_news用户新闻
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserNewsByIds(String[] ids);


	public int insertUserNewsList(@Param("userNews") List<UserNews> userNews);


}