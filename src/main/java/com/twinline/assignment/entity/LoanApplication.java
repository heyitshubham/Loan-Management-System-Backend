package com.twinline.assignment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "loan_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Applicant name is required")
    @Column(nullable = false)
    private String applicantName;
    
    @NotNull(message = "Loan amount is required")
    @Min(value = 1000, message = "Minimum loan amount is 1000")
    @Column(nullable = false)
    private Double loanAmount;
    
    @NotNull(message = "Tenure is required")
    @Min(value = 1, message = "Minimum tenure is 1 month")
    @Max(value = 360, message = "Maximum tenure is 360 months")
    @Column(nullable = false)
    private Integer tenure;
    
    @NotNull(message = "Income is required")
    @Min(value = 1, message = "Income must be positive")
    @Column(nullable = false)
    private Double income;
    
    @NotBlank(message = "Contact details are required")
    @Column(nullable = false)
    private String contactDetails;
    
    @Column(nullable = false)
    private String status = "SUBMITTED";
    
    @Column(nullable = false)
    private LocalDateTime submissionDate = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User submittedBy;
    
    @OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApprovalStep> approvalSteps;
}