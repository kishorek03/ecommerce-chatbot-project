package com.chatbot.backend.controller;

import com.chatbot.backend.dto.*;
import com.chatbot.backend.model.Product;
import com.chatbot.backend.model.Order;
import com.chatbot.backend.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {
    private final ChatbotService chatbotService;
    
    @Autowired
    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }
    
    // Natural language query endpoint
    @PostMapping("/query")
    public ResponseEntity<ChatResponse> handleChatbotQuery(@RequestBody ChatQuery query) {
        ChatResponse response = chatbotService.processNaturalLanguageQuery(query.getQuestion());
        return ResponseEntity.ok(response);
    }
    
    // Product endpoints
    @GetMapping("/products/top")
    public ResponseEntity<List<Product>> getTop5SoldProducts() {
        List<Product> products = chatbotService.getTop5SoldProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        List<Product> products = chatbotService.searchProducts(query);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = chatbotService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = chatbotService.getProductsByBrand(brand);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam double minPrice, 
            @RequestParam double maxPrice) {
        List<Product> products = chatbotService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
    
    // Order endpoints
    @GetMapping("/orders/status/{orderId}")
    public ResponseEntity<OrderStatusResponse> getOrderStatus(@PathVariable String orderId) {
        OrderStatusResponse orderStatus = chatbotService.getOrderStatus(orderId);
        return ResponseEntity.ok(orderStatus);
    }
    
    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String userId) {
        List<Order> orders = chatbotService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }
    
    // Inventory endpoints
    @GetMapping("/inventory/stock/{productName}")
    public ResponseEntity<Integer> getProductStockCount(@PathVariable String productName) {
        int stockCount = chatbotService.getProductStockCount(productName);
        return ResponseEntity.ok(stockCount);
    }
    
    // Metadata endpoints
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = chatbotService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/brands")
    public ResponseEntity<List<String>> getAllBrands() {
        List<String> brands = chatbotService.getAllBrands();
        return ResponseEntity.ok(brands);
    }
    
    @GetMapping("/departments")
    public ResponseEntity<List<String>> getAllDepartments() {
        List<String> departments = chatbotService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
    
    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Chatbot API is running!");
    }
} 