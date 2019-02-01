package com.hkkj.oa.dto;

import java.io.Serializable;

public class PageParam implements Serializable {

	private static final long serialVersionUID = 3364477962230227701L;

	private Integer page = 1;
	
	private Integer pageSize = 20;
	
	private String orderBy;
	
	private String sort;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
