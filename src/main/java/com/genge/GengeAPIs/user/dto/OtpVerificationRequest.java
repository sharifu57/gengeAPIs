package com.genge.GengeAPIs.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerificationRequest {
    private String phone;
    private String otp;
}
