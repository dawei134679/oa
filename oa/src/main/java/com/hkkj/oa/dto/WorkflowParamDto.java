package com.hkkj.oa.dto;

import java.util.List;

public class WorkflowParamDto extends PageParam {

	private static final long serialVersionUID = 8666971022889034735L;
	
	private String name;
	
    private Integer status;

    private Long modifyTime;

    private Integer modifyUserId;
	
    private List<Long> ids;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Integer modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
