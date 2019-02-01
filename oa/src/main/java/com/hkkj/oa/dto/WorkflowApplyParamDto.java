package com.hkkj.oa.dto;

import java.io.Serializable;

public class WorkflowApplyParamDto implements Serializable {

	private static final long serialVersionUID = -697415576287202134L;

	private String applyCode;
	
	private String workflowCode;

	private Integer applyUserId;
	
	private String extra;
	
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public String getWorkflowCode() {
		return workflowCode;
	}
	public void setWorkflowCode(String workflowCode) {
		this.workflowCode = workflowCode;
	}
	public Integer getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(Integer applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
