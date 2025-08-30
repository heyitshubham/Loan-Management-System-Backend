package com.twinline.assignment.controller;

import com.twinline.assignment.dto.ApiResponse;
import com.twinline.assignment.entity.ApprovalStep;
import com.twinline.assignment.entity.LoanApplication;
import com.twinline.assignment.entity.User;
import com.twinline.assignment.service.LoanService;
import com.twinline.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/approval")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ApprovalController {

    private final LoanService loanService;
    private final UserService userService;

    @GetMapping("/{applicationId}/steps")
    public ResponseEntity<ApiResponse<List<ApprovalStep>>> getApprovalSteps(@PathVariable Long applicationId) {
        try {
            LoanApplication application = loanService.getLoanApplicationById(applicationId);
            if (application == null) {
                return ResponseEntity.notFound().build();
            }
            List<ApprovalStep> steps = loanService.getApprovalSteps(application);
            return ResponseEntity.ok(ApiResponse.success("Approval steps retrieved successfully", steps));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to retrieve approval steps"));
        }
    }

    @PostMapping("/update-step")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateApprovalStep(
            @RequestParam Long stepId,
            @RequestParam String status,
            @RequestParam String comments,
            Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            loanService.updateApprovalStep(stepId, status, comments, user);
            return ResponseEntity.ok(ApiResponse.success("Approval step updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to update approval step: " + e.getMessage()));
        }
    }

    @PostMapping("/step/{stepId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateStep(
            @PathVariable Long stepId,
            @RequestBody UpdateStepRequest request,
            Authentication authentication) {
        try {
            User user = userService.getUserByUsername(authentication.getName());
            loanService.updateApprovalStep(stepId, request.getStatus(), request.getComments(), user);
            return ResponseEntity.ok(ApiResponse.success("Approval step updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to update approval step: " + e.getMessage()));
        }
    }

    public static class UpdateStepRequest {
        private String status;
        private String comments;

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getComments() { return comments; }
        public void setComments(String comments) { this.comments = comments; }
    }
}