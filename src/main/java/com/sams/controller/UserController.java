package com.sams.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sams.model.Admin;
import com.sams.model.Instructor;
import com.sams.model.Student;
import com.sams.model.User;
import com.sams.service.AdminService;
import com.sams.service.InstructorService;
import com.sams.service.PasswordResetService;
import com.sams.service.StudentService;
import com.sams.service.UserService;
import com.sams.service.EmailService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/session-info")
    public ResponseEntity<Map<String, Object>> getSessionInfo(HttpSession session) {
        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("studentId", session.getAttribute("studentId"));
        sessionInfo.put("studentName", session.getAttribute("studentName"));
        sessionInfo.put("instructorId", session.getAttribute("instructorId"));
        sessionInfo.put("role", session.getAttribute("role"));
        return ResponseEntity.ok(sessionInfo);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> requestData) {
        String username = requestData.get("username");
        String password = requestData.get("password");
        String role = requestData.get("role");
        String firstName = requestData.get("firstName");
        String lastName = requestData.get("lastName");
        String email = requestData.get("email");

        // Check if the username already exists
        if (userService.findByUsername(username) != null) {
            return new ResponseEntity<>(Map.of("error", "Username already exists"), HttpStatus.BAD_REQUEST);
        }

        // Check if the email already exists
        if (userService.findByEmail(email) != null) {
            return new ResponseEntity<>(Map.of("error", "Email already exists"), HttpStatus.BAD_REQUEST);
        }
        // Create and save the User entity
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        userService.save(user);

        Map<String, Object> response = new HashMap<>();

        // Create role-specific entity and handle student-specific logic
        switch (role) {
            case "STUDENT":
                Student student = new Student();
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setEmail(email);
                student.setRfidTagId(null);
                student.setAttendances(null);
                student.setClasses(null);
                student.setUser(user); // Link user to student
                studentService.createStudent(student); // Save the student entity

                // Send registration email
                emailService.sendRegistrationEmail(student.getEmail(), student.getFirstName());

                // Add studentId to the response if the role is student
                response.put("message", "Registration successful");
                response.put("studentId", student.getId()); // Include studentId for frontend use
                response.put("role", "STUDENT"); // Include role for redirection
                break;

            case "INSTRUCTOR":
                Instructor instructor = new Instructor();
                instructor.setFirstName(firstName);
                instructor.setLastName(lastName);
                instructor.setEmail(email);
                instructor.setUser(user); // Link user to instructor
                instructorService.saveInstructor(instructor); // Save the instructor entity

                // Send registration email
                emailService.sendRegistrationEmail(instructor.getEmail(), instructor.getFirstName());

                // Add redirect for instructor
                response.put("message", "Registration successful. Redirecting to login.");
                response.put("redirectUrl", "/login.html"); // Redirect to login for instructors
                break;

            case "ADMIN":
                Admin admin = new Admin();
                admin.setFirstName(firstName);
                admin.setLastName(lastName);
                admin.setEmail(email);
                admin.setUser(user); // Link user to admin
                adminService.saveAdmin(admin); // Save the admin entity

                // Send registration email
                emailService.sendRegistrationEmail(admin.getEmail(), admin.getFirstName());

                // Add redirect for admin
                response.put("message", "Registration successful. Redirecting to login.");
                response.put("redirectUrl", "/login.html"); // Redirect to login for admins
                break;

            default:
                return new ResponseEntity<>(Map.of("error", "Invalid role"), HttpStatus.BAD_REQUEST);
        }

        // Return the response including redirection for instructors/admins or studentId
        // for students
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpSession session) {
        // Find the user by username
        User existingUser = userService.findByUsername(user.getUsername());

        // Prepare the response map
        Map<String, String> response = new HashMap<>();

        if (existingUser == null) {
            // Username does not exist
            response.put("error", "Username does not exist.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else if (!userService.checkPassword(user.getPassword(), existingUser.getPassword())) {
            // Password is incorrect
            response.put("error", "Incorrect password.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else {
            String role = existingUser.getRole();
            // Set common session attributes
            session.setAttribute("role", role);

            // Prepare the response with the redirect URL based on the user's role
            switch (role) {
                case "STUDENT":
                    // Store student information in session
                    session.setAttribute("studentId", existingUser.getStudent().getId());
                    session.setAttribute("studentName", existingUser.getStudent().getFirstName());
                    response.put("redirectUrl", "/student-dashboard.html");
                    break;

                case "INSTRUCTOR":
                    // Store instructor information in session
                    session.setAttribute("instructorId", existingUser.getInstructor().getId());
                    response.put("redirectUrl", "/instructor-dashboard.html");
                    break;

                case "ADMIN":
                    response.put("redirectUrl", "/admin-dashboard.html");
                    break;

                default:
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        passwordResetService.initiatePasswordReset(email);
        return ResponseEntity.ok("If the email exists in our system, a password reset link has been sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        boolean success = passwordResetService.resetPassword(token, newPassword);
        if (success) {
            return ResponseEntity.ok("Password has been reset successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());

        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
