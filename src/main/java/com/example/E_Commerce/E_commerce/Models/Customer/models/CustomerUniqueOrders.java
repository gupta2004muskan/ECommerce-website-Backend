package com.example.E_Commerce.E_commerce.Models.Customer.models;

import com.example.E_Commerce.E_commerce.Models.Customer.entites.models.UniqueProductOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
@Data
@AllArgsConstructor
public class CustomerUniqueOrders {
    private Long id;
    private UniqueProductOptions uniqueProductOptions;
}