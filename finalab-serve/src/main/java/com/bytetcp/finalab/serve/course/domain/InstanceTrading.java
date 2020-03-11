package com.bytetcp.finalab.serve.course.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易明细表 instance_trading
 * 
 * @author finalab
 * @date 2019-05-22
 */
public class InstanceTrading
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;
	/** 课程id */
	private Long courseId;
	/** 实例id */
	private String instanceId;
	/** 用户ID */
	private Integer userId;
	/** 用户昵称 */
	private String userName;
	/** 股票代码 */
	private String stockId;
	/** 股票名称 */
	private String stockName;
	/** 交易类型, BID("买单"), ASK("卖单"); */
	private String tradeType;
	/** Market_Order("市场单"), Limit_Order("限价单"); */
	private String orderType;
	/** 时间段 */
	private Integer timeTine;
	/** 成交数量 */
	private Integer dealQuantity;
	/** 成交价格 */
	private BigDecimal dealPrice;
	/** 单笔成交总额 */
	private BigDecimal dealPriceTotal;
	/** 累计成交总额 */
	private BigDecimal priceTotal;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date modifyTime;
	//交易费
	private BigDecimal tradingFee;
	//成交回报
	private String limitOrderRebate;
	//罚款
	private BigDecimal penalty;
	//成交均价
	private BigDecimal dealAveragePrice;

	public BigDecimal getDealAveragePrice() {
		return dealAveragePrice;
	}

	public void setDealAveragePrice(BigDecimal dealAveragePrice) {
		this.dealAveragePrice = dealAveragePrice;
	}



	public BigDecimal getTradingFee() {
		return tradingFee;
	}

	public void setTradingFee(BigDecimal tradingFee) {
		this.tradingFee = tradingFee;
	}

	public String getLimitOrderRebate() {
		return limitOrderRebate;
	}

	public void setLimitOrderRebate(String limitOrderRebate) {
		this.limitOrderRebate = limitOrderRebate;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}


	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setCourseId(Long courseId) 
	{
		this.courseId = courseId;
	}

	public Long getCourseId() 
	{
		return courseId;
	}
	public void setInstanceId(String instanceId) 
	{
		this.instanceId = instanceId;
	}

	public String getInstanceId() 
	{
		return instanceId;
	}
	public void setUserId(Integer userId) 
	{
		this.userId = userId;
	}

	public Integer getUserId() 
	{
		return userId;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
	}
	public void setStockId(String stockId) 
	{
		this.stockId = stockId;
	}

	public String getStockId() 
	{
		return stockId;
	}
	public void setStockName(String stockName) 
	{
		this.stockName = stockName;
	}

	public String getStockName() 
	{
		return stockName;
	}
	public void setTradeType(String tradeType) 
	{
		this.tradeType = tradeType;
	}

	public String getTradeType() 
	{
		return tradeType;
	}
	public void setOrderType(String orderType) 
	{
		this.orderType = orderType;
	}

	public String getOrderType() 
	{
		return orderType;
	}
	public void setTimeTine(Integer timeTine) 
	{
		this.timeTine = timeTine;
	}

	public Integer getTimeTine() 
	{
		return timeTine;
	}
	public void setDealQuantity(Integer dealQuantity) 
	{
		this.dealQuantity = dealQuantity;
	}

	public Integer getDealQuantity() 
	{
		return dealQuantity;
	}
	public void setDealPrice(BigDecimal dealPrice) 
	{
		this.dealPrice = dealPrice;
	}

	public BigDecimal getDealPrice() 
	{
		return dealPrice;
	}
	public void setDealPriceTotal(BigDecimal dealPriceTotal) 
	{
		this.dealPriceTotal = dealPriceTotal;
	}

	public BigDecimal getDealPriceTotal() 
	{
		return dealPriceTotal;
	}
	public void setPriceTotal(BigDecimal priceTotal) 
	{
		this.priceTotal = priceTotal;
	}

	public BigDecimal getPriceTotal() 
	{
		return priceTotal;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setModifyTime(Date modifyTime) 
	{
		this.modifyTime = modifyTime;
	}

	public Date getModifyTime() 
	{
		return modifyTime;
	}

	@Override
	public String toString() {
		return "InstanceTrading{" +
				"id=" + id +
				", courseId=" + courseId +
				", instanceId='" + instanceId + '\'' +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", stockId='" + stockId + '\'' +
				", stockName='" + stockName + '\'' +
				", tradeType='" + tradeType + '\'' +
				", orderType='" + orderType + '\'' +
				", timeTine=" + timeTine +
				", dealQuantity=" + dealQuantity +
				", dealPrice=" + dealPrice +
				", dealPriceTotal=" + dealPriceTotal +
				", priceTotal=" + priceTotal +
				", createTime=" + createTime +
				", modifyTime=" + modifyTime +
				", tradingFee=" + tradingFee +
				", limitOrderRebate='" + limitOrderRebate + '\'' +
				", penalty=" + penalty +
				'}';
	}
}
