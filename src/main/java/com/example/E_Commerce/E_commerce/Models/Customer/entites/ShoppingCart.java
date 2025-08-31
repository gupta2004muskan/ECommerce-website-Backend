package com.example.E_Commerce.E_commerce.Models.Customer.entites;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.Product;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.ClarityAndColor;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Metal;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Purity;
import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import com.example.E_Commerce.E_commerce.Models.Customer.models.CustomerSelectedProduct;
import jakarta.persistence.MapsId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "shopping_cart")
@IdClass(CustomerSelectedProduct.class)
@AllArgsConstructor
public class ShoppingCart implements Serializable {

    @Id
    @NonNull
    @MapsId("username")
    @ManyToOne
    private MyUser username;

    @Id
    @NonNull
    @MapsId("product")
    @ManyToOne
    private Product product;

    @Id
    @NonNull
    private ClarityAndColor clarityAndColor;

    @Id
    @NonNull
    private Purity purity;

    @Id
    @NonNull
    private Metal metal;

    private Metal metal2;

    @Id
    @NonNull
    private char ringSize;
}