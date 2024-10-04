package com.sams.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sams.model.Attendance;
import com.sams.model.Clazz;
import com.sams.model.Student;
import com.sams.repository.StudentRepository;
import com.sams.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/name")
    public String getStudentName(@RequestParam Long userId) {
        Student student = studentService.getStudentByUserId(userId);
        if (student != null) {
            return student.getFirstName() + " " + student.getLastName();
        } else {
            return "Student not found";
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/saveFaceDescriptors")
    public ResponseEntity<String> saveFaceDescriptors(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        Optional<Student> studentOpt = studentRepository.findById(id);
    
        if (!studentOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    
        Student student = studentOpt.get();
        String faceDescriptor = requestBody.get("descriptor"); // Ensure key matches
    
        if (faceDescriptor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Face descriptor is missing");
        }
    
        student.setFaceDescriptors(faceDescriptor);
        studentRepository.save(student);
    
        return ResponseEntity.ok("Face descriptors saved successfully");
    }
    
    // Endpoint to get face descriptors for a specific student
    @GetMapping("/{id}/faceDescriptors")
    public ResponseEntity<String> getStudentFaceDescriptors(@PathVariable Long id) {
        Optional<Student> student = studentService.findStudentById(id);

        if (student.isPresent()) {
            String faceDescriptors = student.get().getFaceDescriptors();
            if (faceDescriptors != null) {
                return ResponseEntity.ok(faceDescriptors);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No face descriptors available for this student.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/classes")
    public List<Clazz> getClassesByStudentId(@PathVariable Long id) {
        return studentService.getClassesByStudentId(id);
    }

    @GetMapping("/{id}/attendance")
    public List<Attendance> getAttendanceRecordsByStudentId(@PathVariable Long id) {
        return studentService.getAttendanceRecordsByStudentId(id);
    }

}
