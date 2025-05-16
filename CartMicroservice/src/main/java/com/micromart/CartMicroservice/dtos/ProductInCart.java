package com.micromart.CartMicroservice.dtos;

import com.micromart.CartMicroservice.external.Products.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProductInCart {
    private Product product;
    private int quantity;



}
