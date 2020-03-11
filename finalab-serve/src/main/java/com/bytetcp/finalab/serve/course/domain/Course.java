package com.bytetcp.finalab.serve.course.domain;

import java.util.Date;

/**
 * 课件表 course
 *
 * @authcycleInor finalab
 * @date 2019-03-09
 */
public class Course {
    private static final long serialVersionUID = 1L;

    /**
     * 课程
     */
    private Long id;
    /**
     * 案例id
     */
    private Long caseId;
    /**
     * 课件名称
     */
    private String courseName;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 参与人数
     */
    private Integer addNum;
    /**  */
    private Long teacherId;

    /**
     * 课件运行状态(0:待启动；1：运行中；2：已停止)
     */
    private Integer status;

    /**
     * 允许新名单参与(1:运行；0：不允许)
     */
    private Integer allowIn;

    //金融产品类型
    private Integer caseType;

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }


    /**
     * 循环开启(1:开启；0：关闭)
     */
    private Integer cycleIn;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }

    public Integer getAddNum() {
        return addNum;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getAllowIn() {
        return allowIn;
    }

    public void setAllowIn(Integer allowIn) {
        this.allowIn = allowIn;
    }

    public Integer getCycleIn() {
        return cycleIn;
    }

    public void setCycleIn(Integer cycleIn) {
        this.cycleIn = cycleIn;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", caseId=" + caseId +
                ", courseName='" + courseName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", addNum=" + addNum +
                ", teacherId=" + teacherId +
                ", status=" + status +
                ", allowIn=" + allowIn +
                ", cycleIn=" + cycleIn +
                '}';
    }
}
