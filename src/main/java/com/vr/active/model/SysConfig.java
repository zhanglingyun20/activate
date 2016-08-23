package com.vr.active.model;

import java.util.Date;

public class SysConfig {
    /**
     * 
     */
    private Integer id;

    /**
     * 配置key名称
     */
    private String keyName;

    /**
     * 配置key值
     */
    private String keyValue;

    /**
     * 描述
     */
    private String keyRemark;

    /**
     * 是否有效
     */
    private String isValid;

    /**
     * 创建日期
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyRemark() {
        return keyRemark;
    }

    public void setKeyRemark(String keyRemark) {
        this.keyRemark = keyRemark;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}