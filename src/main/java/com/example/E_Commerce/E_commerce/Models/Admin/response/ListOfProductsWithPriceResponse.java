package com.example.E_Commerce.E_commerce.Models.Admin.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfProductsWithPriceResponse implements Serializable {
    List<ProductWithPricesResponse> productWithPricesResponseList;
}
