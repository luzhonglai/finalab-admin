package com.bytetcp.finalab.serve.courseTargetParam.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课件标的参数表 course_target_param
 * 
 * @author finalab
 * @date 2019-05-03
 */
public class CourseTargetParam
{
	private static final long serialVersionUID = 1L;
	
	/** 自增id */
	private Long id;
	/** 案例表的id */
	private Long caseId;
	//组编号
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
	/** 课件id */
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
	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}
	public void setParamName(String paramName) 
	{
		this.paramName = paramName;
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
            .append("paramName", getParamName())
            .append("targetName", getTargetName())
            .append("targetType", getTargetType())
            .append("targetValue", getTargetValue())
            .append("description", getDescription())
            .append("courseId", getCourseId())
            .toString();
    }
}
