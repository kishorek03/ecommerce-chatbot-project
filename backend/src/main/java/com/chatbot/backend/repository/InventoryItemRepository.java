package com.chatbot.backend.repository;

import com.chatbot.backend.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {
    
    List<InventoryItem> findByProductId(String productId);
    
    List<InventoryItem> findByProductNameContainingIgnoreCase(String productName);
    
    @Query("SELECT COUNT(i) FROM InventoryItem i WHERE i.productName = ?1 AND i.soldAt IS NULL")
    int countByProductNameAndSoldAtIsNull(String productName);
    
    @Query("SELECT COUNT(i) FROM InventoryItem i WHERE i.productId = ?1 AND i.soldAt IS NULL")
    int countByProductIdAndSoldAtIsNull(String productId);
    
    @Query("SELECT COUNT(i) FROM InventoryItem i WHERE i.productDistributionCenterId = ?1 AND i.soldAt IS NULL")
    int countByProductDistributionCenterIdAndSoldAtIsNull(String productDistributionCenterId);
    
    @Query("SELECT i.productName, COUNT(i) FROM InventoryItem i WHERE i.soldAt IS NULL GROUP BY i.productName")
    List<Object[]> getAvailableStockByProduct();
} 