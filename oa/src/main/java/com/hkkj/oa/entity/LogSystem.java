package com.hkkj.oa.entity;

public class LogSystem {
    private Long id;

    private String module;

    private String remark;

    private Integer clientIp;

    private String callClass;

    private String callMethod;

    private Integer callType;

    private String webFullPath;

    private String webMethod;

    private String webClientName;

    private String webClientBrowser;

    private String webClientBrowserVersion;

    private String webClientOs;

    private Long beginTime;

    private Long endTime;

    private Integer useTime;

    private Long createTime;

    private String webParameter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getClientIp() {
        return clientIp;
    }

    public void setClientIp(Integer clientIp) {
        this.clientIp = clientIp;
    }

    public String getCallClass() {
        return callClass;
    }

    public void setCallClass(String callClass) {
        this.callClass = callClass == null ? null : callClass.trim();
    }

    public String getCallMethod() {
        return callMethod;
    }

    public void setCallMethod(String callMethod) {
        this.callMethod = callMethod == null ? null : callMethod.trim();
    }

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public String getWebFullPath() {
        return webFullPath;
    }

    public void setWebFullPath(String webFullPath) {
        this.webFullPath = webFullPath == null ? null : webFullPath.trim();
    }

    public String getWebMethod() {
        return webMethod;
    }

    public void setWebMethod(String webMethod) {
        this.webMethod = webMethod == null ? null : webMethod.trim();
    }

    public String getWebClientName() {
        return webClientName;
    }

    public void setWebClientName(String webClientName) {
        this.webClientName = webClientName == null ? null : webClientName.trim();
    }

    public String getWebClientBrowser() {
        return webClientBrowser;
    }

    public void setWebClientBrowser(String webClientBrowser) {
        this.webClientBrowser = webClientBrowser == null ? null : webClientBrowser.trim();
    }

    public String getWebClientBrowserVersion() {
        return webClientBrowserVersion;
    }

    public void setWebClientBrowserVersion(String webClientBrowserVersion) {
        this.webClientBrowserVersion = webClientBrowserVersion == null ? null : webClientBrowserVersion.trim();
    }

    public String getWebClientOs() {
        return webClientOs;
    }

    public void setWebClientOs(String webClientOs) {
        this.webClientOs = webClientOs == null ? null : webClientOs.trim();
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getWebParameter() {
        return webParameter;
    }

    public void setWebParameter(String webParameter) {
        this.webParameter = webParameter == null ? null : webParameter.trim();
    }

	@Override
	public String toString() {
		return "LogSystem [id=" + id + ", module=" + module + ", remark=" + remark + ", clientIp=" + clientIp
				+ ", callClass=" + callClass + ", callMethod=" + callMethod + ", callType=" + callType
				+ ", webFullPath=" + webFullPath + ", webMethod=" + webMethod + ", webClientName=" + webClientName
				+ ", webClientBrowser=" + webClientBrowser + ", webClientBrowserVersion=" + webClientBrowserVersion
				+ ", webClientOs=" + webClientOs + ", beginTime=" + beginTime + ", endTime=" + endTime + ", useTime="
				+ useTime + ", createTime=" + createTime + ", webParameter=" + webParameter + "]";
	}
    
}