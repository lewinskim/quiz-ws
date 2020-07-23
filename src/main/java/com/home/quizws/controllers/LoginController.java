package com.home.quizws.controllers;

import com.home.quizws.security.MyPasswordEncoder;
import com.home.quizws.user.UserService;
import com.home.quizws.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class LoginController {

    private UserService service;
    private PasswordEncoder encoder;

    @Autowired
    public LoginController(UserService service, MyPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder.getEncoder();
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        log.info("Passed user is: {}", user);
        service.register(toEncodedUser(user));
    }

    @GetMapping("/api/register/all")
    public List<User> getAllUsers() {
        return service.getAll();
    }

    private User toEncodedUser(User notEncodedUser) {
        User user = new User();
        String encodedPassword = encoder.encode(notEncodedUser.getPassword());
        user.setPassword(encodedPassword);
        user.setEmail(notEncodedUser.getEmail());
        log.info("Encoded password is {}", encodedPassword);
        return user;
    }
}
