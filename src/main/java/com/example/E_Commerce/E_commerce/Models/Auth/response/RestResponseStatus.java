package com.example.E_Commerce.E_commerce.Models.Auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class RestResponseStatus implements Serializable {
    private String status;
}