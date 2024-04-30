package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Result;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Common;

import org.springframework.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/users")
@Validated // 启用Spring方法级别的验证支持
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Result<User>> register(@RequestBody User user) {
        // 先找出该用户是否在数据库中存在
        User u = userService.findByUsername(user.getUsername());
        if (u == null) {
            // 该用户不存在时，则注册
            // 如果用户输入的用户名和密码为空,则不注册
            if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Result.error("Invalid username or password!"));
            }
            // 这里还需要进行邮箱的格式正确性的检查，建议使用regular Expression
            if (!Common.isValidEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error("Invalid email format!"));
            }
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(Result.success(registeredUser));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.error("Thie username has been taken!"));
        }
    }

}
