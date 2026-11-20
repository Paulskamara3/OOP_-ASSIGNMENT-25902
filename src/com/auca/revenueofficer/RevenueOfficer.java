package com.auca.revenueofficer;

import com.auca.abstracts.TaxParty;
import com.auca.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueOfficer extends TaxParty {

    private String staffNumber;
    private String department;
    private String role;
    private LocalDate hireDate;
    private BigDecimal approvalLimit;
    private boolean active;

    public RevenueOfficer(
            String partyId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            String staffNumber,
            String department,
            String role,
            LocalDate hireDate,
            BigDecimal approvalLimit,
            boolean active) {

        super(
                partyId,
                firstName,
                lastName,
                email,
                phoneNumber,
                dateOfBirth
        );

        this.staffNumber = staffNumber;
        this.department = department;
        this.role = role;
        this.hireDate = hireDate;
        this.approvalLimit = approvalLimit;
        this.active = active;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public BigDecimal getApprovalLimit() {
        return approvalLimit;
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

        if (getId() == null || getId().trim().isEmpty()) {
            throw new ValidationException("Party ID is required.");
        }

        if (staffNumber == null || staffNumber.trim().isEmpty()) {
            throw new ValidationException("Staff number is required.");
        }

        if (department == null || department.trim().isEmpty()) {
            throw new ValidationException("Department is required.");
        }

        if (role == null || role.trim().isEmpty()) {
            throw new ValidationException("Officer role is required.");
        }

        if (hireDate == null) {
            throw new ValidationException("Hire date is required.");
        }

        if (approvalLimit == null ||
                approvalLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(
                    "Approval limit must be zero or greater."
            );
        }
    }

    @Override
    public void printDetails() {

        System.out.println("\n===== REVENUE OFFICER DETAILS =====");
        System.out.println("Party ID: " + getId());
        System.out.println("Full Name: " + getDisplayName());
        System.out.println("Staff Number: " + staffNumber);
        System.out.println("Department: " + department);
        System.out.println("Role: " + role);
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Hire Date: " + hireDate);
        System.out.println("Approval Limit: " + approvalLimit);
        System.out.println("Active: " + (active ? "Yes" : "No"));
    }

    @Override
    public String getSummary() {
        return "Revenue Officer: " + getDisplayName()
                + " | Staff Number: " + staffNumber
                + " | Department: " + department;
    }
}