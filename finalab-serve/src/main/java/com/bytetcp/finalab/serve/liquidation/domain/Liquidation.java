package com.bytetcp.finalab.serve.liquidation.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 清算表 liquidation
 * 
 * @author finalab
 * @date 2019-05-01
 */
public class Liquidation
{
	private static final long serialVersionUID = 1L;
	
	/** 自增Id */
	private Long id;
	/** 标的名称 */
	private String targetName;
	/** 案例Id */
	private Long caseId;

	private Integer groupNum;
	/** 阶段数字 */
	private Integer phaseNum;
	/** 清算类型 */
	private String liquidationType;
	/** 清算值 */
	private String liquidationValue;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setTargetName(String targetName) 
	{
		this.targetName = targetName;
	}

	public String getTargetName() 
	{
		return targetName;
	}
	public void setCaseId(Long caseId) 
	{
		this.caseId = caseId;
	}

	public Long getCaseId() 
	{
		return caseId;
	}

	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}

	public void setPhaseNum(Integer phaseNum)
	{
		this.phaseNum = phaseNum;
	}

	public Integer getPhaseNum() 
	{
		return phaseNum;
	}
	public void setLiquidationType(String liquidationType) 
	{
		this.liquidationType = liquidationType;
	}

	public String getLiquidationType() 
	{
		return liquidationType;
	}
	public void setLiquidationValue(String liquidationValue) 
	{
		this.liquidationValue = liquidationValue;
	}

	public String getLiquidationValue() 
	{
		return liquidationValue;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("targetName", getTargetName())
            .append("caseId", getCaseId())
            .append("phaseNum", getPhaseNum())
            .append("liquidationType", getLiquidationType())
            .append("liquidationValue", getLiquidationValue())
            .toString();
    }
}
