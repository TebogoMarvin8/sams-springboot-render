package com.sams.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sams.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    public List<Attendance> findByStudentId(Long studentId);

    @Query("SELECT a FROM Attendance a WHERE a.dateTime >= :startOfDay AND a.dateTime <= :endOfDay")
    List<Attendance> findByDate(@Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    public List<Attendance> findByClazzId(Long clazzId);
}
