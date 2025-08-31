package com.example.E_Commerce.E_commerce.Models.Admin.request;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.ClarityAndColor;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.NonCertDiamondName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiamondSpecsRequest implements Serializable {
    private NonCertDiamondName diamondName;
    private String sieveSize;
    private ClarityAndColor clarityAndColor;
    private double totalWeight;
}
