package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;


@Service
public interface UserService {
    User registerUser(User user);

    Optional<User> login(String username, String password);

    User updateUserInformation(User user);

    void deleteUser(Long userId);

    Optional<User> resetPassword(String email);

    void verifyEmail(String token);

    void changeRole(Long userId, String role);

    List<User> getAllUsers();

    List<User> searchUsers(String searchTerm);
    
} 