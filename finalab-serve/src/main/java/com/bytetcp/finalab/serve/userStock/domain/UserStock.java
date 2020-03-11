package com.bytetcp.finalab.serve.userStock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户持仓明细表 user_stock
 * 
 * @author finalab
 * @date 2019-05-27
 */
public class UserStock
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
	/** 持仓数量 */
	private Long stockQuantity;
	/** 成本金额 */
	private BigDecimal costPrice;
	/** 清算金额 */
	private BigDecimal closePrice;
	/** 最新成交价 */
	private BigDecimal dealPrice;
	//最新市值
	private BigDecimal  totalPrice;

	/** 已实现盈利 */
	private BigDecimal profit;
	/** 是否平仓：1：已平仓；0：未平仓 */
	private Boolean isClose;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date modifyTime;

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
	public void setStockQuantity(Long stockQuantity) 
	{
		this.stockQuantity = stockQuantity;
	}

	public Long getStockQuantity() 
	{
		return stockQuantity;
	}
	public void setCostPrice(BigDecimal costPrice) 
	{
		this.costPrice = costPrice;
	}

	public BigDecimal getCostPrice() 
	{
		return costPrice;
	}
	public void setClosePrice(BigDecimal closePrice) 
	{
		this.closePrice = closePrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getClosePrice() 
	{
		return closePrice;
	}
	public void setDealPrice(BigDecimal dealPrice) 
	{
		this.dealPrice = dealPrice;
	}

	public BigDecimal getDealPrice() 
	{
		return dealPrice;
	}
	public void setProfit(BigDecimal profit) 
	{
		this.profit = profit;
	}

	public BigDecimal getProfit() 
	{
		return profit;
	}
	public void setIsClose(Boolean isClose) 
	{
		this.isClose = isClose;
	}

	public Boolean getIsClose() 
	{
		return isClose;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("courseId", getCourseId())
            .append("instanceId", getInstanceId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("stockId", getStockId())
            .append("stockName", getStockName())
            .append("stockQuantity", getStockQuantity())
            .append("costPrice", getCostPrice())
            .append("closePrice", getClosePrice())
            .append("dealPrice", getDealPrice())
            .append("profit", getProfit())
            .append("isClose", getIsClose())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
			.append("totalPrice",getTotalPrice())
            .toString();
    }
}
