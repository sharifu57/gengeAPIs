package com.genge.GengeAPIs.common.entity;

import com.genge.GengeAPIs.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "SMS")
@Getter
@Setter
@ToString
public class SMS extends Auditable<String> implements Serializable {
    @Column(name = "request_id")
    private String requestId;

    @Column(name = "recipient")
    private String recipient;

    private String status;

    @Column(name = "gateway_response")
    private String gatewayResponse;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
