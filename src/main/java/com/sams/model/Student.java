package com.sams.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    // Field to store face descriptors as JSON
    @Column(columnDefinition = "LONGTEXT")
    private String faceDescriptors;

    @Transient // This tells JPA not to persist this field
    private Long rfidTagId;

    //@JsonIgnore
    @OneToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(optional = true)
    @JoinColumn(name = "rfid_tag_id", referencedColumnName = "id")
    private RFIDTag rfidTag;

    @JsonIgnore
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Attendance> attendances;

    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private List<Clazz> classes;

       // No-argument constructor
       public Student() {
    }

    // Constructor with ID
    public Student(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRfidTagId() {
        return rfidTagId;
    }

    public String getFaceDescriptors() {
        return faceDescriptors;
    }

    public void setFaceDescriptors(String faceDescriptors) {
        this.faceDescriptors = faceDescriptors;
    }

    public void setRfidTagId(Long rfidTagId) {
        this.rfidTagId = rfidTagId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RFIDTag getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(RFIDTag rfidTag) {
        this.rfidTag = rfidTag;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public List<Clazz> getClasses() {
        return classes;
    }

    public void setClasses(List<Clazz> classes) {
        this.classes = classes;
    }

}
