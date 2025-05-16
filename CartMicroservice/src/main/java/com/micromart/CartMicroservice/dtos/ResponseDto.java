package com.micromart.CartMicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class ResponseDto {
    private String message;
    private Object data;
}
