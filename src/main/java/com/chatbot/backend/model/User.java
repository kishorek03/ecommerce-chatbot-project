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
@Table(name = "users")
public class User {
    @Id
    private String id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    private String email;
    private int age;
    private String gender;
    private String state;
    
    @Column(name = "street_address")
    private String streetAddress;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    
    @Column(name = "traffic_source")
    private String trafficSource;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
} 