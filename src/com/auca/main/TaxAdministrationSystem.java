
package com.auca.main;

import com.auca.individualtaxpayer.IndividualTaxpayer;
import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.corporatetaxpayer.CorporateTaxpayer;
import com.auca.revenueofficer.RevenueOfficer;
import com.auca.taxtype.TaxType;
import com.auca.taxobligation.TaxObligation;
import com.auca.taxdeclaration.TaxDeclaration;
import com.auca.taxassessment.TaxAssessment;
import com.auca.taxpayment.TaxPayment;
import com.auca.penalty.Penalty;
import com.auca.taxrefund.TaxRefund;
import com.auca.taxclearance.TaxClearance;

import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Scanner;

public class TaxAdministrationSystem {

    private static final int MAX_RECORDS = 100;

    private final Scanner scanner;

    // ==========================================
    // DOMAIN RECORD ARRAYS
    // ==========================================

    private final IndividualTaxpayer[] individualTaxpayers =
            new IndividualTaxpayer[MAX_RECORDS];

    private final CorporateTaxpayer[] corporateTaxpayers =
            new CorporateTaxpayer[MAX_RECORDS];

    private final RevenueOfficer[] revenueOfficers =
            new RevenueOfficer[MAX_RECORDS];

    private final TaxType[] taxTypes =
            new TaxType[MAX_RECORDS];

    private final TaxObligation[] taxObligations =
            new TaxObligation[MAX_RECORDS];

    private final TaxDeclaration[] taxDeclarations =
            new TaxDeclaration[MAX_RECORDS];

    private final TaxAssessment[] taxAssessments =
            new TaxAssessment[MAX_RECORDS];

    private final TaxPayment[] taxPayments =
            new TaxPayment[MAX_RECORDS];

    private final Penalty[] penalties =
            new Penalty[MAX_RECORDS];

    private final TaxRefund[] taxRefunds =
            new TaxRefund[MAX_RECORDS];

    private final TaxClearance[] taxClearances =
            new TaxClearance[MAX_RECORDS];

    // ==========================================
    // RECORD COUNTERS
    // ==========================================

    private int individualTaxpayerCount;
    private int corporateTaxpayerCount;
    private int revenueOfficerCount;
    private int taxTypeCount;
    private int taxObligationCount;
    private int taxDeclarationCount;
    private int taxAssessmentCount;
    private int taxPaymentCount;
    private int penaltyCount;
    private int taxRefundCount;
    private int taxClearanceCount;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public TaxAdministrationSystem(Scanner scanner) {
        this.scanner = Objects.requireNonNull(
                scanner,
                "Scanner cannot be null."
        );
    }

    // ==========================================
    // INDIVIDUAL TAXPAYER REGISTRATION
    // ==========================================

    public void registerIndividualTaxpayer(
            IndividualTaxpayer taxpayer)
            throws ValidationException, DuplicateRecordException {

        if (taxpayer == null) {
            throw new ValidationException(
                    "Individual taxpayer cannot be null."
            );
        }

        taxpayer.validate();

        if (individualTaxpayerCount >= MAX_RECORDS) {
            throw new ValidationException(
                    "Individual taxpayer storage is full."
            );
        }

        for (int i = 0; i < individualTaxpayerCount; i++) {

            IndividualTaxpayer existing = individualTaxpayers[i];

            if (sameText(existing.getId(), taxpayer.getId())) {
                throw new DuplicateRecordException(
                        "Individual taxpayer Party ID already exists."
                );
            }

            if (sameText(existing.getTin(), taxpayer.getTin())) {
                throw new DuplicateRecordException(
                        "Taxpayer TIN already exists."
                );
            }

            if (sameText(
                    existing.getNationalId(),
                    taxpayer.getNationalId())) {

                throw new DuplicateRecordException(
                        "National ID already exists."
                );
            }
        }

        for (int i = 0; i < corporateTaxpayerCount; i++) {

            if (sameText(
                    corporateTaxpayers[i].getTin(),
                    taxpayer.getTin())) {

                throw new DuplicateRecordException(
                        "TIN already belongs to another taxpayer."
                );
            }

            if (sameText(
                    corporateTaxpayers[i].getId(),
                    taxpayer.getId())) {

                throw new DuplicateRecordException(
                        "Party ID already belongs to another taxpayer."
                );
            }
        }

        for (int i = 0; i < revenueOfficerCount; i++) {

            if (sameText(
                    revenueOfficers[i].getId(),
                    taxpayer.getId())) {

                throw new DuplicateRecordException(
                        "Party ID already belongs to a revenue officer."
                );
            }
        }

        individualTaxpayers[individualTaxpayerCount] = taxpayer;
        individualTaxpayerCount++;

        System.out.println(
                "\nIndividual taxpayer registered successfully."
        );
    }

    // ==========================================
    // INDIVIDUAL TAXPAYER REGISTRATION FORM
    // ==========================================

