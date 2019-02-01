package com.hkkj.oa.dto;

import java.io.Serializable;
import java.util.List;

public class WorkflowInfoParamDto implements Serializable {

	private static final long serialVersionUID = -6650135942470130105L;
	
	private String code;

	private String name;

    private String remarks;

    private Long createTime;

    private Integer createUserId;

    private Long modifyTime;

    private Integer modifyUserId;
    
    private List<WorkflowInfoDetailParamDto> details;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Integer modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public List<WorkflowInfoDetailParamDto> getDetails() {
		return details;
	}

	public void setDetails(List<WorkflowInfoDetailParamDto> details) {
		this.details = details;
	}
}
