package com.micromart.CartMicroservice.dtos;

import com.micromart.CartMicroservice.external.Products.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductInCart {
    private Product product;
    private int quantity;


    public ProductInCart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;


    }

    public ProductInCart() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    @Override
    public String toString() {
        return "ProductDto{" +
                "product='" + product + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
