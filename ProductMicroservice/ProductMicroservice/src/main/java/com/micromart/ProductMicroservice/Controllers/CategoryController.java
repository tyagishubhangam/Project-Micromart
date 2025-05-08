package com.micromart.ProductMicroservice.Controllers;

import com.micromart.ProductMicroservice.category.Category;
import com.micromart.ProductMicroservice.dto.CategoryRequest;
import com.micromart.ProductMicroservice.dto.CategoryResponse;
import com.micromart.ProductMicroservice.dto.Response;
import com.micromart.ProductMicroservice.services.categoryservice.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/micromart")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/admin/addCategory")
    public ResponseEntity<Response> addCategory(@RequestBody CategoryRequest category) {
        try{
            Category category1 = new Category();
            category1.setCategoryName(category.getCategory_Name());
            categoryService.addCategory(category1);
        }catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(new Response("ERROR","Duplicate values not allowed"),HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(new Response("SUCCESS","CATEGORY ADDED SUCCESSFULLY"));
    }

    @GetMapping("/category/getAll")
    public ResponseEntity<List<CategoryResponse>> getAllCategory() {
        return new ResponseEntity<>(categoryService.getAllCategories() , HttpStatus.OK);
    }

}
