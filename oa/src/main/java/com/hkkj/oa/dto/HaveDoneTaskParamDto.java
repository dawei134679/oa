package com.hkkj.oa.dto;

public class HaveDoneTaskParamDto extends  PageParam{

	private static final long serialVersionUID = 780792601763730833L;
	
	private Integer  userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
