package com.example.E_Commerce.E_commerce.Models.Customer.request;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.DiamondSetting;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Metal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogFilterRequest {
    private List<DiamondSetting> diamondSetting;
    private List<Metal> metal;
    private Float upperLimit;
    private Float lowerLimit;
}