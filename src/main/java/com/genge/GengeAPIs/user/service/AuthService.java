package com.genge.GengeAPIs.user.service;

import com.genge.GengeAPIs.common.service.JwtService;
import com.genge.GengeAPIs.common.service.SmsService;
import com.genge.GengeAPIs.config.SystemEnv;
import com.genge.GengeAPIs.response.APIResponse;
import com.genge.GengeAPIs.response.AuthResponse;
import com.genge.GengeAPIs.user.dto.OtpVerificationRequest;
import com.genge.GengeAPIs.user.dto.SignInRequest;
import com.genge.GengeAPIs.user.dto.UserResponse;
import com.genge.GengeAPIs.user.entity.OTP;
import com.genge.GengeAPIs.user.entity.RefreshToken;
import com.genge.GengeAPIs.user.entity.User;
import com.genge.GengeAPIs.user.enums.OTPStatus;
import com.genge.GengeAPIs.user.enums.UserStatus;
import com.genge.GengeAPIs.user.repository.OTPRepository;
import com.genge.GengeAPIs.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    SystemEnv systemEnv;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OTPRepository otpRepository;

    @Autowired
    SmsService smsService;

    @Autowired
    JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Transactional
    public AuthResponse signIn(SignInRequest request) {

        AuthResponse response = new AuthResponse<>();

        try {

            if (request.getPhone() == null || request.getPhone().isEmpty()) {
                response.setStatus(false);
                response.setMessage("Phone number is required.");
                return response;
            }

            User user = userRepository.findByPhone(request.getPhone()).orElse(null);

            if (user == null) {
                user = new User();
                user.setPhone(request.getPhone());
                user.setFullName(request.getFullName() != null
                        ? request.getFullName()
                        : "User");

                user.setStatus(UserStatus.ACTIVE);

                user = userRepository.save(user);
            }

            String otpCode = generateOtp();

            OTP otp = new OTP();
            otp.setOtp(otpCode);
            otp.setPhone(user.getPhone());
            otp.setUser(user);
            otp.setExpiresAt(
                    new Date(System.currentTimeMillis() + (5 * 60 * 1000))
            );
            otp.setStatus(OTPStatus.PENDING);

            otpRepository.save(otp);

            // 5. SEND SMS
            String message = "Your verification code is: " + otpCode;
            String subject = user.getPhone();
            String accessToken = jwtService.generateAccessToken(subject);
            RefreshToken newToken = refreshTokenService.createRefreshToken((long) user.getId());
            List<String> phones = List.of(user.getPhone());
            smsService.sendMessage(
                    message,
                    phones,
                    (long) user.getId()
            );

            response.setStatus(true);
            response.setMessage("OTP sent successfully");
            response.setPhone(user.getPhone());
            response.setToken(accessToken);
            response.setRefreshToken(newToken.getToken());
            response.setExpired(String.valueOf(jwtService.getExpirationTime()));

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(false);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Transactional
    public APIResponse<?> verifyOtp(OtpVerificationRequest request) {

        APIResponse<Object> response = new APIResponse<>();

        try {

            if (request.getPhone() == null || request.getPhone().isBlank()) {
                response.setStatus(false);
                response.setMessage("Nama ya sim inahitajiaka.");
                return response;
            }

            if (request.getOtp() == null || request.getOtp().isBlank()) {
                response.setStatus(false);
                response.setMessage("OTP inahitajika.");
                return response;
            }

            OTP otp = otpRepository
                    .findFirstByPhoneAndOtpAndStatusOrderByIdDesc(
                            request.getPhone(),
                            request.getOtp(),
                            OTPStatus.PENDING
                    )
                    .orElse(null);

            if (otp == null) {
                response.setStatus(false);
                response.setMessage("OTP sio sahihi");
                return response;
            }

            if (otp.getExpiresAt() != null &&
                    otp.getExpiresAt().before(new Date())) {

                response.setStatus(false);
                response.setMessage("OTP ime expire.");
                return response;
            }

            otp.setStatus(OTPStatus.VERIFIED);
            otpRepository.save(otp);

            User user = otp.getUser();

            UserResponse userResponse = new UserResponse(
                    (long) user.getId(),
                    user.getFullName(),
                    user.getPhone()
            );

            response.setStatus(true);
            response.setMessage("OTP Imethibitishwa kikamilifu.");
            response.setData(userResponse);

            return response;

        } catch (Exception e) {
            LOGGER.error("OTP Verification Error", e);

            response.setStatus(false);
            response.setMessage("Imeshindikana ku verifai OTP.");

            return response;
        }
    }
}
