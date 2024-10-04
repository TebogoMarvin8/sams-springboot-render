package com.sams.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.sams.model.Clazz;
import com.sams.model.Course;
import com.sams.model.Instructor;
import com.sams.model.RFIDTag;
import com.sams.model.Student;
import com.sams.service.AdminService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.saveAdmin(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        return adminService.createCourse(course);
    }

    @PostMapping("/classes")
    public Clazz createClass(@RequestBody Clazz aClass) {
        return adminService.createClass(aClass);
    }

    @PutMapping("/classes/{classId}/instructors/{instructorId}")
    public ResponseEntity<Void> assignInstructorToClass(@PathVariable Long classId, @PathVariable Long instructorId) {
        adminService.assignInstructorToClass(instructorId, classId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return adminService.getAllCourses();
    }

    @GetMapping("/classes")
    public List<Clazz> getAllClasses() {
        return adminService.getAllClasses();
    }

    @GetMapping("/classes/instructors/{instructorId}")
    public List<Clazz> getClassesByInstructorId(@PathVariable Long instructorId) {
        return adminService.getClassesByInstructorId(instructorId);
    }

    @PutMapping("/students")
    public Student editStudent(@RequestBody Student student) {
        return adminService.editStudent(student);
    }

    @PutMapping("/instructors")
    public Instructor editInstructor(@RequestBody Instructor instructor) {
        return adminService.editInstructor(instructor);
    }

    @PutMapping("/students/{studentId}/rfid-tags/{rfidTagId}")
    public RFIDTag assignRFIDTagToStudent(@PathVariable Long studentId, @PathVariable Long rfidTagId) {
        return adminService.assignRFIDTagToStudent(studentId, rfidTagId);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = adminService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/courses")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        Course updatedCourse = adminService.updateCourse(course);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        adminService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<Clazz> getClassById(@PathVariable Long id) {
        Optional<Clazz> clazz = adminService.getClassById(id);
        return clazz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/classes")
    public ResponseEntity<Clazz> updateClass(@RequestBody Clazz aClass) {
        Clazz updatedClass = adminService.updateClass(aClass);
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/classes/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        adminService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

}
