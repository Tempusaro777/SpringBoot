package com.example.demo.DAL;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    @Query("select u from User where u.username = ?1")
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);



}
