package com.micromart.ProductMicroservice.services.categoryservice;

import com.micromart.ProductMicroservice.category.Category;
import com.micromart.ProductMicroservice.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public Category deleteCategory(Category category);
    public List<CategoryResponse> getAllCategories();
    public Category getCategoryByName(String categoryName);


}
