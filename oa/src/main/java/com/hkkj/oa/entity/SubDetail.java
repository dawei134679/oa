package com.hkkj.oa.entity;

public class SubDetail {
	private String goodsCode;

    private String goodsName;

    private String warehouseName;

    private Integer goodsAmount;
    
    private String preCapacity;
    
    private String goodsUnit;

    private Double transportCost;

    private Double dTransportCost;
    
    private String note;

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Integer getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(Integer goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getPreCapacity() {
		return preCapacity;
	}

	public void setPreCapacity(String preCapacity) {
		this.preCapacity = preCapacity;
	}

	public Double getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(Double transportCost) {
		this.transportCost = transportCost;
	}

	public Double getdTransportCost() {
		return dTransportCost;
	}

	public void setdTransportCost(Double dTransportCost) {
		this.dTransportCost = dTransportCost;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
