package com.micromart.CartMicroservice.mapper;

import com.micromart.CartMicroservice.Cart.CartItem;
import com.micromart.CartMicroservice.Clients.ProductMicroserviceClient;
import com.micromart.CartMicroservice.dtos.ProductInCart;
import com.micromart.CartMicroservice.repositories.TestRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class TestCartMapper {
    public final TestRepo testRepo;
    public final ProductMicroserviceClient productClient;

    public List<ProductInCart> getAllProductsInCart(long userId) {
        List<CartItem> userCartItem = testRepo.findByUserId(userId);
        List<ProductInCart> myProductsInCart = new ArrayList<>();
        try {
            for (CartItem cartItem : userCartItem) {
                ProductInCart productInCart = new ProductInCart();
                productInCart.setProduct(productClient.getProduct(cartItem.getProductId()));
                productInCart.setQuantity(cartItem.getQuantity());

                myProductsInCart.add(productInCart);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return myProductsInCart;


        //Getting TestCart object from the database
//        List<TestCart> myList = testRepo.findAll();
//        List<ProductInCart> myProductsInCart = new ArrayList<>();
//        try{
//        for (TestCart testCart : myList) {
//            if (testCart.getUserId() == userId) {
//                ProductInCart productInCart = new ProductInCart();
//                productInCart.setProduct(productClient.getProduct(testCart.getProductId()));
//                productInCart.setQuantity(testCart.getQuantity());
//
//                myProductsInCart.add(productInCart);
//            }
//        }}
//        catch(FeignException.NotFound e){
//            System.out.println(e.getMessage());
//        }
//        return myProductsInCart;


    }


}
