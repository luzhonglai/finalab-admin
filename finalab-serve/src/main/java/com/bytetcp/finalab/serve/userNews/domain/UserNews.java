package com.bytetcp.finalab.serve.userNews.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * user_news用户新闻表 user_news
 *
 * @author finalab
 * @date 2019-05-25
 */
public class UserNews {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 分组编号
     */
    private Integer groupNum;

    /**
     * 标的名称
     */
    private String targetName;
    /**
     * 阶段数字
     */
    private Integer phaseNum;
    /**
     * 时间数字
     */
    private Integer timeNum;
    /**
     * 数量
     */
    private Double number;
    /**
     * 价格
     */
    private Double price;
    /**
     * 买入/卖出
     */
    private String tradeType;
//	/** 目标类型 */
//	private String aimType;
    /**
     * 目标
     */
    private String aim;
    /**
     * 持续时长
     */
    private Integer continueTime;
    /**
     * 案例id
     */
    private Long caseId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setPhaseNum(Integer phaseNum) {
        this.phaseNum = phaseNum;
    }

    public Integer getPhaseNum() {
        return phaseNum;
    }

    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
    }

    public Integer getTimeNum() {
        return timeNum;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getNumber() {
        return number;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getAim() {
        return aim;
    }

    public void setContinueTime(Integer continueTime) {
        this.continueTime = continueTime;
    }

    public Integer getContinueTime() {
        return continueTime;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("targetName", getTargetName())
                .append("phaseNum", getPhaseNum())
                .append("timeNum", getTimeNum())
                .append("number", getNumber())
                .append("price", getPrice())
                .append("tradeType", getTradeType())
                .append("groupNum", getGroupNum())
                .append("aim", getAim())
                .append("continueTime", getContinueTime())
                .append("caseId", getCaseId())
                .toString();
    }
}
