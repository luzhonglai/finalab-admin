package com.bytetcp.finalab.serve.userNews.mapper;

import com.bytetcp.finalab.serve.userNews.domain.UserNews;
import com.bytetcp.finalab.serve.userNews.domain.UserNewsDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * user_news_detail用户新闻 数据层
 *
 * @author finalab
 * @date 2020-06-01
 */
public interface UserNewsDetailMapper
{
	/**
     * 新增user_news_detail用户新闻
     *
     * @param userNewsDetail user_news_detail用户新闻信息
     * @return 结果
     */
	public int insert(UserNewsDetail userNewsDetail);

	public void deleteRepeat();

	List<UserNewsDetail> selectByInstanceId(String instanceId);
}