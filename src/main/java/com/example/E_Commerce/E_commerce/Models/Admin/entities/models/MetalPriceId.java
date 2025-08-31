package com.example.E_Commerce.E_commerce.Models.Admin.entities.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class MetalPriceId implements Serializable {
    MetalForPricing metal;
    Purity purity;
}
