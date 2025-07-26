package com.chatbot.backend.repository;

import com.chatbot.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    
    List<Order> findByUserId(String userId);
    
    List<Order> findByStatus(String status);
    
    List<Order> findByUserIdAndStatus(String userId, String status);
    
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN ?1 AND ?2")
    List<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT o FROM Order o WHERE o.userId = ?1 AND o.createdAt BETWEEN ?2 AND ?3")
    List<Order> findByUserIdAndDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = ?1")
    long countByStatus(String status);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.userId = ?1")
    long countByUserId(String userId);
} 