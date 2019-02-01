package com.hkkj.oa.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaseDetailParamDto implements Serializable {
	private static final long serialVersionUID = 5800190384107783739L;

	private String name;// 名称
	private String specifications;// 规格
	private Double num;// 数量
	private String unit;// 单位
	private BigDecimal price;// 价格
	private String description;// 说明

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
