package com.chatbot.backend.repository;

import com.chatbot.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByCategoryIgnoreCase(String category);
    
    List<Product> findByBrandIgnoreCase(String brand);
    
    List<Product> findByDepartmentIgnoreCase(String department);
    
    @Query("SELECT p FROM Product p WHERE p.retailPrice BETWEEN ?1 AND ?2")
    List<Product> findByPriceRange(double minPrice, double maxPrice);
    
    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findAllCategories();
    
    @Query("SELECT DISTINCT p.brand FROM Product p")
    List<String> findAllBrands();
    
    @Query("SELECT DISTINCT p.department FROM Product p")
    List<String> findAllDepartments();
} 