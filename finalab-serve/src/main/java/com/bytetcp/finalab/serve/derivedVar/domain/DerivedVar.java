package com.bytetcp.finalab.serve.derivedVar.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 衍生变量表 derived_var
 * 
 * @author finalab
 * @date 2019-09-24
 */
public class DerivedVar
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	private Long id;
	/** 案例id */
	private Long caseId;
	/** 变量名称 */
	private String varName;
	/** 标的名称 */
	private String targetName;
	/** 阶段数 */
	private Integer period;
	/** 时间数字 */
	private Integer timeNum;
	/** 变量值 */
	private Double varValue;
	/** 组数 */
	private Integer groupNum;
	/** 创建时间 */
	private Date createTime;

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
	public void setVarName(String varName) 
	{
		this.varName = varName;
	}

	public String getVarName() 
	{
		return varName;
	}
	public void setTargetName(String targetName) 
	{
		this.targetName = targetName;
	}

	public String getTargetName() 
	{
		return targetName;
	}
	public void setPeriod(Integer period) 
	{
		this.period = period;
	}

	public Integer getPeriod() 
	{
		return period;
	}

	public void setTimeNum(Integer timeNum) 
	{
		this.timeNum = timeNum;
	}

	public Integer getTimeNum() 
	{
		return timeNum;
	}
	public void setVarValue(Double varValue)
	{
		this.varValue = varValue;
	}

	public Double getVarValue()
	{
		return varValue;
	}
	public void setGroupNum(Integer groupNum) 
	{
		this.groupNum = groupNum;
	}

	public Integer getGroupNum() 
	{
		return groupNum;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("caseId", getCaseId())
            .append("varName", getVarName())
            .append("targetName", getTargetName())
            .append("period", getPeriod())
            .append("timeNum", getTimeNum())
            .append("varValue", getVarValue())
            .append("groupNum", getGroupNum())
            .append("createTime", getCreateTime())
            .toString();
    }
}
