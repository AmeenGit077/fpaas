package com.fpass.service.services;

import com.fpass.service.entity.User;
import com.fpass.service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }
}
