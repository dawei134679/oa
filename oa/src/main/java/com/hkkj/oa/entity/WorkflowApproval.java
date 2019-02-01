package com.hkkj.oa.entity;

import java.io.Serializable;

public class WorkflowApproval implements Serializable {
	private static final long serialVersionUID = -9096177766554818774L;

	private Long id;

    private Long workflowId;

    private Long workflowApplyId;

    private Integer status;

    private String remarks;

    private Long approvalTime;

    private Integer approvalUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public Long getWorkflowApplyId() {
        return workflowApplyId;
    }

    public void setWorkflowApplyId(Long workflowApplyId) {
        this.workflowApplyId = workflowApplyId;
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
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Long approvalTime) {
        this.approvalTime = approvalTime;
    }

    public Integer getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(Integer approvalUserId) {
        this.approvalUserId = approvalUserId;
    }
}