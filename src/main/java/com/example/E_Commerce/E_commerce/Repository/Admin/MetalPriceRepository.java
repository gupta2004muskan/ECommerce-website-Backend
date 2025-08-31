package com.example.E_Commerce.E_commerce.Repository.Admin;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.MetalPrice;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.MetalPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MetalPriceRepository extends JpaRepository<MetalPrice, MetalPriceId>, JpaSpecificationExecutor<MetalPrice> {

}
