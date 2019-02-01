package com.hkkj.oa.entity;

public class IoDetailInfo {
    private Integer id;

    private Integer type;

    private Integer transportType;

    private String tansportName;

    private String tansportNo;

    private String transportCode;

    private String contractCode;

    private String billCode;

    private String provider;

    private String responsibleMan;

    private String operator;

    private Integer operateTime;

    private String dTransportNo;

    private String wtypeName;

    private String wtypeCode;

    private String detailOrder;

    private Integer noStatus;
    
    private String subDetails;

    public String  getSubDetails() {
		return subDetails;
	}

	public void setSubDetails(String  subDetails) {
		this.subDetails = subDetails;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTransportType() {
        return transportType;
    }

    public void setTransportType(Integer transportType) {
        this.transportType = transportType;
    }

    public String getTansportName() {
        return tansportName;
    }

    public void setTansportName(String tansportName) {
        this.tansportName = tansportName == null ? null : tansportName.trim();
    }

    public String getTansportNo() {
        return tansportNo;
    }

    public void setTansportNo(String tansportNo) {
        this.tansportNo = tansportNo == null ? null : tansportNo.trim();
    }

    public String getTransportCode() {
        return transportCode;
    }

    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode == null ? null : transportCode.trim();
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode == null ? null : billCode.trim();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider == null ? null : provider.trim();
    }

    public String getResponsibleMan() {
        return responsibleMan;
    }

    public void setResponsibleMan(String responsibleMan) {
        this.responsibleMan = responsibleMan == null ? null : responsibleMan.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Integer getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Integer operateTime) {
        this.operateTime = operateTime;
    }

    public String getdTransportNo() {
        return dTransportNo;
    }

    public void setdTransportNo(String dTransportNo) {
        this.dTransportNo = dTransportNo == null ? null : dTransportNo.trim();
    }

    public String getWtypeName() {
        return wtypeName;
    }

    public void setWtypeName(String wtypeName) {
        this.wtypeName = wtypeName == null ? null : wtypeName.trim();
    }

    public String getWtypeCode() {
        return wtypeCode;
    }

    public void setWtypeCode(String wtypeCode) {
        this.wtypeCode = wtypeCode == null ? null : wtypeCode.trim();
    }

    public String getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(String detailOrder) {
        this.detailOrder = detailOrder == null ? null : detailOrder.trim();
    }

    public Integer getNoStatus() {
        return noStatus;
    }

    public void setNoStatus(Integer noStatus) {
        this.noStatus = noStatus;
    }
}