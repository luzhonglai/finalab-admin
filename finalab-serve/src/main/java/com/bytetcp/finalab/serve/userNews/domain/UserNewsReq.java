package com.bytetcp.finalab.serve.userNews.domain;

import java.io.Serializable;

public class UserNewsReq implements Serializable {
    private Integer timeNum;
    private Long courseId;
    private Integer thePeriod;
    private Integer groupNum;

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Integer getThePeriod() {
        return thePeriod;
    }

    public void setThePeriod(Integer thePeriod) {
        this.thePeriod = thePeriod;
    }

    public Integer getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
