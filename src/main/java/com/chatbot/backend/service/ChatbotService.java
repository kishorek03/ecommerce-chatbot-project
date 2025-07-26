package com.chatbot.backend.service;

import com.chatbot.backend.model.*;
import com.chatbot.backend.repository.*;
import com.chatbot.backend.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatbotService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final OrderItemRepository orderItemRepository;
    
    @Autowired
    public ChatbotService(ProductRepository productRepository, 
                        OrderRepository orderRepository,
                        InventoryItemRepository inventoryItemRepository,
                        OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.orderItemRepository = orderItemRepository;
    }
    
    public List<Product> getTop5SoldProducts() {
        // Simple approach: get first 5 products
        return productRepository.findAll().stream().limit(5).toList();
    }
    
    public OrderStatusResponse getOrderStatus(String orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        
        OrderStatusResponse response = new OrderStatusResponse(
            order.getOrderId(),
            order.getStatus(),
            order.getShippedAt(),
            order.getDeliveredAt()
        );
        response.setReturnedAt(order.getReturnedAt());
        response.setNumOfItem(order.getNumOfItem());
        response.setTotalAmount(0.0); // Simplified for now
        
        // Generate user-friendly message
        String message = generateOrderStatusMessage(order);
        response.setMessage(message);
        
        return response;
    }
    
    public int getProductStockCount(String productName) {
        return inventoryItemRepository.countByProductNameAndSoldAtIsNull(productName);
    }
    
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }
    
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrandIgnoreCase(brand);
    }
    
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }
    
    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }
    
    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }
    
    public List<String> getAllBrands() {
        return productRepository.findAllBrands();
    }
    
    public List<String> getAllDepartments() {
        return productRepository.findAllDepartments();
    }
    
    public ChatResponse processNaturalLanguageQuery(String question) {
        System.out.println("User asked: " + question);
        String lowerQuestion = question.toLowerCase();

        // Categories
        if (lowerQuestion.contains("category") || lowerQuestion.contains("categories")) {
            System.out.println("Matched category intent");
            List<String> categories = getAllCategories();
            System.out.println("Categories: " + categories);
            return new ChatResponse("Here are all available product categories:\n" + String.join(", ", categories));
        }

        // Brands
        if (lowerQuestion.contains("brand") || lowerQuestion.contains("brands")) {
            System.out.println("Matched brand intent");
            List<String> brands = getAllBrands();
            System.out.println("Brands: " + brands);
            return new ChatResponse("Here are all available brands:\n" + String.join(", ", brands));
        }

        // Order status queries
        if (lowerQuestion.contains("order status") || lowerQuestion.contains("track order") || lowerQuestion.contains("status of order")) {
            System.out.println("Matched order status intent");
            String orderId = extractOrderId(question);
            System.out.println("Extracted orderId: " + orderId);
            if (orderId != null) {
                try {
                    OrderStatusResponse orderStatus = getOrderStatus(orderId);
                    List<Map<String, Object>> data = new ArrayList<>();
                    Map<String, Object> orderData = new HashMap<>();
                    orderData.put("orderId", orderStatus.getOrderId());
                    orderData.put("status", orderStatus.getStatus());
                    orderData.put("shippedAt", orderStatus.getShippedAt());
                    orderData.put("deliveredAt", orderStatus.getDeliveredAt());
                    orderData.put("numOfItem", orderStatus.getNumOfItem());
                    orderData.put("totalAmount", orderStatus.getTotalAmount());
                    data.add(orderData);
                    System.out.println("Order status found: " + orderStatus.getStatus());
                    return new ChatResponse(orderStatus.getMessage(), "order_status", data);
                } catch (RuntimeException e) {
                    System.out.println("Order not found for ID: " + orderId);
                    return new ChatResponse("Sorry, I couldn't find an order with ID: " + orderId);
                }
            } else {
                System.out.println("No order ID extracted");
                return new ChatResponse("Please provide an order ID to check the status.");
            }
        }

        // Stock queries (fuzzy, case-insensitive)
        if (lowerQuestion.contains("stock") || lowerQuestion.contains("available") || lowerQuestion.contains("inventory")) {
            System.out.println("Matched stock intent");
            String productName = extractProductName(question);
            System.out.println("Extracted productName: " + productName);
            if (productName != null) {
                List<Product> products = productRepository.findByNameContainingIgnoreCase(productName);
                System.out.println("Products found: " + products.size());
                if (!products.isEmpty()) {
                    int totalStock = 0;
                    for (Product p : products) {
                        totalStock += getProductStockCount(p.getName());
                    }
                    System.out.println("Total stock for '" + productName + "': " + totalStock);
                    return new ChatResponse("We have " + totalStock + " units of '" + productName + "' in stock.");
                } else {
                    System.out.println("No products found matching: " + productName);
                    return new ChatResponse("Sorry, I couldn't find any products matching '" + productName + "'.");
                }
            } else {
                System.out.println("No product name extracted");
                return new ChatResponse("Please specify which product you'd like to check stock for.");
            }
        }

        // Top products queries
        if (lowerQuestion.contains("top") || lowerQuestion.contains("popular") || lowerQuestion.contains("best selling")) {
            System.out.println("Matched top products intent");
            List<Product> topProducts = getTop5SoldProducts();
            List<Map<String, Object>> data = new ArrayList<>();
            for (Product product : topProducts) {
                Map<String, Object> productData = new HashMap<>();
                productData.put("id", product.getId());
                productData.put("name", product.getName());
                productData.put("brand", product.getBrand());
                productData.put("category", product.getCategory());
                productData.put("retailPrice", product.getRetailPrice());
                data.add(productData);
            }
            return new ChatResponse("Here are our top 5 best-selling products:", "product_list", data);
        }

        // Product search queries
        if (lowerQuestion.contains("find") || lowerQuestion.contains("search") || lowerQuestion.contains("looking for")) {
            System.out.println("Matched product search intent");
            String productName = extractProductName(question);
            System.out.println("Extracted productName: " + productName);
            if (productName != null) {
                List<Product> products = searchProducts(productName);
                if (!products.isEmpty()) {
                    List<Map<String, Object>> data = new ArrayList<>();
                    for (Product product : products) {
                        Map<String, Object> productData = new HashMap<>();
                        productData.put("id", product.getId());
                        productData.put("name", product.getName());
                        productData.put("brand", product.getBrand());
                        productData.put("category", product.getCategory());
                        productData.put("retailPrice", product.getRetailPrice());
                        data.add(productData);
                    }
                    return new ChatResponse("I found " + products.size() + " products matching '" + productName + "':", "product_list", data);
                } else {
                    System.out.println("No products found matching: " + productName);
                    return new ChatResponse("Sorry, I couldn't find any products matching '" + productName + "'.");
                }
            }
        }

        System.out.println("No intent matched. Returning default response.");
        // Default response
        return new ChatResponse("I'm here to help! You can ask me about order status, product availability, search for products, or find our top-selling items.");
    }
    
    private String extractOrderId(String question) {
        // Match numbers or alphanumeric IDs
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\b[0-9A-Z]{1,}\\b");
        java.util.regex.Matcher matcher = pattern.matcher(question.toUpperCase());
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    
    private String extractProductName(String question) {
        // Simple extraction - look for words after "find", "search", "stock", etc.
        String[] keywords = {"find", "search", "stock", "available", "looking for"};
        for (String keyword : keywords) {
            if (question.toLowerCase().contains(keyword)) {
                String[] parts = question.toLowerCase().split(keyword);
                if (parts.length > 1) {
                    String afterKeyword = parts[1].trim();
                    // Remove common words and punctuation
                    afterKeyword = afterKeyword.replaceAll("\\b(the|a|an|for|of|in|on|at|to|with|by)\\b", "").trim();
                    afterKeyword = afterKeyword.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
                    if (!afterKeyword.isEmpty()) {
                        return afterKeyword;
                    }
                }
            }
        }
        return null;
    }
    
    private String generateOrderStatusMessage(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        
        switch (order.getStatus().toLowerCase()) {
            case "pending":
                return "Your order #" + order.getOrderId() + " is currently pending and will be processed soon.";
            case "processing":
                return "Your order #" + order.getOrderId() + " is being processed and will be shipped shortly.";
            case "shipped":
                String shippedDate = order.getShippedAt() != null ? 
                    order.getShippedAt().format(formatter) : "recently";
                return "Your order #" + order.getOrderId() + " was shipped on " + shippedDate + " and is on its way to you.";
            case "delivered":
                String deliveredDate = order.getDeliveredAt() != null ? 
                    order.getDeliveredAt().format(formatter) : "recently";
                return "Your order #" + order.getOrderId() + " was delivered on " + deliveredDate + ". Thank you for your purchase!";
            case "returned":
                String returnedDate = order.getReturnedAt() != null ? 
                    order.getReturnedAt().format(formatter) : "recently";
                return "Your order #" + order.getOrderId() + " was returned on " + returnedDate + ".";
            default:
                return "Your order #" + order.getOrderId() + " status is: " + order.getStatus();
        }
    }
} 