package com.example.E_Commerce.E_commerce.Models.Admin.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondSpecsResponse implements Serializable {
    private double price;
    private double perDiamondWeight;
    private int numberOfDiamonds;
    private double totalPrice;
}
