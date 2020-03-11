package com.bytetcp.finalab.serve.tradingTarget.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 标的表 trading_target
 * 
 * @author finalab
 * @date 2019-05-01
 */
public class TradingTarget
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;
	/**  */
	private Long caseId;
	/**  */
	private String targetNum;
	/**  */
	private String targetType;
	/**  */
	private String targetName;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setCaseId(Long caseId) 
	{
		this.caseId = caseId;
	}

	public Long getCaseId() 
	{
		return caseId;
	}
	public void setTargetNum(String targetNum) 
	{
		this.targetNum = targetNum;
	}

	public String getTargetNum() 
	{
		return targetNum;
	}
	public void setTargetType(String targetType) 
	{
		this.targetType = targetType;
	}

	public String getTargetType() 
	{
		return targetType;
	}
	public void setTargetName(String targetName) 
	{
		this.targetName = targetName;
	}

	public String getTargetName() 
	{
		return targetName;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("caseId", getCaseId())
            .append("targetNum", getTargetNum())
            .append("targetType", getTargetType())
            .append("targetName", getTargetName())
            .toString();
    }
}
