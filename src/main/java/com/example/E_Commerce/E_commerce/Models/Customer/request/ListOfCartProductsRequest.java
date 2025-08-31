package com.example.E_Commerce.E_commerce.Models.Customer.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfCartProductsRequest implements Serializable {
    private List<CartProductsRequest> cartProductsRequestList;
}
