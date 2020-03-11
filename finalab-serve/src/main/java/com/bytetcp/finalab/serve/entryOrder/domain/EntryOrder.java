package com.bytetcp.finalab.serve.entryOrder.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 挂单表 entry_order
 * 
 * @author finalab
 * @date 2019-07-28
 */
public class EntryOrder implements Serializable
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
	/** 订单id */
	private String orderBookId;
	/** 实例id */
	private String instanceId;
	/** 课件id */
	private Long courseId;
	/** 股票id */
	private String stockId;
	/**  */
	private String stockName;
	/** 订单价格 */
	private BigDecimal price;
	/** 数量 */
	private Integer quantity;
	/**  */
	private Integer timeLine;
	/** linux时间戳 */
	private Long orderTime;
	/** 交易人id,机器人是-1 */
	private Long traderId;
	/** 交易人名字,机器人ANON */
	private String traderName;
	/** 交易类型, BID("买单"), ASK("卖单"); */
	private String tradeType;
	/** Market_Order("市场单"), Limit_Order("限价单"); */
	private String orderType;
	/** 金融产品类型,股票（stock)，期货(future)，股指(index)，期权(option)，现汇（spot) */
	private String financialType;
	/** BID("买单"), ASK("卖单"), CANCEL("取消"), CLOSE_OUT("平仓"),NIT_PEND("初始化挂单"), TREND_PEND("走势挂单"), REBOT_PEND("机器人挂单"); */
	private String orderAction;

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
	public void setOrderBookId(String orderBookId) 
	{
		this.orderBookId = orderBookId;
	}

	public String getOrderBookId() 
	{
		return orderBookId;
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
	public void setPrice(BigDecimal price) 
	{
		this.price = price;
	}

	public BigDecimal getPrice() 
	{
		return price;
	}
	public void setQuantity(Integer quantity) 
	{
		this.quantity = quantity;
	}

	public Integer getQuantity() 
	{
		return quantity;
	}
	public void setTimeLine(Integer timeLine) 
	{
		this.timeLine = timeLine;
	}

	public Integer getTimeLine() 
	{
		return timeLine;
	}
	public void setOrderTime(Long orderTime) 
	{
		this.orderTime = orderTime;
	}

	public Long getOrderTime() 
	{
		return orderTime;
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
	public void setFinancialType(String financialType) 
	{
		this.financialType = financialType;
	}

	public String getFinancialType() 
	{
		return financialType;
	}
	public void setOrderAction(String orderAction) 
	{
		this.orderAction = orderAction;
	}

	public String getOrderAction() 
	{
		return orderAction;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
            .append("thePeriod", getThePeriod())
            .append("loopNum", getLoopNum())
            .append("orderBookId", getOrderBookId())
            .append("instanceId", getInstanceId())
            .append("courseId", getCourseId())
            .append("stockId", getStockId())
            .append("stockName", getStockName())
            .append("price", getPrice())
            .append("quantity", getQuantity())
            .append("timeLine", getTimeLine())
            .append("orderTime", getOrderTime())
            .append("traderId", getTraderId())
            .append("traderName", getTraderName())
            .append("tradeType", getTradeType())
            .append("orderType", getOrderType())
            .append("financialType", getFinancialType())
            .append("orderAction", getOrderAction())
            .toString();
    }
}
