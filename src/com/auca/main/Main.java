package com.auca.main;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TaxAdministrationSystem system = new TaxAdministrationSystem(scanner);

    public static void main(String[] args) {
        int choice = 0;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1: system.registerIndividualTaxpayerForm(); break;
                    case 2: system.registerCorporateTaxpayerForm(); break;
                    case 3: system.registerRevenueOfficerForm(); break;
                    case 4: system.registerTaxTypeForm(); break;
                    case 5: system.createTaxObligationForm(); break;
                    case 6: system.fileTaxDeclarationForm(); break;
                    case 7: system.assessTaxDeclarationForm(); break;
                    case 8: system.recordTaxPaymentForm(); break;
                    case 9: system.generatePenaltyForm(); break;
                    case 10: system.requestTaxRefundForm(); break;
                    case 11: system.requestTaxClearanceForm(); break;
                    case 12: system.displayIndividualTaxpayers(); break;
                    case 13: system.displayCorporateTaxpayers(); break;
                    case 14: system.displayRevenueOfficers(); break;
                    case 15: system.displayTaxTypes(); break;
                    case 16: system.displayTaxObligations(); break;
                    case 17: system.displayTaxDeclarations(); break;
                    case 18: system.displayTaxAssessments(); break;
                    case 19: system.displayTaxPayments(); break;
                    case 20: system.displayPenalties(); break;
                    case 21: system.displayRefundsAndClearances(); break;
                    case 22: System.out.println("\nThank you for using TAMS!"); break;
                    default: System.out.println("Invalid choice. Please select 1-22.");
                }
            } catch (NumberFormatException e) {
                choice = 0;
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("\nOperation could not be completed: " + e.getMessage());
            }
        } while (choice != 22);
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n==============================================");
        System.out.println("     TAX ADMINISTRATION MANAGEMENT SYSTEM");
        System.out.println("==============================================");
        System.out.println("1.  Register Individual Taxpayer");
        System.out.println("2.  Register Corporate Taxpayer");
        System.out.println("3.  Register Revenue Officer");
        System.out.println("4.  Register Tax Type");
        System.out.println("5.  Create Tax Obligation");
        System.out.println("6.  File Tax Declaration");
        System.out.println("7.  Assess Tax Declaration");
        System.out.println("8.  Record Tax Payment");
        System.out.println("9.  Generate Penalty");
        System.out.println("10. Request Tax Refund");
        System.out.println("11. Request Tax Clearance");
        System.out.println("----------------------------------------------");
        System.out.println("12. View All Individual Taxpayers");
        System.out.println("13. View All Corporate Taxpayers");
        System.out.println("14. View All Revenue Officers");
        System.out.println("15. View All Tax Types");
        System.out.println("16. View All Tax Obligations");
        System.out.println("17. View All Tax Declarations");
        System.out.println("18. View All Tax Assessments");
        System.out.println("19. View All Tax Payments");
        System.out.println("20. View All Penalties");
        System.out.println("21. View All Refunds and Clearances");
        System.out.println("----------------------------------------------");
        System.out.println("22. Exit");
    }
}
