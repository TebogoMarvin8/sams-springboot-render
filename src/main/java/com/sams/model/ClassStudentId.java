package com.sams.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ClassStudentId implements Serializable {
    private Long class_id; // Match the database column name
    private Long student_id; // Match the database column name

    public ClassStudentId() {}

    public ClassStudentId(Long classId, Long studentId) {
        this.class_id = classId;
        this.student_id = studentId;
    }

    // Getters and Setters

    public Long getClass_id() {
        return class_id;
    }

    public void setClass_id(Long classId) {
        this.class_id = classId;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long studentId) {
        this.student_id = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassStudentId)) return false;
        ClassStudentId that = (ClassStudentId) o;
        return Objects.equals(getClass_id(), that.getClass_id()) &&
               Objects.equals(getStudent_id(), that.getStudent_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass_id(), getStudent_id());
    }
}
