package com.example.E_Commerce.E_commerce.Models.Customer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
@Data
@AllArgsConstructor
public class CustomerWishListProduct implements Serializable {
    private String username;

    private Long product;
}