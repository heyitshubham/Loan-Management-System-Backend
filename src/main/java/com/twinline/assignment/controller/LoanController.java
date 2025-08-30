package com.twinline.assignment.controller;

import com.twinline.assignment.dto.ApiResponse;
import com.twinline.assignment.entity.LoanApplication;
import com.twinline.assignment.entity.User;
import com.twinline.assignment.service.LoanService;
import com.twinline.assignment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LoanController {

    private final LoanService loanService;
    private final UserService userService;

    @GetMapping("/my-applications")
    public ResponseEntity<ApiResponse<List<LoanApplication>>> getMyApplications(Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            List<LoanApplication> applications = loanService.getLoanApplicationsByUser(user);
            return ResponseEntity.ok(ApiResponse.success("Applications retrieved successfully", applications));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to retrieve applications"));
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<LoanApplication>>> getAllApplications() {
        try {
            List<LoanApplication> applications = loanService.getAllLoanApplications();
            return ResponseEntity.ok(ApiResponse.success("All applications retrieved successfully", applications));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to retrieve applications"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanApplication>> getApplicationById(@PathVariable Long id) {
        try {
            LoanApplication application = loanService.getLoanApplicationById(id);
            if (application == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ApiResponse.success("Application retrieved successfully", application));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to retrieve application"));
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<LoanApplication>> submitApplication(
            @Valid @RequestBody LoanApplication loanApplication,
            Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            LoanApplication savedApplication = loanService.submitLoanApplication(loanApplication, user);
            return ResponseEntity.ok(ApiResponse.success("Loan application submitted successfully", savedApplication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to submit application: " + e.getMessage()));
        }
    }
}