package com.example.E_Commerce.E_commerce.Repository.Customer;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.Product;
import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.WishList;
import com.example.E_Commerce.E_commerce.Models.Customer.models.CustomerWishListProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, CustomerWishListProduct> {
    Optional<List<WishList>> findAllByUsername(MyUser username);
    Optional<WishList> findByUsernameAndProduct(MyUser username, Product product);
}