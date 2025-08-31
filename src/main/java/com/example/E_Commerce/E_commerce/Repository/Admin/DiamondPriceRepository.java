package com.example.E_Commerce.E_commerce.Repository.Admin;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.DiamondPricing;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.embedables.DiamondIdentity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DiamondPriceRepository  extends JpaRepository<DiamondPricing, DiamondIdentity>, JpaSpecificationExecutor<DiamondPricing> {
}
