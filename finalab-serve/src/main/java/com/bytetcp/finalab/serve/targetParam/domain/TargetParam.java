package com.bytetcp.finalab.serve.targetParam.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 标的参数表 target_param
 * 
 * @author finalab
 * @date 2019-05-01
 */
public class TargetParam
{
	private static final long serialVersionUID = 1L;
	
	/** 自增id */
	private Long id;
	/** 案例表的id */
	private Long caseId;

	private Integer groupNum;
	/** 参数名称 */
	private String paramName;
	/** 标的名称 */
	private String targetName;
	/** 类型 */
	private String targetType;
	/** 取值 */
	private String targetValue;
	/** 说明 */
	private String description;

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
	public void setParamName(String paramName) 
	{
		this.paramName = paramName;
	}

	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}

	public String getParamName()
	{
		return paramName;
	}
	public void setTargetName(String targetName) 
	{
		this.targetName = targetName;
	}

	public String getTargetName()
	{
		return targetName;
	}
	public void setTargetType(String targetType)
	{
		this.targetType = targetType;
	}

	public String getTargetType()
	{
		return targetType;
	}
	public void setTargetValue(String targetValue) 
	{
		this.targetValue = targetValue;
	}

	public String getTargetValue() 
	{
		return targetValue;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("caseId", getCaseId())
            .append("paramName", getParamName())
            .append("targetName", getTargetName())
            .append("targetType", getTargetType())
            .append("targetValue", getTargetValue())
            .append("description", getDescription())
            .toString();
    }
}
