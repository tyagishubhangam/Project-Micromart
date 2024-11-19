package com.micromart.CartMicroservice.Controllers;

import com.micromart.CartMicroservice.Cart.TestDisplayCart;
import com.micromart.CartMicroservice.CartService.TestCartService;
import com.micromart.CartMicroservice.Clients.ProductMicroserviceClient;
import com.micromart.CartMicroservice.Clients.UserMicroserviceClient;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("/api/micromart/cart/user/{userId}")
public class TestController {
    public final TestCartService testService;
    private final UserMicroserviceClient userMicroserviceClient;
    private final ProductMicroserviceClient productMicroserviceClient;

    public TestController(TestCartService testService, UserMicroserviceClient userMicroserviceClient, ProductMicroserviceClient productMicroserviceClient) {
        this.testService = testService;
        this.userMicroserviceClient = userMicroserviceClient;
        this.productMicroserviceClient = productMicroserviceClient;
    }

    @GetMapping("/getCart")
    public ResponseEntity<TestDisplayCart> getCart(HttpServletRequest request, @PathVariable("userId") long userId, @RequestHeader("Authorization") String header){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            System.out.println(authorizationHeader);
            token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            System.out.println(token);
        }
      try{
          if(userMicroserviceClient.getUser(userId, "Bearer "+ token) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }

        return new ResponseEntity<>(testService.getTestCart(userId), HttpStatus.OK);
      }
      catch(FeignException.NotFound e){
          System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }catch (FeignException.Unauthorized e){
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
    }
    @PostMapping("/addToCart/product/{productId}/quantity/{quantity}")
    public ResponseEntity<String> addToCart(HttpServletRequest request, @PathVariable("userId") long userId, @PathVariable("productId") long productId, @PathVariable("quantity") int quantity) {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                System.out.println(authorizationHeader);
                token = authorizationHeader.substring(7); // Remove "Bearer " prefix
                System.out.println(token);
            }
            if(userMicroserviceClient.getUser(userId, "Bearer "+token) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (productMicroserviceClient.getProduct(productId) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            testService.addToCart(productId, userId, quantity);
            return new ResponseEntity<>("Product Added successfully", HttpStatus.OK);
        } catch (FeignException.FeignClientException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(HttpServletRequest request, @PathVariable("userId") long userId, @PathVariable("productId") long productId){
        try{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            }
            if(userMicroserviceClient.getUser(userId, "Bearer "+token) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(productMicroserviceClient.getProduct(productId) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        if(testService.removeFromCart(productId, userId)){
            return new ResponseEntity<>("Product Deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }
    catch(FeignException.FeignClientException e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/product/{productId}/updateQuantity/{quantity}")
    public ResponseEntity<String> updateCart(HttpServletRequest request, @PathVariable("userId") long userId, @PathVariable("productId") long productId, @PathVariable("quantity") int quantity){
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            }
            if(userMicroserviceClient.getUser(userId, "Bearer "+token) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(productMicroserviceClient.getProduct(productId) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if(testService.updateQuantity(productId, userId, quantity)){
            return new ResponseEntity<>("Product Updated scuucessfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);

        }
        catch(FeignException.FeignClientException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
