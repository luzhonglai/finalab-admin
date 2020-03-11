package com.bytetcp.finalab.serve.userMoneyDetail.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户金额明细表 user_money_detail
 * 
 * @author finalab
 * @date 2019-08-02
 */
public class UserMoneyDetail
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date modifyTime;
	/** 阶段数 */
	private Integer thePeriod;
	/** 第几次循环 */
	private Integer loopNum;
	/** 一秒一秒的时间 */
	private Integer timeLine;
	/**  */
	private String instanceId;
	/** 课件id */
	private Long courseId;
	/** 股票id */
	private String stockId;
	/** 股票名称 */
	private String stockName;
	/** 成交价格 */
	private BigDecimal dealPrice;
	/** 成交数量 */
	private Integer dealQuantity;
	/** 资金流向，买是负，卖是正 */
	private BigDecimal costPrice;
	/** 总资金 */
	private BigDecimal totalPrice;
	/** 交易人id,机器人是-1 */
	private Long traderId;
	/** 交易人名字,机器人ANON */
	private String traderName;
	/** 金融产品类型,股票（stock)，期货(future)，股指(index)，期权(option)，现汇（spot) */
	private String financialType;
	/** order_action 或者 ClearActionName */
	private String actionName;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
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
	public void setThePeriod(Integer thePeriod) 
	{
		this.thePeriod = thePeriod;
	}

	public Integer getThePeriod() 
	{
		return thePeriod;
	}
	public void setLoopNum(Integer loopNum) 
	{
		this.loopNum = loopNum;
	}

	public Integer getLoopNum() 
	{
		return loopNum;
	}
	public void setTimeLine(Integer timeLine) 
	{
		this.timeLine = timeLine;
	}

	public Integer getTimeLine() 
	{
		return timeLine;
	}
	public void setInstanceId(String instanceId) 
	{
		this.instanceId = instanceId;
	}

	public String getInstanceId() 
	{
		return instanceId;
	}
	public void setCourseId(Long courseId) 
	{
		this.courseId = courseId;
	}

	public Long getCourseId() 
	{
		return courseId;
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
	public void setDealPrice(BigDecimal dealPrice) 
	{
		this.dealPrice = dealPrice;
	}

	public BigDecimal getDealPrice() 
	{
		return dealPrice;
	}
	public void setDealQuantity(Integer dealQuantity) 
	{
		this.dealQuantity = dealQuantity;
	}

	public Integer getDealQuantity() 
	{
		return dealQuantity;
	}
	public void setCostPrice(BigDecimal costPrice) 
	{
		this.costPrice = costPrice;
	}

	public BigDecimal getCostPrice() 
	{
		return costPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) 
	{
		this.totalPrice = totalPrice;
	}

	public BigDecimal getTotalPrice() 
	{
		return totalPrice;
	}
	public void setTraderId(Long traderId) 
	{
		this.traderId = traderId;
	}

	public Long getTraderId() 
	{
		return traderId;
	}
	public void setTraderName(String traderName) 
	{
		this.traderName = traderName;
	}

	public String getTraderName() 
	{
		return traderName;
	}
	public void setFinancialType(String financialType) 
	{
		this.financialType = financialType;
	}

	public String getFinancialType() 
	{
		return financialType;
	}
	public void setActionName(String actionName) 
	{
		this.actionName = actionName;
	}

	public String getActionName() 
	{
		return actionName;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
            .append("thePeriod", getThePeriod())
            .append("loopNum", getLoopNum())
            .append("timeLine", getTimeLine())
            .append("instanceId", getInstanceId())
            .append("courseId", getCourseId())
            .append("stockId", getStockId())
            .append("stockName", getStockName())
            .append("dealPrice", getDealPrice())
            .append("dealQuantity", getDealQuantity())
            .append("costPrice", getCostPrice())
            .append("totalPrice", getTotalPrice())
            .append("traderId", getTraderId())
            .append("traderName", getTraderName())
            .append("financialType", getFinancialType())
            .append("actionName", getActionName())
            .toString();
    }
}
