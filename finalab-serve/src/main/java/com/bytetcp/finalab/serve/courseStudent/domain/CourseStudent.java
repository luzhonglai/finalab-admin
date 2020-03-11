package com.bytetcp.finalab.serve.courseStudent.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 学生参与课件表 course_student
 *
 * @author finalab
 * @date 2019-03-15
 */
public class CourseStudent {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**
     * 课程id
     */
    private Long courseId;
    /**
     * 学生id
     */
    private Long studentId;

    private String userName;

    private Integer signUp;

    private Integer onLine;
    /**
     * 报名时间
     */
    private Date addTime;
    /**
     * 退出时间
     */
    private Date leaveTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getSignUp() {
        return signUp;
    }

    public void setSignUp(Integer signUp) {
        this.signUp = signUp;
    }

    public Integer getOnLine() {
        return onLine;
    }

    public void setOnLine(Integer onLine) {
        this.onLine = onLine;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("courseId", getCourseId())
                .append("studentId", getStudentId())
                .append("addTime", getAddTime())
                .append("signUp", getSignUp())
                .append("onLine", getOnLine())
                .append("leaveTime", getLeaveTime())
                .toString();
    }
}
