package com.micromart.ProductMicroservice.repositories;

import com.micromart.ProductMicroservice.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
//    public Product findById(long id);
//    public boolean deleteProduct(Long id);
//    public boolean updateProduct(Product product);



}
