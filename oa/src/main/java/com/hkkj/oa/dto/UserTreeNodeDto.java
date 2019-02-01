package com.hkkj.oa.dto;

import java.io.Serializable;

public class UserTreeNodeDto implements Serializable {

	private static final long serialVersionUID = -1551107039189725638L;

	private Integer uid;

	private String userCode;

	private String userName;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

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

}
