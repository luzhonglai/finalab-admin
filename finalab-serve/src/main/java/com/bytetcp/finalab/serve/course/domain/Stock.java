package com.bytetcp.finalab.serve.course.domain;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class Stock implements Serializable {
    private String stockId;
    private String stockName;
    private String liquidationValue;
    private String financialType;
    private String displayUnit;
    private String startPrice;

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

    public String getLiquidationValue() {
        return liquidationValue;
    }

    public void setLiquidationValue(String liquidationValue) {
        this.liquidationValue = liquidationValue;
    }

    public String getFinancialType() {
        return financialType;
    }

    public void setFinancialType(String financialType) {
        this.financialType = financialType;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(String displayUnit) {
        this.displayUnit = displayUnit;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
