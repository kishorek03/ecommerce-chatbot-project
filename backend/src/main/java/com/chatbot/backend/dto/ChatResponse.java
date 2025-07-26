package com.chatbot.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String message;
    private String type; // "text", "product_list", "order_status", "stock_info"
    private List<Map<String, Object>> data;
    private boolean success;
    private String errorMessage;
    
    public ChatResponse(String message, String type) {
        this.message = message;
        this.type = type;
        this.success = true;
    }
    
    public ChatResponse(String message, String type, List<Map<String, Object>> data) {
        this.message = message;
        this.type = type;
        this.data = data;
        this.success = true;
    }
    
    public ChatResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        this.success = false;
    }
} 