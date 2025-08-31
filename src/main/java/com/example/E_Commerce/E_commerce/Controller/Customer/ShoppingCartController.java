package com.example.E_Commerce.E_commerce.Controller.Customer;

import com.example.E_Commerce.E_commerce.Exceptions.Customer.InvalidPropertyMyUserId;
import com.example.E_Commerce.E_commerce.Exceptions.Customer.InvalidPropertyProductId;
import com.example.E_Commerce.E_commerce.Models.Admin.response.ListOfProductsWithPriceResponse;
import com.example.E_Commerce.E_commerce.Models.Auth.response.RaisedExceptionResponse;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.ShoppingCart;
import com.example.E_Commerce.E_commerce.Models.Customer.request.CartProductsRequest;
import com.example.E_Commerce.E_commerce.Models.Customer.request.ListOfCartProductsRequest;
import com.example.E_Commerce.E_commerce.Models.Customer.response.ListOfProductsResponse;
import com.example.E_Commerce.E_commerce.Service.Customer.ShoppingCartService;
import com.example.E_Commerce.E_commerce.Service.admin.JwtExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private JwtExtractService jwtExtractService;

    @RequestMapping(value = "/shoppingCart/addProduct", method = RequestMethod.POST)
    public ResponseEntity<?> addAProductToCart(@RequestBody CartProductsRequest addToShoppingCartRequest, HttpServletRequest httpServletRequest) throws InvalidPropertyProductId, InvalidPropertyMyUserId {
        try {
            String username = jwtExtractService.getUserName(httpServletRequest);
            shoppingCartService.addProductToShoppingCart(username, addToShoppingCartRequest);
            return ResponseEntity.ok("Added to Cart Successfully");
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(noSuchElementException.getMessage()));
        } catch (InvalidPropertyProductId invalidPropertyProductId){
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(invalidPropertyProductId.getMessage()));
        }
    }

    @RequestMapping(value = "/shoppingCart/addMultipleProducts", method = RequestMethod.POST)
    public ResponseEntity<?> addMultipleProductsToCart(@RequestBody ListOfCartProductsRequest addListToShoppingCartRequest, HttpServletRequest httpServletRequest) throws InvalidPropertyProductId, InvalidPropertyMyUserId {
        try {
            String username = jwtExtractService.getUserName(httpServletRequest);
            shoppingCartService.addProductsListToShoppingCart(username, addListToShoppingCartRequest);
            return ResponseEntity.ok("Added to Cart Successfully");
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(noSuchElementException.getMessage()));
        } catch (InvalidPropertyProductId invalidPropertyProductId){
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(invalidPropertyProductId.getMessage()));
        }
    }

    @RequestMapping(value = "/shoppingCart/removeProduct", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeProductFromCart(@RequestBody CartProductsRequest deleteFromShoppingCartRequest, HttpServletRequest httpServletRequest) throws InvalidPropertyProductId, InvalidPropertyMyUserId {
        try {
            String username = jwtExtractService.getUserName(httpServletRequest);
            shoppingCartService.removeProductFromShoppingCart(username, deleteFromShoppingCartRequest);
            return ResponseEntity.ok("Removed From the Cart Successfully");
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(noSuchElementException.getMessage()));
        } catch (InvalidPropertyProductId invalidPropertyProductId){
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(invalidPropertyProductId.getMessage()));
        }
    }

    @RequestMapping(value = "/shoppingCart/removeMultipleProducts", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeMultipleProductsFromCart(@RequestBody ListOfCartProductsRequest deleteListToShoppingCartRequest, HttpServletRequest httpServletRequest) throws InvalidPropertyProductId, InvalidPropertyMyUserId {
        try {
            String username = jwtExtractService.getUserName(httpServletRequest);
            shoppingCartService.removeProductsListFromShoppingCart(username, deleteListToShoppingCartRequest);
            return ResponseEntity.ok("Removed from the Cart Successfully");
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(noSuchElementException.getMessage()));
        } catch (InvalidPropertyProductId invalidPropertyProductId){
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(invalidPropertyProductId.getMessage()));
        }
    }

    @RequestMapping(value = "/shoppingCart/readCart", method = RequestMethod.GET)
    public ResponseEntity<?> readCart(HttpServletRequest httpServletRequest) throws InvalidPropertyMyUserId {
        try {
            String username = jwtExtractService.getUserName(httpServletRequest);
            List<ShoppingCart> shoppingCartList = shoppingCartService.readShoppingList(username);
            ListOfProductsWithPriceResponse listOfProductsWithPriceResponse = shoppingCartService.listOfProductsWithPrices(shoppingCartList);
//        ListOfProductsResponse listOfProductsResponse =new ListOfProductsResponse(username);
//        shoppingCartService.setShoppingListOfProduct(shoppingCartList,listOfProductsResponse);
////        listOfProductsResponse.setShoppingListOfProduct(shoppingCartList);
//        System.out.println(listOfProductsResponse.toString());
            return ResponseEntity.ok().body(listOfProductsWithPriceResponse);
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(noSuchElementException.getMessage()));
        }
    }

    @RequestMapping(value = "/shoppingCart/readCartId", method = RequestMethod.GET)
    public ResponseEntity<?> readCartId(HttpServletRequest httpServletRequest) throws InvalidPropertyMyUserId {
        try {
            String username = jwtExtractService.getUserName(httpServletRequest);
            List<ShoppingCart> shoppingCartList = shoppingCartService.readShoppingList(username);
            ListOfProductsResponse listOfProductsResponse = new ListOfProductsResponse(username);
            listOfProductsResponse.setShoppingListOfProductIds(shoppingCartList);
            System.out.println(listOfProductsResponse.toString());
            return ResponseEntity.ok().body(listOfProductsResponse);
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.badRequest().body(new RaisedExceptionResponse(noSuchElementException.getMessage()));
        }
    }
}
