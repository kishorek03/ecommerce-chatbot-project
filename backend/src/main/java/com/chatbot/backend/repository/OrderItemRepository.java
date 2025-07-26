package com.chatbot.backend.repository;

import com.chatbot.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    
    List<OrderItem> findByOrderId(String orderId);
    
    List<OrderItem> findByUserId(String userId);
    
    List<OrderItem> findByProductId(String productId);
    
    List<OrderItem> findByStatus(String status);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.orderId = ?1 AND oi.status = ?2")
    List<OrderItem> findByOrderIdAndStatus(String orderId, String status);
} 