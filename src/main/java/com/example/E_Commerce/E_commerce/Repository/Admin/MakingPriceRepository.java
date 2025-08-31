package com.example.E_Commerce.E_commerce.Repository.Admin;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.MakingPrice;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MakingPriceRepository extends JpaRepository<MakingPrice, Category>, JpaSpecificationExecutor<MakingPrice> {
}
