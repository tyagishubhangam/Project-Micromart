package com.micromart.ProductMicroservice.Controllers;

import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.services.ProductService.ProductService;
import com.micromart.ProductMicroservice.dto.ProductAddRequest;
import com.micromart.ProductMicroservice.dto.ProductCard;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;
import com.micromart.ProductMicroservice.services.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@EnableWebMvc
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/micromart/product")
public class ProductController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestPart ProductAddRequest product, @RequestPart("image") MultipartFile imageFile) throws Exception {
       try{
//           String imageUrl = productService.uploadProductImage(imageFile);
           String imageUrl = cloudinaryService.upload(imageFile);

           product.setImage(imageUrl);
           productService.addProduct(product);
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }

        return ResponseEntity.ok("Product added Successfully");
    }
    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductWithReviewDto> getProductById(@PathVariable("productId") long id) {
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

    @GetMapping("/getCards")
    public ResponseEntity<List<ProductCard>> getProductsCards(){
           return new ResponseEntity<>(productService.getAllProductCards(), HttpStatus.OK);
    }
}
