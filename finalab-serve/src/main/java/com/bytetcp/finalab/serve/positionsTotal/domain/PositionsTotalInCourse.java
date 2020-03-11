package com.bytetcp.finalab.serve.positionsTotal.domain;

import java.math.BigDecimal;

/**
 * 课件详情页面需要另外的字段：
 * 目前市场价，最新市值，为实现盈利
 */
public class PositionsTotalInCourse extends PositionsTotal {

    //当前市场最新价格
    private BigDecimal price;

    //最新市值
    private BigDecimal latestMarketValue;

    private String userName;


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        if (price == null) {
            this.price = new BigDecimal(0);
        }
        Integer nowQuantity = this.getNowQuantity() == null ? 0 : this.getNowQuantity();
        BigDecimal latestMarketValue = this.price.multiply(new BigDecimal(nowQuantity));
        setLatestMarketValue(latestMarketValue);
    }

    public BigDecimal getLatestMarketValue() {
        return latestMarketValue;
    }

    private void setLatestMarketValue(BigDecimal latestMarketValue) {
        this.latestMarketValue = latestMarketValue;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
