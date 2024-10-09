package com.sams.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sams.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u " +
           "WHERE u.id IN (" +
           "SELECT s.user.id FROM Student s WHERE s.email = :email " +
           "UNION " +
           "SELECT i.user.id FROM Instructor i WHERE i.email = :email " +
           "UNION " +
           "SELECT a.user.id FROM Admin a WHERE a.email = :email)")
    User findByEmail(@Param("email") String email);

    User findByResetToken(String resetToken);
}