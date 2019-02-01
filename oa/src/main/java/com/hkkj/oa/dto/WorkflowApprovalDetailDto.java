package com.hkkj.oa.dto;

import java.io.Serializable;

public class WorkflowApprovalDetailDto implements Serializable {
	private static final long serialVersionUID = 5713108642067561416L;
	 
	private  Long workflowDetailId;
	
	private  String workflowDetailCode;
	
	private  String workflowDetailParentCode;
	
	private  Integer approvalUserId;
	
	private  String  approvalUserCode;

	private  String approvalUserName;
	
	private  Integer approvalOrgId;
	
	private  String approvalOrgCode;
	
	private  String approvalOrgName;

	public Long getWorkflowDetailId() {
		return workflowDetailId;
	}

	public void setWorkflowDetailId(Long workflowDetailId) {
		this.workflowDetailId = workflowDetailId;
	}

	public String getWorkflowDetailCode() {
		return workflowDetailCode;
	}

	public void setWorkflowDetailCode(String workflowDetailCode) {
		this.workflowDetailCode = workflowDetailCode;
	}

	public String getWorkflowDetailParentCode() {
		return workflowDetailParentCode;
	}

	public void setWorkflowDetailParentCode(String workflowDetailParentCode) {
		this.workflowDetailParentCode = workflowDetailParentCode;
	}

	public Integer getApprovalUserId() {
		return approvalUserId;
	}

	public void setApprovalUserId(Integer approvalUserId) {
		this.approvalUserId = approvalUserId;
	}

	public String getApprovalUserCode() {
		return approvalUserCode;
	}

	public void setApprovalUserCode(String approvalUserCode) {
		this.approvalUserCode = approvalUserCode;
	}

	public String getApprovalUserName() {
		return approvalUserName;
	}

	public void setApprovalUserName(String approvalUserName) {
		this.approvalUserName = approvalUserName;
	}

	public Integer getApprovalOrgId() {
		return approvalOrgId;
	}

	public void setApprovalOrgId(Integer approvalOrgId) {
		this.approvalOrgId = approvalOrgId;
	}

	public String getApprovalOrgCode() {
		return approvalOrgCode;
	}

	public void setApprovalOrgCode(String approvalOrgCode) {
		this.approvalOrgCode = approvalOrgCode;
	}

	public String getApprovalOrgName() {
		return approvalOrgName;
	}

	public void setApprovalOrgName(String approvalOrgName) {
		this.approvalOrgName = approvalOrgName;
	}
}