    public void registerIndividualTaxpayerForm() {

        System.out.println("\n======================================");
        System.out.println("   INDIVIDUAL TAXPAYER REGISTRATION");
        System.out.println("======================================");
        System.out.println("Type CANCEL or EXIT at any prompt to cancel.");

        try {

            String partyId = readRequiredText("Enter Party ID: ");
            String firstName = readRequiredText("Enter First Name: ");
            String lastName = readRequiredText("Enter Last Name: ");
            String email = readEmail("Enter Email Address: ");
            String phoneNumber = readRequiredText("Enter Phone Number: ");

            LocalDate dateOfBirth =
                    readDate("Enter Date of Birth (YYYY-MM-DD): ");

            String tin = readRequiredText("Enter TIN: ");

            LocalDate registrationDate =
                    readDate("Enter Registration Date (YYYY-MM-DD): ");

            String taxpayerStatus =
                    readTaxpayerStatus(
                            "Enter Taxpayer Status (ACTIVE/INACTIVE): "
                    );

            String district = readRequiredText("Enter District: ");
            String sector = readRequiredText("Enter Sector: ");
            String taxOffice = readRequiredText("Enter Tax Office: ");
            String nationalId = readRequiredText("Enter National ID: ");
            String occupation = readRequiredText("Enter Occupation: ");

            String businessName =
                    readOptionalText(
                            "Enter Business Name (or leave blank): "
                    );

            LocalDate businessStartDate = null;

            if (!businessName.isEmpty()) {
                businessStartDate =
                        readDate(
                                "Enter Business Start Date (YYYY-MM-DD): "
                        );
            }

            BigDecimal annualTurnover =
                    readMoney("Enter Annual Turnover: ");

            boolean resident =
                    readYesNo("Is the taxpayer a resident? (Y/N): ");

            IndividualTaxpayer taxpayer = new IndividualTaxpayer(
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
                    taxOffice,
                    nationalId,
                    occupation,
                    businessName,
                    businessStartDate,
                    annualTurnover,
                    resident
            );

            registerIndividualTaxpayer(taxpayer);

            taxpayer.printDetails();

        } catch (FormCancelledException e) {

            System.out.println(
                    "\nRegistration cancelled. No record was saved."
            );

        } catch (ValidationException | DuplicateRecordException e) {

            System.out.println(
                    "\nRegistration failed: " + e.getMessage()
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "\nRegistration failed: " + e.getMessage()
            );
        }
    }

    // ==========================================
    // CORPORATE TAXPAYER REGISTRATION
    // ==========================================

    public void registerCorporateTaxpayer(
            CorporateTaxpayer taxpayer)
            throws ValidationException, DuplicateRecordException {

        if (taxpayer == null) {
            throw new ValidationException(
                    "Corporate taxpayer cannot be null."
            );
        }

        taxpayer.validate();

        if (corporateTaxpayerCount >= MAX_RECORDS) {
            throw new ValidationException(
                    "Corporate taxpayer storage is full."
            );
        }

        for (int i = 0; i < corporateTaxpayerCount; i++) {

            CorporateTaxpayer existing = corporateTaxpayers[i];

            if (sameText(existing.getId(), taxpayer.getId())) {
                throw new DuplicateRecordException(
                        "Corporate taxpayer Party ID already exists."
                );
            }

            if (sameText(existing.getTin(), taxpayer.getTin())) {
                throw new DuplicateRecordException(
                        "Corporate taxpayer TIN already exists."
                );
            }

            if (sameText(
                    existing.getCompanyRegistrationNo(),
                    taxpayer.getCompanyRegistrationNo())) {

                throw new DuplicateRecordException(
                        "Company registration number already exists."
                );
            }
        }

        for (int i = 0; i < individualTaxpayerCount; i++) {

            IndividualTaxpayer existing = individualTaxpayers[i];

            if (sameText(existing.getId(), taxpayer.getId())) {
                throw new DuplicateRecordException(
                        "Party ID already belongs to another taxpayer."
                );
            }

            if (sameText(existing.getTin(), taxpayer.getTin())) {
                throw new DuplicateRecordException(
                        "TIN already belongs to another taxpayer."
                );
            }
        }

        for (int i = 0; i < revenueOfficerCount; i++) {

            if (sameText(
                    revenueOfficers[i].getId(),
                    taxpayer.getId())) {

                throw new DuplicateRecordException(
                        "Party ID already belongs to a revenue officer."
                );
            }
        }

        corporateTaxpayers[corporateTaxpayerCount] = taxpayer;
        corporateTaxpayerCount++;

        System.out.println(
                "\nCorporate taxpayer registered successfully."
        );
    }

    // ==========================================
    // CORPORATE TAXPAYER REGISTRATION FORM
    // ==========================================

    public void registerCorporateTaxpayerForm() {

        System.out.println("\n======================================");
        System.out.println("    CORPORATE TAXPAYER REGISTRATION");
        System.out.println("======================================");
        System.out.println("Type CANCEL or EXIT at any prompt to cancel.");

        try {

            String partyId = readRequiredText("Enter Party ID: ");
            String legalName = readRequiredText("Enter Company Legal Name: ");

            String firstName =
                    readRequiredText("Enter Representative First Name: ");

            String lastName =
                    readRequiredText("Enter Representative Last Name: ");

            String authorizedRepresentative = firstName + " " + lastName;

            String email = readEmail("Enter Company Email Address: ");
            String phoneNumber = readRequiredText("Enter Company Phone Number: ");
            String tin = readRequiredText("Enter Company TIN: ");

            LocalDate registrationDate =
                    readDate("Enter Tax Registration Date (YYYY-MM-DD): ");

            String taxpayerStatus =
                    readTaxpayerStatus(
                            "Enter Taxpayer Status (ACTIVE/INACTIVE): "
                    );

            String district = readRequiredText("Enter District: ");
            String sector = readRequiredText("Enter Location Sector: ");
            String taxOffice = readRequiredText("Enter Tax Office: ");

            String companyRegistrationNo =
                    readRequiredText("Enter Company Registration Number: ");

            LocalDate incorporationDate =
                    readDate("Enter Incorporation Date (YYYY-MM-DD): ");

            String businessSector =
                    readRequiredText("Enter Business Sector/Industry: ");

            BigDecimal annualTurnover =
                    readMoney("Enter Annual Turnover: ");

            CorporateTaxpayer taxpayer = new CorporateTaxpayer(
                    partyId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    null,
                    tin,
                    registrationDate,
                    taxpayerStatus,
                    district,
                    sector,
                    taxOffice,
                    companyRegistrationNo,
                    legalName,
                    incorporationDate,
                    businessSector,
                    authorizedRepresentative,
                    annualTurnover
            );

            registerCorporateTaxpayer(taxpayer);
            taxpayer.printDetails();

        } catch (FormCancelledException e) {

            System.out.println(
                    "\nCorporate registration cancelled. No record was saved."
            );

        } catch (ValidationException | DuplicateRecordException e) {

            System.out.println(
                    "\nCorporate registration failed: " + e.getMessage()
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "\nCorporate registration failed: " + e.getMessage()
            );
        }
    }

