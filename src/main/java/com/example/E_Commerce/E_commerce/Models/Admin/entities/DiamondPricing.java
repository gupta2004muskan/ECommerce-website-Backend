package com.example.E_Commerce.E_commerce.Models.Admin.entities;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.embedables.DiamondIdentity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "diamond_pricing")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondPricing implements Serializable {

    @NonNull
    @EmbeddedId
    private DiamondIdentity diamondIdentity;

    @NonNull
    @Column(precision = 10, scale = 2)
    private double price;
}
