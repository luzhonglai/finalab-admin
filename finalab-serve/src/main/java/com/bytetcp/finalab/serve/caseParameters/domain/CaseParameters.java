package com.bytetcp.finalab.serve.caseParameters.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 案例参数表 case_parameters
 * 
 * @author finalab
 * @date 2019-03-09
 */
public class CaseParameters
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long caseParamId;
	/** 案例主键id */
	private Long caseId;
	/** 案例参数名称 */
	private String paraName;
	/** 按理参数值 */
	private String paraValue;
	/** 参数描述 */
	private String paraDesc;
	/** 参数类型 */
	private String paraTypeId;

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
            .append("caseParamId", getCaseParamId())
            .append("caseId", getCaseId())
            .append("paraName", getParaName())
            .append("paraValue", getParaValue())
            .append("paraDesc", getParaDesc())
            .append("paraTypeId", getParaTypeId())
            .toString();
    }
}
