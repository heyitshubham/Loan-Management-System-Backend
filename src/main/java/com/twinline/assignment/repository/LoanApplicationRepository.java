package com.twinline.assignment.repository;

import com.twinline.assignment.entity.LoanApplication;
import com.twinline.assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findBySubmittedByOrderBySubmissionDateDesc(User submittedBy);
    List<LoanApplication> findAllByOrderBySubmissionDateDesc();
    List<LoanApplication> findByStatus(String status);
}