package com.hkkj.oa.dto;

import java.io.Serializable;
import java.util.List;

public class OrganizationTreeNodeDto implements Serializable {

	private static final long serialVersionUID = 2537811206019909569L;
	private Integer id;

    private String orgCode;

    private String orgName;
    
    private Integer leaderId;

    private Integer orgType;

    private Integer parentId;

    private String parentCode;

    private String remarks;

    private Integer sort;
	
	private List<OrganizationTreeNodeDto> childNode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<OrganizationTreeNodeDto> getChildNode() {
		return childNode;
	}

	public void setChildNode(List<OrganizationTreeNodeDto> childNode) {
		this.childNode = childNode;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}
	
}
