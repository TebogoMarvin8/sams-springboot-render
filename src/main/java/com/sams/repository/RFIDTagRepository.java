package com.sams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sams.model.RFIDTag;

public interface RFIDTagRepository extends JpaRepository<RFIDTag, Long> {
    // Custom query to find RFIDTags not associated with any Student
    @Query("SELECT r FROM RFIDTag r WHERE r.student IS NULL")
    List<RFIDTag> findUnassignedRFIDTags();
}
