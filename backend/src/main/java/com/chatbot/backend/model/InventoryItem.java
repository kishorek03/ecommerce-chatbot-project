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
@Table(name = "inventory_items")
public class InventoryItem {
    @Id
    private String id;
    
    @Column(name = "product_id")
    private String productId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "sold_at")
    private LocalDateTime soldAt;
    
    private double cost;
    
    @Column(name = "product_category")
    private String productCategory;
    
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "product_brand")
    private String productBrand;
    
    @Column(name = "product_retail_price")
    private double productRetailPrice;
    
    @Column(name = "product_department")
    private String productDepartment;
    
    @Column(name = "product_sku")
    private String productSku;
    
    @Column(name = "product_distribution_center_id")
    private String productDistributionCenterId;
} 