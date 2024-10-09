package com.sams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String name) {
        String subject = "Registration Successful!";
        String message = String.format("Hello %s,\n\nYour registration was successful! You can now log in and start using the SAMS system.\n\nBest regards,\nSAMS Team", name);

        sendEmail(to, subject, message);
    }

    public void sendAttendanceMarkedEmail(String to, String name, String className) {
        String subject = "Attendance Marked Successfully!";
        String message = String.format("Hello %s,\n\nYour attendance for the class '%s' has been successfully marked.\n\nBest regards,\nSAMS Team", name, className);

        sendEmail(to, subject, message);
    }

    public void sendPasswordResetEmail(String to, String name, String resetToken) {
        String subject = "Password Reset Request";
        String resetLink = "http://localhost:8081/reset-password.html?token=" + resetToken;
        //String resetLink = "https://sams-springboot-render.onrender.com/reset-password.html?token=" + resetToken;
        String message = String.format("Hello %s,\n\nWe received a request to reset your password. If you did not make this request, please ignore this email.\n\nTo reset your password, please click on the following link:\n%s\n\nThis link will expire in 1 hour.\n\nBest regards,\nSAMS Team", name, resetLink);

        sendEmail(to, subject, message);
    }

    private void sendEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}