package com.bytetcp.finalab.serve.userMoneyDetail.domain;

import java.math.BigDecimal;

/***
 * 学生交易费、罚款费、未实现盈利
 */
public class UserMoneyDetailInCourse extends UserMoneyDetail {
    //交易费
    private BigDecimal transactionFee;
    //罚款费
    private BigDecimal totalFine;
    //未实现盈利
    private BigDecimal unrealizedProfitandLoss = BigDecimal.ZERO;


    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }

    public BigDecimal getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(BigDecimal totalFine) {
        this.totalFine = totalFine;
    }

    public BigDecimal getUnrealizedProfitandLoss() {
        return unrealizedProfitandLoss;
    }

    public void setUnrealizedProfitandLoss(BigDecimal unrealizedProfitandLoss) {
        this.unrealizedProfitandLoss = unrealizedProfitandLoss;
    }
}
