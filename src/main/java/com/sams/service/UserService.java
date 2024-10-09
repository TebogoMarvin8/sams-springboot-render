package com.sams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sams.model.User;
import com.sams.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Save a new user with a custom password transformation
    public void save(User user) {
        String transformedPassword = customPasswordTransform(user.getPassword());
        user.setPassword(transformedPassword);
        userRepository.save(user);
    }

    // Simple custom password transformation function
    private String customPasswordTransform(String password) {
        // A salt-like string and shift characters by 2
        String salt = "S@1t&^#*&@^#*^123863860hsuig"; // Custom "salt" added
        StringBuilder sb = new StringBuilder();

        // Combine the salt and password, shifting characters
        for (char c : (salt + password).toCharArray()) {
            sb.append((char) (c + 4)); // Shift characters by 2 positions in ASCII
        }

        return sb.toString();
    }

    // Check password by applying the same transformation
    public boolean checkPassword(String rawPassword, String storedPassword) {
        String transformedPassword = customPasswordTransform(rawPassword);
        return transformedPassword.equals(storedPassword);
    }

    // Find a user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email); // Assuming a JPA repository method
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
