package com.micromart.CartMicroservice.Cart;
import java.util.List;

import com.micromart.CartMicroservice.dtos.ProductInCart;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TestDisplayCart {
    private List<ProductInCart> productsInCart;

    private Double totalAmount;


}
