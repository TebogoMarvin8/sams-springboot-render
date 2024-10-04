package com.sams.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sams.model.Student;
import com.sams.model.User;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(Long userId);
    Optional<Student> findByFirstName(String firstName);
    Student findByUser(User user);
    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
    
    @Query(value = "SELECT s.* FROM student s JOIN rfidtag rt ON s.rfid_tag_id = rt.id WHERE rt.tag_number = :cardUID", nativeQuery = true)
    Student findByRFIDCardUID(@Param("cardUID") String cardUID);
}
