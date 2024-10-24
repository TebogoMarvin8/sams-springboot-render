package com.sams;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SamsApplication extends SpringBootServletInitializer {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+2"));  // Setting Spring Boot TimeZone to GMT+2
    }

    public static void main(String[] args) {
        SpringApplication.run(SamsApplication.class, args);
    }
}