package com.example.E_Commerce.E_commerce.Models.Admin.response;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.SieveSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondSpecs implements Serializable {
    private SieveSize sieveSize;
    private double weightInCarat;
    private double pricePerCarat;
    private int numberOfDiamonds;
}
