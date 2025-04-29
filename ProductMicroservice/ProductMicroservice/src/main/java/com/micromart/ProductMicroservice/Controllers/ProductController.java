package com.micromart.ProductMicroservice.Controllers;

import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.ProductService.ProductService;
import com.micromart.ProductMicroservice.dto.ProductAddRequest;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@EnableWebMvc
@RestController
@CrossOrigin
@RequestMapping("/api/micromart/product")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductAddRequest product) {
        productService.addProduct(product);
        return ResponseEntity.ok("Product added Successfully");
    }
    @GetMapping("/get")
    public ResponseEntity<ProductWithReviewDto> getProductById(@RequestParam long id) {
        ProductWithReviewDto product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductWithReviewDto>> getAllProducts() {
        List<ProductWithReviewDto> products = productService.getAllProducts();
        if (products == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }
    @PostMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok("Product deleted Successfully");
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        if (productService.updateProduct(product)) {
            return ResponseEntity.ok("Product Updated Successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
