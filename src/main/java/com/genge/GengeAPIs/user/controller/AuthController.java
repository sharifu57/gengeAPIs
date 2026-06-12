package com.genge.GengeAPIs.user.controller;

import com.genge.GengeAPIs.response.APIResponse;
import com.genge.GengeAPIs.response.AuthResponse;
import com.genge.GengeAPIs.user.dto.OtpVerificationRequest;
import com.genge.GengeAPIs.user.dto.SignInRequest;
import com.genge.GengeAPIs.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;

    @Value("${http.secure.value}")
    private boolean httpSecureValue;

    @Value("${same.site}")
    private String sameSite;

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse<?>> signIn(@Valid @RequestBody SignInRequest request){
        AuthResponse<?> response = authService.signIn(request);
        ResponseCookie accessCookie = ResponseCookie.from("token", response.getToken())
                .httpOnly(true)
                .secure(httpSecureValue) //true in prod
                .path("/")
                .sameSite(sameSite)
                .maxAge(60 * 60 * 24) //1 day
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", response.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite(sameSite)
                .maxAge(60 * 60 * 24 * 7) //7 days
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<APIResponse> verifyOtp(@Valid @RequestBody OtpVerificationRequest request){
        APIResponse response = authService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }
}
