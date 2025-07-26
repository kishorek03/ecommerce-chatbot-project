package com.chatbot.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatQuery {
    private String question;
    private String userId;
    private String sessionId;
} 