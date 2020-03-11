package com.bytetcp.finalab.serve.courseCaseParamters.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课程案例参数表 course_case_paramters
 * 
 * @author finalab
 * @date 2019-04-02
 */
public class CourseCaseParamters
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;
	/**  */
	private Long caseParamId;
	/** 案例主键id */
	private Long caseId;
	/** 课程主键id */
	private Long courseId;
	/** 案例参数名称 */
	private String paraName;
	/** 案例参数值 */
	private String paraValue;
	/** 参数描述 */
	private String paraDesc;
	/** 参数类型 */
	private String paraTypeId;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setCaseParamId(Long caseParamId) 
	{
		this.caseParamId = caseParamId;
	}

	public Long getCaseParamId() 
	{
		return caseParamId;
	}
	public void setCaseId(Long caseId) 
	{
		this.caseId = caseId;
	}

	public Long getCaseId() 
	{
		return caseId;
	}
	public void setCourseId(Long courseId) 
	{
		this.courseId = courseId;
	}

	public Long getCourseId() 
	{
		return courseId;
	}
	public void setParaName(String paraName) 
	{
		this.paraName = paraName;
	}

	public String getParaName() 
	{
		return paraName;
	}
	public void setParaValue(String paraValue) 
	{
		this.paraValue = paraValue;
	}

	public String getParaValue() 
	{
		return paraValue;
	}
	public void setParaDesc(String paraDesc) 
	{
		this.paraDesc = paraDesc;
	}

	public String getParaDesc() 
	{
		return paraDesc;
	}
	public void setParaTypeId(String paraTypeId) 
	{
		this.paraTypeId = paraTypeId;
	}

	public String getParaTypeId() 
	{
		return paraTypeId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("caseParamId", getCaseParamId())
            .append("caseId", getCaseId())
            .append("courseId", getCourseId())
            .append("paraName", getParaName())
            .append("paraValue", getParaValue())
            .append("paraDesc", getParaDesc())
            .append("paraTypeId", getParaTypeId())
            .toString();
    }
}
