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
import com.micromart.ProductMicroservice.dto.ProductAddRequest;
import com.micromart.ProductMicroservice.dto.ProductCard;
import com.micromart.ProductMicroservice.dto.ProductWithReviewDto;
import com.micromart.ProductMicroservice.mappers.ProductCardMapper;
import com.micromart.ProductMicroservice.mappers.ProductWithReviewMapper;
import com.micromart.ProductMicroservice.repositories.ProductRepo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {
    private final String IMGBB_API_KEY = "db5884b66ef39291e15ade8a0896d890";
    public final ProductRepo productRepo;
    public final ProductWithReviewMapper productWithReviewMapper;
    public final ReviewClient reviewClient;
    private final ProductCardMapper productCardMapper;

    public ProductServiceImpl(ProductRepo productRepo, ProductWithReviewMapper productWithReviewMapper, ReviewClient reviewClient, ProductCardMapper productCardMapper) {
        this.productRepo = productRepo;
        this.productWithReviewMapper = productWithReviewMapper;
        this.reviewClient = reviewClient;
        this.productCardMapper = productCardMapper;
    }

    @Override
    public String uploadProductImage(MultipartFile file)throws Exception {

        String url = "https://api.imgbb.com/1/upload?key=" + IMGBB_API_KEY;
        try{
            byte[] bytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("image", base64Image);
            map.add("name", file.getOriginalFilename());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                System.out.println(responseBody);
                // extract URL from JSON (simple way)
                String imageUrl = responseBody.split("\"url\":\"")[1].split("\"")[0].replace("\\/", "/");
                return imageUrl;
            } else {
                throw new RuntimeException("Image upload failed");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

       return null;
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
        Product product = Product.builder().productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription())
                .category(productRequest.getCategory()).price(productRequest.getPrice()).quantity(productRequest.getQuantity())
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
