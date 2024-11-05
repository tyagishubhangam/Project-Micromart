package com.micromart.ProductMicroservice.ProductService;//package com.micromart.ProductMicroservice.ProductService;
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
import com.micromart.ProductMicroservice.ProductService.ProductService;
import com.micromart.ProductMicroservice.clients.ReviewClient;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;
import com.micromart.ProductMicroservice.mappers.ProductWithReviewMapper;
import com.micromart.ProductMicroservice.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {

    public final ProductRepo productRepo;
    public final ProductWithReviewMapper productWithReviewMapper;
    public final ReviewClient reviewClient;
    public ProductServiceImpl(ProductRepo productRepo, ProductWithReviewMapper productWithReviewMapper, ReviewClient reviewClient) {
        this.productRepo = productRepo;
        this.productWithReviewMapper = productWithReviewMapper;
        this.reviewClient = reviewClient;

    }

    @Override
    public void addProduct(Product product) {
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
