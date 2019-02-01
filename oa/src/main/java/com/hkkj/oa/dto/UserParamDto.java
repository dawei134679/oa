package com.hkkj.oa.dto;

public class UserParamDto extends PageParam {

	private static final long serialVersionUID = 276282466270888726L;

	private String userCode;

	private String userName;

	private Integer orgId;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	
}
