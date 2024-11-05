package com.micromart.CartMicroservice.Controllers;

import com.micromart.CartMicroservice.Cart.TestDisplayCart;
import com.micromart.CartMicroservice.CartService.TestCartService;
import com.micromart.CartMicroservice.Clients.ProductMicroserviceClient;
import com.micromart.CartMicroservice.Clients.UserMicroserviceClient;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/micromart/user/{userId}")
public class TestController {
    public final TestCartService testService;
    private final UserMicroserviceClient userMicroserviceClient;
    private final ProductMicroserviceClient productMicroserviceClient;

    public TestController(TestCartService testService, UserMicroserviceClient userMicroserviceClient, ProductMicroserviceClient productMicroserviceClient) {
        this.testService = testService;
        this.userMicroserviceClient = userMicroserviceClient;
        this.productMicroserviceClient = productMicroserviceClient;
    }

    @GetMapping("/cart")
    public ResponseEntity<TestDisplayCart> getCart(@PathVariable("userId") long userId){
      try{
          if(userMicroserviceClient.getUser(userId) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }

        return new ResponseEntity<>(testService.getTestCart(userId), HttpStatus.OK);
      }
      catch(FeignException.FeignClientException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    @PostMapping("/addToCart/product/{productId}/quantity/{quantity}")
    public ResponseEntity<String> addToCart(@PathVariable("userId") long userId, @PathVariable("productId") long productId, @PathVariable("quantity") int quantity) {
        try {
            if(userMicroserviceClient.getUser(userId) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (productMicroserviceClient.getProduct(productId) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            testService.addToCart(productId, userId, quantity);
            return new ResponseEntity<>("Product Added successfully", HttpStatus.OK);
        } catch (FeignException.FeignClientException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cart/delete/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable("userId") long userId, @PathVariable("productId") long productId){
        try{
            if(userMicroserviceClient.getUser(userId) == null){
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
    @PutMapping("/cart/product/{productId}/updateQuantity/{quantity}")
    public ResponseEntity<String> updateCart(@PathVariable("userId") long userId, @PathVariable("productId") long productId, @PathVariable("quantity") int quantity){
        try {
            if(userMicroserviceClient.getUser(userId) == null){
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
