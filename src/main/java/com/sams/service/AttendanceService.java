package com.sams.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sams.model.Attendance;
import com.sams.model.Clazz;
import com.sams.model.Student;
import com.sams.repository.AttendanceRepository;
import com.sams.repository.ClassRepository;
import com.sams.repository.StudentRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    public List<Attendance> getAttendanceByDate(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return attendanceRepository.findByDate(startOfDay, endOfDay);
    }

    public List<Attendance> getAttendanceByClassId(Long classId) {
        return attendanceRepository.findByClazzId(classId);
    }

    public List<Attendance> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    // for rfid attendance
    public void markAttendance(Long studentId, Long classId) {
        // Fetch the student and class from the database
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Clazz clazz = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));

        // Create a new Attendance record
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setClazz(clazz);
        attendance.setAttendanceTime(LocalDateTime.now()); // Set the current time

        // Save the Attendance record to the database
        attendanceRepository.save(attendance);
    }
}
