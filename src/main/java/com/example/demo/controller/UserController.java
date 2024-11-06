package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Result;
import com.example.demo.model.User;
import com.example.demo.service.S3Service;
import com.example.demo.service.UserService;
import com.example.demo.utils.Common;
import com.example.demo.utils.Jwt;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("api/v1/users")
@Validated // 启用Spring方法级别的验证支持
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, S3Service s3Service) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
    }

    @PostMapping("/register")
    public ResponseEntity<Result<User>> register(@RequestBody User user) {
        // 先找出该用户是否在数据库中存在
        User u = userService.findByUsername(user.getUsername());
        if (u == null) {
            // 该用户不存在时，则注册
            // 如果用户输入的用户名和密码为空,则不注册
            if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Result.error("Invalid username or password!"));
            }
            // 这里还需要进行邮箱的格式正确性的检查，建议使用regular Expression
            if (!Common.isValidEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error("Invalid email format!"));
            }
            user.setRegisterDate(LocalDate.now());
            user.setAvatarUrl("https://my-mio.s3.us-east-1.amazonaws.com/Cat.JPG");
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(Result.success(registeredUser));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.error("This username has been taken!"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Result<String>> login(@RequestBody User loginUser) {
        User user = userService.findByUsername(loginUser.getUsername());
        if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = Jwt.generateToken(claims);
            return ResponseEntity.ok(Result.success(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error("Invalid username or password!"));
        }
    }

    @GetMapping("/showUsers")
    public ResponseEntity<Result<List<User>>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(Result.success(users));
    }

    @PostMapping("/uploadAvatar")
    public ResponseEntity<Result<String>> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.error("User not found!"));
        }
        User user = userOptional.get();
        try {
            String avatarUrl = s3Service.uploadFile(file);
            user.setAvatarUrl(avatarUrl);
            userService.save(user);
            return ResponseEntity.ok(Result.success("Avatar updated successfully!" + " " + s3Service.uploadFile(file)));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.error("Failed to upload avatar: " + e.getMessage()));
        }
    }

}
