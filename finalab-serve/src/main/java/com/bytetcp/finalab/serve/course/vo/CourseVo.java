package com.bytetcp.finalab.serve.course.vo;

import com.bytetcp.finalab.serve.course.domain.Course;

/**
 * @author chenchuan@autohome.com.cn
 * @create 2019-03-13-上午12:50
 * @description
 */
public class CourseVo extends Course {
    private String caseIcon;
    private String caseName;
    private String teacherName;

    public String getCaseIcon() {
        return caseIcon;
    }

    public void setCaseIcon(String caseIcon) {
        this.caseIcon = caseIcon;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
