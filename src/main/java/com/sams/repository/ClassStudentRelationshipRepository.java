package com.sams.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sams.model.ClassStudentRelationship;
import com.sams.model.Clazz;
import com.sams.model.Student;

@Repository
public interface ClassStudentRelationshipRepository extends JpaRepository<ClassStudentRelationship, Long> {

    @Query("SELECT csr.student FROM ClassStudentRelationship csr WHERE csr.clazz.id = :classId")
    List<Student> findStudentsByClassId(@Param("classId") Long classId);

    // Query to find classes by student_id using JPQL, JPQL operates on entities
    // rather than raw database tables
    @Query("SELECT csr.clazz FROM ClassStudentRelationship csr WHERE csr.student.id = :studentId")
    List<Clazz> findByStudent_Id(@Param("studentId") Long studentId);

    // Query to find by both class_id and student_id using JPQL, JPQL operates on
    // entities rather than raw database tables
    @Query("SELECT csr FROM ClassStudentRelationship csr WHERE csr.clazz.id = :classId AND csr.student.id = :studentId")
    Optional<ClassStudentRelationship> findByClazz_IdAndStudent_Id(@Param("classId") Long classId,
            @Param("studentId") Long studentId);
}
