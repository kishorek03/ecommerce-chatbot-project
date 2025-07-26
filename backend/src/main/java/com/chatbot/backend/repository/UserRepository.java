package com.chatbot.backend.repository;

import com.chatbot.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByGender(String gender);
    
    List<User> findByState(String state);
    
    List<User> findByCity(String city);
    
    List<User> findByStateAndCity(String state, String city);
} 