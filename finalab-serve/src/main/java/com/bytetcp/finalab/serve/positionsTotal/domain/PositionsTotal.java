package com.bytetcp.finalab.serve.positionsTotal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 持仓汇总，实例-用户-股票，一只股票一个持仓
 * 当平仓时，删除改记录表 positions_total
 *
 * @author finalab
 * @date 2019-07-31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionsTotal implements Serializable {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 阶段数
     */
    private Integer thePeriod;
    /**
     * 第几次循环
     */
    private Integer loopNum;
    /**  */
    private String instanceId;
    /**
     * 股票id
     */
    private String stockId;
    /**
     * 股票名称
     */
    private String stockName;
    /**
     * 成本金额
     */
    private BigDecimal totalPrice;
    /**
     * 已实现盈利
     */
    private BigDecimal profit;
    /**
     * 毛仓位,|买入|+|卖出|
     */
    private Integer totalQuantity;
    /**
     * 净仓位
     */
    private Integer nowQuantity;
    /**
     * 交易人id,机器人是-1
     */
    private Long traderId;
    /**
     * 交易人名字,机器人ANON
     */
    private String traderName;

    private Long courseId;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private  Integer quantity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setThePeriod(Integer thePeriod) {
        this.thePeriod = thePeriod;
    }

    public Integer getThePeriod() {
        return thePeriod;
    }

    public void setLoopNum(Integer loopNum) {
        this.loopNum = loopNum;
    }

    public Integer getLoopNum() {
        return loopNum;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setNowQuantity(Integer nowQuantity) {
        this.nowQuantity = nowQuantity;
    }

    public Integer getNowQuantity() {
        return nowQuantity;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public String getTraderName() {
        return traderName;
    }


    @Override
    public String toString() {
        return "PositionsTotal{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", thePeriod=" + thePeriod +
                ", loopNum=" + loopNum +
                ", instanceId='" + instanceId + '\'' +
                ", stockId='" + stockId + '\'' +
                ", stockName='" + stockName + '\'' +
                ", totalPrice=" + totalPrice +
                ", profit=" + profit +
                ", totalQuantity=" + totalQuantity +
                ", nowQuantity=" + nowQuantity +
                ", traderId=" + traderId +
                ", traderName='" + traderName + '\'' +
                ", courseId=" + courseId +
                ", sellPrice=" + sellPrice +
                ", sellQuantity=" + sellQuantity +
                ", Cost=" + Cost +
                '}';
    }

    public Integer getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Integer sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    private BigDecimal sellPrice;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    private Integer sellQuantity;

    public BigDecimal getCost() {
        return Cost;
    }

    public void setCost(BigDecimal cost) {
        Cost = cost;
    }

    private BigDecimal Cost;

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
