package com.bytetcp.finalab.serve.cases.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 案例表 cases
 * 
 * @author finalab
 * @date 2019-03-09
 */
public class Cases
{
	private static final long serialVersionUID = 1L;
	
	/** 案例id */
	private Long id;
	/** 案例名称 */
	private String caseName;
	/** 当前正在使用的版本信息  */
	private Integer version;
	/** 案例图片路径 */
	private String caseIcon;
	/** 案例描述 */
	private String caseDesc;
	/** 案例类型 */
	private Integer caseType;

	private String pdfPath;

	private String pdfName;
	/** 上传人id */
	private Long createId;
	/** 上传人名字 */
	private String createName;
	/** 课件数 */
	private Integer courseNum;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setCaseName(String caseName) 
	{
		this.caseName = caseName;
	}

	public String getCaseName() 
	{
		return caseName;
	}
	public void setVersion(Integer version) 
	{
		this.version = version;
	}

	public Integer getVersion() 
	{
		return version;
	}
	public void setCaseIcon(String caseIcon) 
	{
		this.caseIcon = caseIcon;
	}

	public String getCaseIcon() 
	{
		return caseIcon;
	}
	public void setCaseDesc(String caseDesc) 
	{
		this.caseDesc = caseDesc;
	}

	public String getCaseDesc() 
	{
		return caseDesc;
	}
	public void setCaseType(Integer caseType) 
	{
		this.caseType = caseType;
	}

	public Integer getCaseType() 
	{
		return caseType;
	}
	public void setCreateId(Long createId) 
	{
		this.createId = createId;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public Long getCreateId()
	{
		return createId;
	}
	public void setCreateName(String createName) 
	{
		this.createName = createName;
	}

	public String getCreateName() 
	{
		return createName;
	}
	public void setCourseNum(Integer courseNum) 
	{
		this.courseNum = courseNum;
	}

	public Integer getCourseNum() 
	{
		return courseNum;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("caseName", getCaseName())
            .append("version", getVersion())
            .append("caseIcon", getCaseIcon())
            .append("caseDesc", getCaseDesc())
            .append("caseType", getCaseType())
            .append("createId", getCreateId())
            .append("createName", getCreateName())
            .append("courseNum", getCourseNum())
            .toString();
    }
}
