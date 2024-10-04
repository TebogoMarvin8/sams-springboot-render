package com.sams.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sams.model.Admin;
import com.sams.model.Clazz;
import com.sams.model.Course;
import com.sams.model.Instructor;
import com.sams.model.RFIDTag;
import com.sams.model.Student;
import com.sams.repository.AdminRepository;
import com.sams.repository.ClassRepository;
import com.sams.repository.CourseRepository;
import com.sams.repository.InstructorRepository;
import com.sams.repository.RFIDTagRepository;
import com.sams.repository.StudentRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RFIDTagRepository rfidTagRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    public Student registerStudent(Student student) {
        return studentRepository.save(student);
    }

    public Instructor registerInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void assignInstructorToClass(Long instructorId, Long classId) {
        Clazz aClass = classRepository.findById(classId).orElseThrow();
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow();
        aClass.setInstructor(instructor);
        classRepository.save(aClass);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Course operations
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // Class operations
    public List<Clazz> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<Clazz> getClassById(Long id) {
        return classRepository.findById(id);
    }

    public Clazz createClass(Clazz aClass) {
        return classRepository.save(aClass);
    }

    public Clazz updateClass(Clazz aClass) {
        return classRepository.save(aClass);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public List<Clazz> getClassesByInstructorId(Long instructorId) {
        return classRepository.findClassesByInstructorId(instructorId);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public void deleteInstructor(Long instructorId) {
        instructorRepository.deleteById(instructorId);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public Instructor editInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public RFIDTag assignRFIDTagToStudent(Long studentId, Long rfidTagId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        RFIDTag rfidTag = rfidTagRepository.findById(rfidTagId).orElseThrow();
        student.setRfidTag(rfidTag);
        studentRepository.save(student);
        return rfidTag;
    }
}
