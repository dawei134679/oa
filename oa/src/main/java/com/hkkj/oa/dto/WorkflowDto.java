package com.hkkj.oa.dto;

import java.io.Serializable;
import java.util.List;

public class WorkflowDto implements Serializable {

	private static final long serialVersionUID = 4011049205335251426L;

	private  Long workflowId;
	
	private  String workflowCode;
	
	private String workflowName;
	
	private String workflowRemarks;
	
	private List<WorkflowApprovalDetailDto> approvalDetailList;

	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

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

	public String getWorkflowRemarks() {
		return workflowRemarks;
	}

	public void setWorkflowRemarks(String workflowRemarks) {
		this.workflowRemarks = workflowRemarks;
	}

	public List<WorkflowApprovalDetailDto> getApprovalDetailList() {
		return approvalDetailList;
	}

	public void setApprovalDetailList(List<WorkflowApprovalDetailDto> approvalDetailList) {
		this.approvalDetailList = approvalDetailList;
	}
}
