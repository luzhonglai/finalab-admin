package com.bytetcp.finalab.serve.course.domain;

import com.bytetcp.finalab.common.enums.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
    private static final long serialVersionUID = 1778389426533933352L;
    //实例id
    private String instanceId;
    //课件id
    private long courseId;
    //股票id
    private String stockId;
    //股票名称
    private String stockName;
    //股票价格
    private BigDecimal price;
    //交易数据
    private String quantity;
    //交易人id
    private long traderId;
    //交易人名称
    private String traderName;
    //挂单类型
    private TradeType tradeType;
    //订单类型
    private OrderType orderType;
    //金融产品类型
    private FinancialType financialType;
    //订单操作类型，是闪电下单还是正常下单
    private OrderAction orderAction;

    public Order() {
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public long getTraderId() {
        return traderId;
    }

    public void setTraderId(long traderId) {
        this.traderId = traderId;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType traderType) {
        this.tradeType = traderType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public FinancialType getFinancialType() {
        return financialType;
    }

    public void setFinancialType(FinancialType financialType) {
        this.financialType = financialType;
    }

    public OrderAction getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(OrderAction orderAction) {
        this.orderAction = orderAction;
    }



}
