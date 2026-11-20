package com.auca.individualtaxpayer;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IndividualTaxpayer extends RegisteredTaxpayer {

    private String nationalId;
    private String occupation;
    private String businessName;
    private LocalDate businessStartDate;
    private BigDecimal annualTurnover;
    private boolean resident;

    public IndividualTaxpayer(
            String partyId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            String tin,
            LocalDate registrationDate,
            String taxpayerStatus,
            String district,
            String sector,
            String taxOffice,
            String nationalId,
            String occupation,
            String businessName,
            LocalDate businessStartDate,
            BigDecimal annualTurnover,
            boolean resident) {

        super(
                partyId,
                firstName,
                lastName,
                email,
                phoneNumber,
                dateOfBirth,
                tin,
                registrationDate,
                taxpayerStatus,
                district,
                sector,
                taxOffice
        );

        this.nationalId = nationalId;
        this.occupation = occupation;
        this.businessName = businessName;
        this.businessStartDate = businessStartDate;
        this.annualTurnover = annualTurnover;
        this.resident = resident;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getBusinessName() {
        return businessName;
    }

    public LocalDate getBusinessStartDate() {
        return businessStartDate;
    }

    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    public boolean isResident() {
        return resident;
    }

    @Override
    public void validate() throws ValidationException {

        if (getId() == null || getId().trim().isEmpty()) {
            throw new ValidationException("Party ID is required.");
        }

        if (nationalId == null || nationalId.trim().isEmpty()) {
            throw new ValidationException("National ID is required.");
        }

        if (occupation == null || occupation.trim().isEmpty()) {
            throw new ValidationException("Occupation is required.");
        }

        if (annualTurnover == null ||
                annualTurnover.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Annual turnover must be zero or greater."
            );
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== INDIVIDUAL TAXPAYER DETAILS =====");
        System.out.println("Party ID: " + getId());
        System.out.println("Full Name: " + getDisplayName());
        System.out.println("National ID: " + nationalId);
        System.out.println("TIN: " + getTin());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Occupation: " + occupation);
        System.out.println("Business Name: " + businessName);
        System.out.println("Business Start Date: " + businessStartDate);
        System.out.println("Annual Turnover: " + annualTurnover);
        System.out.println("Resident: " + (resident ? "Yes" : "No"));
        System.out.println("Taxpayer Status: " + getTaxpayerStatus());
        System.out.println("District: " + getDistrict());
        System.out.println("Sector: " + getSector());
        System.out.println("Tax Office: " + getTaxOffice());
    }

    @Override
    public String getSummary() {
        return "Individual Taxpayer: " + getDisplayName()
                + " | TIN: " + getTin()
                + " | National ID: " + nationalId;
    }
}