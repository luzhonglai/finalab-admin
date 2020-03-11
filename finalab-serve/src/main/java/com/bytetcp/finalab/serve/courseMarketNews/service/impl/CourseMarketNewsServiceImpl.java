package com.bytetcp.finalab.serve.courseMarketNews.service.impl;

import com.bytetcp.finalab.common.support.Convert;
import com.bytetcp.finalab.serve.courseMarketNews.domain.CourseMarketNews;
import com.bytetcp.finalab.serve.courseMarketNews.mapper.CourseMarketNewsMapper;
import com.bytetcp.finalab.serve.courseMarketNews.service.ICourseMarketNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课件市场新闻 服务层实现
 *
 * @author finalab
 * @date 2019-05-25
 */
@Service
public class CourseMarketNewsServiceImpl implements ICourseMarketNewsService {
    @Autowired
    private CourseMarketNewsMapper courseMarketNewsMapper;

    /**
     * 查询课件市场新闻信息
     *
     * @param id 课件市场新闻ID
     * @return 课件市场新闻信息
     */
    @Override
    public CourseMarketNews selectCourseMarketNewsById(Long id) {
        return courseMarketNewsMapper.selectCourseMarketNewsById(id);
    }

    @Override
    public CourseMarketNews selectCourseMarketNewsByTimeNum(Integer timeNum, Long courseId, Integer thePeriod, Integer groupNum) {
        return courseMarketNewsMapper.selectCourseMarketNewsByTimeNum(timeNum, courseId, thePeriod, groupNum);
    }

    /**
     * 查询课件市场新闻列表
     *
     * @param courseMarketNews 课件市场新闻信息
     * @return 课件市场新闻集合
     */
    @Override
    public List<CourseMarketNews> selectCourseMarketNewsList(CourseMarketNews courseMarketNews) {
        return courseMarketNewsMapper.selectCourseMarketNewsList(courseMarketNews);
    }

    /**
     * 新增课件市场新闻
     *
     * @param courseMarketNews 课件市场新闻信息
     * @return 结果
     */
    @Override
    public int insertCourseMarketNews(CourseMarketNews courseMarketNews) {
        return courseMarketNewsMapper.insertCourseMarketNews(courseMarketNews);
    }

    /**
     * 修改课件市场新闻
     *
     * @param courseMarketNews 课件市场新闻信息
     * @return 结果
     */
    @Override
    public int updateCourseMarketNews(CourseMarketNews courseMarketNews) {
        return courseMarketNewsMapper.updateCourseMarketNews(courseMarketNews);
    }

    /**
     * 删除课件市场新闻对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseMarketNewsByIds(String ids) {
        return courseMarketNewsMapper.deleteCourseMarketNewsByIds(Convert.toStrArray(ids));
    }

}
