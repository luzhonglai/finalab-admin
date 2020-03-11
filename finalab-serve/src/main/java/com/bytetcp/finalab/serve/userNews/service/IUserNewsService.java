package com.bytetcp.finalab.serve.userNews.service;

import com.bytetcp.finalab.serve.cases.service.IExcelHandler;
import com.bytetcp.finalab.serve.userNews.domain.UserNews;

import java.util.List;

/**
 * user_news用户新闻 服务层
 * 
 * @author finalab
 * @date 2019-05-25
 */
public interface IUserNewsService extends IExcelHandler
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
     * 删除user_news用户新闻信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserNewsByIds(String ids);
	
}
