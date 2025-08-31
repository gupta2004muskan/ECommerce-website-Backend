package com.example.E_Commerce.E_commerce.Models.Admin.request;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Category;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.ClarityAndColor;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Metal;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Purity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatePriceRequest implements Serializable {
    private Category category;
    private ClarityAndColor clarityAndColor;
    private List<DiamondSpecAndWeight> diamondSpecs;
    private Purity purity;
    private double discount;
    private Map<Metal,Double> metal;
}
