package com.bytetcp.finalab.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 金融产品类型
 */
public enum FinancialType {
    /**
     * 股票
     */
    stock("股票"),
    /**
     * 期货
     */
    future("期货"),
    /**
     * 股指
     */
    index("股指"),
    /**
     * option
     */
    option("option"),
    /**
     * 现汇
     */
    spot("现汇");

    FinancialType(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
