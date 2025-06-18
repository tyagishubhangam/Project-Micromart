package com.micromart.ProductMicroservice.mappers;

import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.clients.ReviewClient;
import com.micromart.ProductMicroservice.dto.ProductCard;
import com.micromart.ProductMicroservice.external.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCardMapper {
    private final ReviewClient reviewClient;

    public ProductCard mapProductCard(Product product) {
        ProductCard productCard = new ProductCard();
        productCard.setId(product.getId());
        productCard.setName(product.getProductName());
        productCard.setPrice(product.getPrice());
        productCard.setRating(calculateRating(product.getId()));
        productCard.setImage(product.getImage());

        return productCard;
    }

    public double calculateRating(String productId) {
        double rating = 0.0;
        try{
            if (productId != null) {
                List<Review> reviews = reviewClient.getReviews(productId);
                for (Review review : reviews) {
                    rating += review.getRating();
                }

                rating = rating / reviews.size();
            }
        }catch (Exception e) {
            log.error(e.getMessage());
            return 0.0;
        }

        return rating;
    }
}
