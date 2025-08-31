package com.example.E_Commerce.E_commerce.Models.Admin.entities.embedables;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.ClarityAndColor;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.NonCertDiamondName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DiamondIdentity implements Serializable {

    //    @MapsId("chart")
//    @ManyToOne
//    private DiamondChart diamondChart;
    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private NonCertDiamondName nonCertDiamondName;

    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private ClarityAndColor clarityAndColor;

    @NonNull
    @Column(precision = 10, scale = 2)
    private double sieveSizeGreaterThan;

    @NonNull
    @Column(precision = 10, scale = 2)
    private double sieveSizeLessThan;
}
