package com.bytetcp.finalab.serve.courseLiquidation.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 清算表 course_liquidation
 * 
 * @author finalab
 * @date 2019-05-03
 */
public class CourseLiquidation
{
	private static final long serialVersionUID = 1L;
	
	/** 自增Id */
	private Long id;
	/** 案例Id */
	private Long caseId;
	/** 标的名称 */
	private String targetName;
	/**组编号**/
	private Integer groupNum;

	/** 阶段数字 */
	private Integer phaseNum;
	/** 清算类型 */
	private String liquidationType;
	/** 清算值 */
	private String liquidationValue;
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
	public void setTargetName(String targetName) 
	{
		this.targetName = targetName;
	}

	public String getTargetName() 
	{
		return targetName;
	}

	public void setGroupNum(Integer groupNum)
	{
		this.groupNum = groupNum;
	}

	public Integer getGroupNum()
	{
		return groupNum;
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
            .append("targetName", getTargetName())
            .append("phaseNum", getPhaseNum())
            .append("liquidationType", getLiquidationType())
            .append("liquidationValue", getLiquidationValue())
            .append("courseId", getCourseId())
            .toString();
    }
}
