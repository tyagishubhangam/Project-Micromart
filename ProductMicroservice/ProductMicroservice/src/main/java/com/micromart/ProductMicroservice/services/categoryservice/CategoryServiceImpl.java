package com.micromart.ProductMicroservice.services.categoryservice;

import com.micromart.ProductMicroservice.category.Category;
import com.micromart.ProductMicroservice.dto.CategoryResponse;
import com.micromart.ProductMicroservice.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public Category addCategory(Category category) {

        return categoryRepo.save(category);

    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public Category deleteCategory(Category category) {
        return null;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> allCategories = categoryRepo.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category : allCategories) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setCategoryName(category.getCategoryName());
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;

    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName).orElse(null);
    }


}
