package com.sams.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sams.model.Clazz;

public interface ClassRepository extends JpaRepository<Clazz, Long> {

    @Query("SELECT c FROM Clazz c JOIN c.students s WHERE s.id = :studentId")
    List<Clazz> findClassesByStudentId(@Param("studentId") Long studentId);

    public List<Clazz> findClassesByInstructorId(Long instructorId);

    @Query("SELECT c FROM Clazz c JOIN c.students s WHERE s.id = :studentId AND c.startTime <= :currentTime AND c.endTime >= :currentTime AND c.dayOfWeek = :currentDay")
    Clazz findActiveClassByStudentAndTime(@Param("studentId") Long studentId,
            @Param("currentTime") LocalTime currentTime, @Param("currentDay") String currentDay);

}
