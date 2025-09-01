package com.fpass.service.DAO;

import com.fpass.service.entity.Users;
import com.fpass.service.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAO {

    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users save(Users users) {
        return userRepository.save(users);
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsernameNativeIgnoreCase(username);
    }
}
