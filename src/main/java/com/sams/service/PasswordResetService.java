package com.sams.service;

import com.sams.model.User;
import com.sams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String resetToken = UUID.randomUUID().toString();
            user.setResetToken(resetToken);
            user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
            userRepository.save(user);

            if (user.getStudent() != null) {
                emailService.sendPasswordResetEmail(user.getStudent().getEmail(), user.getStudent().getFirstName(),resetToken);
            } else if (user.getInstructor() != null) {
                emailService.sendPasswordResetEmail(user.getInstructor().getEmail(),user.getInstructor().getFirstName(), resetToken);
            } else if (user.getAdmin() != null) {
                emailService.sendPasswordResetEmail(user.getAdmin().getEmail(), user.getAdmin().getFirstName(),resetToken);
            }
        }
    }

    public boolean resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token);
        if (user != null && user.getResetTokenExpiry().isAfter(LocalDateTime.now())) {
            user.setPassword(newPassword);
            user.setResetToken(null);
            user.setResetTokenExpiry(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}