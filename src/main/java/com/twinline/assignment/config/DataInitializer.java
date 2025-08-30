package com.twinline.assignment.config;

import com.twinline.assignment.entity.User;
import com.twinline.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (!userService.existsByUsername("twinline")) {
            userService.createAdmin("twinline", "Admin@123", "Admin User", "admin@twinline.com");
            System.out.println("Twinline admin user created - Username: twinline, Password: Admin@123");
        }
        
        if (!userService.existsByUsername("shubham")) {
            userService.createUser("shubham", "Shubham@123", "Test User", "shubham@twinline.com");
            System.out.println("Shubham Test user created - Username: shubham, Password: Shubham@123");
        }

        if (!userService.existsByUsername("test")) {
            userService.createUser("test", "Test@123", "Test Test", "test@twinline.com");
            System.out.println("Test user created - Username: test, Password: Test@123");
        }
    }
}