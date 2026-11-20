
package com.auca.corporatetaxpayer;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CorporateTaxpayer extends RegisteredTaxpayer {

    private String companyRegistrationNo;
    private String legalName;
    private LocalDate incorporationDate;
    private String businessSector;
    private String authorizedRepresentative;
    private BigDecimal annualTurnover;

    public CorporateTaxpayer(
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
            String companyRegistrationNo,
            String legalName,
            LocalDate incorporationDate,
            String businessSector,
            String authorizedRepresentative,
            BigDecimal annualTurnover) {

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

        this.companyRegistrationNo = companyRegistrationNo;
        this.legalName = legalName;
        this.incorporationDate = incorporationDate;
        this.businessSector = businessSector;
        this.authorizedRepresentative = authorizedRepresentative;
        this.annualTurnover = annualTurnover;
    }

    // Getters

    public String getCompanyRegistrationNo() {
        return companyRegistrationNo;
    }

    public String getLegalName() {
        return legalName;
    }

    public LocalDate getIncorporationDate() {
        return incorporationDate;
    }

    public String getBusinessSector() {
        return businessSector;
    }

    public String getAuthorizedRepresentative() {
        return authorizedRepresentative;
    }

    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    // Display name

    @Override
    public String getDisplayName() {
        return legalName;
    }

    // Validation

    @Override
    public void validate() throws ValidationException {

        if (getId() == null || getId().trim().isEmpty()) {
            throw new ValidationException("Party ID is required.");
        }

        if (companyRegistrationNo == null ||
                companyRegistrationNo.trim().isEmpty()) {
            throw new ValidationException(
                    "Company registration number is required."
            );
        }

        if (legalName == null || legalName.trim().isEmpty()) {
            throw new ValidationException(
                    "Company legal name is required."
            );
        }

        if (incorporationDate == null) {
            throw new ValidationException(
                    "Incorporation date is required."
            );
        }

        if (incorporationDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                    "Incorporation date cannot be in the future."
            );
        }

        if (businessSector == null || businessSector.trim().isEmpty()) {
            throw new ValidationException(
                    "Business sector is required."
            );
        }

        if (authorizedRepresentative == null ||
                authorizedRepresentative.trim().isEmpty()) {
            throw new ValidationException(
                    "Authorized representative is required."
            );
        }

        if (annualTurnover == null ||
                annualTurnover.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Annual turnover must be zero or greater."
            );
        }
    }

    // Print full details

    @Override
    public void printDetails() {

        System.out.println("\n========================================");
        System.out.println("       CORPORATE TAXPAYER DETAILS       ");
        System.out.println("========================================");

        System.out.println("Party ID: " + getId());
        System.out.println("Legal Name: " + legalName);
        System.out.println("Company Registration No: "
                + companyRegistrationNo);
        System.out.println("TIN: " + getTin());
        System.out.println("Incorporation Date: " + incorporationDate);
        System.out.println("Business Sector: " + businessSector);
        System.out.println("Authorized Representative: "
                + authorizedRepresentative);
        System.out.println("Annual Turnover: " + annualTurnover);

        System.out.println("----------------------------------------");

        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Taxpayer Status: " + getTaxpayerStatus());
        System.out.println("District: " + getDistrict());
        System.out.println("Sector: " + getSector());
        System.out.println("Tax Office: " + getTaxOffice());

        System.out.println("========================================");
    }

    // Summary

    @Override
    public String getSummary() {

        return "Corporate Taxpayer: " + legalName
                + " | TIN: " + getTin()
                + " | Registration No: " + companyRegistrationNo;
    }
}
