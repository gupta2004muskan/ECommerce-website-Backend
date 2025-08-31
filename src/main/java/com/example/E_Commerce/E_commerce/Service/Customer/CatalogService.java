package com.example.E_Commerce.E_commerce.Service.Customer;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.Product;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Category;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.DiamondSetting;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Metal;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Section;
import com.example.E_Commerce.E_commerce.Models.Admin.response.ProductWithPricesResponse;
import com.example.E_Commerce.E_commerce.Models.Customer.request.CatalogFilterRequest;
import com.example.E_Commerce.E_commerce.Repository.Admin.ProductRepository;
import com.example.E_Commerce.E_commerce.Service.admin.InventoryManagementService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CatalogService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryManagementService inventoryManagementService;

    public List<Product> getProductByCategoryAndSection(String category, String section) throws NoSuchElementException {
        Optional<List<Product>> productList = productRepository.findAllByCategoryAndSection(Category.valueOf(category), Section.valueOf(section));
        if (productList.isEmpty() || productList.get().isEmpty())
            throw new NoSuchElementException("No such category and section");
        return productList.get();
    }

    public List<ProductWithPricesResponse> getProductByCategorySectionAndFilter(String category, String section, CatalogFilterRequest catalogFilterRequest) {

        List<Product> productList = productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                // Category and Section predicates
                predicates.add(criteriaBuilder.equal(root.get("category"), Category.valueOf(category)));
                predicates.add(criteriaBuilder.equal(root.get("section"), Section.valueOf(section)));

                // Metal filter (collection membership)
                if (catalogFilterRequest.getMetal() != null && !catalogFilterRequest.getMetal().isEmpty()) {
                    List<Predicate> metalPredicates = new ArrayList<>();
                    for (Metal metal : catalogFilterRequest.getMetal()) {
                        metalPredicates.add(criteriaBuilder.isMember(metal, root.get("metal")));
                    }
                    predicates.add(criteriaBuilder.or(metalPredicates.toArray(new Predicate[0])));
                }

                // Diamond Setting filter (enum equality)
                if (catalogFilterRequest.getDiamondSetting() != null && !catalogFilterRequest.getDiamondSetting().isEmpty()) {
                    List<Predicate> diamondSettingPredicates = new ArrayList<>();
                    for (DiamondSetting diamondSetting : catalogFilterRequest.getDiamondSetting()) {
                        diamondSettingPredicates.add(criteriaBuilder.equal(root.get("diamondSetting"), diamondSetting));
                    }
                    predicates.add(criteriaBuilder.or(diamondSettingPredicates.toArray(new Predicate[0])));
                }

                // Price range filters
                if (catalogFilterRequest.getLowerLimit() != null && catalogFilterRequest.getUpperLimit() != null) {
                    predicates.add(criteriaBuilder.between(root.get("price"), catalogFilterRequest.getLowerLimit(), catalogFilterRequest.getUpperLimit()));
                } else if (catalogFilterRequest.getLowerLimit() == null && catalogFilterRequest.getUpperLimit() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), catalogFilterRequest.getUpperLimit()));
                } else if (catalogFilterRequest.getLowerLimit() != null && catalogFilterRequest.getUpperLimit() == null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), catalogFilterRequest.getLowerLimit()));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });

        List<ProductWithPricesResponse> productWithPricesResponses = new ArrayList<>();
        for (Product product : productList) {
            productWithPricesResponses.add(new ProductWithPricesResponse(inventoryManagementService.getProductPrices(product)));
        }
        return productWithPricesResponses;
    }
}