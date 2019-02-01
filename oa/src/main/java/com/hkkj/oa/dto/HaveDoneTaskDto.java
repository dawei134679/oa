package com.hkkj.oa.dto;

import java.io.Serializable;

public class HaveDoneTaskDto implements Serializable {

	private static final long serialVersionUID = 7501144609589811345L;

	private  String workflowCode;
	
	private String workflowName;
	
	private String applyCode;
	
	private Long applyTime;
	
	private Integer status;
	
	private String extra;
	
	private String remarks;
	
	private Long approvalTime;

	public String getWorkflowCode() {
		return workflowCode;
	}

	public void setWorkflowCode(String workflowCode) {
		this.workflowCode = workflowCode;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public Long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Long approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	
}
