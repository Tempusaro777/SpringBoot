package com.example.demo.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "userpassword", nullable = false)
    private String userpassword;
    @Column(name = "email", nullable = false)
    private String email;
    private Date registerDate;
    
    public User(String username, String userpassword, String email, Date registerDate) {
        this.username = username;
        this.userpassword = userpassword;
        this.email = email;
        this.registerDate = registerDate;
    }

    public User(Long id, String username, String userpassword, String email, Date registerDate) {
        this.id = id;
        this.username = username;
        this.userpassword = userpassword;
        this.email = email;
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", userpassword=" + userpassword + ", email=" + email
                + ", registerDate=" + registerDate + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    
}
