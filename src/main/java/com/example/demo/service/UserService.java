package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public interface UserService {

    void deleteUser(Long userId);

    List<User> getAllUsers();

    User findByUsername(String username);

    User registerUser(User user);

    Optional<User> findById(Long userId);

    void save(User user);

}