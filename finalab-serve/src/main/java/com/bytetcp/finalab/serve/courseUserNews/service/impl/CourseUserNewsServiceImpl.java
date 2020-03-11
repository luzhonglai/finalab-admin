package com.bytetcp.finalab.serve.courseUserNews.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.courseStudent.domain.CourseStudent;
import com.bytetcp.finalab.serve.courseStudent.service.ICourseStudentService;
import com.bytetcp.finalab.serve.courseUserNews.domain.CourseUserNews;
import com.bytetcp.finalab.serve.courseUserNews.mapper.CourseUserNewsMapper;
import com.bytetcp.finalab.serve.courseUserNews.service.ICourseUserNewsService;
import com.bytetcp.finalab.serve.userNews.domain.UserNewsReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课件用户新闻 服务层实现
 * 
 * @author finalab
 * @date 2019-05-25
 */
@Service
public class CourseUserNewsServiceImpl implements ICourseUserNewsService 
{
	private static Logger logger = LoggerFactory.getLogger(CourseUserNewsServiceImpl.class);
	@Autowired
	private CourseUserNewsMapper courseUserNewsMapper;

	@Autowired
	private ICourseStudentService courseStudentService;

	/**
     * 查询课件用户新闻信息
     * 
     * @param id 课件用户新闻ID
     * @return 课件用户新闻信息
     */
    @Override
	public CourseUserNews selectCourseUserNewsById(Long id)
	{
	    return courseUserNewsMapper.selectCourseUserNewsById(id);
	}
	
	/**
     * 查询课件用户新闻列表
     * 
     * @param courseUserNews 课件用户新闻信息
     * @return 课件用户新闻集合
     */
	@Override
	public List<CourseUserNews> selectCourseUserNewsList(CourseUserNews courseUserNews)
	{
	    return courseUserNewsMapper.selectCourseUserNewsList(courseUserNews);
	}

	@Override
	public CourseUserNews getUserNews(UserNewsReq userNewsReq, Long userId){
		CourseStudent courseStudent = new CourseStudent();
		courseStudent.setCourseId(userNewsReq.getCourseId());
		List<CourseStudent> courseStudents = courseStudentService.selectCourseStudentList(courseStudent);

		CourseUserNews courseUserNews = new CourseUserNews();
		courseUserNews.setTimeNum(userNewsReq.getTimeNum());
		courseUserNews.setCourseId(userNewsReq.getCourseId());
		courseUserNews.setGroupNum(userNewsReq.getGroupNum());
		List<CourseUserNews> userNews = selectCourseUserNewsList(courseUserNews);

//		logger.info("入参 {}, userid:{}", JSON.toJSONString(userNewsReq), userId);
//		logger.info("学生列表 {}", JSON.toJSONString(courseStudents));
//		logger.info("新闻列表 {}", JSON.toJSONString(userNews));

		for (CourseUserNews news : userNews) {
				int aim = Integer.parseInt(news.getAim());
			if (aim > courseStudents.size()) {
				continue;
			}
			CourseStudent student = courseStudents.get(aim - 1);
			if (userId.equals(student.getStudentId())) {
				return news;
			}
		}
		return null;
	}
	
    /**
     * 新增课件用户新闻
     * 
     * @param courseUserNews 课件用户新闻信息
     * @return 结果
     */
	@Override
	public int insertCourseUserNews(CourseUserNews courseUserNews)
	{
	    return courseUserNewsMapper.insertCourseUserNews(courseUserNews);
	}
	
	/**
     * 修改课件用户新闻
     * 
     * @param courseUserNews 课件用户新闻信息
     * @return 结果
     */
	@Override
	public int updateCourseUserNews(CourseUserNews courseUserNews)
	{
	    return courseUserNewsMapper.updateCourseUserNews(courseUserNews);
	}

	/**
     * 删除课件用户新闻对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCourseUserNewsByIds(String ids)
	{
		return courseUserNewsMapper.deleteCourseUserNewsByIds(Convert.toStrArray(ids));
	}
	
}
