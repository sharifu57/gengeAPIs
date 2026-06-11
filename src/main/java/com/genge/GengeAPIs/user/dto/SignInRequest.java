package com.genge.GengeAPIs.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String fullName;
    private String phone;
}
