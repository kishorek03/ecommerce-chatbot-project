package com.chatbot.backend.service;

import com.chatbot.backend.model.*;
import com.chatbot.backend.repository.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvDataLoaderService {
    
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final DistributionCenterRepository distributionCenterRepository;
    private final ResourceLoader resourceLoader;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER_UTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
    
    @Autowired
    public CsvDataLoaderService(ProductRepository productRepository,
                              UserRepository userRepository,
                              OrderRepository orderRepository,
                              OrderItemRepository orderItemRepository,
                              InventoryItemRepository inventoryItemRepository,
                              DistributionCenterRepository distributionCenterRepository,
                              ResourceLoader resourceLoader) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.distributionCenterRepository = distributionCenterRepository;
        this.resourceLoader = resourceLoader;
    }
    
    public void loadAllData() {
        try {
            loadDistributionCenters();
            loadProducts();
            loadUsers();
            loadInventoryItems();
            loadOrders();
            loadOrderItems();
            System.out.println("All CSV data loaded successfully!");
        } catch (Exception e) {
            System.err.println("Error loading CSV data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadDistributionCenters() throws IOException, CsvValidationException {
        Resource resource = resourceLoader.getResource("classpath:distribution_centers.csv");
        if (!resource.exists()) {
            System.out.println("distribution_centers.csv not found, skipping...");
            return;
        }
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readNext(); // Skip header
            String[] line;
            List<DistributionCenter> centers = new ArrayList<>();
            
            while ((line = reader.readNext()) != null) {
                if (line.length >= 4) {
                    DistributionCenter center = new DistributionCenter();
                    center.setId(line[0]);
                    center.setName(line[1]);
                    center.setLatitude(Double.parseDouble(line[2]));
                    center.setLongitude(Double.parseDouble(line[3]));
                    centers.add(center);
                }
            }
            
            distributionCenterRepository.saveAll(centers);
            System.out.println("Loaded " + centers.size() + " distribution centers");
        }
    }
    
    private void loadProducts() throws IOException, CsvValidationException {
        Resource resource = resourceLoader.getResource("classpath:products.csv");
        if (!resource.exists()) {
            System.out.println("products.csv not found, skipping...");
            return;
        }
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readNext(); // Skip header
            String[] line;
            List<Product> products = new ArrayList<>();
            
            while ((line = reader.readNext()) != null) {
                if (line.length >= 9) {
                    Product product = new Product();
                    product.setId(line[0]);
                    product.setCost(Double.parseDouble(line[1]));
                    product.setCategory(line[2]);
                    product.setName(line[3]);
                    product.setBrand(line[4]);
                    product.setRetailPrice(Double.parseDouble(line[5]));
                    product.setDepartment(line[6]);
                    product.setSku(line[7]);
                    product.setDistributionCenterId(line[8]);
                    products.add(product);
                }
            }
            
            productRepository.saveAll(products);
            System.out.println("Loaded " + products.size() + " products");
        }
    }
    
    private void loadUsers() throws IOException, CsvValidationException {
        Resource resource = resourceLoader.getResource("classpath:users.csv");
        if (!resource.exists()) {
            System.out.println("users.csv not found, skipping...");
            return;
        }
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readNext(); // Skip header
            String[] line;
            List<User> users = new ArrayList<>();
            
            while ((line = reader.readNext()) != null) {
                if (line.length >= 16) {
                    User user = new User();
                    user.setId(line[0]);
                    user.setFirstName(line[1]);
                    user.setLastName(line[2]);
                    user.setEmail(line[3]);
                    user.setAge(Integer.parseInt(line[4]));
                    user.setGender(line[5]);
                    user.setState(line[6]);
                    user.setStreetAddress(line[7]);
                    user.setPostalCode(line[8]);
                    user.setCity(line[9]);
                    user.setCountry(line[10]);
                    user.setLatitude(Double.parseDouble(line[11]));
                    user.setLongitude(Double.parseDouble(line[12]));
                    user.setTrafficSource(line[13]);
                    user.setCreatedAt(parseDateTime(line[14]));
                    users.add(user);
                }
            }
            
            userRepository.saveAll(users);
            System.out.println("Loaded " + users.size() + " users");
        }
    }
    
    private void loadInventoryItems() throws IOException, CsvValidationException {
        Resource resource = resourceLoader.getResource("classpath:inventory_items.csv");
        if (!resource.exists()) {
            System.out.println("inventory_items.csv not found, skipping...");
            return;
        }
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readNext(); // Skip header
            String[] line;
            List<InventoryItem> items = new ArrayList<>();
            
            while ((line = reader.readNext()) != null) {
                if (line.length >= 12) {
                    InventoryItem item = new InventoryItem();
                    item.setId(line[0]);
                    item.setProductId(line[1]);
                    item.setCreatedAt(parseDateTime(line[2]));
                    item.setSoldAt(line[3].isEmpty() ? null : parseDateTime(line[3]));
                    item.setCost(Double.parseDouble(line[4]));
                    item.setProductCategory(line[5]);
                    item.setProductName(line[6]);
                    item.setProductBrand(line[7]);
                    item.setProductRetailPrice(Double.parseDouble(line[8]));
                    item.setProductDepartment(line[9]);
                    item.setProductSku(line[10]);
                    item.setProductDistributionCenterId(line[11]);
                    items.add(item);
                }
            }
            
            inventoryItemRepository.saveAll(items);
            System.out.println("Loaded " + items.size() + " inventory items");
        }
    }
    
    private void loadOrders() throws IOException, CsvValidationException {
        Resource resource = resourceLoader.getResource("classpath:orders.csv");
        if (!resource.exists()) {
            System.out.println("orders.csv not found, skipping...");
            return;
        }
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readNext(); // Skip header
            String[] line;
            List<Order> orders = new ArrayList<>();
            
            while ((line = reader.readNext()) != null) {
                if (line.length >= 9) {
                    Order order = new Order();
                    order.setOrderId(line[0]);
                    order.setUserId(line[1]);
                    order.setStatus(line[2]);
                    order.setGender(line[3]);
                    order.setCreatedAt(parseDateTime(line[4]));
                    order.setReturnedAt(line[5].isEmpty() ? null : parseDateTime(line[5]));
                    order.setShippedAt(line[6].isEmpty() ? null : parseDateTime(line[6]));
                    order.setDeliveredAt(line[7].isEmpty() ? null : parseDateTime(line[7]));
                    order.setNumOfItem(Integer.parseInt(line[8]));
                    orders.add(order);
                }
            }
            
            orderRepository.saveAll(orders);
            System.out.println("Loaded " + orders.size() + " orders");
        }
    }
    
    private void loadOrderItems() throws IOException, CsvValidationException {
        Resource resource = resourceLoader.getResource("classpath:order_items.csv");
        if (!resource.exists()) {
            System.out.println("order_items.csv not found, skipping...");
            return;
        }
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readNext(); // Skip header
            String[] line;
            List<OrderItem> items = new ArrayList<>();
            
            while ((line = reader.readNext()) != null) {
                if (line.length >= 9) {
                    OrderItem item = new OrderItem();
                    item.setId(line[0]);
                    item.setOrderId(line[1]);
                    item.setUserId(line[2]);
                    item.setProductId(line[3]);
                    item.setInventoryItemId(line[4]);
                    item.setStatus(line[5]);
                    item.setCreatedAt(parseDateTime(line[6]));
                    item.setShippedAt(line[7].isEmpty() ? null : parseDateTime(line[7]));
                    item.setDeliveredAt(line[8].isEmpty() ? null : parseDateTime(line[8]));
                    item.setReturnedAt(line.length > 9 && !line[9].isEmpty() ? parseDateTime(line[9]) : null);
                    items.add(item);
                }
            }
            
            orderItemRepository.saveAll(items);
            System.out.println("Loaded " + items.size() + " order items");
        }
    }
    
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        try {
            // Handle timezone offset format (e.g., 2022-07-02 07:09:20+00:00)
            if (dateTimeStr.contains("+") || (dateTimeStr.contains("-") && dateTimeStr.lastIndexOf("-") > 10)) {
                // Remove timezone offset and parse as LocalDateTime
                String dateTimeWithoutTz = dateTimeStr.replaceAll("[+-]\\d{2}:?\\d{2}$", "");
                return LocalDateTime.parse(dateTimeWithoutTz, DATE_FORMATTER);
            }
            // Try with UTC format
            else if (dateTimeStr.endsWith("UTC")) {
                return LocalDateTime.parse(dateTimeStr, DATE_FORMATTER_UTC);
            } 
            // Try with standard format
            else {
                return LocalDateTime.parse(dateTimeStr, DATE_FORMATTER);
            }
        } catch (Exception e) {
            System.err.println("Error parsing date: " + dateTimeStr + " - " + e.getMessage());
            return null;
        }
    }
} 