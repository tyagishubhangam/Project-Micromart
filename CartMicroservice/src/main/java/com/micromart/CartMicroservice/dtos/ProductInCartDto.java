package com.micromart.CartMicroservice.dtos;

import java.util.List;

public class ProductInCartDto {
    List<ProductInCart> productsInCart;
    Double totalPrice;

    public ProductInCartDto(List<ProductInCart> productsInCart, Double totalPrice) {
        this.productsInCart = productsInCart;
        this.totalPrice = totalPrice;
    }

    public ProductInCartDto() {
    }

    public List<ProductInCart> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(List<ProductInCart> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
