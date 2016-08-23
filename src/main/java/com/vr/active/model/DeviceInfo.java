package com.vr.active.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class DeviceInfo {
    /**
     * 
     */
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * VR唯一标识mac
     */
    private String deviceMac;

    /**
     * VR设备编号
     */
    private String deviceCode;

    /**
     * VR设备名称
     */
    private String deviceName;

    /**
     * 场地名称
     */
    private String siteName;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 场地编码
     */
    private String siteCode;

    /**
     * 是否已经同步到服务端
     */
    private String isSync;

    /**
     * 创建时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getIsSync() {
        return isSync;
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public DeviceInfo(Integer id, String account, String username,
			String password, String deviceMac, String deviceCode,
			String deviceName, String siteName, String provinceCode,
			String cityCode, String siteCode, String isSync, Date createTime) {
		super();
		this.id = id;
		this.account = account;
		this.username = username;
		this.password = password;
		this.deviceMac = deviceMac;
		this.deviceCode = deviceCode;
		this.deviceName = deviceName;
		this.siteName = siteName;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.siteCode = siteCode;
		this.isSync = isSync;
		this.createTime = createTime;
	}

    
	public DeviceInfo() {
		super();
	}

	public enum Sync {
		YES("yes"), 
		NO("no");
		private final String value;

		Sync(String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return value;
		}

	}
	
	@Override
	public String toString() {
		return "DeviceInfo [id=" + id + ", account=" + account + ", username="
				+ username + ", password=" + password + ", deviceMac="
				+ deviceMac + ", deviceCode=" + deviceCode + ", deviceName="
				+ deviceName + ", siteName=" + siteName + ", provinceCode="
				+ provinceCode + ", cityCode=" + cityCode + ", siteCode="
				+ siteCode + ", isSync=" + isSync + ", createTime="
				+ createTime + "]";
	}

	public static void main(String[] args) {
		List<DeviceInfo> infos = new ArrayList<DeviceInfo>();
		infos.add(new DeviceInfo(1, "account", "1username", "password1", "deviceMac1", "deviceCode", "deviceName", "siteName", "provinceCode", "cityCode", "siteCode", "isSync", new Date()));
		System.out.println(JSONArray.toJSONString(infos));
	}
}