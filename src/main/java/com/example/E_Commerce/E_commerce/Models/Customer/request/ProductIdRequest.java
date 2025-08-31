package com.example.E_Commerce.E_commerce.Models.Customer.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductIdRequest implements Serializable {
    private Long productId;
}