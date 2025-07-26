package com.chatbot.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    private String id;
    
    @Column(name = "order_id")
    private String orderId;
    
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "product_id")
    private String productId;
    
    @Column(name = "inventory_item_id")
    private String inventoryItemId;
    
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;
    
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    
    @Column(name = "returned_at")
    private LocalDateTime returnedAt;
} 