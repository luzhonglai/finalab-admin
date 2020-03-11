package com.bytetcp.finalab.serve.positionsDetail.domain;

import com.bytetcp.finalab.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户持仓明细，实例-用户-股票，记录每次加/减仓位
 * eg：买单成交：则+成交数
 * eg：卖单成交：则-成交数表 positions_detail
 *
 * @author finalab
 * @date 2019-08-07
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionsDetail {
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
     * 交易人id,机器人是-1
     */
    @Excel(name = "TraderId")
    private Long traderId;

    /**
     * 阶段数
     */
    @Excel(name = "阶段")
    private Integer thePeriod;

    /**
     * 一秒一秒的时间
     */
    @Excel(name = "成交时间")
    private Integer timeLine;

    /**
     * 交易人名字,机器人ANON
     */
//    @Excel(name = "账号")
    private String traderName;

//    @Excel(name = "姓名")
    private String userName;

    /**
     * 股票名称
     */
    @Excel(name = "交易标的")
    private String stockName;


    /***
     * 只为导出使用，计算 均价
     */
//    @Excel(name = "均价")
    private BigDecimal avgPrice;

    /**
     * 成交价格
     */
    @Excel(name = "成交价格")
    private BigDecimal dealPrice;

    /**
     * 成交数量，买单为正，卖单、清仓为负数
     */
    @Excel(name = "成交数量")
    private Integer dealQuantity;

    /**
     * 只为导出使用，计算成本金额
     */
    @Excel(name = "成本金额")
    private BigDecimal cost;



    /**
     * 当前持仓量
     */
//    @Excel(name = "净仓位")
    private Integer positionQuantity;


    /**
     * 交易类型, BID("买单"), ASK("卖单");
     */
    @Excel(name = "类型")
    private String tradeType;
    /**
     * 金融产品类型,股票（stock)，期货(future)，股指(index)，期权(option)，现汇（spot)
     */
    private String financialType;
    /**
     * BID("买单"), ASK("卖单"), CANCEL("取消"), CLOSE_OUT("平仓"),NIT_PEND("初始化挂单"), TREND_PEND("走势挂单"), REBOT_PEND("机器人挂单");
     */
    private String orderAction;


    public void fixed() {
        if (this.dealPrice != null) {
            this.dealPrice = this.dealPrice.setScale(2);
        }
    }

    public void setCostAndAvg(){
        if (this.dealPrice != null && this.dealQuantity != null) {
            this.cost =  this.dealPrice.multiply(new BigDecimal(this.dealQuantity)).setScale(2);
        } else {
            this.cost = BigDecimal.ZERO.setScale(2);
        }
        //todo 计算均价 逻辑欠缺
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

    public void setTimeLine(Integer timeLine) {
        this.timeLine = timeLine;
    }

    public Integer getTimeLine() {
        return timeLine;
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

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealQuantity(Integer dealQuantity) {
        this.dealQuantity = dealQuantity;
    }

    public Integer getDealQuantity() {
        return dealQuantity;
    }

    public void setPositionQuantity(Integer positionQuantity) {
        this.positionQuantity = positionQuantity;
    }

    public Integer getPositionQuantity() {
        return positionQuantity;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("thePeriod", getThePeriod())
                .append("loopNum", getLoopNum())
                .append("timeLine", getTimeLine())
                .append("instanceId", getInstanceId())
                .append("stockId", getStockId())
                .append("stockName", getStockName())
                .append("dealPrice", getDealPrice())
                .append("dealQuantity", getDealQuantity())
                .append("positionQuantity", getPositionQuantity())
                .append("traderId", getTraderId())
                .append("traderName", getTraderName())
                .append("tradeType", getTradeType())
                .append("financialType", getFinancialType())
                .append("orderAction", getOrderAction())
                .toString();
    }
}
