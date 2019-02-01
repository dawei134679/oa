package com.hkkj.oa.dto;

import java.io.Serializable;

public class ApprovalDto implements Serializable{

	private static final long serialVersionUID = -8376298707028220631L;

	private String applyCode;
	
	private Integer status;
	
	private String remarks;
	
	private Integer approvalUserId;

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getApprovalUserId() {
		return approvalUserId;
	}

	public void setApprovalUserId(Integer approvalUserId) {
		this.approvalUserId = approvalUserId;
	}
}
