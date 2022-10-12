package com.qa.request.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleLoginRequestPojo {
    @JsonProperty("pagecode")
    private String pagecode;
    @JsonProperty("primaryDeviceId")
    private String primaryDeviceId;
    @JsonProperty("appVersion")
    private String appVersion;
    @JsonProperty("platformVersion")
    private String platformVersion;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("modelNo")
    private String modelNo;
    @JsonProperty("networkType")
    private String networkType;
    @JsonProperty("deviceType")
    private String deviceType;
    @JsonProperty("ipaddress")
    private String ipaddress;
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @JsonProperty("password")
    private String password;
    @JsonProperty("passwordType")
    private String passwordType;
    @JsonProperty("screenSize")
    private String screenSize;

    public SimpleLoginRequestPojo() {
    }

    public SimpleLoginRequestPojo(String pagecode, String primaryDeviceId, String appVersion, String platformVersion,
                                  String platform, String modelNo, String networkType,
                                  String deviceType, String ipaddress, String mobileNumber,
                                  String password, String passwordType, String screenSize) {
        super();
        this.pagecode = pagecode;
        this.primaryDeviceId = primaryDeviceId;
        this.appVersion = appVersion;
        this.platformVersion = platformVersion;
        this.platform = platform;
        this.modelNo = modelNo;
        this.networkType = networkType;
        this.deviceType = deviceType;
        this.ipaddress = ipaddress;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.passwordType = passwordType;
        this.screenSize = screenSize;
    }

    @JsonProperty("pagecode")
    public String getPagecode() {
        return pagecode;
    }

    @JsonProperty("pagecode")
    public void setPagecode(String pagecode) {
        this.pagecode = pagecode;
    }

    @JsonProperty("primaryDeviceId")
    public String getPrimaryDeviceId() {
        return primaryDeviceId;
    }

    @JsonProperty("primaryDeviceId")
    public void setPrimaryDeviceId(String primaryDeviceId) {
        this.primaryDeviceId = primaryDeviceId;
    }

    @JsonProperty("appVersion")
    public String getAppVersion() {
        return appVersion;
    }

    @JsonProperty("appVersion")
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @JsonProperty("platformVersion")
    public String getPlatformVersion() {
        return platformVersion;
    }

    @JsonProperty("platformVersion")
    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    @JsonProperty("platform")
    public String getPlatform() {
        return platform;
    }

    @JsonProperty("platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @JsonProperty("modelNo")
    public String getModelNo() {
        return modelNo;
    }

    @JsonProperty("modelNo")
    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    @JsonProperty("networkType")
    public String getNetworkType() {
        return networkType;
    }

    @JsonProperty("networkType")
    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    @JsonProperty("deviceType")
    public String getDeviceType() {
        return deviceType;
    }

    @JsonProperty("deviceType")
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @JsonProperty("ipaddress")
    public String getIpaddress() {
        return ipaddress;
    }

    @JsonProperty("ipaddress")
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    @JsonProperty("mobileNumber")
    public String getMobileNumber() {
        return mobileNumber;
    }

    @JsonProperty("mobileNumber")
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("passwordType")
    public String getPasswordType() {
        return passwordType;
    }

    @JsonProperty("passwordType")
    public void setPasswordType(String passwordType) {
        this.passwordType = passwordType;
    }

    @JsonProperty("screenSize")
    public String getScreenSize() {
        return screenSize;
    }

    @JsonProperty("screenSize")
    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }
}
