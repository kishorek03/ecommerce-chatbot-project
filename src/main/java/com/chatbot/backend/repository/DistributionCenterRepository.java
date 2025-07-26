package com.chatbot.backend.repository;

import com.chatbot.backend.model.DistributionCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DistributionCenterRepository extends JpaRepository<DistributionCenter, String> {
    
    List<DistributionCenter> findByName(String name);
    
    List<DistributionCenter> findByNameContainingIgnoreCase(String name);
} 