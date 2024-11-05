package com.micromart.CartMicroservice.mapper;

import com.micromart.CartMicroservice.Cart.TestCart;
import com.micromart.CartMicroservice.Clients.ProductMicroserviceClient;
import com.micromart.CartMicroservice.dtos.ProductInCart;
import com.micromart.CartMicroservice.repositories.TestRepo;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class TestCartMapper {
    public final TestRepo testRepo;
    public final ProductMicroserviceClient productClient;
    public TestCartMapper(TestRepo testRepo, ProductMicroserviceClient productClient) {
        this.testRepo = testRepo;
        this.productClient = productClient;
    }
//    public TestDisplayCart mapCartToTestDisplayCart(long userId) {
//
//
//        return ;
//    }

    public List<ProductInCart> getAllProductsInCart(long userId) {

        //Getting TestCart object from the database
        List<TestCart> myList = testRepo.findAll();
        List<ProductInCart> myProductsInCart = new ArrayList<>();
        try{
        for (TestCart testCart : myList) {
            if (testCart.getUserId() == userId) {
                ProductInCart productInCart = new ProductInCart();
                productInCart.setProduct(productClient.getProduct(testCart.getProductId()));
                productInCart.setQuantity(testCart.getQuantity());

                myProductsInCart.add(productInCart);
            }
        }}
        catch(FeignException.NotFound e){
            System.out.println(e.getMessage());
        }
        return myProductsInCart;
    }


}
