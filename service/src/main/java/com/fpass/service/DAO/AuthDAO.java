package com.fpass.service.DAO;

import com.fpass.service.DTO.UserDTO;
import com.fpass.service.entity.Users;
import com.fpass.service.exceptionHandler.AppException;
import com.fpass.service.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthDAO {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDAO userDAO;

    public String register(UserDTO userDTO) {

        if (userDAO.findByUsername(userDTO.getUsername()).isPresent()) {
            throw AppException.userAlreadyExists(userDTO.getUsername());
        }

        Users users = new Users();
        users.setUsername(userDTO.getUsername());
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDAO.save(users);
        return "User registered successfully!";
    }


    public String login(UserDTO userDTO) {
        Users dbUsers = userDAO.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> AppException.userNotFound(userDTO.getUsername()));

        if (!passwordEncoder.matches(userDTO.getPassword(), dbUsers.getPassword())) {
            throw AppException.invalidCredentials();
        }

        return jwtService.generateToken(dbUsers.getUsername());
    }
}
