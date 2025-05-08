package com.micromart.ProductMicroservice.repositories;

import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.category.Category;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
//    public Product findById(long id);
//    public boolean deleteProduct(Long id);
//    public boolean updateProduct(Product product);

    public List<Product> findAllByCategory(Category category);



}
