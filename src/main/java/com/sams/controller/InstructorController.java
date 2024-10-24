package com.sams.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.sams.model.Instructor;
import com.sams.service.InstructorService;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public List<Instructor> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id) {
        Optional<Instructor> instructor = instructorService.getInstructorById(id);
        return instructor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        return instructorService.saveInstructor(instructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/classes")
    public ResponseEntity<?> getClassesByInstructorId(@PathVariable Long id) {
        try {
            List<Clazz> classes = instructorService.getClassesByInstructorId(id);
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching instructor classes: " + e.getMessage());
        }
    }

    @GetMapping("/attendance/date")
    public List<Attendance> getAttendanceByDate(
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        // Convert the date to ZonedDateTime in GMT+2
        ZonedDateTime startOfDay = date.atStartOfDay(ZoneId.of("GMT+2"));
        ZonedDateTime endOfDay = date.atTime(LocalTime.MAX).atZone(ZoneId.of("GMT+2")); // End of day in GMT+2

        // Convert ZonedDateTime to LocalDateTime
        LocalDateTime start = startOfDay.toLocalDateTime();
        LocalDateTime end = endOfDay.toLocalDateTime();

        // Fetch attendance records between start and end of the day in GMT+2
        return instructorService.getAttendanceByDate(start, end);
    }

    @GetMapping("/attendance/class/{classId}")
    public List<Attendance> getAttendanceByClassId(@PathVariable Long classId) {
        return instructorService.getAttendanceByClassId(classId);
    }

    @GetMapping("/attendance/student/{studentId}")
    public List<Attendance> getAttendanceByStudentId(@PathVariable Long studentId) {
        return instructorService.getAttendanceByStudentId(studentId);
    }
}
