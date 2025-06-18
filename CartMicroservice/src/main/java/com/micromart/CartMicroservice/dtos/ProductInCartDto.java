package com.micromart.CartMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductInCartDto {
    List<ProductInCart> productsInCart;
    Double totalPrice;



}
