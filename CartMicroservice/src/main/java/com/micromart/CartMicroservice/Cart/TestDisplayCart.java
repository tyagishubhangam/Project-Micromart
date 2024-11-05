package com.micromart.CartMicroservice.Cart;
import java.util.List;

import com.micromart.CartMicroservice.dtos.ProductInCart;
import org.springframework.stereotype.Component;

@Component
public class TestDisplayCart {
    private List<ProductInCart> productsInCart;

    private Double totalAmount;

//    public TestDisplayCart( TestCartMapper testCartMapper) {
////        this.productsInCart = productsInCart;
//        this.testCartMapper = testCartMapper;
//    }


//    public TestDisplayCart() {
//    }
//
//    public TestDisplayCart(List<ProductInCart> productsInCart, Double totalAmount) {
//        this.productsInCart = productsInCart;
//        this.totalAmount = totalAmount;
//    }


    public List<ProductInCart> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(List<ProductInCart> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
