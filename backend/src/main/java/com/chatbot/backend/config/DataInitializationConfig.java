package com.chatbot.backend.config;

import com.chatbot.backend.service.CsvDataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializationConfig implements CommandLineRunner {
    
    private final CsvDataLoaderService csvDataLoaderService;
    
    @Autowired
    public DataInitializationConfig(CsvDataLoaderService csvDataLoaderService) {
        this.csvDataLoaderService = csvDataLoaderService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting CSV data loading...");
        csvDataLoaderService.loadAllData();
        System.out.println("CSV data loading completed!");
    }
} 