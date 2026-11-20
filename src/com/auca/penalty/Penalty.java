package com.auca.penalty;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxobligation.TaxObligation;

import java.math.BigDecimal;

public class Penalty implements Identifiable, Validatable {

    private String penaltyId;
    private TaxObligation obligation;
    private String penaltyType;
    private BigDecimal baseAmount;
    private BigDecimal penaltyRate;
    private BigDecimal penaltyAmount;

    public Penalty(
            String penaltyId,
            TaxObligation obligation,
            String penaltyType,
            BigDecimal baseAmount,
            BigDecimal penaltyRate,
            BigDecimal penaltyAmount) {

        this.penaltyId = penaltyId;
        this.obligation = obligation;
        this.penaltyType = penaltyType;
        this.baseAmount = baseAmount;
        this.penaltyRate = penaltyRate;
        this.penaltyAmount = penaltyAmount;
    }

    @Override
    public String getId() {
        return penaltyId;
    }

    public TaxObligation getObligation() {
        return obligation;
    }

    public String getPenaltyType() {
        return penaltyType;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public BigDecimal getPenaltyRate() {
        return penaltyRate;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    @Override
    public String getDisplayName() {
        return "Penalty - " + penaltyId;
    }

    @Override
    public boolean isActive() {
        return obligation != null && obligation.isActive();
    }

    @Override
    public void validate() throws ValidationException {

        if (penaltyId == null || penaltyId.trim().isEmpty()) {
            throw new ValidationException("Penalty ID is required.");
        }

        if (obligation == null) {
            throw new ValidationException(
                    "A valid tax obligation must be selected."
            );
        }

        if (penaltyType == null || penaltyType.trim().isEmpty()) {
            throw new ValidationException(
                    "Penalty type is required."
            );
        }

        if (baseAmount == null
                || baseAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Base amount must be zero or greater."
            );
        }

        if (penaltyRate == null
                || penaltyRate.compareTo(BigDecimal.ZERO) < 0
                || penaltyRate.compareTo(new BigDecimal("100")) > 0) {
            throw new ValidationException(
                    "Penalty rate must be between 0 and 100."
            );
        }

        if (penaltyAmount == null
                || penaltyAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Penalty amount must be zero or greater."
            );
        }

        BigDecimal expectedAmount = baseAmount
                .multiply(penaltyRate)
                .divide(new BigDecimal("100"), 2,
                        java.math.RoundingMode.HALF_UP);

        if (penaltyAmount.compareTo(expectedAmount) != 0) {
            throw new ValidationException(
                    "Penalty amount does not match the calculated amount."
            );
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== PENALTY DETAILS =====");
        System.out.println("Penalty ID: " + penaltyId);
        System.out.println("Obligation ID: " + obligation.getId());
        System.out.println("Penalty Type: " + penaltyType);
        System.out.println("Base Amount: " + baseAmount);
        System.out.println("Penalty Rate: " + penaltyRate + "%");
        System.out.println("Penalty Amount: " + penaltyAmount);
    }

    @Override
    public String getSummary() {

        return "Penalty ID: " + penaltyId
                + " | Type: " + penaltyType
                + " | Amount: " + penaltyAmount;
    }
}