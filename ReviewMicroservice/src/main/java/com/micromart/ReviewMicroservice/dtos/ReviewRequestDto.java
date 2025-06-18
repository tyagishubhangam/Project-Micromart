package com.micromart.ReviewMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private String userFullName;
    private String productId;
    private String description;
    private int rating;
}
