package com.chatbot.backend.controller;

import com.chatbot.backend.dto.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ChatResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ChatResponse errorResponse = new ChatResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ChatResponse> handleGenericException(Exception ex, WebRequest request) {
        ChatResponse errorResponse = new ChatResponse("An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ChatResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ChatResponse errorResponse = new ChatResponse("Invalid request: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
} 