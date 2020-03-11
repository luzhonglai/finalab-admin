package com.bytetcp.finalab.serve.clearActionDetail.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 记录用户的平仓动作，一次平仓一条记录。平仓之前会记录用户该股票的成本价，这样才能计算出盈亏表 clear_action_detail
 *
 * @author finalab
 * @date 2019-08-02
 */
public class ClearActionDetail {
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
     * 一秒一秒的时间
     */
    private Integer timeLine;
    /**
     * 股票id
     */
    private String stockId;
    /**
     * 股票名称
     */
    private String stockName;
    /**
     * 平仓之前该股票成本价格，是总价
     */
    private BigDecimal costAllPrice;
    /**
     * 平仓股票价格，总价
     */
    private BigDecimal clearAllPrice;
    /**
     * 盈亏，cost_all_price-clear_all_price，保留符合，保留小数点和案例参数一致
     */
    private BigDecimal profitLoss;
    /**
     * 平仓数量
     */
    private Integer clearQuantity;
    /**
     * 交易人id,机器人是-1
     */
    private Long traderId;
    /**
     * 交易人名字,机器人ANON
     */
    private String traderName;
    /**
     * 交易类型, BID("买单"), ASK("卖单");
     */
    private String tradeType;
    /**
     * 金融产品类型,股票（stock)，期货(future)，股指(index)，期权(option)，现汇（spot)
     */
    private String financialType;
    /**
     * BID("买单"), ASK("卖单"), CANCEL("取消"), CLOSE_OUT("平仓"),NIT_PEND("初始化挂单"), TREND_PEND("走势挂单"), REBOT_PEND("机器人挂单");
     */
    private String orderAction;

    /**
     * 成交时的单价
     */
    private BigDecimal clearPrice;

    public BigDecimal getClearPrice() {
        return clearPrice;
    }

    public void setDealPrice(BigDecimal clearPrice) {
        this.clearPrice = clearPrice;
    }

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

    public void setTimeLine(Integer timeLine) {
        this.timeLine = timeLine;
    }

    public Integer getTimeLine() {
        return timeLine;
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

    public void setCostAllPrice(BigDecimal costAllPrice) {
        this.costAllPrice = costAllPrice;
    }

    public BigDecimal getCostAllPrice() {
        return costAllPrice;
    }

    public void setClearAllPrice(BigDecimal clearAllPrice) {
        this.clearAllPrice = clearAllPrice;
    }

    public BigDecimal getClearAllPrice() {
        return clearAllPrice;
    }

    public void setProfitLoss(BigDecimal profitLoss) {
        this.profitLoss = profitLoss;
    }

    public BigDecimal getProfitLoss() {
        return profitLoss;
    }

    public void setClearQuantity(Integer clearQuantity) {
        this.clearQuantity = clearQuantity;
    }

    public Integer getClearQuantity() {
        return clearQuantity;
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

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setFinancialType(String financialType) {
        this.financialType = financialType;
    }

    public String getFinancialType() {
        return financialType;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("thePeriod", getThePeriod())
                .append("loopNum", getLoopNum())
                .append("instanceId", getInstanceId())
                .append("timeLine", getTimeLine())
                .append("stockId", getStockId())
                .append("stockName", getStockName())
                .append("costAllPrice", getCostAllPrice())
                .append("clearAllPrice", getClearAllPrice())
                .append("profitLoss", getProfitLoss())
                .append("clearQuantity", getClearQuantity())
                .append("traderId", getTraderId())
                .append("traderName", getTraderName())
                .append("tradeType", getTradeType())
                .append("financialType", getFinancialType())
                .append("orderAction", getOrderAction())
                .toString();
    }
}
