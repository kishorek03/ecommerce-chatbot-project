package com.chatbot.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusResponse {
    private String orderId;
    private String status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime returnedAt;
    private int numOfItem;
    private double totalAmount;
    private String message;
    
    public OrderStatusResponse(String orderId, String status, LocalDateTime shippedAt, LocalDateTime deliveredAt) {
        this.orderId = orderId;
        this.status = status;
        this.shippedAt = shippedAt;
        this.deliveredAt = deliveredAt;
    }
} 