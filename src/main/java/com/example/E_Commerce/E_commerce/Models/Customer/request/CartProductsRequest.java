package com.example.E_Commerce.E_commerce.Models.Customer.request;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.ClarityAndColor;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Metal;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.Purity;
import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class CartProductsRequest implements Serializable {
    @NonNull
    private Long productId;

    @NonNull
    private ClarityAndColor clarityAndColor;

    @NonNull
    private Purity purity;

    @NonNull
    private Metal metal;

    private char ringSize;
}
