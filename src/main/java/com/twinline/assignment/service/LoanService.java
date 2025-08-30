package com.twinline.assignment.service;

import com.twinline.assignment.entity.ApprovalStep;
import com.twinline.assignment.entity.LoanApplication;
import com.twinline.assignment.entity.User;
import com.twinline.assignment.repository.ApprovalStepRepository;
import com.twinline.assignment.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private ApprovalStepRepository approvalStepRepository;

    public LoanApplication submitLoanApplication(LoanApplication loanApplication, User user) {
        loanApplication.setSubmittedBy(user);
        loanApplication.setSubmissionDate(LocalDateTime.now());
        loanApplication.setStatus("SUBMITTED");
        
        LoanApplication savedApplication = loanApplicationRepository.save(loanApplication);
        
        createApprovalSteps(savedApplication);
        
        return savedApplication;
    }

    private void createApprovalSteps(LoanApplication loanApplication) {
        String[] stepNames = {"Initial Review", "Document Verification", "Final Approval"};
        
        for (int i = 0; i < stepNames.length; i++) {
            ApprovalStep step = new ApprovalStep();
            step.setStepName(stepNames[i]);
            step.setStatus("PENDING");
            step.setStepOrder(i + 1);
            step.setLoanApplication(loanApplication);
            step.setCreatedDate(LocalDateTime.now());
            
            approvalStepRepository.save(step);
        }
    }

    public List<LoanApplication> getAllLoanApplications() {
        return loanApplicationRepository.findAllByOrderBySubmissionDateDesc();
    }

    public List<LoanApplication> getLoanApplicationsByUser(User user) {
        return loanApplicationRepository.findBySubmittedByOrderBySubmissionDateDesc(user);
    }

    public LoanApplication getLoanApplicationById(Long id) {
        return loanApplicationRepository.findById(id).orElse(null);
    }

    public List<ApprovalStep> getApprovalSteps(LoanApplication loanApplication) {
        return approvalStepRepository.findByLoanApplicationOrderByStepOrder(loanApplication);
    }

    public void updateApprovalStep(Long stepId, String status, String comments, User user) {
        ApprovalStep step = approvalStepRepository.findById(stepId).orElse(null);
        if (step != null) {
            step.setStatus(status);
            step.setComments(comments);
            step.setUpdatedDate(LocalDateTime.now());
            step.setUpdatedBy(user);
            
            approvalStepRepository.save(step);
            
            updateLoanApplicationStatus(step.getLoanApplication());
        }
    }

    private void updateLoanApplicationStatus(LoanApplication loanApplication) {
        List<ApprovalStep> steps = getApprovalSteps(loanApplication);
        
        boolean allApproved = true;
        boolean hasRejected = false;
        
        for (ApprovalStep step : steps) {
            if ("REJECTED".equals(step.getStatus())) {
                hasRejected = true;
                break;
            }
            if (!"APPROVED".equals(step.getStatus())) {
                allApproved = false;
            }
        }
        
        if (hasRejected) {
            loanApplication.setStatus("REJECTED");
        } else if (allApproved) {
            loanApplication.setStatus("APPROVED");
        } else {
            loanApplication.setStatus("IN_PROGRESS");
        }
        
        loanApplicationRepository.save(loanApplication);
    }
}