package com.example.E_Commerce.E_commerce.Models.Admin.entities;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Metal;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.MetalForPricing;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.MetalPriceId;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Purity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MetalPriceId.class)
public class MetalPrice implements Serializable {
    @Id
    MetalForPricing metal;

    @Id
    @NonNull
    Purity purity;

    @NonNull
    @Column(precision = 10, scale = 2)
    double price;

    public static MetalForPricing getMetalForPricing(Metal realMetal){
        if(realMetal==Metal.Platinum){
            return MetalForPricing.Platinum;
        }else{
            return MetalForPricing.Gold;
        }
    }
}