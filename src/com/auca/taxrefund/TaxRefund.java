package com.auca.taxrefund;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxassessment.TaxAssessment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxRefund implements Identifiable, Validatable {

    private String refundId;
    private RegisteredTaxpayer taxpayer;
    private TaxAssessment assessment;
    private BigDecimal requestedAmount;
    private LocalDate requestDate;
    private String status;

    public TaxRefund(
            String refundId,
            RegisteredTaxpayer taxpayer,
            TaxAssessment assessment,
            BigDecimal requestedAmount,
            LocalDate requestDate,
            String status) {

        this.refundId = refundId;
        this.taxpayer = taxpayer;
        this.assessment = assessment;
        this.requestedAmount = requestedAmount;
        this.requestDate = requestDate;
        this.status = status;
    }

    @Override
    public String getId() {
        return refundId;
    }

    public RegisteredTaxpayer getTaxpayer() {
        return taxpayer;
    }

    public TaxAssessment getAssessment() {
        return assessment;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getDisplayName() {
        return "Tax Refund - " + refundId;
    }

    @Override
    public boolean isActive() {
        return "PENDING".equalsIgnoreCase(status)
                || "APPROVED".equalsIgnoreCase(status);
    }

    @Override
    public void validate() throws ValidationException {

        if (refundId == null || refundId.trim().isEmpty()) {
            throw new ValidationException("Refund ID is required.");
        }

        if (taxpayer == null) {
            throw new ValidationException(
                    "A registered taxpayer must be selected."
            );
        }

        if (!taxpayer.isActive()) {
            throw new ValidationException(
                    "The selected taxpayer is not active."
            );
        }

        if (assessment == null) {
            throw new ValidationException(
                    "A tax assessment must be selected."
            );
        }

        if (!"CONFIRMED".equalsIgnoreCase(assessment.getStatus())) {
            throw new ValidationException(
                    "Refund requires a confirmed tax assessment."
            );
        }

        if (assessment.getDeclaration() == null
                || assessment.getDeclaration().getObligation() == null
                || assessment.getDeclaration().getObligation()
                        .getTaxpayer() == null
                || !taxpayer.getTin().equals(
                        assessment.getDeclaration().getObligation()
                                .getTaxpayer().getTin())) {
            throw new ValidationException(
                    "The assessment does not belong to the selected taxpayer."
            );
        }

        if (requestedAmount == null
                || requestedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(
                    "Requested refund amount must be greater than zero."
            );
        }

        if (requestDate == null) {
            throw new ValidationException(
                    "Refund request date is required."
            );
        }

        if (requestDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                    "Refund request date cannot be in the future."
            );
        }

        if (status == null
                || (!status.equalsIgnoreCase("PENDING")
                && !status.equalsIgnoreCase("APPROVED")
                && !status.equalsIgnoreCase("REJECTED"))) {
            throw new ValidationException(
                    "Refund status must be PENDING, APPROVED, or REJECTED."
            );
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== TAX REFUND DETAILS =====");
        System.out.println("Refund ID: " + refundId);
        System.out.println("Taxpayer: " + taxpayer.getDisplayName());
        System.out.println("TIN: " + taxpayer.getTin());
        System.out.println("Assessment ID: " + assessment.getId());
        System.out.println("Requested Amount: " + requestedAmount);
        System.out.println("Request Date: " + requestDate);
        System.out.println("Status: " + status);
    }

    @Override
    public String getSummary() {

        return "Refund ID: " + refundId
                + " | Taxpayer: " + taxpayer.getDisplayName()
                + " | Amount: " + requestedAmount
                + " | Status: " + status;
    }
}
