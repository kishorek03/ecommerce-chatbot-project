package com.chatbot.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "distribution_centers")
public class DistributionCenter {
    @Id
    private String id;
    
    private String name;
    private double latitude;
    private double longitude;
} 