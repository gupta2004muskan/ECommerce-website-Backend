package com.example.E_Commerce.E_commerce.Repository.Admin;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.Product;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Category;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Section;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import reactor.util.annotation.Nullable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    Optional<List<Product>> findAllByCategoryAndSection(Category category, Section section);
}