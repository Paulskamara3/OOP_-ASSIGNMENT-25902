package com.auca.taxassessment;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxdeclaration.TaxDeclaration;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxAssessment implements Identifiable, Validatable {

    private String assessmentId;
    private TaxDeclaration declaration;
    private BigDecimal assessedTax;
    private BigDecimal adjustmentAmount;
    private LocalDate assessmentDate;
    private String status;

    public TaxAssessment(
            String assessmentId,
            TaxDeclaration declaration,
            BigDecimal assessedTax,
            BigDecimal adjustmentAmount,
            LocalDate assessmentDate,
            String status) {

        this.assessmentId = assessmentId;
        this.declaration = declaration;
        this.assessedTax = assessedTax;
        this.adjustmentAmount = adjustmentAmount;
        this.assessmentDate = assessmentDate;
        this.status = status;
    }

    @Override
    public String getId() {
        return assessmentId;
    }

    public TaxDeclaration getDeclaration() {
        return declaration;
    }

    public BigDecimal getAssessedTax() {
        return assessedTax;
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getDisplayName() {
        return "Tax Assessment - " + assessmentId;
    }

    @Override
    public boolean isActive() {
        return "DRAFT".equalsIgnoreCase(status)
                || "CONFIRMED".equalsIgnoreCase(status);
    }

    @Override
    public void validate() throws ValidationException {

        if (assessmentId == null || assessmentId.trim().isEmpty()) {
            throw new ValidationException(
                    "Assessment ID is required."
            );
        }

        if (declaration == null) {
            throw new ValidationException(
                    "A tax declaration must be selected."
            );
        }

        if ("REJECTED".equalsIgnoreCase(declaration.getStatus())) {
            throw new ValidationException(
                    "A rejected declaration cannot be assessed."
            );
        }

        if (assessedTax == null
                || assessedTax.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Assessed tax must be zero or greater."
            );
        }

        if (adjustmentAmount == null) {
            throw new ValidationException(
                    "Adjustment amount is required."
            );
        }

        if (assessedTax.add(adjustmentAmount)
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Total assessed tax cannot be negative."
            );
        }

        if (assessmentDate == null) {
            throw new ValidationException(
                    "Assessment date is required."
            );
        }

        if (status == null
                || (!status.equalsIgnoreCase("DRAFT")
                && !status.equalsIgnoreCase("CONFIRMED")
                && !status.equalsIgnoreCase("CANCELLED"))) {
            throw new ValidationException(
                    "Status must be DRAFT, CONFIRMED, or CANCELLED."
            );
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== TAX ASSESSMENT DETAILS =====");
        System.out.println("Assessment ID: " + assessmentId);
        System.out.println("Declaration ID: " + declaration.getId());
        System.out.println("Assessed Tax: " + assessedTax);
        System.out.println("Adjustment Amount: " + adjustmentAmount);
        System.out.println("Total Assessment: "
                + assessedTax.add(adjustmentAmount));
        System.out.println("Assessment Date: " + assessmentDate);
        System.out.println("Status: " + status);
    }

    @Override
    public String getSummary() {

        return "Assessment ID: " + assessmentId
                + " | Assessed Tax: " + assessedTax
                + " | Status: " + status;
    }
}