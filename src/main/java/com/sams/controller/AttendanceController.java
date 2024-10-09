package com.sams.controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sams.model.Attendance;
import com.sams.model.Clazz;
import com.sams.model.Student;
import com.sams.repository.AttendanceRepository;
import com.sams.repository.ClassRepository;
import com.sams.repository.StudentRepository;
import com.sams.service.AttendanceService;
import com.sams.service.EmailService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody Map<String, String> request) {
        // Get studentId and classId from the request
        String studentId = request.get("studentId");
        String classId = request.get("classId");

        // Find the student
        Optional<Student> studentOpt = studentRepository.findById(Long.parseLong(studentId));
        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        // Find the class
        Optional<Clazz> clazzOpt = classRepository.findById(Long.parseLong(classId));
        if (clazzOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Class not found");
        }

        // Create a new attendance record
        Student student = studentOpt.get();
        Clazz clazz = clazzOpt.get();
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setClazz(clazz);
        attendance.setAttendanceTime(LocalDateTime.now());

        // Save the attendance
        attendanceRepository.save(attendance);

        // Send attendance email
       emailService.sendAttendanceMarkedEmail(student.getEmail(), student.getFirstName(), clazz.getClassName());
    
        return ResponseEntity
                .ok("Attendance marked for " + student.getFirstName() + " in class " + clazz.getClassName());
    }

    // Attendance MARKING WITH RFIDTAG
    @PostMapping("/markRFID")
    public ResponseEntity<String> markAttendanceRFID(@RequestBody Map<String, String> request) {
        String cardUID = request.get("cardUID");

        // Fetch the student based on RFID tag
        Student student = studentRepository.findByRFIDCardUID(cardUID);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        // Get current time and day
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        DayOfWeek currentDay = now.getDayOfWeek();
        String dayOfWeekString = currentDay.name(); // Returns "MONDAY", "TUESDAY", etc. converted to String

        // Find the class based on the student's schedule and the current time
        Clazz currentClass = classRepository.findActiveClassByStudentAndTime(student.getId(), currentTime,dayOfWeekString);

        if (currentClass == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No active class for student at the current time");
        }

                
        // Mark attendance for this class
        attendanceService.markAttendance(student.getId(), currentClass.getId());


        // Send attendance email
       emailService.sendAttendanceMarkedEmail(student.getEmail(), student.getFirstName(), currentClass.getClassName());

        return ResponseEntity.ok("Attendance marked for " + student.getFirstName() + " in class " + currentClass.getClassName());
    }

}
