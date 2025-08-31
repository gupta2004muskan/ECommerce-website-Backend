package com.example.E_Commerce.E_commerce.Repository.Admin;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.SieveSize;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SieveSizeRepository extends JpaRepository<SieveSize,String>, JpaSpecificationExecutor<SieveSize> {
}
