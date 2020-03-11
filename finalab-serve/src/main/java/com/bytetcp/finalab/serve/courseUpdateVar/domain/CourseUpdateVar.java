package com.bytetcp.finalab.serve.courseUpdateVar.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课件变量更新表 course_update_var
 *
 * @author finalab
 * @date 2019-08-17
 */
public class CourseUpdateVar {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 阶段
     */
    private Integer period;
    /**
     * 秒数
     */
    private Integer tick;
    /**
     *
     */
    private String type;
    /**
     * 标的编码
     */
    private String subType;
    /**
     * 变量名称
     */
    private String variable;
    /**
     * 值
     */
    private String value;
    /**
     * 案例id
     */
    private Integer caseId;
    /**
     * 课件id
     */
    private Long courseId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setTick(Integer tick) {
        this.tick = tick;
    }

    public Integer getTick() {
        return tick;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubType() {
        return subType;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("period", getPeriod())
                .append("tick", getTick())
                .append("type", getType())
                .append("subType", getSubType())
                .append("variable", getVariable())
                .append("value", getValue())
                .append("caseId", getCaseId())
                .append("courseId", getCourseId())
                .toString();
    }
}
