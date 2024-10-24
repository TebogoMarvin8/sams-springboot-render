package com.sams.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sams.model.ClassStudentId;
import com.sams.model.ClassStudentRelationship;
import com.sams.model.Clazz;
import com.sams.model.Student;
import com.sams.repository.ClassStudentRelationshipRepository;

@Service
public class ClassStudentRelationshipService {

    @Autowired
    private ClassStudentRelationshipRepository repository;

    // Get list of students by class ID
    public List<Student> getStudentsByClassId(Long classId) {
        return repository.findStudentsByClassId(classId);
    }

    // Get list of classes by student ID
    public List<Clazz> getClassesByStudentId(Long studentId) {
        return repository.findByStudent_Id(studentId);
    }

    // Add a student to a class
    public ClassStudentRelationship addStudentToClass(Long classId, Long studentId) {

        ClassStudentRelationship classStudentRelationship = new ClassStudentRelationship();

        // Create a new ClassStudentId instance with the appropriate IDs
        ClassStudentId classStudentId = new ClassStudentId(classId, studentId);

        // Set the embedded ID
        classStudentRelationship.setId(classStudentId);

        Clazz clazz = new Clazz();
        clazz.setId(classId);
        classStudentRelationship.setClazz(clazz);

        Student student = new Student();
        student.setId(studentId);
        classStudentRelationship.setStudent(student);

        classStudentRelationship.setClazz(clazz);
        classStudentRelationship.setStudent(student);

        return repository.save(classStudentRelationship);
    }

    // Remove a student from a class
    public void removeStudentFromClass(Long classId, Long studentId) {
        Optional<ClassStudentRelationship> relationship = repository.findByClazz_IdAndStudent_Id(classId, studentId);
        relationship.ifPresent(repository::delete);
    }
}
