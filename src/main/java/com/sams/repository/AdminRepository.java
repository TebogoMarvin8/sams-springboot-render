package com.sams.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sams.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
