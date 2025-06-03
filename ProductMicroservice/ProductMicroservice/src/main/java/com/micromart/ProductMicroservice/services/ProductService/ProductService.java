package com.micromart.ProductMicroservice.services.ProductService;

import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.dto.ProductAddRequest;
import com.micromart.ProductMicroservice.dto.ProductCard;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public void addProduct(ProductAddRequest product);
    public List<ProductWithReviewDto> getAllProducts();
    public ProductWithReviewDto getProductById(String id);
    public boolean deleteProduct(String id);
    public boolean updateProduct(Product product);
    public List<ProductCard> getProductCardsByCategory(String categoryName);
    public List<ProductCard> getAllProductCards();

}
