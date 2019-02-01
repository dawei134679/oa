package com.hkkj.oa.dto;

import java.io.Serializable;

public class ApprovalRecordDto implements Serializable {

	private static final long serialVersionUID = -6308367039078557073L;

	private Integer approvalUserId;
	private String approvalUserCode;
	private String approvalUserName;
	private Integer approvalOrgId;
	private String approvalOrgCode;
	private String approvalOrgName;
	private Integer approvalStatus;
	private String approvalRemarks;
	private Long approvalTime;

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

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalRemarks() {
		return approvalRemarks;
	}

	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	public Long getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Long approvalTime) {
		this.approvalTime = approvalTime;
	}

}