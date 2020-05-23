//Rezvan Nafee 111
//11293468
//Recitation Section: 04

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents the simulation of Jack's account by creating a two GeneralLedger objects that will serve as
 * Jack's current ledger and backup ledger. A list of menu options will be printed into the console allowing different
 * management methods to be performed on either or both the ledgers created.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */

public class GeneralLedgerManager {
    /**
     * The main method where the simulation of Jack's Financial Account will be emulated and it also creates the current
     * GeneralLedger and backup GeneralLedger to be used later on in the menu options listed.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        System.out.println();
        GeneralLedger bank = new GeneralLedger();
        GeneralLedger backup = new GeneralLedger();
        boolean backUp = false;
        Scanner input = new Scanner(System.in);
        String key = " ";
        while (!key.equals("Q")) {
            System.out.print("(A) Add Transaction\n(G) Get Transaction\n(R) Remove Transaction" +
                    "\n(P) Print Transactions " + "General Ledger\n(F) Filter by Date\n(L) Look for Transaction" +
                    "\n(S) Size\n(B) Backup\n(PB)" + " Print Transactions in Back Up\n(RB) Revert to Backup\n" +
                    "(CB) Compare Backup with Current\n" + "(PF) Print Financial Information\n(Q) Quit\n\n");
            System.out.print("Enter a selection: ");
            key = input.nextLine().trim().toUpperCase();
            switch (key) {
                case ("A"): //Add transaction
                    try {
                        String date = "";
                        double amount = 0.0;
                        String info = "";
                        System.out.print("\nEnter Date: ");
                        date = input.nextLine();
                        try {
                            System.out.print("Enter Amount: ");
                            amount = input.nextDouble();
                            input.nextLine();
                        } catch (InputMismatchException ex) {
                            input.nextLine();
                            amount = 0;
                            System.out.print("Enter Description: ");
                            info = input.nextLine().trim();
                            System.out.println("\nTransaction not added: Not a valid Transaction!\n");
                            continue;
                        }
                        System.out.print("Enter Description: ");
                        info = input.nextLine().trim();
                        Transaction add = new Transaction(date, amount, info);
                        bank.addTransaction(add);
                        System.out.println("\nTransaction added successfully.\n");
                    } catch (InvalidTransactionException ex) {
                        System.out.println();
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println();
                    } catch (FullGeneralLedgerException ex) {
                        System.out.println();
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println();
                    } catch (TransactionAlreadyExistsException ex) {
                        System.out.println();
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println();
                    }
                    break;
                case ("G"): //Get Transaction
                    try {
                        System.out.print("\nEnter a selection: ");
                        int pos = input.nextInt();
                        input.nextLine();
                        Transaction get = bank.getTransaction(pos);
                        System.out.println();
                    } catch (InvalidLedgerPositionException ex) {
                        System.out.println();
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println();
                    } catch (InputMismatchException ex) {
                        input.nextLine();
                        System.out.println();
                        System.out.println("Couldn't Get Transaction: Position provided is invalid.");
                        System.out.println();
                    }
                    break;
                case ("R"): //Remove Transaction
                    try {
                        System.out.print("\nEnter a selection: ");
                        int pos = input.nextInt();
                        System.out.println();
                        input.nextLine();
                        bank.removeTransaction(pos);
                        System.out.println("Transaction has been removed successfully from the general ledger.\n");
                    } catch (InvalidLedgerPositionException ex) {
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println();
                    } catch (InputMismatchException ex) {
                        input.nextLine();
                        System.out.println();
                        System.out.println("Transaction not removed: Invalid position given.");
                        System.out.println();
                    }
                    break;
                case ("P"): //Print Transaction
                    if (bank.getSize() == 0)
                        System.out.println("\nNo transactions currently in the general ledger.\n");
                    else
                        System.out.println("\n" + bank);
                    break;
                case ("F"): //Filter Transaction
                    try {
                        System.out.print("\nEnter a date: ");
                        String date = input.nextLine().trim();
                        GeneralLedger.filter(bank, date);
                        System.out.println();
                    } catch (Exception ex) {
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println();
                    }
                    break;
                case ("L"): //Look for Transaction
                    try {
                        String date = "";
                        double amount = 0.0;
                        String info = "";
                        System.out.print("\nEnter Date: ");
                        date = input.nextLine();
                        try {
                            System.out.print("Enter Amount: ");
                            amount = input.nextDouble();
                            input.nextLine();
                        } catch (InputMismatchException ex) {
                            input.nextLine();
                            amount = 0;
                            System.out.print("Enter Description: ");
                            info = input.nextLine().trim();
                            System.out.println("\nSearch Error: Not a valid Transaction!\n");
                            continue;
                        }
                        System.out.print("Enter Description: ");
                        info = input.nextLine().trim();
                        Transaction add = new Transaction(date, amount, info);
                        bank.lookForTransaction(add);
                        System.out.println();
                    } catch (Exception ex) {
                        System.out.println();
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println();
                    }
                    break;
                case ("S"): //Size of Ledger
                    if (bank.size() == 0)
                        System.out.println("\nNo transactions currently in the general ledger.\n");
                    if (bank.size() == 1)
                        System.out.println("\nThere is " + bank.size() + " transactions currently in the general ledger\n");
                    if (bank.size() > 1)
                        System.out.println("\nThere are " + bank.size() + " transactions currently in the general ledger\n");
                    break;
                case ("B"): //Backup backup
                    try {
                        backup = (GeneralLedger) bank.clone();
                        backUp = true;
                    } catch (CloneNotSupportedException ex) {
                    }
                    System.out.println("\nCreated a backup of the current general ledger.\n");
                    break;
                case ("PB"): //Print Backup
                    if (!backUp) {
                        System.out.println("\nPrinting Backup Error: Backup may not have been made.\n");
                        continue;
                    } else
                        System.out.println("\n" + backup + "\n");
                    break;
                case ("RB"): //Revert to Backup
                    if (!backUp)
                        System.out.println("\nRevert Backup Error: Backup may not have been made.\n");
                    else {
                        bank = new GeneralLedger(backup.getLedger(), backup.getTotalDebitAmount(),
                                backup.getTotalCreditAmount(), backup.getSize());
                        System.out.println("\nSuccessfully reverted to backup!\n");
                    }
                    break;
                case ("CB"): //Compare Backup
                    if (!backUp) {
                        System.out.println("\nCompare Backup Error: Backup may not have been made.\n");
                        continue;
                    }
                    if (GeneralLedger.equals(bank, backup))
                        System.out.println("\nThe current general ledger is the same as the backup copy.\n");
                    else
                        System.out.println("\nThe current general ledger is NOT the same as the backup copy.\n");
                    break;
                case ("PF"): //Print Financial Information
                    System.out.println();
                    bank.printFinancialAssets();
                    System.out.println();
                    System.out.println();
                    break;
                case ("Q"): //Quit the program
                    System.out.println("\nProgram terminating successfully...");
                    break;
                default:
                    System.out.println("\nNot a valid input!\n");
            }
        }
    }
}