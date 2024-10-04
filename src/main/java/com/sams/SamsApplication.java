package com.sams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SamsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SamsApplication.class, args);
    }
}