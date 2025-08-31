package com.example.E_Commerce.E_commerce.Models.Customer.models;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.Product;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.ClarityAndColor;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Metal;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Purity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
@Data
@AllArgsConstructor
public class CustomerSelectedProduct implements Serializable {
    private String username;

    private Long product;

    private ClarityAndColor clarityAndColor;

    private Purity purity;

    private Metal metal;

    private char ringSize;
}