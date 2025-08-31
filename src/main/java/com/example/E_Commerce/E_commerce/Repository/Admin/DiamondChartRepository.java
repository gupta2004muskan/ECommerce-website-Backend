package com.example.E_Commerce.E_commerce.Repository.Admin;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.DiamondChart;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.SieveSize;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.embedables.Diamond;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.NonCertDiamondName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DiamondChartRepository extends JpaRepository<DiamondChart, Diamond>, JpaSpecificationExecutor<DiamondChart> {
    Optional<DiamondChart> findByDiamondSieveSizeAndDiamondNonCertDiamondName(SieveSize sieveSize, NonCertDiamondName diamondName);
}
