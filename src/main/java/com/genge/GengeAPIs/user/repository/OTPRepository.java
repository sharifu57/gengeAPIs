package com.genge.GengeAPIs.user.repository;

import com.genge.GengeAPIs.user.entity.OTP;
import com.genge.GengeAPIs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP,Long> {
    Optional<OTP> findByUser(
            User user
    );

    Optional<OTP> findByPhone(String phone);
    Optional<OTP> findByPhoneAndOtp(String phone, String otp);
    Optional<OTP> findByOtp(String otp);
}
