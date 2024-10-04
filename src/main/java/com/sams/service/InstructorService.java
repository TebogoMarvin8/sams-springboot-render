package com.sams.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sams.model.Attendance;
import com.sams.model.Clazz;
import com.sams.model.Instructor;
import com.sams.model.Student;
import com.sams.repository.AttendanceRepository;
import com.sams.repository.ClassRepository;
import com.sams.repository.InstructorRepository;
import com.sams.repository.StudentRepository;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Optional<Instructor> getInstructorById(Long id) {
        return instructorRepository.findById(id);
    }

    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Clazz> getClassesByInstructorId(Long instructorId) {
        return classRepository.findClassesByInstructorId(instructorId);
    }

    public List<Attendance> getAttendanceByDate(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return attendanceRepository.findByDate(startOfDay, endOfDay);
    }

    public List<Attendance> getAttendanceByClassId(Long classId) {
        return attendanceRepository.findByClazzId(classId);
    }

    public List<Attendance> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }
}
