package com.micromart.ProductMicroservice.mappers;

import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.dto.ProductCard;
import org.springframework.stereotype.Component;

@Component
public class ProductCardMapper {

    public ProductCard mapProductCard(Product product) {
        ProductCard productCard = new ProductCard();
        productCard.setId(product.getId());
        productCard.setName(product.getProductName());
        productCard.setPrice(product.getPrice());
        productCard.setImage(product.getImage());

        return productCard;
    }
}
