package com.qa.request.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionInitiatePojo {
    int amount;
    String appkey;
    String bankAccountNo;
    String bankCode;
    String bankName;
    String cuserId;
    String ifscCode;
    String redirectUrl;
    String transactionMethod;

    @JsonProperty("amount")
    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @JsonProperty("appkey")
    public String getAppkey() {
        return this.appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    @JsonProperty("bankAccountNo")
    public String getBankAccountNo() {
        return this.bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    @JsonProperty("bankCode")
    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @JsonProperty("bankName")
    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @JsonProperty("cuserId")
    public String getCuserId() {
        return this.cuserId;
    }

    public void setCuserId(String cuserId) {
        this.cuserId = cuserId;
    }

    @JsonProperty("ifscCode")
    public String getIfscCode() {
        return this.ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    @JsonProperty("redirectUrl")
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @JsonProperty("transactionMethod")
    public String getTransactionMethod() {
        return this.transactionMethod;
    }

    public void setTransactionMethod(String transactionMethod) {
        this.transactionMethod = transactionMethod;
    }
}
