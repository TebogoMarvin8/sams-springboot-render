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

    // Save a new user, without encoding password
    public void save(User user) {
        // Note: For simplicity, we are not encoding the password here
        userRepository.save(user);
    }

    // Check if the raw password matches the stored password
    public boolean checkPassword(String rawPassword, String storedPassword) {
        return rawPassword.equals(storedPassword); // Simple comparison, not secure
    }

    // Find a user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
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
