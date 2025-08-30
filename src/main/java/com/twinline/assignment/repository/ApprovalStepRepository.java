package com.twinline.assignment.repository;

import com.twinline.assignment.entity.ApprovalStep;
import com.twinline.assignment.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApprovalStepRepository extends JpaRepository<ApprovalStep, Long> {
    List<ApprovalStep> findByLoanApplicationOrderByStepOrder(LoanApplication loanApplication);
    List<ApprovalStep> findByLoanApplicationAndStatus(LoanApplication loanApplication, String status);
}