package com.chatbot.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String id;
    
    private double cost;
    private String category;
    private String name;
    private String brand;
    
    @Column(name = "retail_price")
    private double retailPrice;
    
    private String department;
    private String sku;
    
    @Column(name = "distribution_center_id")
    private String distributionCenterId;
} 