package com.example.E_Commerce.E_commerce.Service.Customer;

import com.example.E_Commerce.E_commerce.Exceptions.Customer.InvalidPropertyMyUserId;
import com.example.E_Commerce.E_commerce.Exceptions.Customer.InvalidPropertyProductId;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.Product;
import com.example.E_Commerce.E_commerce.Models.Admin.response.ListOfProductsWithPriceResponse;
import com.example.E_Commerce.E_commerce.Models.Admin.response.ProductWithPricesResponse;
import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import com.example.E_Commerce.E_commerce.Models.Customer.entites.WishList;
import com.example.E_Commerce.E_commerce.Models.Customer.request.ProductIdRequest;
import com.example.E_Commerce.E_commerce.Models.Customer.request.ProductListRequest;
import com.example.E_Commerce.E_commerce.Repository.Admin.ProductRepository;
import com.example.E_Commerce.E_commerce.Repository.Auth.MyUserRepository;
import com.example.E_Commerce.E_commerce.Repository.Customer.WishListRepository;
import com.example.E_Commerce.E_commerce.Service.admin.InventoryManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishListService {
    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private WishListService wishListService;

    @Autowired
    private InventoryManagementService inventoryManagementService;

    private void saveProductToWishList(MyUser myUser, Long productIdRequest) throws InvalidPropertyProductId {
        Optional<Product> product = productRepository.findById(productIdRequest);
        if(product.isPresent()){
            wishListRepository.save(new WishList(myUser,product.get()));
        } else {
            throw new InvalidPropertyProductId("Cannot find Product");
        }
    }
    public void addProductToWishList(String username, ProductIdRequest productIdRequest) throws InvalidPropertyMyUserId, InvalidPropertyProductId {
        Optional<MyUser> myUser = myUserRepository.findByUsername(username);
        if(myUser.isPresent()){
            wishListService.saveProductToWishList(myUser.get(),productIdRequest.getProductId());
        }else{
            throw new InvalidPropertyMyUserId("Cannot find User");
        }
    }

    public void addProductsListToWishList(String username, ProductListRequest productListRequest) throws InvalidPropertyMyUserId, InvalidPropertyProductId  {
        Optional<MyUser> myUser = myUserRepository.findByUsername(username);
        if(myUser.isPresent()){
            for (Long productIdRequest : productListRequest.getProductIdList()) {
                wishListService.saveProductToWishList(myUser.get(), productIdRequest);
            }
        }else{
            throw new InvalidPropertyMyUserId("Cannot find User");
        }
    }

    private void deleteProductFromShoppingCart(MyUser myUser, Long productIdRequest) throws InvalidPropertyProductId {
        Optional<Product> product = productRepository.findById(productIdRequest);
        if(product.isPresent()){
            wishListRepository.delete(new WishList(myUser,product.get()));
        } else {
            throw new InvalidPropertyProductId("Cannot find Product");
        }
    }
    public void removeProductFromWishList(String username, ProductIdRequest productIdRequest) throws InvalidPropertyMyUserId, InvalidPropertyProductId {
        Optional<MyUser> myUser = myUserRepository.findByUsername(username);
        if(myUser.isPresent()){
            wishListService.deleteProductFromShoppingCart(myUser.get(),productIdRequest.getProductId());
        }else{
            throw new InvalidPropertyMyUserId("Cannot find User");
        }
    }

    public void removeProductsListFromWishList(String username, ProductListRequest productListRequest) throws InvalidPropertyMyUserId, InvalidPropertyProductId  {
        Optional<MyUser> myUser = myUserRepository.findByUsername(username);
        if(myUser.isPresent()){
            for (Long productIdRequest : productListRequest.getProductIdList()) {
                wishListService.deleteProductFromShoppingCart(myUser.get(), productIdRequest);
            }
        }else{
            throw new InvalidPropertyMyUserId("Cannot find User");
        }
    }

    public List<WishList> readWishList(String username) throws InvalidPropertyMyUserId {
        Optional<MyUser> myUser = myUserRepository.findByUsername(username);
        if(myUser.isPresent()){
            Optional<List<WishList>> shoppingCartList = wishListRepository.findAllByUsername(myUser.get());
            if(shoppingCartList.isPresent()){
                return shoppingCartList.get();
            }else {
                throw new InvalidPropertyMyUserId("Shopping Cart Not There");
            }
        }else{
            throw new InvalidPropertyMyUserId("Cannot find User");
        }
    }

    public ListOfProductsWithPriceResponse listOfProductsWithPrices(List<WishList> wishLists) {
        List<ProductWithPricesResponse> productWithPricesResponseList = new ArrayList<>();
        wishLists.forEach((item)->{
            ProductWithPricesResponse productWithPricesResponse= inventoryManagementService.getProductPrices(item.getProduct());
            productWithPricesResponseList.add(productWithPricesResponse);
        });
        return new ListOfProductsWithPriceResponse(productWithPricesResponseList);
    }
}