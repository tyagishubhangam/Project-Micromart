package com.micromart.OrderMicroservice.models;

import com.micromart.OrderMicroservice.dtos.OrderItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String orderId;
    private LocalDateTime orderedAt;
    private String orderStatus;

    // NOTE  this is used to initialize the id with UUId and orderAt before persisting to DB
    @PrePersist
    public void prePersist() {

        if (orderId == null) {
            orderId = UUID.randomUUID().toString();
        }
        if(orderedAt == null){
            orderedAt = LocalDateTime.now();
        }
        if(orderStatus == null){
            orderStatus = "ordered";
        }
    }


    private String userId;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderItems;

    private double totalAmount;


    @Column(unique = true)
    private String paymentId;


}