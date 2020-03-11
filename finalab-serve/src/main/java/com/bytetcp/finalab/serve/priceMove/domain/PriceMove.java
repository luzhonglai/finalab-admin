package com.bytetcp.finalab.serve.priceMove.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 价格走势表 price_move
 *
 * @author finalab
 * @date 2019-05-02
 */
public class PriceMove {
    private static final long serialVersionUID = 1L;

    /**
     * 自增Id
     */
    private Long id;
    /**
     * 组合名称
     */
    private String combinationName;
    /**
     * 标的名称
     */
    private String targetName;
    /**
     * 案例Id
     */
    private Long caseId;

    private Integer groupNum;

    /**
     * 时间数字
     */
    private Long dateNum;
    /**
     * 价格
     */
    private Double price;

    /**
     * 阶段数
     */
    private Integer stage;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getCaseId() {
        return caseId;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
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
                .append("combinationName", getCombinationName())
                .append("targetName", getTargetName())
                .append("caseId", getCaseId())
                .append("dateNum", getDateNum())
                .append("price", getPrice())
                .append("stage", getStage())
                .toString();
    }
}