    // ==========================================
    // REVENUE OFFICER REGISTRATION
    // ==========================================

    public void registerRevenueOfficer(RevenueOfficer officer)
            throws ValidationException, DuplicateRecordException {

        if (officer == null) {
            throw new ValidationException(
                    "Revenue officer cannot be null."
            );
        }

        officer.validate();

        if (revenueOfficerCount >= MAX_RECORDS) {
            throw new ValidationException(
                    "Revenue officer storage is full."
            );
        }

        for (int i = 0; i < revenueOfficerCount; i++) {

            RevenueOfficer existing = revenueOfficers[i];

            if (sameText(existing.getId(), officer.getId())) {
                throw new DuplicateRecordException(
                        "Revenue officer Party ID already exists."
                );
            }

            if (sameText(
                    existing.getStaffNumber(),
                    officer.getStaffNumber())) {

                throw new DuplicateRecordException(
                        "Staff number already exists."
                );
            }
        }

        for (int i = 0; i < individualTaxpayerCount; i++) {

            if (sameText(
                    individualTaxpayers[i].getId(),
                    officer.getId())) {

                throw new DuplicateRecordException(
                        "Party ID already belongs to an individual taxpayer."
                );
            }
        }

        for (int i = 0; i < corporateTaxpayerCount; i++) {

            if (sameText(
                    corporateTaxpayers[i].getId(),
                    officer.getId())) {

                throw new DuplicateRecordException(
                        "Party ID already belongs to a corporate taxpayer."
                );
            }
        }

        revenueOfficers[revenueOfficerCount] = officer;
        revenueOfficerCount++;

        System.out.println(
                "\nRevenue officer registered successfully."
        );
    }

    // ==========================================
    // REVENUE OFFICER REGISTRATION FORM
    // ==========================================

    public void registerRevenueOfficerForm() {

        System.out.println("\n======================================");
        System.out.println("       REVENUE OFFICER REGISTRATION");
        System.out.println("======================================");
        System.out.println("Type CANCEL or EXIT at any prompt to cancel.");

        try {

            String partyId = readRequiredText("Enter Party ID: ");
            String firstName = readRequiredText("Enter First Name: ");
            String lastName = readRequiredText("Enter Last Name: ");
            String email = readEmail("Enter Email Address: ");
            String phoneNumber = readRequiredText("Enter Phone Number: ");

            LocalDate dateOfBirth =
                    readDate("Enter Date of Birth (YYYY-MM-DD): ");

            String staffNumber = readRequiredText("Enter Staff Number: ");
            String department = readRequiredText("Enter Department: ");
            String role = readRequiredText("Enter Officer Role: ");

            LocalDate hireDate =
                    readDate("Enter Hire Date (YYYY-MM-DD): ");

            BigDecimal approvalLimit =
                    readMoney("Enter Approval Limit: ");

            boolean active =
                    readYesNo("Is the officer active? (Y/N): ");

            RevenueOfficer officer = new RevenueOfficer(
                    partyId,
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    staffNumber,
                    department,
                    role,
                    hireDate,
                    approvalLimit,
                    active
            );

            registerRevenueOfficer(officer);
            officer.printDetails();

        } catch (FormCancelledException e) {

            System.out.println(
                    "\nRevenue officer registration cancelled. "
                            + "No record was saved."
            );

        } catch (ValidationException | DuplicateRecordException e) {

            System.out.println(
                    "\nRevenue officer registration failed: "
                            + e.getMessage()
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "\nRevenue officer registration failed: "
                            + e.getMessage()
            );
        }
    }

    // ==========================================
    // DISPLAY INDIVIDUAL TAXPAYERS
    // ==========================================

    public void displayIndividualTaxpayers() {

        if (individualTaxpayerCount == 0) {
            System.out.println(
                    "\nNo individual taxpayers registered yet."
            );
            return;
        }

        System.out.println("\n==============================================");
        System.out.println("       REGISTERED INDIVIDUAL TAXPAYERS");
        System.out.println("==============================================");

        for (int i = 0; i < individualTaxpayerCount; i++) {

            System.out.println("\nRecord " + (i + 1));
            individualTaxpayers[i].printDetails();

            System.out.println("----------------------------------------------");
        }

        System.out.println(
                "\nTotal Individual Taxpayers: " + individualTaxpayerCount
        );
    }

    // ==========================================
    // DISPLAY CORPORATE TAXPAYERS
    // ==========================================

    public void displayCorporateTaxpayers() {

        if (corporateTaxpayerCount == 0) {
            System.out.println(
                    "\nNo corporate taxpayers registered yet."
            );
            return;
        }

        System.out.println("\n==============================================");
        System.out.println("        REGISTERED CORPORATE TAXPAYERS");
        System.out.println("==============================================");

        for (int i = 0; i < corporateTaxpayerCount; i++) {

            System.out.println("\nRecord " + (i + 1));
            corporateTaxpayers[i].printDetails();

            System.out.println("----------------------------------------------");
        }

        System.out.println(
                "\nTotal Corporate Taxpayers: " + corporateTaxpayerCount
        );
    }

    // ==========================================
    // DISPLAY REVENUE OFFICERS
    // ==========================================

