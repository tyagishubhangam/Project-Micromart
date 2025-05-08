package com.micromart.ProductMicroservice.services.ProductService;//package com.micromart.ProductMicroservice.ProductService;
//
//import com.micromart.ProductMicroservice.Product.Product;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    public ArrayList<Product> products = new ArrayList<Product>();
//    @Override
//    public void addProduct(Product product) {
//        products.add(product);
//    }
//
//    @Override
//    public List<Product> getAllProducts() {
//        return products;
//    }
//
//    @Override
//    public Product getProductById(Long id) {
//        for (Product product : products) {
//            if (product.getId().equals(id)) {
//                return product;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean deleteProduct(Long id) {
//        for(Product product : products) {
//            if (product.getId().equals(id)) {
//                products.remove(product);
//                return true;
//            }
//
//        }return false;
//
//    }
//
//    @Override
//    public boolean updateProduct(Product product) {
//        for(Product product2 : products) {
//            if (product.getId().equals(product2.getId())) {
//                products.set(products.indexOf(product2), product);
//                return true;
//            }
//        }
//        return false;
//    }
//}


import com.micromart.ProductMicroservice.Product.Product;
import com.micromart.ProductMicroservice.category.Category;
import com.micromart.ProductMicroservice.clients.ReviewClient;
import com.micromart.ProductMicroservice.dto.ProductAddRequest;
import com.micromart.ProductMicroservice.dto.ProductCard;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;
import com.micromart.ProductMicroservice.mappers.ProductCardMapper;
import com.micromart.ProductMicroservice.mappers.ProductWithReviewMapper;
import com.micromart.ProductMicroservice.repositories.CategoryRepo;
import com.micromart.ProductMicroservice.repositories.ProductRepo;
import com.micromart.ProductMicroservice.services.categoryservice.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    public final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;



    public final ProductWithReviewMapper productWithReviewMapper;
    public final ReviewClient reviewClient;
    private final ProductCardMapper productCardMapper;
    private final CategoryService categoryService;



    @Override
    public List<ProductCard> getProductCardsByCategory(String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        List<Product> allProducts = productRepo.findAllByCategory(category);
        List<ProductCard> productCards = new ArrayList<>();
        for (Product product : allProducts) {
            ProductCard productCard = productCardMapper.mapProductCard(product);
            productCards.add(productCard);
        }
        return productCards;
    }


    @Override
    public List<ProductCard> getAllProductCards() {
        List<Product> products = productRepo.findAll();
        List<ProductCard> productCards = new ArrayList<>();
        for (Product product : products) {
            ProductCard productCard = productCardMapper.mapProductCard(product);
            productCards.add(productCard);
        }
        return productCards;
    }

    @Override
    public void addProduct(ProductAddRequest productRequest) {
        Category category = categoryRepo.findById(productRequest.getCategoryId()).orElse(null);
        if(category == null) {
            throw new RuntimeException("Category not found");
        }
        Product product = Product.builder().productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription())
                .category(category).price(productRequest.getPrice()).quantity(productRequest.getQuantity())
                .image(productRequest.getImage()).build();

        productRepo.save(product);
    }

    @Override
    public List<ProductWithReviewDto> getAllProducts() {
      return productWithReviewMapper.mapAllToDto(productRepo.findAll());
    }

    @Override
    public ProductWithReviewDto getProductById(Long id) {
        Optional<Product> prod = productRepo.findById(id);
        return prod.isPresent() ? productWithReviewMapper.mapToDto( prod.get()) : null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        try{
            productRepo.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {

            Optional<Product> prod = productRepo.findById(product.getId());
            if (prod.isPresent()) {
                productRepo.save(product);
                return true;
            }else {
                return false;

        }
    }



}
