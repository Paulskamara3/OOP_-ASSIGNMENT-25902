package com.auca.taxclearance;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;
import com.auca.revenueofficer.RevenueOfficer;

import java.time.LocalDate;

public class TaxClearance implements Identifiable, Validatable {

    private String clearanceId;
    private RegisteredTaxpayer taxpayer;
    private LocalDate requestDate;
    private LocalDate expiryDate;
    private String status;
    private RevenueOfficer approvedBy;

    public TaxClearance(
            String clearanceId,
            RegisteredTaxpayer taxpayer,
            LocalDate requestDate,
            LocalDate expiryDate,
            String status,
            RevenueOfficer approvedBy) {

        this.clearanceId = clearanceId;
        this.taxpayer = taxpayer;
        this.requestDate = requestDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.approvedBy = approvedBy;
    }

    @Override
    public String getId() {
        return clearanceId;
    }

    public RegisteredTaxpayer getTaxpayer() {
        return taxpayer;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public RevenueOfficer getApprovedBy() {
        return approvedBy;
    }

    @Override
    public String getDisplayName() {
        return "Tax Clearance - " + clearanceId;
    }

    @Override
    public boolean isActive() {
        return "APPROVED".equalsIgnoreCase(status)
                && expiryDate != null
                && !expiryDate.isBefore(LocalDate.now());
    }

    @Override
    public void validate() throws ValidationException {

        if (clearanceId == null || clearanceId.trim().isEmpty()) {
            throw new ValidationException(
                    "Clearance ID is required."
            );
        }

        if (taxpayer == null) {
            throw new ValidationException(
                    "A registered taxpayer must be selected."
            );
        }

        if (!taxpayer.isActive()) {
            throw new ValidationException(
                    "Only active taxpayers can request tax clearance."
            );
        }

        if (requestDate == null) {
            throw new ValidationException(
                    "Request date is required."
            );
        }

        if (requestDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                    "Request date cannot be in the future."
            );
        }

        if (expiryDate == null) {
            throw new ValidationException(
                    "Expiry date is required."
            );
        }

        if (!expiryDate.isAfter(requestDate)) {
            throw new ValidationException(
                    "Expiry date must be after request date."
            );
        }

        if (status == null
                || (!status.equalsIgnoreCase("PENDING")
                && !status.equalsIgnoreCase("APPROVED")
                && !status.equalsIgnoreCase("REJECTED"))) {
            throw new ValidationException(
                    "Status must be PENDING, APPROVED, or REJECTED."
            );
        }

        if ("APPROVED".equalsIgnoreCase(status)) {

            if (approvedBy == null) {
                throw new ValidationException(
                        "An approving revenue officer is required."
                );
            }

            if (!approvedBy.isActive()) {
                throw new ValidationException(
                        "The approving revenue officer is not active."
                );
            }
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== TAX CLEARANCE DETAILS =====");
        System.out.println("Clearance ID: " + clearanceId);
        System.out.println("Taxpayer: " + taxpayer.getDisplayName());
        System.out.println("TIN: " + taxpayer.getTin());
        System.out.println("Request Date: " + requestDate);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("Status: " + status);

        System.out.println("Approved By: "
                + (approvedBy != null
                ? approvedBy.getDisplayName()
                : "Not yet approved"));
    }

    @Override
    public String getSummary() {

        return "Clearance ID: " + clearanceId
                + " | Taxpayer: " + taxpayer.getDisplayName()
                + " | Status: " + status
                + " | Expiry Date: " + expiryDate;
    }
}