    public void displayRevenueOfficers() {

        if (revenueOfficerCount == 0) {
            System.out.println(
                    "\nNo revenue officers registered yet."
            );
            return;
        }

        System.out.println("\n==============================================");
        System.out.println("          REGISTERED REVENUE OFFICERS");
        System.out.println("==============================================");

        for (int i = 0; i < revenueOfficerCount; i++) {

            System.out.println("\nRecord " + (i + 1));
            revenueOfficers[i].printDetails();

            System.out.println("----------------------------------------------");
        }

        System.out.println(
                "\nTotal Revenue Officers: " + revenueOfficerCount
        );
    }

    // ==========================================
    // TAX TYPE REGISTRATION
    // ==========================================

    public void registerTaxTypeForm() {
        System.out.println("\n===== TAX TYPE REGISTRATION =====");
        System.out.println("Type CANCEL or EXIT at any prompt to cancel.");
        try {
            String id = readRequiredText("Enter Tax Type ID: ");
            String code = readRequiredText("Enter Tax Code: ");
            String name = readRequiredText("Enter Tax Name: ");
            BigDecimal rate = readPercentage("Enter Tax Rate (%): ");
            String frequency = readRequiredText("Enter Filing Frequency (MONTHLY/QUARTERLY/ANNUALLY): ");
            boolean active = readYesNo("Is this tax type active? (Y/N): ");
            TaxType type = new TaxType(id, code, name, rate, frequency.toUpperCase(), active);
            registerTaxType(type);
            type.printDetails();
        } catch (FormCancelledException e) {
            System.out.println("Tax type registration cancelled. No record was saved.");
        } catch (ValidationException | DuplicateRecordException e) {
            System.out.println("Tax type registration failed: " + e.getMessage());
        }
    }

    public void registerTaxType(TaxType type)
            throws ValidationException, DuplicateRecordException {
        if (type == null) throw new ValidationException("Tax type cannot be null.");
        type.validate();
        if (taxTypeCount >= MAX_RECORDS) throw new ValidationException("Tax type storage is full.");
        for (int i = 0; i < taxTypeCount; i++) {
            if (sameText(taxTypes[i].getId(), type.getId()))
                throw new DuplicateRecordException("Tax type ID already exists.");
            if (sameText(taxTypes[i].getTaxCode(), type.getTaxCode()))
                throw new DuplicateRecordException("Tax code already exists.");
            if (sameText(taxTypes[i].getTaxName(), type.getTaxName()))
                throw new DuplicateRecordException("Tax name already exists.");
        }
        taxTypes[taxTypeCount++] = type;
        System.out.println("Tax type registered successfully.");
    }

    // ==========================================
    // TAX OBLIGATION CREATION
    // ==========================================

    public void createTaxObligationForm() {
        System.out.println("\n===== CREATE TAX OBLIGATION =====");
        try {
            if (getRegisteredTaxpayerCount() == 0) throw new ValidationException("Register an individual or corporate taxpayer first.");
            if (taxTypeCount == 0) throw new ValidationException("Register a tax type first.");
            displayTaxpayerChoices();
            RegisteredTaxpayer taxpayer = findTaxpayerByTin(readRequiredText("Enter taxpayer TIN: "));
            if (taxpayer == null) throw new ValidationException("No taxpayer found with that TIN.");
            displayTaxTypes();
            TaxType type = findTaxType(readRequiredText("Enter Tax Type ID or Code: "));
            if (type == null) throw new ValidationException("Tax type not found.");
            String id = readRequiredText("Enter Obligation ID: ");
            LocalDate start = readDate("Enter Tax Period Start (YYYY-MM-DD): ");
            LocalDate end = readDate("Enter Tax Period End (YYYY-MM-DD): ");
            LocalDate due = readDate("Enter Payment Due Date (YYYY-MM-DD): ");
            if (start.isAfter(end)) throw new ValidationException("Period start cannot be after period end.");
            TaxObligation obligation = new TaxObligation(id, taxpayer, type, start, end, due);
            registerTaxObligation(obligation);
            obligation.printDetails();
        } catch (FormCancelledException e) {
            System.out.println("Obligation creation cancelled. No record was saved.");
        } catch (ValidationException | DuplicateRecordException e) {
            System.out.println("Obligation creation failed: " + e.getMessage());
        }
    }

    public void registerTaxObligation(TaxObligation obligation)
            throws ValidationException, DuplicateRecordException {
        if (obligation == null) throw new ValidationException("Tax obligation cannot be null.");
        obligation.validate();
        if (taxObligationCount >= MAX_RECORDS) throw new ValidationException("Tax obligation storage is full.");
        for (int i = 0; i < taxObligationCount; i++)
            if (sameText(taxObligations[i].getId(), obligation.getId()))
                throw new DuplicateRecordException("Obligation ID already exists.");
        taxObligations[taxObligationCount++] = obligation;
        System.out.println("Tax obligation created successfully.");
    }

    // ==========================================
    // TAX DECLARATION FILING
    // ==========================================

