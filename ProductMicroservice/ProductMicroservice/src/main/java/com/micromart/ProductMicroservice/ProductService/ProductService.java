package com.micromart.ProductMicroservice.ProductService;

import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.dto.ProductAddRequest;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;

import java.util.List;

public interface ProductService {
    public void addProduct(ProductAddRequest product);
    public List<ProductWithReviewDto> getAllProducts();
    public ProductWithReviewDto getProductById(Long id);
    public boolean deleteProduct(Long id);
    public boolean updateProduct(Product product);

}
