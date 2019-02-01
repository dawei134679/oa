package com.hkkj.oa.dto;

public class InventoryDetailParamDto extends PageParam {

	private static final long serialVersionUID = -7065188737091554669L;

	private String warehouseName;

	private String goodsCode;

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
}
