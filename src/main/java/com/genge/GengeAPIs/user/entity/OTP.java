package com.genge.GengeAPIs.user.entity;

import com.genge.GengeAPIs.common.entity.Auditable;
import com.genge.GengeAPIs.user.enums.OTPStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name="OTP")
public class OTP extends Auditable<String> implements Serializable {
    @Column(unique = true, length = 6)
    private String otp;

    @Column(name = "phone")
    private String phone;

    @Column(name = "otp_status")
    @Enumerated(EnumType.STRING)
    private OTPStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,referencedColumnName = "id")
    private User user;
}
