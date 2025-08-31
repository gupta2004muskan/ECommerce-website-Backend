package com.example.E_Commerce.E_commerce.Models.Admin.entities.models;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.SieveSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class NonCertDiamondAndSieveSize implements Serializable {
    private NonCertDiamondName nonCertDiamondName;
    private SieveSize sieveSize;
}