//Rezvan Nafee
//11293468
//Recitation Section: 04

import java.text.DecimalFormat;

/**
 * This class represent the ledger that will hold a max of 50 transactions for Jack's account and manages basic
 * functions such as adding, removing, filtering, looking and getting Transactions. In addition it also clones the
 * current ledger and save it as a backup. In doing so allows the General Ledger to revert to the backup or compare the
 * current ledger with the backed up ledger.
 * The General Ledger is comprised of the the ledger,debit amount, credit amount, size, and a flag to tell if the ledger
 * was successfully made.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class GeneralLedger {
    /**
     * Represents the max number of transaction the ledger can hold
     */
    public static final int MAX_TRANSACTIONS = 50;

    private Transaction[] ledger;
    private double totalDebitAmount;
    private double totalCreditAmount;
    private int size = 0;
    /**
     * Creates a DecimalFormat to allow us to display the amount determined by the given Transaction as correct monetary
     * amount.
     */
    public DecimalFormat df = new DecimalFormat("#,###,##0.00");

    /**
     * Creates the GeneralLedger and ensures that it can hold ONLY 50 transactions
     */
    public GeneralLedger() {
        ledger = new Transaction[MAX_TRANSACTIONS];
    }

    /**
     * This is a COPY constructor that allows for a deep clone to be made of the GeneralLedger object.
     *
     * @param ledger The ledger to hold and manage the Transactions.
     * @param debit  The total of debit Transactions that were made.
     * @param credit The total of credit Transactions that were made.
     * @param size   The number of Transactions that are presently found in the ledger.
     */
    public GeneralLedger(Transaction[] ledger, double debit, double credit, int size) {
        this.ledger = ledger;
        this.totalDebitAmount = debit;
        this.totalCreditAmount = credit;
        this.size = size;
    }

    /**
     * Adds a Transaction object to the GeneralLedger
     *
     * @param newTransaction The Transaction that would like to be added into the GeneralLedger
     * @throws FullGeneralLedgerException        Thrown if the GeneralLedger already has reached its maximum capacity of 50 Transaction objects
     * @throws InvalidTransactionException       Thrown if the Transaction object is invalid because it has an incorrect date format, incorrect amount
     *                                           inputted, or if the amount of the Transaction is 0.
     * @throws TransactionAlreadyExistsException Thrown if the Transaction object that would like to be added already exists in the GeneralLedger.
     */
    public void addTransaction(Transaction newTransaction) throws FullGeneralLedgerException,
            InvalidTransactionException, TransactionAlreadyExistsException {
        if (this.size == 50)
            throw new FullGeneralLedgerException("Transaction not added: The general ledger is full!");
        if (exists(newTransaction))
            throw new TransactionAlreadyExistsException("Transaction not added: Transaction already exists in ledger!");
        if (!checkDate(newTransaction) || newTransaction.getAmount() == 0)
            throw new InvalidTransactionException("Transaction not added: Not a valid Transaction!");
        ledger[size] = newTransaction;
        size++;
        if (size > 1)
            sortByDates();
        if (newTransaction.getAmount() < 0)
            totalCreditAmount += newTransaction.getAmount();
        else
            totalDebitAmount += newTransaction.getAmount();
    }

    /**
     * Removes a Transaction object from the ledger based on it's position on the ledger.
     *
     * @param position The position of the Transaction that would like to be removed from the GeneralLedger.
     * @throws InvalidLedgerPositionException Thrown if the position that was provided doesn't exist or is not a valid position number inside the
     *                                        GeneralLedger
     */
    public void removeTransaction(int position) throws InvalidLedgerPositionException {
        if (ledger[position - 1] == null)
            throw new InvalidLedgerPositionException("Transaction not removed: No such transaction in the general " +
                    "ledger.");
        try {
            Transaction temp = ledger[position - 1];

        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new InvalidLedgerPositionException("Transaction not removed: Invalid position given.");
        }
        boolean[] bool = new boolean[size];
        int trueCounter = 0;
        for (int i = 0; i < size; i++) {
            if (ledger[i].equals(ledger[position - 1])) {
                bool[i] = false;
            } else {
                bool[i] = true;
                trueCounter++;
            }
        }
        Transaction[] newLedger = new Transaction[MAX_TRANSACTIONS];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (bool[i] && index < trueCounter) {
                newLedger[index] = ledger[i];
                index++;
            }
        }
        this.ledger = newLedger;
        size--;
    }

    /**
     * Returns the Transaction that corresponds to the specified position and prints out in a neatly formatted table
     * the position of the Transaction in the GeneralLedger and the information of the Transaction.
     *
     * @param position The position of the Transaction in the GeneralLedger.
     * @return Returns the Transaction object at the specified position.
     * @throws InvalidLedgerPositionException Thrown if the specified position doesn't not exists in the GeneralLeger.
     */
    public Transaction getTransaction(int position) throws InvalidLedgerPositionException {
        if (ledger[position - 1] == null)
            throw new InvalidLedgerPositionException("Couldn't Get Transaction: No such transaction in the general" +
                    " ledger.");
        try {
            Transaction temp = ledger[position - 1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new InvalidLedgerPositionException("Couldn't Get Transaction: Position provided is invalid.");
        }
        Transaction temp = ledger[position - 1];
        int[] pos = {position};
        Transaction[] arr = {temp};
        printSpecificTable(pos, arr);
        return ledger[position - 1];
    }

    /**
     * Filters the GeneralLedger by a specified date in YYYY/MM/DD format and prints out the Transactions that share
     * the same date along with the specified position in the ledger and the information of the shared Transaction(s).
     *
     * @param generalLedger The ledger that would like to be filtered.
     * @param date          The date that will be used to filter the specified ledger.
     * @throws Exception Thrown if the date that was entered is in the in the incorrect format or if there is no Transactions in the
     *                   ledger that share the same date as the specified date.
     */
    public static void filter(GeneralLedger generalLedger, String date) throws Exception {
        if (!GeneralLedger.checkDate(date))
            throw new Exception("Filter error: An invalid date was entered.");
        GeneralLedger filter = new GeneralLedger();
        int counterFound = 0;
        for (int i = 0; i < generalLedger.getSize(); i++) {
            if (generalLedger.getLedger()[i].getDate().equalsIgnoreCase(date)) {
                filter.addTransaction(generalLedger.getLedger()[i]);
                counterFound++;
            }
        }
        if (filter.getSize() == 0)
            throw new Exception("Filter error: Couldn't find transactions with provided date.");
        int[] pos = new int[counterFound];
        Transaction[] arr = new Transaction[counterFound];
        for (int i = 0; i < filter.getSize(); i++) {
            pos[i] = generalLedger.position(filter.getLedger()[i]);
            arr[i] = filter.getLedger()[i];
        }
        filter.printSpecificTable(pos, arr);
    }

    /**
     * Checks if the Transaction is already present in the GeneralLedger
     *
     * @param transaction The Transaction object that will be compared to all the Transactions found in the GeneralLedger.
     * @return Returns true if the Transaction object already is found in the GeneralLedger
     * Returns false if the Transaction object is not found in the GeneralLedger.
     */
    public boolean exists(Transaction transaction) {
        for (int i = 0; i < size; i++) {
            if (ledger[i].equals(transaction))
                return true;
        }
        return false;
    }

    /**
     * Returns the number of Transactions objects found in the GeneralLedger
     *
     * @return Returns size.
     */
    public int size() {
        return size;
    }

    /**
     * Prints out the specific Transaction if it is currently existing in the GeneralLedger
     *
     * @param transaction The Transaction that would like to be searched in the GeneralLedger
     * @throws Exception Thrown if the Transaction provided is not a proper Transaction or if the Transaction provided does not exist
     *                   in the current GeneralLedger.
     */
    public void lookForTransaction(Transaction transaction) throws Exception {
        if (!checkDate(transaction.getDate()))
            throw new Exception("Search Error: Not a valid Transaction!");
        if (!exists(transaction))
            throw new Exception("Search Error: Transaction doesn't exist in ledger.");
        int index = 1;
        for (int i = 0; i < size; i++) {
            if (ledger[i].equals(transaction))
                index += i;
        }
        int[] pos = {index};
        Transaction[] temp = {ledger[index - 1]};
        printSpecificTable(pos, temp);
    }

    /**
     * Prints all the Transactions in the GeneralLedger in neatly formatted table.
     */
    public void printAllTransaction() {
        ledger.toString();
    }

    /**
     * Sorts the dates of the Transactions in the GeneralLedger to keep it organized by using a Bubble-Sort Algorithm.
     */
    public void sortByDates() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (ledger[i].getDate().compareToIgnoreCase(ledger[j].getDate()) > 0) {
                    Transaction temp = ledger[j];
                    ledger[j] = ledger[i];
                    ledger[i] = temp;
                }
            }
        }
    }

    /**
     * Returns the ledger that is holding the Transaction objects.
     *
     * @return The ledger field.
     */
    public Transaction[] getLedger() {
        return ledger;
    }

    /**
     * Sets the ledger to provided and specified ledger.
     *
     * @param ledger The ledger of the GeneralLedger.
     */
    public void setLedger(Transaction[] ledger) {
        this.ledger = ledger;
    }

    /**
     * Returns the total credit transactions made by Frank.
     *
     * @return The totalCreditAmount field.
     */

    public double getTotalCreditAmount() {
        return totalCreditAmount;
    }

    /**
     * Returns the total debit transactions made by Frank.
     *
     * @return The totalDebitAmount field.
     */
    public double getTotalDebitAmount() {
        return totalDebitAmount;
    }

    /**
     * Sets the size of the GeneralLedger to specified size.
     *
     * @param size The size of the GeneralLedger
     */
    public void setCounter(int size) {
        this.size = size;
    }

    /**
     * Sets the total credit Transactions to a specified total.
     *
     * @param totalCreditAmount The total credit of the GeneralLedger.
     */
    public void setTotalCreditAmount(double totalCreditAmount) {
        this.totalCreditAmount = totalCreditAmount;
    }

    /**
     * Sets the total debit Transactions to a specified total.
     *
     * @param totalDebitAmount The total debit of the GeneralLedger.
     */
    public void setTotalDebitAmount(double totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    /**
     * Returns the size of the GeneralLedger or the total number of Transactions in the GeneralLedger
     *
     * @return The size field of the GeneralLedger.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns a deep copy of the GeneralLedger by using the COPY constructor.
     *
     * @return A deep copy of GeneralLedger.
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        Transaction[] data = new Transaction[MAX_TRANSACTIONS];
        for (int i = 0; i < this.size; i++) {
            Object trans = ledger[i].clone();
            data[i] = (Transaction) trans;
        }
        GeneralLedger backup = new GeneralLedger(data, getTotalDebitAmount(), getTotalCreditAmount(), getSize());
        return backup;
    }

    /**
     * Takes a specified array of position(s) that is parallel to the specified array of Transaction(s) that needs
     * to be printed.
     *
     * @param position The position of the Transaction(s) that needs to be printed in a neatly formatted table.
     * @param trans    The Transaction(s) infomration that needs to be printed in a neatly formatted table.
     */
    public void printSpecificTable(int[] position, Transaction[] trans) {
        System.out.printf("\n%-10s%-15s%-15s%-15s%-15s\n", "No.", "Date", "Debit", "Credit", "Description");
        System.out.print("----------------------------------------------------------------------------------------" +
                "------------------------\n");
        for (int i = 0; i < position.length; i++) {
            if (trans[i].getAmount() < 0)
                System.out.printf("%-10d%-15s%-15s%-15s%-15s\n", position[i], trans[i].getDate(), "", df.format(Math.abs(trans[i].getAmount()))
                        , trans[i].getDescription());
            if (trans[i].getAmount() > 0)
                System.out.printf("%-10d%-15s%-15s%-15s%-15s\n", position[i], trans[i].getDate(), df.format(trans[i].getAmount()), ""
                        , trans[i].getDescription());
        }
    }

    /**
     * Checks if the date that was provided by the Transaction is correctly formatted in the YYYY/MM/DD format.
     *
     * @param transaction The Transaction object.
     * @return Returns true if the Transaction's date is correctly formatted.
     * Returns false if the Transaction's date is incorrectly formatted.
     */
    public boolean checkDate(Transaction transaction) {
        if (!transaction.getDate().contains(String.valueOf('/')))
            return false;
        if (transaction.getDate().split("/").length != 3)
            return false;
        String[] date = transaction.getDate().split("/");
        String year = date[0];
        String month = date[1];
        String day = date[2];
        if (year.length() != 4)
            return false;
        if (month.length() != 2)
            return false;
        if (day.length() != 2)
            return false;
        int yearNum = 0;
        int monthNum = 0;
        int dayNum = 0;
        try {
            yearNum = Integer.parseInt(year);
            monthNum = Integer.parseInt(month);
            dayNum = Integer.parseInt(day);
        } catch (NumberFormatException ex) {
            return false;
        }
        if (!(yearNum >= 1900 && yearNum <= 2050 && monthNum >= 1 && monthNum <= 12 && dayNum >= 1 && dayNum <= 30))
            return false;
        return true;
    }

    /**
     * Checks if the date that was provided is correctly formatted in the YYYY/MM/DD format.
     *
     * @param string The date of the Transaction.
     * @return Returns true if the String's date is correctly formatted.
     * Returns false if the String's date is incorrectly formatted.
     */
    public static boolean checkDate(String string) {
        if (!string.contains(String.valueOf('/')))
            return false;
        if (string.split("/").length != 3)
            return false;
        String[] date = string.split("/");
        String year = date[0];
        String month = date[1];
        String day = date[2];
        if (year.length() != 4)
            return false;
        if (month.length() != 2)
            return false;
        if (day.length() != 2)
            return false;
        int yearNum = 0;
        int monthNum = 0;
        int dayNum = 0;
        try {
            yearNum = Integer.parseInt(year);
            monthNum = Integer.parseInt(month);
            dayNum = Integer.parseInt(day);
        } catch (NumberFormatException ex) {
            return false;
        }
        if (!(yearNum >= 1900 && yearNum <= 2050 && monthNum >= 1 && monthNum <= 12 && dayNum >= 1 && dayNum <= 30))
            return false;
        return true;
    }

    /**
     * Checks if two GeneralLedger objects are the same.
     *
     * @param ledger The GeneralLedger that would like to be compared with the other.
     * @param backup The other GeneralLedger that would like to be compared.
     * @return Returns true if the both the GeneralLedger objects are the same if the have the equivalent fields.
     * Returns false if the two GeneralLedger objects do not have equivalent fields.
     */
    public static boolean equals(GeneralLedger ledger, GeneralLedger backup) {
        return ledger.getSize() == backup.getSize() &&
                ledger.getTotalCreditAmount() == backup.getTotalCreditAmount() &&
                ledger.getTotalDebitAmount() == backup.getTotalDebitAmount() && checkLedgers(ledger, backup);
    }

    /**
     * Checks the GeneralLedgers's respective ledgers' contents and checks if the contain the same Transaction(s).
     *
     * @param ledger The GeneralLedger's ledger field that would like to be compared with the other.
     * @param backup The other GeneralLedger's ledger field that would like to be compared.
     * @return Returns true if the both ledgers are the same size and hold the same Transaction(s).
     * Reuturs false if the two ledgers do not have the same size or they do not hold the same Transaction(s).
     */
    public static boolean checkLedgers(GeneralLedger ledger, GeneralLedger backup) {
        if (ledger.getSize() != backup.getSize())
            return false;
        for (int i = 0; i < ledger.getSize(); i++) {
            if (!ledger.getLedger()[i].equals(backup.getLedger()[i]))
                return false;
        }
        return true;
    }

    /**
     * Returns the contents of the GeneralLedger in a neatly formatted table.
     *
     * @return Returns the neatly formatted table of the contents found in the GeneralLedger.
     */
    public String toString() {
        String result = "";
        result += String.format("%-10s%-15s%-15s%-15s%-15s\n", "No.", "Date", "Debit", "Credit", "Description");
        result += ("---------------------------------------------------------------------------------------" +
                "------------------------\n");
        int index = 1;
        for (int i = 0; i < size; i++) {
            if (ledger[i].getAmount() < 0)
                result += String.format("%-10d%-15s%-15s%-15s%-15s\n", index++, ledger[i].getDate(), "", df.format(Math.abs(ledger[i].getAmount()))
                        , ledger[i].getDescription());
            if (ledger[i].getAmount() > 0)
                result += String.format("%-10d%-15s%-15s%-15s%-15s\n", index++, ledger[i].getDate(), df.format(ledger[i].getAmount()), ""
                        , ledger[i].getDescription());
        }
        return result;
    }

    /**
     * Prints the total debit and total credit transactions and the net worth of the Jack's account in a neatly
     * formatted table.
     */
    public void printFinancialAssets() {
        System.out.println("Financial Data for Jack's Account ");
        System.out.println("---------------------------------------------------------------------------------------"
                + "------------------------");
        String debit = df.format(getTotalDebitAmount());
        String credit = df.format(Math.abs(getTotalCreditAmount()));
        String net = df.format(getTotalDebitAmount() - Math.abs(getTotalCreditAmount()));
        System.out.printf("%12s %-8s", "Assets:", ("$" + debit));
        System.out.println();
        System.out.printf("%12s %-8s", "Liabilities:", ("$" + credit));
        System.out.println();
        System.out.printf("%12s %-8s", "Net Worth:", ("$" + net));
    }

    /**
     * Returns the position of a specified Transaction. If it's not found it returns -1
     *
     * @param t
     * @return Return the position of the specified Transaction and if it is not found, the function returns -1.
     */
    public int position(Transaction t) {
        for (int i = 0; i < ledger.length; i++) {
            if (ledger[i].equals(t))
                return (i + 1);
        }
        return -1;
    }
}