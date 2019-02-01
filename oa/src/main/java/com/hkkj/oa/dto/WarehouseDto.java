package com.hkkj.oa.dto;

import java.io.Serializable;

public class WarehouseDto implements Serializable {

	private static final long serialVersionUID = -3225191049951783233L;

	private Integer id;

	private String name;
	
	private String capacity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	
}
