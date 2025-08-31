package com.example.E_Commerce.E_commerce.Controller.Customer;

import com.example.E_Commerce.E_commerce.Exceptions.Customer.InvalidPropertyMyUserId;
import com.example.E_Commerce.E_commerce.Exceptions.Customer.InvalidPropertyProductId;
import com.example.E_Commerce.E_commerce.Models.Admin.response.ListOfProductsWithPriceResponse;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.WishList;
import com.example.E_Commerce.E_commerce.Models.Customer.request.ProductIdRequest;
import com.example.E_Commerce.E_commerce.Models.Customer.request.ProductListRequest;
import com.example.E_Commerce.E_commerce.Service.Customer.WishListService;
import com.example.E_Commerce.E_commerce.Service.admin.JwtExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @Autowired
    private JwtExtractService jwtExtractService;

    @RequestMapping(value = "/wishList/addProduct", method = RequestMethod.POST)
    public ResponseEntity<?> addAProductToCart(@RequestBody ProductIdRequest productIdRequest, HttpServletRequest httpServletRequest) throws InvalidPropertyProductId, InvalidPropertyMyUserId {
        String username = jwtExtractService.getUserName(httpServletRequest);
        wishListService.addProductToWishList(username,productIdRequest);
        return ResponseEntity.ok("Added to Cart Successfully");
    }

    @RequestMapping(value = "/wishList/addMultipleProducts", method = RequestMethod.POST)
    public ResponseEntity<?> addMultipleProductsToCart(@RequestBody ProductListRequest productListRequest, HttpServletRequest httpServletRequest) throws InvalidPropertyProductId, InvalidPropertyMyUserId {
        String username = jwtExtractService.getUserName(httpServletRequest);
        wishListService.addProductsListToWishList(username,productListRequest);
        return ResponseEntity.ok("Added to Cart Successfully");
    }

    @RequestMapping(value = "/wishList/removeProduct", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeProductFromCart(@RequestBody ProductIdRequest productIdRequest, HttpServletRequest httpServletRequest) throws InvalidPropertyProductId, InvalidPropertyMyUserId {
        String username = jwtExtractService.getUserName(httpServletRequest);
        wishListService.removeProductFromWishList(username, productIdRequest);
        return ResponseEntity.ok("Removed From the Cart Successfully");
    }

    @RequestMapping(value = "/wishList/removeMultipleProducts", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeMultipleProductsFromCart(@RequestBody ProductListRequest productListRequest, HttpServletRequest httpServletRequest) throws InvalidPropertyProductId, InvalidPropertyMyUserId {
        String username = jwtExtractService.getUserName(httpServletRequest);
        wishListService.removeProductsListFromWishList(username,productListRequest);
        return ResponseEntity.ok("Removed from the Cart Successfully");
    }

    @RequestMapping(value = "/wishList/readCart", method = RequestMethod.GET)
    public ResponseEntity<?> readCart( HttpServletRequest httpServletRequest) throws InvalidPropertyMyUserId {
        String username = jwtExtractService.getUserName(httpServletRequest);
        List<WishList> wishLists = wishListService.readWishList(username);
//        ListOfCartProductsRequest listOfCartProductsRequest = new ListOfCartProductsRequest(List<CartProductsRequest> )
        ListOfProductsWithPriceResponse listOfProductsWithPriceResponse =  wishListService.listOfProductsWithPrices(wishLists);
        return ResponseEntity.ok().body(listOfProductsWithPriceResponse);
    }
}
