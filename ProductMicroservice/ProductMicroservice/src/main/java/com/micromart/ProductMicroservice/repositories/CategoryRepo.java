package com.micromart.ProductMicroservice.repositories;

import com.micromart.ProductMicroservice.category.Category;
import com.micromart.ProductMicroservice.mappers.ProductWithReviewMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);
}