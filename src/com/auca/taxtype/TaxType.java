package com.auca.taxtype;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;

import java.math.BigDecimal;

public class TaxType implements Identifiable, Validatable {

    private String taxTypeId;
    private String taxCode;
    private String taxName;
    private BigDecimal rate;
    private String filingFrequency;
    private boolean active;

    public TaxType(
            String taxTypeId,
            String taxCode,
            String taxName,
            BigDecimal rate,
            String filingFrequency,
            boolean active) {

        this.taxTypeId = taxTypeId;
        this.taxCode = taxCode;
        this.taxName = taxName;
        this.rate = rate;
        this.filingFrequency = filingFrequency;
        this.active = active;
    }

    @Override
    public String getId() {
        return taxTypeId;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getTaxName() {
        return taxName;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getFilingFrequency() {
        return filingFrequency;
    }

    @Override
    public String getDisplayName() {
        return taxName;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void validate() throws ValidationException {

        if (taxTypeId == null || taxTypeId.trim().isEmpty()) {
            throw new ValidationException("Tax type ID is required.");
        }

        if (taxCode == null || taxCode.trim().isEmpty()) {
            throw new ValidationException("Tax code is required.");
        }

        if (taxName == null || taxName.trim().isEmpty()) {
            throw new ValidationException("Tax name is required.");
        }

        if (rate == null ||
                rate.compareTo(BigDecimal.ZERO) < 0 ||
                rate.compareTo(new BigDecimal("100")) > 0) {
            throw new ValidationException(
                    "Tax rate must be between 0 and 100."
            );
        }

        if (filingFrequency == null ||
                filingFrequency.trim().isEmpty()) {
            throw new ValidationException(
                    "Filing frequency is required."
            );
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== TAX TYPE DETAILS =====");
        System.out.println("Tax Type ID: " + taxTypeId);
        System.out.println("Tax Code: " + taxCode);
        System.out.println("Tax Name: " + taxName);
        System.out.println("Rate: " + rate + "%");
        System.out.println("Filing Frequency: " + filingFrequency);
        System.out.println("Active: " + (active ? "Yes" : "No"));
    }

    @Override
    public String getSummary() {
        return "Tax Type: " + taxName
                + " | Code: " + taxCode
                + " | Rate: " + rate + "%";
    }
}