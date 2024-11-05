package com.micromart.ProductMicroservice.mappers;

import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.clients.ReviewClient;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;
import com.micromart.ProductMicroservice.external.Review;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductWithReviewMapper {
    public final ReviewClient reviewClient;
    public ProductWithReviewMapper(ReviewClient reviewClient) {
        this.reviewClient = reviewClient;
    }

    public ProductWithReviewDto mapToDto(Product product) {
        ProductWithReviewDto productWithReviewDto = new ProductWithReviewDto();
        productWithReviewDto.setId(product.getId());
        productWithReviewDto.setProductName(product.getProductName());
        productWithReviewDto.setProductDescription(product.getProductDescription());
        productWithReviewDto.setPrice(product.getPrice());
        productWithReviewDto.setQuantity(product.getQuantity());
        productWithReviewDto.setImage(product.getImage());
        try{
        productWithReviewDto.setReviews(reviewClient.getReviews(product.getId()));}
        catch(FeignException.FeignClientException e){
            productWithReviewDto.setReviews(new ArrayList<>());
        }
        return productWithReviewDto;
    }

    public List<ProductWithReviewDto> mapAllToDto(List<Product> products ) {
        List<ProductWithReviewDto> productWithReviewDtos = new ArrayList<>();
        for (Product product : products) {
            productWithReviewDtos.add(mapToDto(product));

        }
        return productWithReviewDtos;
    }
}
