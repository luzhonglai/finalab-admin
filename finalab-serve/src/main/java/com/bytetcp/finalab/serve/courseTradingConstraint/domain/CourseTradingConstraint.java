package com.bytetcp.finalab.serve.courseTradingConstraint.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课件交易约束表 course_trading_constraint
 * 
 * @author finalab
 * @date 2019-05-03
 */
public class CourseTradingConstraint
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	private Long id;
	/**  */
	private Long caseId;
	/** 约束名称 */
	private String constraintName;
	/** 仓位毛数量 */
	private Integer grossQuantity;
	/** 毛单位罚款 */
	private Integer grossUnitFines;
	/** 净仓位 */
	private Integer netPosition;
	/** 净单位罚款 */
	private Integer netUnitFines;
	/** 约束的标的 */
	private String tradingTarget;
	/**  */
	private Long courseId;

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
	public void setConstraintName(String constraintName) 
	{
		this.constraintName = constraintName;
	}

	public String getConstraintName() 
	{
		return constraintName;
	}
	public void setGrossQuantity(Integer grossQuantity) 
	{
		this.grossQuantity = grossQuantity;
	}

	public Integer getGrossQuantity() 
	{
		return grossQuantity;
	}
	public void setGrossUnitFines(Integer grossUnitFines) 
	{
		this.grossUnitFines = grossUnitFines;
	}

	public Integer getGrossUnitFines() 
	{
		return grossUnitFines;
	}
	public void setNetPosition(Integer netPosition) 
	{
		this.netPosition = netPosition;
	}

	public Integer getNetPosition() 
	{
		return netPosition;
	}
	public void setNetUnitFines(Integer netUnitFines) 
	{
		this.netUnitFines = netUnitFines;
	}

	public Integer getNetUnitFines() 
	{
		return netUnitFines;
	}
	public void setTradingTarget(String tradingTarget) 
	{
		this.tradingTarget = tradingTarget;
	}

	public String getTradingTarget() 
	{
		return tradingTarget;
	}
	public void setCourseId(Long courseId) 
	{
		this.courseId = courseId;
	}

	public Long getCourseId() 
	{
		return courseId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("caseId", getCaseId())
            .append("constraintName", getConstraintName())
            .append("grossQuantity", getGrossQuantity())
            .append("grossUnitFines", getGrossUnitFines())
            .append("netPosition", getNetPosition())
            .append("netUnitFines", getNetUnitFines())
            .append("tradingTarget", getTradingTarget())
            .append("courseId", getCourseId())
            .toString();
    }
}
