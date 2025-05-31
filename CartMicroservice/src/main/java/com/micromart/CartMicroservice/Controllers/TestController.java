package com.micromart.CartMicroservice.Controllers;

import com.micromart.CartMicroservice.Cart.TestDisplayCart;
import com.micromart.CartMicroservice.CartService.TestCartService;
import com.micromart.CartMicroservice.Clients.ProductMicroserviceClient;
import com.micromart.CartMicroservice.Clients.UserMicroserviceClient;
import com.micromart.CartMicroservice.dtos.AddToCartRequest;
import com.micromart.CartMicroservice.dtos.ResponseDto;
import feign.FeignException;
import feign.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/api/micromart/cart")
@RequiredArgsConstructor
public class TestController {
    public final TestCartService testService;
    private final UserMicroserviceClient userMicroserviceClient;
    private final ProductMicroserviceClient productMicroserviceClient;

    //ToDo I have to change the response of every APi to ResponseDto and also @PatchMapping I have to use in some apis here

    @GetMapping("/user/getCart")
    public ResponseEntity<TestDisplayCart> getCart(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String userId = request.getHeader("userId");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            System.out.println(authorizationHeader);
            token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        }
      try{
          if(userMicroserviceClient.getUser(Long.valueOf(userId), "Bearer "+ token) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }

        return new ResponseEntity<>(testService.getTestCart(Long.parseLong(userId)), HttpStatus.OK);
      }
      catch(FeignException.NotFound e){
         log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }catch (FeignException.Unauthorized e){
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }catch (Exception e){
          log.error(e.getMessage());
          httpServletResponse.getWriter().write(e.getMessage());
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
    }
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(HttpServletRequest request, @RequestBody AddToCartRequest addToCartRequest) {
        try {
//            long userId =addToCartRequest.getUserId();
            String userId = request.getHeader("userId");
            long productId = addToCartRequest.getProductId();
            int quantity = addToCartRequest.getQuantity();
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                System.out.println(authorizationHeader);
                token = authorizationHeader.substring(7); // Remove "Bearer " prefix
                System.out.println(token);
            }
            if(userMicroserviceClient.getUser(Long.valueOf(userId), "Bearer "+token) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (productMicroserviceClient.getProduct(productId) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            testService.addToCart(productId, Long.parseLong(userId), quantity);
            return new ResponseEntity<>("Product Added successfully", HttpStatus.OK);
        } catch (FeignException.FeignClientException e) {
//            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/delete/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(HttpServletRequest request, @PathVariable("productId") long productId){
        try{
            String userId = request.getHeader("userId");
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            }
            if(userMicroserviceClient.getUser(Long.valueOf(userId), "Bearer "+token) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(productMicroserviceClient.getProduct(productId) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        if(testService.removeFromCart(productId, Long.parseLong(userId))){
            return new ResponseEntity<>("Product Deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }
    catch(FeignException.FeignClientException e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/user/product/{productId}/updateQuantity/{quantity}")
    public ResponseEntity<String> updateCart(HttpServletRequest request, @PathVariable("productId") long productId, @PathVariable("quantity") int quantity){
        try {
            String userId = request.getHeader("userId");
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            }
            if(userMicroserviceClient.getUser(Long.valueOf(userId), "Bearer "+token) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(productMicroserviceClient.getProduct(productId) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if(testService.updateQuantity(productId, Long.parseLong(userId), quantity)){
            return new ResponseEntity<>("Product Updated scuucessfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);

        }
        catch(FeignException.FeignClientException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCart(String userId){
        boolean res = testService.deleteCart(Long.parseLong(userId));
        if(res){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success","Cart Deleted for userId:"+userId));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("Error","Cart Not Found for userId:"+userId));


    }

}
