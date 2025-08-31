package com.example.E_Commerce.E_commerce.Models.Auth.request;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class confirmEmailId implements Serializable {
    private String userName;
    private String otp;
}

