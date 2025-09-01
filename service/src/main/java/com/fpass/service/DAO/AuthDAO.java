package com.fpass.service.DAO;

import com.fpass.service.DTO.UserDTO;
import com.fpass.service.Handler.AppException;
import com.fpass.service.entity.User;
import com.fpass.service.repository.UserRepository;
import com.fpass.service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthDAO {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserDAO userDAO;

    public String register(UserDTO userDTO) {

        if (userDAO.findByUsername(userDTO.getUsername()).isPresent()) {
            throw AppException.userAlreadyExists(userDTO.getUsername());
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDAO.save(user);
        return "User registered successfully!";
    }


    public String login(UserDTO userDTO) {
        User dbUser = userDAO.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> AppException.userNotFound(userDTO.getUsername()));

        if (!passwordEncoder.matches(userDTO.getPassword(), dbUser.getPassword())) {
            throw AppException.invalidCredentials();
        }

        return jwtUtil.generateToken(dbUser.getUsername());
    }
}
