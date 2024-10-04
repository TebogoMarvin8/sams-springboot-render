package com.sams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sams.model.ClassStudentRelationship;
import com.sams.model.Clazz;
import com.sams.model.Student;
import com.sams.service.ClassStudentRelationshipService;

@RestController
@RequestMapping("/api/class-student")
public class ClassStudentRelationshipController {
    @Autowired
    private ClassStudentRelationshipService service;

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Student>> getStudentsByClassId(@PathVariable Long classId) {
        return ResponseEntity.ok(service.getStudentsByClassId(classId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Clazz>> getClassesByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getClassesByStudentId(studentId));
    }

    @PostMapping("/{classId}/{studentId}")
    public ResponseEntity<ClassStudentRelationship> addStudentToClass(@PathVariable Long classId, @PathVariable Long studentId) {
        return ResponseEntity.ok(service.addStudentToClass(classId, studentId));
    }

    @DeleteMapping("/{classId}/{studentId}")
    public ResponseEntity<Void> removeStudentFromClass(@PathVariable Long classId, @PathVariable Long studentId) {
        service.removeStudentFromClass(classId, studentId);
        return ResponseEntity.noContent().build();
    }
}