package com.qa.request.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsentOtpVerifyPojo {

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("otp")
    private String otp;

    public ConsentOtpVerifyPojo() {
    }

    public ConsentOtpVerifyPojo(String mobileNumber, String otp) {
        super();
        this.mobileNumber = mobileNumber;
        this.otp = otp;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return this.otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
