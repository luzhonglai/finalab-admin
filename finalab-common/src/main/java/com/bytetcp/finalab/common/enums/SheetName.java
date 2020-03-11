package com.bytetcp.finalab.common.enums;

/**
 * excel的sheetName枚举
 */
public enum  SheetName {

    CASE_PARAMETER("案例参数", "casesServiceImpl"),
    TARGET_PARAMETER("标的参数", "targetParamServiceImpl"),
//    TARGET("标的", "tradingTargetServiceImpl"),
    TRADING_CONSTRAINT("交易约束", "tradingConstraintServiceImpl"),
    LIQUIDATION("清算表", "liquidationServiceImpl"),
    USER_NEWS("用户新闻", "userNewsServiceImpl"),
    MARKET_NEWS("市场新闻", "marketNewsServiceImpl"),
    PRICE_MOVE("价格走势", "priceMoveServiceImpl"),
    UPDATE_VAR("变量更新", "updateVarServiceImpl"),
    DERIVED_VAR("衍生变量", "derivedVarServiceImpl")
    ;
    String sheetName;
    String handlerBeanName;

    SheetName(String sheetName, String handlerBeanName) {
        this.sheetName = sheetName;
        this.handlerBeanName = handlerBeanName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public String getHandlerBeanName() {
        return handlerBeanName;
    }
}
