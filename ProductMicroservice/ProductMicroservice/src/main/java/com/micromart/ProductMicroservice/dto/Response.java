package com.micromart.ProductMicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
   private String status;
    private String message;
}
