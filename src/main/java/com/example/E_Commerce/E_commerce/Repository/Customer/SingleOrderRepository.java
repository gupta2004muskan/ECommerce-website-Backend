package com.example.E_Commerce.E_commerce.Repository.Customer;

import com.example.E_Commerce.E_commerce.Models.Customer.models.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SingleOrderRepository extends JpaRepository<OrderedProduct, Long>{
}
