package com.sams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sams.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
