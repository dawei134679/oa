package com.hkkj.oa.dto;

import java.io.Serializable;
import java.util.List;

public class PurchaseParamDto implements Serializable {
	private static final long serialVersionUID = -7754717105005414291L;

	private String title;
	private String remark;
	private Integer createUserId;
	private String subDetails;
	private List<PurchaseDetailParamDto> purchaseDetailList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getSubDetails() {
		return subDetails;
	}

	public void setSubDetails(String subDetails) {
		this.subDetails = subDetails;
	}

	public List<PurchaseDetailParamDto> getPurchaseDetailList() {
		return purchaseDetailList;
	}

	public void setPurchaseDetailList(List<PurchaseDetailParamDto> purchaseDetailList) {
		this.purchaseDetailList = purchaseDetailList;
	}

}
