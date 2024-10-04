package com.sams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sams.model.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
