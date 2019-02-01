package com.hkkj.oa.dto;

import java.io.Serializable;

public class WorkflowApplyInfoParamDto extends PageParam implements Serializable {

	private static final long serialVersionUID = -7866014486207515229L;

	private Integer userId;
	
	private Integer  status;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
