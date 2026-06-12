package com.genge.GengeAPIs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse<T> {
    private boolean status;
    private String message;
    private String phone;
    private String token;
    private String refreshToken;
    private String expired;
    private T data;

    public AuthResponse(boolean status, String message, String phone, T data) {
        this.status = status;
        this.message = message;
        this.phone = phone;
        this.token = null;
        this.refreshToken = null;
        this.data = data;
    }
}
