package com.hkkj.oa.entity;

public class CwarehouseInfo {
    private Integer id;

    private String name;

    private String capacity;

    private String note;

    private Integer type;

    private Integer administratorUid;

    private String administratorName;

    private String administratorPhone;

    private Integer status;

    private Long createTime;

    private Integer createUserId;

    private Long updateTime;

    private Integer updateUserId;

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
        this.name = name == null ? null : name.trim();
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity == null ? null : capacity.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAdministratorUid() {
        return administratorUid;
    }

    public void setAdministratorUid(Integer administratorUid) {
        this.administratorUid = administratorUid;
    }

    public String getAdministratorName() {
        return administratorName;
    }

    public void setAdministratorName(String administratorName) {
        this.administratorName = administratorName == null ? null : administratorName.trim();
    }

    public String getAdministratorPhone() {
        return administratorPhone;
    }

    public void setAdministratorPhone(String administratorPhone) {
        this.administratorPhone = administratorPhone == null ? null : administratorPhone.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}