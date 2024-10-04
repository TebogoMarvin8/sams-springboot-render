package com.sams.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sams.model.Attendance;
import com.sams.model.Clazz;
import com.sams.model.RFIDTag;
import com.sams.model.Student;
import com.sams.model.User;
import com.sams.repository.AttendanceRepository;
import com.sams.repository.ClassRepository;
import com.sams.repository.RFIDTagRepository;
import com.sams.repository.StudentRepository;
import com.sams.repository.UserRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RFIDTagRepository rfidTagRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        // Check if rfidTagId is provided
        if (student.getRfidTagId() != null) {
            RFIDTag rfidTag = rfidTagRepository.findById(student.getRfidTagId())
                    .orElseThrow(() -> new RuntimeException("RFIDTag not found"));
            student.setRfidTag(rfidTag);
        }

        // Save the student
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Clazz> getClassesByStudentId(Long studentId) {
        return classRepository.findClassesByStudentId(studentId);
    }

    public List<Attendance> getAttendanceRecordsByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public Attendance markAttendance(Long studentId, Long classId) {
        Attendance attendance = new Attendance();
        attendance.setStudent(studentRepository.findById(studentId).orElseThrow());
        attendance.setClazz(classRepository.findById(classId).orElseThrow());
        attendance.setAttendanceTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    public Student getStudentByUserId(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return studentRepository.findByUser(user);
    }
    
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }
}
