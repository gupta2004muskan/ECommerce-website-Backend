package com.example.E_Commerce.E_commerce.Repository.Customer;
import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.ShoppingCart;
import com.example.E_Commerce.E_commerce.Models.Customer.models.CustomerSelectedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, CustomerSelectedProduct> {
    Optional<List<ShoppingCart>> findAllByUsername(MyUser username);
}
