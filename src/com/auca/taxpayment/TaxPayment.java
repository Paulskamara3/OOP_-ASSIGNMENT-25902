package com.auca.taxpayment;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxassessment.TaxAssessment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxPayment implements Identifiable, Validatable {

    private String paymentId;
    private TaxAssessment assessment;
    private String paymentReference;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;

    public TaxPayment(
            String paymentId,
            TaxAssessment assessment,
            String paymentReference,
            BigDecimal amount,
            LocalDate paymentDate,
            String paymentMethod) {

        this.paymentId = paymentId;
        this.assessment = assessment;
        this.paymentReference = paymentReference;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String getId() {
        return paymentId;
    }

    public TaxAssessment getAssessment() {
        return assessment;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String getDisplayName() {
        return "Tax Payment - " + paymentId;
    }

    @Override
    public boolean isActive() {
        return assessment != null
                && "CONFIRMED".equalsIgnoreCase(
                        assessment.getStatus()
                );
    }

    @Override
    public void validate() throws ValidationException {

        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new ValidationException(
                    "Payment ID is required."
            );
        }

        if (assessment == null) {
            throw new ValidationException(
                    "A tax assessment must be selected."
            );
        }

        if (!"CONFIRMED".equalsIgnoreCase(assessment.getStatus())) {
            throw new ValidationException(
                    "Payment can only be made against a confirmed assessment."
            );
        }

        if (paymentReference == null
                || paymentReference.trim().isEmpty()) {
            throw new ValidationException(
                    "Payment reference is required."
            );
        }

        if (amount == null
                || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(
                    "Payment amount must be greater than zero."
            );
        }

        if (paymentDate == null) {
            throw new ValidationException(
                    "Payment date is required."
            );
        }

        if (paymentDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                    "Payment date cannot be in the future."
            );
        }

        if (paymentMethod == null
                || paymentMethod.trim().isEmpty()) {
            throw new ValidationException(
                    "Payment method is required."
            );
        }

        if (!paymentMethod.equalsIgnoreCase("CASH")
                && !paymentMethod.equalsIgnoreCase("BANK TRANSFER")
                && !paymentMethod.equalsIgnoreCase("MOBILE MONEY")
                && !paymentMethod.equalsIgnoreCase("CARD")) {
            throw new ValidationException(
                    "Invalid payment method."
            );
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== TAX PAYMENT DETAILS =====");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Assessment ID: " + assessment.getId());
        System.out.println("Payment Reference: " + paymentReference);
        System.out.println("Amount: " + amount);
        System.out.println("Payment Date: " + paymentDate);
        System.out.println("Payment Method: " + paymentMethod);
    }

    @Override
    public String getSummary() {

        return "Payment ID: " + paymentId
                + " | Reference: " + paymentReference
                + " | Amount: " + amount
                + " | Method: " + paymentMethod;
    }
}