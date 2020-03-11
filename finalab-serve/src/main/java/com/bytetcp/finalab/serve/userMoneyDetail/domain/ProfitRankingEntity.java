package com.bytetcp.finalab.serve.userMoneyDetail.domain;

import java.math.BigDecimal;

public class ProfitRankingEntity {

    private String userName;

    private BigDecimal originValue;

    private BigDecimal value;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getOrginValue() {
        return originValue;
    }

    public void setOrginValue(BigDecimal originValue) {
        this.originValue = originValue;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
