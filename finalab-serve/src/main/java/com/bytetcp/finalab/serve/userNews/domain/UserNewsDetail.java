package com.bytetcp.finalab.serve.userNews.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * user_news_detail 用户新闻表接受详情 user_news_detail
 *
 * @author finalab
 * @date 2020-06-01
 */
public class UserNewsDetail {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 新闻详情
     */
    private String userNews;
    /**
     * 时间数字
     */
    private Integer timeNum;
    /**
     * 案例id
     */
    private Long caseId;
    /**
     * 课件id
     */
    private Long courseId;
    /**
     * 实例id
     */
    private String instanceId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
    }

    public Integer getTimeNum() {
        return timeNum;
    }


    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNews() {
        return userNews;
    }

    public void setUserNews(String userNews) {
        this.userNews = userNews;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String toString() {
        return "UserNewsDetail{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userNews='" + userNews + '\'' +
                ", timeNum=" + timeNum +
                ", caseId=" + caseId +
                ", courseId=" + courseId +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }
}