    public void fileTaxDeclarationForm() {
        System.out.println("\n===== FILE TAX DECLARATION =====");
        try {
            if (taxObligationCount == 0) throw new ValidationException("Create a tax obligation first.");
            displayTaxObligations();
            TaxObligation obligation = findTaxObligation(readRequiredText("Enter Obligation ID: "));
            if (obligation == null) throw new ValidationException("Tax obligation not found.");
            String id = readRequiredText("Enter Declaration ID: ");
            LocalDate filingDate = readDate("Enter Filing Date (YYYY-MM-DD): ");
            BigDecimal taxableAmount = readMoney("Enter Taxable Amount: ");
            BigDecimal declaredTax = taxableAmount.multiply(obligation.getTaxType().getRate())
                    .divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_UP);
            TaxDeclaration declaration = new TaxDeclaration(id, obligation, filingDate, taxableAmount, declaredTax, "PENDING");
            registerTaxDeclaration(declaration);
            System.out.println("Tax calculated automatically using the registered tax rate.");
            declaration.printDetails();
        } catch (FormCancelledException e) {
            System.out.println("Declaration filing cancelled. No record was saved.");
        } catch (ValidationException | DuplicateRecordException e) {
            System.out.println("Declaration filing failed: " + e.getMessage());
        }
    }

    public void registerTaxDeclaration(TaxDeclaration declaration)
            throws ValidationException, DuplicateRecordException {
        if (declaration == null) throw new ValidationException("Tax declaration cannot be null.");
        declaration.validate();
        if (taxDeclarationCount >= MAX_RECORDS) throw new ValidationException("Declaration storage is full.");
        for (int i = 0; i < taxDeclarationCount; i++)
            if (sameText(taxDeclarations[i].getId(), declaration.getId()))
                throw new DuplicateRecordException("Declaration ID already exists.");
        taxDeclarations[taxDeclarationCount++] = declaration;
        System.out.println("Tax declaration filed successfully.");
    }

    // ==========================================
    // TAX ASSESSMENT
    // ==========================================

    public void assessTaxDeclarationForm() {
        System.out.println("\n===== ASSESS TAX DECLARATION =====");
        try {
            if (taxDeclarationCount == 0) throw new ValidationException("File a tax declaration first.");
            displayTaxDeclarations();
            TaxDeclaration declaration = findTaxDeclaration(readRequiredText("Enter Declaration ID: "));
            if (declaration == null) throw new ValidationException("Declaration not found.");
            for (int i = 0; i < taxAssessmentCount; i++)
                if (taxAssessments[i].getDeclaration() == declaration)
                    throw new ValidationException("This declaration has already been assessed.");
            String id = readRequiredText("Enter Assessment ID: ");
            BigDecimal adjustment = readSignedMoney("Enter Adjustment Amount (use negative for reduction): ");
            String status = readYesNo("Confirm this assessment now? (Y/N): ") ? "CONFIRMED" : "DRAFT";
            TaxAssessment assessment = new TaxAssessment(id, declaration, declaration.getDeclaredTax(), adjustment, LocalDate.now(), status);
            registerTaxAssessment(assessment);
            assessment.printDetails();
        } catch (FormCancelledException e) {
            System.out.println("Assessment cancelled. No record was saved.");
        } catch (ValidationException | DuplicateRecordException e) {
            System.out.println("Assessment failed: " + e.getMessage());
        }
    }

    public void registerTaxAssessment(TaxAssessment assessment)
            throws ValidationException, DuplicateRecordException {
        if (assessment == null) throw new ValidationException("Tax assessment cannot be null.");
        assessment.validate();
        if (taxAssessmentCount >= MAX_RECORDS) throw new ValidationException("Assessment storage is full.");
        for (int i = 0; i < taxAssessmentCount; i++)
            if (sameText(taxAssessments[i].getId(), assessment.getId()))
                throw new DuplicateRecordException("Assessment ID already exists.");
        taxAssessments[taxAssessmentCount++] = assessment;
        System.out.println("Tax assessment recorded successfully.");
    }

    // ==========================================
    // TAX PAYMENT
    // ==========================================

    public void recordTaxPaymentForm() {
        System.out.println("\n===== RECORD TAX PAYMENT =====");
        try {
            if (taxAssessmentCount == 0) throw new ValidationException("Create a tax assessment first.");
            displayTaxAssessments();
            TaxAssessment assessment = findTaxAssessment(readRequiredText("Enter Assessment ID: "));
            if (assessment == null) throw new ValidationException("Assessment not found.");
            if (!"CONFIRMED".equalsIgnoreCase(assessment.getStatus())) throw new ValidationException("Only confirmed assessments can receive payments.");
            BigDecimal outstanding = getAssessmentOutstanding(assessment);
            if (outstanding.compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException("This assessment has no outstanding balance.");
            System.out.println("Outstanding balance: " + outstanding);
            String id = readRequiredText("Enter Payment ID: ");
            String reference = readRequiredText("Enter Payment Reference: ");
            BigDecimal amount = readMoney("Enter Payment Amount: ");
            if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException("Payment must be greater than zero.");
            if (amount.compareTo(outstanding) > 0) throw new ValidationException("Payment exceeds outstanding balance. Maximum allowed: " + outstanding);
            LocalDate date = readDate("Enter Payment Date (YYYY-MM-DD): ");
            String method = readRequiredText("Enter Payment Method (CASH/BANK TRANSFER/MOBILE MONEY/CARD): ").toUpperCase();
            TaxPayment payment = new TaxPayment(id, assessment, reference, amount, date, method);
            registerTaxPayment(payment);
            payment.printDetails();
            System.out.println("Remaining balance: " + getAssessmentOutstanding(assessment));
        } catch (FormCancelledException e) {
            System.out.println("Payment recording cancelled. No record was saved.");
        } catch (ValidationException | DuplicateRecordException e) {
            System.out.println("Payment recording failed: " + e.getMessage());
        }
    }

    public void registerTaxPayment(TaxPayment payment)
            throws ValidationException, DuplicateRecordException {
        if (payment == null) throw new ValidationException("Tax payment cannot be null.");
        payment.validate();
        if (taxPaymentCount >= MAX_RECORDS) throw new ValidationException("Payment storage is full.");
        for (int i = 0; i < taxPaymentCount; i++) {
            if (sameText(taxPayments[i].getId(), payment.getId())) throw new DuplicateRecordException("Payment ID already exists.");
            if (sameText(taxPayments[i].getPaymentReference(), payment.getPaymentReference())) throw new DuplicateRecordException("Payment reference already exists.");
        }
        BigDecimal outstanding = getAssessmentOutstanding(payment.getAssessment());
        if (payment.getAmount().compareTo(outstanding) > 0) throw new ValidationException("Payment exceeds the assessment outstanding balance.");
        taxPayments[taxPaymentCount++] = payment;
        System.out.println("Tax payment recorded successfully.");
    }

    // ==========================================
    // PENALTY GENERATION
    // ==========================================

    public void generatePenaltyForm() {
        System.out.println("\n===== GENERATE PENALTY =====");
        try {
            if (taxObligationCount == 0) throw new ValidationException("No tax obligations are available.");
            displayTaxObligations();
            TaxObligation obligation = findTaxObligation(readRequiredText("Enter Obligation ID: "));
            if (obligation == null) throw new ValidationException("Obligation not found.");
            if (!obligation.getDueDate().isBefore(LocalDate.now())) throw new ValidationException("Penalty can only be generated after the obligation due date.");
            BigDecimal outstanding = getObligationOutstanding(obligation);
            if (outstanding.compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException("No unpaid assessed tax remains for this obligation.");
            System.out.println("Outstanding tax amount: " + outstanding);
            String id = readRequiredText("Enter Penalty ID: ");
            String type = readRequiredText("Enter Penalty Type (LATE FILING/LATE PAYMENT/OTHER): ");
            BigDecimal rate = readPercentage("Enter Penalty Rate (%): ");
            BigDecimal amount = outstanding.multiply(rate).divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_UP);
            Penalty penalty = new Penalty(id, obligation, type.toUpperCase(), outstanding, rate, amount);
            registerPenalty(penalty);
            penalty.printDetails();
        } catch (FormCancelledException e) {
            System.out.println("Penalty generation cancelled. No record was saved.");
        } catch (ValidationException | DuplicateRecordException e) {
            System.out.println("Penalty generation failed: " + e.getMessage());
        }
    }

    public void registerPenalty(Penalty penalty)
            throws ValidationException, DuplicateRecordException {
        if (penalty == null) throw new ValidationException("Penalty cannot be null.");
        penalty.validate();
        if (penaltyCount >= MAX_RECORDS) throw new ValidationException("Penalty storage is full.");
        for (int i = 0; i < penaltyCount; i++)
            if (sameText(penalties[i].getId(), penalty.getId())) throw new DuplicateRecordException("Penalty ID already exists.");
        penalties[penaltyCount++] = penalty;
        System.out.println("Penalty generated successfully.");
    }

    // ==========================================
    // TAX REFUND REQUEST
    // ==========================================

    public void requestTaxRefundForm() {
        System.out.println("\n===== REQUEST TAX REFUND =====");
        try {
            if (taxAssessmentCount == 0) throw new ValidationException("No tax assessments are available.");
            displayTaxpayerChoices();
            RegisteredTaxpayer taxpayer = findTaxpayerByTin(readRequiredText("Enter taxpayer TIN: "));
            if (taxpayer == null) throw new ValidationException("Taxpayer not found.");
            displayTaxAssessments();
            TaxAssessment assessment = findTaxAssessment(readRequiredText("Enter Assessment ID: "));
            if (assessment == null) throw new ValidationException("Assessment not found.");
            String id = readRequiredText("Enter Refund ID: ");
            BigDecimal amount = readMoney("Enter Requested Refund Amount: ");
            LocalDate date = readDate("Enter Request Date (YYYY-MM-DD): ");
            TaxRefund refund = new TaxRefund(id, taxpayer, assessment, amount, date, "PENDING");
            refund.validate();
            if (taxRefundCount >= MAX_RECORDS) throw new ValidationException("Refund storage is full.");
            for (int i = 0; i < taxRefundCount; i++) if (sameText(taxRefunds[i].getId(), id)) throw new DuplicateRecordException("Refund ID already exists.");
            taxRefunds[taxRefundCount++] = refund;
            System.out.println("Tax refund request submitted successfully. Status: PENDING");
            refund.printDetails();
        } catch (FormCancelledException e) {
            System.out.println("Refund request cancelled. No record was saved.");
        } catch (ValidationException | DuplicateRecordException e) {
            System.out.println("Refund request failed: " + e.getMessage());
        }
    }

    // ==========================================
    // TAX CLEARANCE REQUEST
    // ==========================================

    public void requestTaxClearanceForm() {
        System.out.println("\n===== REQUEST TAX CLEARANCE =====");
        try {
            if (getRegisteredTaxpayerCount() == 0) throw new ValidationException("No taxpayers are registered.");
            displayTaxpayerChoices();
            RegisteredTaxpayer taxpayer = findTaxpayerByTin(readRequiredText("Enter taxpayer TIN: "));
            if (taxpayer == null) throw new ValidationException("Taxpayer not found.");
            BigDecimal outstanding = getTaxpayerOutstanding(taxpayer);
            if (outstanding.compareTo(BigDecimal.ZERO) > 0) throw new ValidationException("Clearance cannot be issued. Outstanding tax and penalty balance: " + outstanding);
            String id = readRequiredText("Enter Clearance ID: ");
            LocalDate requestDate = readDate("Enter Request Date (YYYY-MM-DD): ");
            LocalDate expiryDate = readDate("Enter Expiry Date (YYYY-MM-DD): ");
            boolean approve = readYesNo("Approve this clearance now? (Y/N): ");
            RevenueOfficer approver = null;
            String status = "PENDING";
            if (approve) {
                if (revenueOfficerCount == 0) throw new ValidationException("Register a revenue officer before approving clearances.");
                displayRevenueOfficers();
                approver = findRevenueOfficer(readRequiredText("Enter approving officer Party ID or Staff Number: "));
                if (approver == null) throw new ValidationException("Revenue officer not found.");
                if (!approver.isActive()) throw new ValidationException("Only active revenue officers can approve clearances.");
                if (approver.getApprovalLimit().compareTo(BigDecimal.ZERO) < 0) throw new ValidationException("Officer approval limit is invalid.");
                status = "APPROVED";
            }
            TaxClearance clearance = new TaxClearance(id, taxpayer, requestDate, expiryDate, status, approver);
            clearance.validate();
            if (taxClearanceCount >= MAX_RECORDS) throw new ValidationException("Clearance storage is full.");
            for (int i = 0; i < taxClearanceCount; i++) if (sameText(taxClearances[i].getId(), id)) throw new DuplicateRecordException("Clearance ID already exists.");
            taxClearances[taxClearanceCount++] = clearance;
            System.out.println("Tax clearance request recorded successfully. Status: " + status);
            clearance.printDetails();
        } catch (FormCancelledException e) {
            System.out.println("Clearance request cancelled. No record was saved.");
        } catch (ValidationException | DuplicateRecordException e) {
            System.out.println("Clearance request failed: " + e.getMessage());
        }
    }

    // ==========================================
    // DISPLAY ALL REMAINING RECORD TYPES
    // ==========================================

    public void displayTaxTypes() { displayRecords("REGISTERED TAX TYPES", taxTypes, taxTypeCount); }
    public void displayTaxObligations() { displayRecords("TAX OBLIGATIONS", taxObligations, taxObligationCount); }
    public void displayTaxDeclarations() { displayRecords("TAX DECLARATIONS", taxDeclarations, taxDeclarationCount); }
    public void displayTaxAssessments() { displayRecords("TAX ASSESSMENTS", taxAssessments, taxAssessmentCount); }
    public void displayTaxPayments() { displayRecords("TAX PAYMENTS", taxPayments, taxPaymentCount); }
    public void displayPenalties() { displayRecords("TAX PENALTIES", penalties, penaltyCount); }

    public void displayRefundsAndClearances() {
        System.out.println("\n===== TAX REFUNDS =====");
        if (taxRefundCount == 0) System.out.println("No refund requests recorded.");
        for (int i = 0; i < taxRefundCount; i++) { taxRefunds[i].printDetails(); System.out.println("----------------------------------------"); }
        System.out.println("\n===== TAX CLEARANCES =====");
        if (taxClearanceCount == 0) System.out.println("No tax clearance requests recorded.");
        for (int i = 0; i < taxClearanceCount; i++) { taxClearances[i].printDetails(); System.out.println("----------------------------------------"); }
    }

    private void displayRecords(String title, Object[] records, int count) {
        System.out.println("\n========================================");
        System.out.println("       " + title);
        System.out.println("========================================");
        if (count == 0) { System.out.println("No records available."); return; }
        for (int i = 0; i < count; i++) {
            Object record = records[i];
            if (record instanceof TaxType) ((TaxType) record).printDetails();
            else if (record instanceof TaxObligation) ((TaxObligation) record).printDetails();
            else if (record instanceof TaxDeclaration) ((TaxDeclaration) record).printDetails();
            else if (record instanceof TaxAssessment) ((TaxAssessment) record).printDetails();
            else if (record instanceof TaxPayment) ((TaxPayment) record).printDetails();
            else if (record instanceof Penalty) ((Penalty) record).printDetails();
            System.out.println("----------------------------------------");
        }
        System.out.println("Total records: " + count);
    }

    // ==========================================
    // LOOKUP AND FINANCIAL HELPER METHODS
    // ==========================================

    private int getRegisteredTaxpayerCount() { return individualTaxpayerCount + corporateTaxpayerCount; }

    private void displayTaxpayerChoices() {
        System.out.println("\n--- INDIVIDUAL TAXPAYERS ---");
        for (int i = 0; i < individualTaxpayerCount; i++) System.out.println(individualTaxpayers[i].getTin() + " | " + individualTaxpayers[i].getDisplayName());
        System.out.println("--- CORPORATE TAXPAYERS ---");
        for (int i = 0; i < corporateTaxpayerCount; i++) System.out.println(corporateTaxpayers[i].getTin() + " | " + corporateTaxpayers[i].getDisplayName());
    }

    private RegisteredTaxpayer findTaxpayerByTin(String tin) {
        for (int i = 0; i < individualTaxpayerCount; i++) if (sameText(individualTaxpayers[i].getTin(), tin)) return individualTaxpayers[i];
        for (int i = 0; i < corporateTaxpayerCount; i++) if (sameText(corporateTaxpayers[i].getTin(), tin)) return corporateTaxpayers[i];
        return null;
    }

    private TaxType findTaxType(String key) {
        for (int i = 0; i < taxTypeCount; i++) if (sameText(taxTypes[i].getId(), key) || sameText(taxTypes[i].getTaxCode(), key)) return taxTypes[i];
        return null;
    }

    private TaxObligation findTaxObligation(String id) {
        for (int i = 0; i < taxObligationCount; i++) if (sameText(taxObligations[i].getId(), id)) return taxObligations[i];
        return null;
    }

    private TaxDeclaration findTaxDeclaration(String id) {
        for (int i = 0; i < taxDeclarationCount; i++) if (sameText(taxDeclarations[i].getId(), id)) return taxDeclarations[i];
        return null;
    }

    private TaxAssessment findTaxAssessment(String id) {
        for (int i = 0; i < taxAssessmentCount; i++) if (sameText(taxAssessments[i].getId(), id)) return taxAssessments[i];
        return null;
    }

    private RevenueOfficer findRevenueOfficer(String key) {
        for (int i = 0; i < revenueOfficerCount; i++) if (sameText(revenueOfficers[i].getId(), key) || sameText(revenueOfficers[i].getStaffNumber(), key)) return revenueOfficers[i];
        return null;
    }

    private BigDecimal getAssessmentOutstanding(TaxAssessment assessment) {
        BigDecimal total = assessment.getAssessedTax().add(assessment.getAdjustmentAmount());
        BigDecimal paid = BigDecimal.ZERO;
        for (int i = 0; i < taxPaymentCount; i++) if (taxPayments[i].getAssessment() == assessment) paid = paid.add(taxPayments[i].getAmount());
        BigDecimal result = total.subtract(paid);
        return result.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : result;
    }

    private BigDecimal getObligationOutstanding(TaxObligation obligation) {
        BigDecimal balance = BigDecimal.ZERO;
        for (int i = 0; i < taxAssessmentCount; i++) {
            TaxAssessment assessment = taxAssessments[i];
            if (assessment.getDeclaration().getObligation() == obligation && "CONFIRMED".equalsIgnoreCase(assessment.getStatus())) balance = balance.add(getAssessmentOutstanding(assessment));
        }
        return balance;
    }

    private BigDecimal getTaxpayerOutstanding(RegisteredTaxpayer taxpayer) {
        BigDecimal balance = BigDecimal.ZERO;
        for (int i = 0; i < taxObligationCount; i++) if (taxObligations[i].getTaxpayer() == taxpayer) balance = balance.add(getObligationOutstanding(taxObligations[i]));
        for (int i = 0; i < penaltyCount; i++) if (penalties[i].getObligation().getTaxpayer() == taxpayer) balance = balance.add(penalties[i].getPenaltyAmount());
        return balance;
    }

    private BigDecimal readPercentage(String prompt) throws FormCancelledException {
        while (true) {
            BigDecimal value = readMoney(prompt);
            if (value.compareTo(new BigDecimal("100")) <= 0) return value;
            System.out.println("Percentage cannot exceed 100.");
        }
    }

    private BigDecimal readSignedMoney(String prompt) throws FormCancelledException {
        while (true) {
            String value = readRequiredText(prompt);
            try { return new BigDecimal(value); }
            catch (NumberFormatException e) { System.out.println("Invalid amount. Example: -250.00 or 250.00"); }
        }
    }

    // ==========================================
    // INPUT VALIDATION METHODS
    // ==========================================

    private String readRequiredText(String prompt)
            throws FormCancelledException {

        while (true) {

            System.out.print(prompt);

            if (!scanner.hasNextLine()) {
                throw new FormCancelledException();
            }

            String value = scanner.nextLine().trim();

            if (value.equalsIgnoreCase("CANCEL")
                    || value.equalsIgnoreCase("EXIT")) {
                throw new FormCancelledException();
            }

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                    "This field is required. Please try again."
            );
        }
    }

    private String readOptionalText(String prompt)
            throws FormCancelledException {

        System.out.print(prompt);

        if (!scanner.hasNextLine()) {
            throw new FormCancelledException();
        }

        String value = scanner.nextLine().trim();

        if (value.equalsIgnoreCase("CANCEL")
                || value.equalsIgnoreCase("EXIT")) {
            throw new FormCancelledException();
        }

        return value;
    }

    private String readEmail(String prompt)
            throws FormCancelledException {

        while (true) {

            String email = readRequiredText(prompt);

            if (email.matches(
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            )) {
                return email;
            }

            System.out.println(
                    "Invalid email address. Example: name@example.com"
            );
        }
    }

    private LocalDate readDate(String prompt)
            throws FormCancelledException {

        while (true) {

            String value = readRequiredText(prompt);

            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Invalid date. Please use YYYY-MM-DD."
                );
            }
        }
    }

    private BigDecimal readMoney(String prompt)
            throws FormCancelledException {

        while (true) {

            String value = readRequiredText(prompt);

            try {

                BigDecimal amount = new BigDecimal(value);

                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Amount cannot be negative.");
                    continue;
                }

                return amount;

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid amount. Example: 250000.00"
                );
            }
        }
    }

    private String readTaxpayerStatus(String prompt)
            throws FormCancelledException {

        while (true) {

            String status = readRequiredText(prompt);

            if (status.equalsIgnoreCase("ACTIVE")) {
                return "ACTIVE";
            }

            if (status.equalsIgnoreCase("INACTIVE")) {
                return "INACTIVE";
            }

            System.out.println(
                    "Invalid status. Enter ACTIVE or INACTIVE."
            );
        }
    }

    private boolean readYesNo(String prompt)
            throws FormCancelledException {

        while (true) {

            String value = readRequiredText(prompt);

            if (value.equalsIgnoreCase("Y")
                    || value.equalsIgnoreCase("YES")) {
                return true;
            }

            if (value.equalsIgnoreCase("N")
                    || value.equalsIgnoreCase("NO")) {
                return false;
            }

            System.out.println(
                    "Invalid choice. Please enter Y or N."
            );
        }
    }

    // ==========================================
    // COMMON TEXT COMPARISON
    // ==========================================

    private static boolean sameText(String first, String second) {

        return first != null
                && second != null
                && first.trim().equalsIgnoreCase(second.trim());
    }

    // ==========================================
    // RECORD COUNT GETTERS
    // ==========================================

    public int getIndividualTaxpayerCount() {
        return individualTaxpayerCount;
    }

    public int getCorporateTaxpayerCount() {
        return corporateTaxpayerCount;
    }

    public int getRevenueOfficerCount() {
        return revenueOfficerCount;
    }

    public int getTaxTypeCount() {
        return taxTypeCount;
    }

    public int getTaxObligationCount() {
        return taxObligationCount;
    }

    public int getTaxDeclarationCount() {
        return taxDeclarationCount;
    }

    public int getTaxAssessmentCount() {
        return taxAssessmentCount;
    }

    public int getTaxPaymentCount() {
        return taxPaymentCount;
    }

    public int getPenaltyCount() {
        return penaltyCount;
    }

    public int getTaxRefundCount() {
        return taxRefundCount;
    }

    public int getTaxClearanceCount() {
        return taxClearanceCount;
    }

    // ==========================================
    // FORM CANCELLATION EXCEPTION
    // ==========================================

    private static class FormCancelledException extends Exception {

        private static final long serialVersionUID = 1L;
    }
}
