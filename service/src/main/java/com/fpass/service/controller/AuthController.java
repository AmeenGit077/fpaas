package com.fpass.service.controller;

import com.fpass.service.DAO.AuthDAO;
import com.fpass.service.DTO.UserDTO;
import com.fpass.service.repository.UserRepository;
import com.fpass.service.util.CorrelationIdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    private UserRepository userRepository;


    private final AuthDAO authDAO;

    @PostMapping("/register")
    public String register(@RequestBody UserDTO user) {

        String correlationId = CorrelationIdUtil.getCorrelationId();
        log.info("[{}] Register API called for username: {}", correlationId, user.getUsername());

        String result = authDAO.register(user);

        log.info("[{}] Register successful for username: {}", correlationId, user.getUsername());
        return result;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user) {
        String correlationId = CorrelationIdUtil.getCorrelationId();
        log.info("[{}] Login API called for username: {}", correlationId, user.getUsername());

        String token = authDAO.login(user);

        log.info("[{}] Login successful for username: {}", correlationId, user.getUsername());
        return token;
    }
}