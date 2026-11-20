package com.auca.taxdeclaration;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.taxobligation.TaxObligation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxDeclaration implements Identifiable, Validatable {

    private String declarationId;
    private TaxObligation obligation;
    private LocalDate filingDate;
    private BigDecimal taxableAmount;
    private BigDecimal declaredTax;
    private String status;

    public TaxDeclaration(
            String declarationId,
            TaxObligation obligation,
            LocalDate filingDate,
            BigDecimal taxableAmount,
            BigDecimal declaredTax,
            String status) {

        this.declarationId = declarationId;
        this.obligation = obligation;
        this.filingDate = filingDate;
        this.taxableAmount = taxableAmount;
        this.declaredTax = declaredTax;
        this.status = status;
    }

    @Override
    public String getId() {
        return declarationId;
    }

    public TaxObligation getObligation() {
        return obligation;
    }

    public LocalDate getFilingDate() {
        return filingDate;
    }

    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }

    public BigDecimal getDeclaredTax() {
        return declaredTax;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getDisplayName() {
        return "Tax Declaration - " + declarationId;
    }

    @Override
    public boolean isActive() {
        return "PENDING".equalsIgnoreCase(status)
                || "APPROVED".equalsIgnoreCase(status);
    }

    @Override
    public void validate() throws ValidationException {

        if (declarationId == null || declarationId.trim().isEmpty()) {
            throw new ValidationException(
                    "Declaration ID is required."
            );
        }

        if (obligation == null) {
            throw new ValidationException(
                    "A valid tax obligation must be selected."
            );
        }

        if (filingDate == null) {
            throw new ValidationException(
                    "Filing date is required."
            );
        }

        if (taxableAmount == null
                || taxableAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Taxable amount must be zero or greater."
            );
        }

        if (declaredTax == null
                || declaredTax.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Declared tax must be zero or greater."
            );
        }

        if (status == null || status.trim().isEmpty()) {
            throw new ValidationException(
                    "Declaration status is required."
            );
        }

        if (!status.equalsIgnoreCase("PENDING")
                && !status.equalsIgnoreCase("APPROVED")
                && !status.equalsIgnoreCase("REJECTED")) {
            throw new ValidationException(
                    "Invalid declaration status."
            );
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== TAX DECLARATION DETAILS =====");
        System.out.println("Declaration ID: " + declarationId);
        System.out.println("Obligation ID: " + obligation.getId());
        System.out.println("Taxpayer: "
                + obligation.getTaxpayer().getDisplayName());
        System.out.println("Tax Type: "
                + obligation.getTaxType().getTaxName());
        System.out.println("Filing Date: " + filingDate);
        System.out.println("Taxable Amount: " + taxableAmount);
        System.out.println("Declared Tax: " + declaredTax);
        System.out.println("Status: " + status);
    }

    @Override
    public String getSummary() {

        return "Declaration ID: " + declarationId
                + " | Taxable Amount: " + taxableAmount
                + " | Declared Tax: " + declaredTax
                + " | Status: " + status;
    }
}