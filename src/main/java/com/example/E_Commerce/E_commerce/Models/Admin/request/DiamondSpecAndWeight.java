package com.example.E_Commerce.E_commerce.Models.Admin.request;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.NonCertDiamondName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondSpecAndWeight implements Serializable {
    private NonCertDiamondName nonCertDiamondName;
    private String sieveSize;
    private double totalDiamondWeight;
}
