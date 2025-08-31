package com.example.E_Commerce.E_commerce.Models.Customer.entites;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.Product;
import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import com.example.E_Commerce.E_commerce.Models.Customer.models.CustomerWishListProduct;
import jakarta.persistence.MapsId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "wish_list")
@IdClass(CustomerWishListProduct.class)
public class WishList implements Serializable {

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
}