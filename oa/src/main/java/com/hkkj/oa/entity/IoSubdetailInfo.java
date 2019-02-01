package com.hkkj.oa.entity;

public class IoSubdetailInfo {
	private Integer id;

	private String detailOrder;

	private String goodsCode;

	private String warehouseName;

	private Integer goodsAmount;

	private String preCapacity;

	private Double transportCost;

	private Double dTransportCost;

	private String note;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetailOrder() {
		return detailOrder;
	}

	public void setDetailOrder(String detailOrder) {
		this.detailOrder = detailOrder == null ? null : detailOrder.trim();
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode == null ? null : goodsCode.trim();
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName == null ? null : warehouseName.trim();
	}

	public Integer getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(Integer goodsAmount) {
		this.goodsAmount = goodsAmount;
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
		this.note = note == null ? null : note.trim();
	}
}