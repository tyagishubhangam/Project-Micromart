package com.micromart.OrderMicroservice.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetails {
    private String paymentId;  // e.g., "PAYID-NARYYNQ5XP1236533346913Y"
    private String status;     // e.g., "APPROVED"
    private String method;     // e.g., "PAYPAL"
}