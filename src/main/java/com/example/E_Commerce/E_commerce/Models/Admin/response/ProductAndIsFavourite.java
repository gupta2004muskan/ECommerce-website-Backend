package com.example.E_Commerce.E_commerce.Models.Admin.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductAndIsFavourite extends ProductWithPricesResponse implements Serializable {

    private boolean isMarkedAsFavourite;

    public ProductAndIsFavourite(ProductWithPricesResponse productWithPricesResponse, boolean isMarkedAsFavourite) {
//        super(product.getId(), product.getProductName(), product.getSection(), product.getCategory(), product.getDiamond(), product.getOldPrice(), product.getPrice(), product.getDiamondSetting(), product.isBestSeller(), product.getPurity(), product.getMetal(), product.getLeftImages(), product.getBottomImages(), product.getNonCertDiamondName(), product.getClarityAndColor(), product.getDiamondSpecs(), product.getAvgPrice(), product.getTotalPrice(), product.getTotalWeight(), product.getTotalDiamonds());
        super(productWithPricesResponse);
        this.isMarkedAsFavourite = isMarkedAsFavourite;
    }
}
