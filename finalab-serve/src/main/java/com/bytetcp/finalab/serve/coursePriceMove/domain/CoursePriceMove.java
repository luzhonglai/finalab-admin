package com.bytetcp.finalab.serve.coursePriceMove.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 价格走势表 course_price_move
 *
 * @author finalab
 * @date 2019-05-03
 */
public class CoursePriceMove {
    private static final long serialVersionUID = 1L;

    /**
     * 自增Id
     */
    private Long id;
    /**
     * 案例Id
     */
    private Long caseId;
    /**
     * 组合名称
     */
    private String combinationName;
    /**
     * 标的名称
     */
    private String targetName;


    private Integer groupNum;

    /**
     * 时间数字
     */
    private Long dateNum;
    /**
     * 价格
     */
    private BigDecimal price;
    /**  */
    private Long courseId;

    /**
     * 阶段
     */
    private Integer stage;

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

    public void setCombinationName(String combinationName) {
        this.combinationName = combinationName;
    }

    public String getCombinationName() {
        return combinationName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetName() {
        return targetName;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public void setDateNum(Long dateNum) {
        this.dateNum = dateNum;
    }

    public Long getDateNum() {
        return dateNum;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("caseId", getCaseId())
                .append("combinationName", getCombinationName())
                .append("targetName", getTargetName())
                .append("dateNum", getDateNum())
                .append("price", getPrice())
                .append("courseId", getCourseId())
                .append("stage", getStage())
                .toString();
    }
}
