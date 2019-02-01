package com.hkkj.oa.dto;

public class ProcessedTasksParamDto extends  PageParam{

	private static final long serialVersionUID = 9152234787380780511L;

	private Integer  userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
