package com.micromart.CartMicroservice.CartService;


import com.micromart.CartMicroservice.Cart.CartItem;
import com.micromart.CartMicroservice.Cart.TestDisplayCart;
import com.micromart.CartMicroservice.Clients.UserMicroserviceClient;
import com.micromart.CartMicroservice.dtos.ProductInCart;
import com.micromart.CartMicroservice.mapper.TestCartMapper;
import com.micromart.CartMicroservice.repositories.TestRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TestCartServiceImpl implements TestCartService {
    public final TestRepo testRepo;
    public final UserMicroserviceClient userClient;
    long temp =1L;

    private final TestCartMapper testCartMapper;


    public TestCartServiceImpl(TestRepo testRepo, TestDisplayCart testDisplayCart, TestCartMapper testCartMapper, UserMicroserviceClient userClient) {
        this.testRepo = testRepo;
        this.testCartMapper = testCartMapper;
        this.userClient = userClient;
    }
    @Override
    public void addToCart(long productId, long userId, int quantity) {


        if (testRepo.findByProductIdAndUserId(productId, userId) != null) {
            updateQuantity(productId, userId, quantity);
            return;
        }
       CartItem cartItem = new CartItem();
       cartItem.setProductId(productId);
       cartItem.setUserId(userId);
       cartItem.setQuantity(quantity);
       cartItem.setId(temp++);
       testRepo.save(cartItem);

    }

    @Override
    public TestDisplayCart getTestCart(long userId) {
        double totalAmount = 0.0;
        TestDisplayCart testDisplayCart = new TestDisplayCart();
        List<ProductInCart> productsInCart = testCartMapper.getAllProductsInCart(userId);
        for (ProductInCart testCart : productsInCart) {
            totalAmount += testCart.getProduct().getPrice() * testCart.getQuantity();
        }
        testDisplayCart.setTotalAmount(totalAmount);
        testDisplayCart.setProductsInCart(testCartMapper.getAllProductsInCart(userId));
//        System.out.println(testDisplayCart.getProductsInCart(userId));

        return testDisplayCart;

    }

    @Override
    public boolean removeFromCart(long productId, long userId) {
        if(testRepo.findByProductIdAndUserId(productId, userId) != null) {
            testRepo.delete(testRepo.findByProductIdAndUserId(productId, userId));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateQuantity(long productId, long userId, int quantity) {
        if(testRepo.findByProductIdAndUserId(productId, userId) != null){
            CartItem cartItem = testRepo.findByProductIdAndUserId(productId, userId);
            cartItem.setQuantity(quantity);
            testRepo.save(cartItem);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCart(long userId) {
        try{
            testRepo.deleteAll(testRepo.findByUserId(userId));
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
