package com.hkkj.oa.dto;

import java.io.Serializable;

public class WorkflowInfoDetailParamDto implements Serializable  {

	private static final long serialVersionUID = 6121249404609639439L;

    private String key;

    private Integer approvalId;

    private Integer type;

    private String conditions;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Integer approvalId) {
		this.approvalId = approvalId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
}