package com.example.E_Commerce.E_commerce.Models.Admin.response;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.NonCertDiamondName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonCertDiamondPrice implements Serializable {
    private NonCertDiamondName nonCertDiamondName;
    private double totalPrice;
    private double totalWeight;
    private int totalDiamonds;
}