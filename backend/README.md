# E-commerce Customer Support Chatbot Backend API

A Spring Boot-based REST API for an e-commerce customer support chatbot that provides intelligent responses to customer queries about products, orders, and inventory using real e-commerce data.

## Features

- **Natural Language Processing**: Handle customer queries in natural language
- **Order Tracking**: Check order status and delivery information
- **Product Search**: Search products by name, category, brand, or price range
- **Inventory Management**: Check product availability and stock levels
- **Top Products**: Get best-selling products
- **User Management**: Track customer orders and preferences
- **Real Data Integration**: Uses actual e-commerce dataset from CSV files

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **H2 Database** (Development/Testing)
- **MySQL** (Production)
- **Gradle**
- **Lombok**
- **OpenCSV** (for CSV data loading)

## Dataset

The application uses the following CSV files from the e-commerce dataset:

- `distribution_centers.csv` - Distribution center locations
- `inventory_items.csv` - Individual inventory items with product details
- `order_items.csv` - Items within orders
- `orders.csv` - Order information and status
- `products.csv` - Product catalog
- `users.csv` - Customer information

## Project Structure

```
src/main/java/com/chatbot/backend/
├── config/
│   └── DataInitializationConfig.java
├── controller/
│   ├── ChatbotController.java
│   └── GlobalExceptionHandler.java
├── model/
│   ├── Product.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── InventoryItem.java
│   ├── User.java
│   └── DistributionCenter.java
├── repository/
│   ├── ProductRepository.java
│   ├── OrderRepository.java
│   ├── OrderItemRepository.java
│   ├── InventoryItemRepository.java
│   ├── UserRepository.java
│   └── DistributionCenterRepository.java
├── service/
│   ├── ChatbotService.java
│   └── CsvDataLoaderService.java
├── dto/
│   ├── ChatQuery.java
│   ├── ChatResponse.java
│   └── OrderStatusResponse.java
└── BackendApplication.java
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Gradle 7.0 or higher

### Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd backend
   ```

2. **Add CSV files**
   Place your CSV files in the `src/main/resources/` directory:
   - `distribution_centers.csv`
   - `inventory_items.csv`
   - `order_items.csv`
   - `orders.csv`
   - `products.csv`
   - `users.csv`

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:ecommerce`
     - Username: `sa`
     - Password: (empty)

## API Endpoints

### Chatbot Query Endpoint

**POST** `/api/chatbot/query`

Process natural language queries from customers.

**Request Body:**
```json
{
  "question": "What's the status of my order ORD001?",
  "userId": "USER001",
  "sessionId": "session123"
}
```

**Response:**
```json
{
  "message": "Your order #ORD001 was delivered on Jan 18, 2024. Thank you for your purchase!",
  "type": "order_status",
  "data": [
    {
      "orderId": "ORD001",
      "status": "delivered",
      "shippedAt": "2024-01-16T14:20:00",
      "deliveredAt": "2024-01-18T11:45:00",
      "numOfItem": 2,
      "totalAmount": 79.98
    }
  ],
  "success": true
}
```

### Product Endpoints

**GET** `/api/chatbot/products/top`
- Get top 5 best-selling products

**GET** `/api/chatbot/products/search?query=shirt`
- Search products by name

**GET** `/api/chatbot/products/category/{category}`
- Get products by category

**GET** `/api/chatbot/products/brand/{brand}`
- Get products by brand

**GET** `/api/chatbot/products/price-range?minPrice=10&maxPrice=100`
- Get products within price range

### Order Endpoints

**GET** `/api/chatbot/orders/status/{orderId}`
- Get order status and details

**GET** `/api/chatbot/orders/user/{userId}`
- Get all orders for a user

### Inventory Endpoints

**GET** `/api/chatbot/inventory/stock/{productName}`
- Get stock count for a product

### Metadata Endpoints

**GET** `/api/chatbot/categories`
- Get all available categories

**GET** `/api/chatbot/brands`
- Get all available brands

**GET** `/api/chatbot/departments`
- Get all available departments

### Health Check

**GET** `/api/chatbot/health`
- Check if the API is running

## Natural Language Query Examples

The chatbot can understand various types of queries:

### Order Status Queries
- "What's the status of my order ORD001?"
- "Track order ORD002"
- "Where is my order ORD003?"

### Stock Queries
- "Do you have Classic Cotton T-Shirt in stock?"
- "How many Wireless Headphones are available?"
- "Check stock for Running Shoes"

### Product Search Queries
- "Find t-shirts"
- "Search for electronics"
- "I'm looking for running shoes"

### Top Products Queries
- "What are your top products?"
- "Show me best-selling items"
- "Most popular products"

## Data Loading

The application automatically loads CSV data on startup using the `CsvDataLoaderService`. The data loading process:

1. Reads CSV files from `src/main/resources/`
2. Parses and validates the data
3. Saves entities to the database
4. Provides progress feedback in the console

## Database Configuration

### Development (H2)
The application uses H2 in-memory database by default with data loaded from CSV files.

### Production (MySQL)
To use MySQL in production, update `application.properties`:

```properties
# Comment out H2 configuration
# spring.datasource.url=jdbc:h2:mem:ecommerce

# Uncomment MySQL configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_chatbot
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## Testing the API

### Using curl

1. **Health Check**
   ```bash
   curl http://localhost:8080/api/chatbot/health
   ```

2. **Natural Language Query**
   ```bash
   curl -X POST http://localhost:8080/api/chatbot/query \
     -H "Content-Type: application/json" \
     -d '{"question": "What is the status of order ORD001?"}'
   ```

3. **Get Top Products**
   ```bash
   curl http://localhost:8080/api/chatbot/products/top
   ```

4. **Check Stock**
   ```bash
   curl http://localhost:8080/api/chatbot/inventory/stock/Classic%20Cotton%20T-Shirt
   ```

### Using Postman

Import the following collection:

```json
{
  "info": {
    "name": "E-commerce Chatbot API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/chatbot/health"
      }
    },
    {
      "name": "Chatbot Query",
      "request": {
        "method": "POST",
        "url": "http://localhost:8080/api/chatbot/query",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\"question\": \"What is the status of order ORD001?\"}"
        }
      }
    }
  ]
}
```

## Error Handling

The API provides consistent error responses:

```json
{
  "message": null,
  "type": null,
  "data": null,
  "success": false,
  "errorMessage": "Order not found with ID: INVALID123"
}
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please contact the development team or create an issue in the repository. 