package com.auca.abstracts;

import java.time.LocalDate;

public abstract class RegisteredTaxpayer extends TaxParty {

    private String tin;
    private LocalDate registrationDate;
    private String taxpayerStatus;
    private String district;
    private String sector;
    private String taxOffice;

    public RegisteredTaxpayer(
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
            String taxOffice) {

        super(partyId, firstName, lastName, email,
              phoneNumber, dateOfBirth);

        this.tin = tin;
        this.registrationDate = registrationDate;
        this.taxpayerStatus = taxpayerStatus;
        this.district = district;
        this.sector = sector;
        this.taxOffice = taxOffice;
    }

    public String getTin() {
        return tin;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getTaxpayerStatus() {
        return taxpayerStatus;
    }

    public String getDistrict() {
        return district;
    }

    public String getSector() {
        return sector;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(taxpayerStatus);
    }
}