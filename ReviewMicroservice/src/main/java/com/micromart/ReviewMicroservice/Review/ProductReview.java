package com.micromart.ReviewMicroservice.Review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {
    @Id
    private String id;
    @PrePersist()
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }

    }

    private String userId;
    private String productId;
    private double rating;
    private String description;



}
