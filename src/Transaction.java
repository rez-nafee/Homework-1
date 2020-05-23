//Rezvan Nafee
//11293468
//Recitation Section: 04

/**
 * This class represents a transaction which has a Date in YYYY/MM/DD format, amount of dollars, and the a short
 * description of the transaction being made.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class Transaction {
    private String date;
    private double amount;
    private String description;

    /**
     * This is a constructor that creates a new empty Transaction with it's respective initial values.
     */
    public Transaction() {
        this.date = "";
        this.amount = 0;
        this.description = "";
    }

    /**
     * This is a constructor that creates a new specified Transaction.
     *
     * @param date        The date in YYYY/MM/DD format
     * @param amount      The numeric cost of the transaction made.
     * @param description The short description of what the Transaction was for.
     */
    public Transaction(String date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /**
     * This is a COPY constructor that allows for a deep clone to be made of the Transaction object.
     *
     * @param transaction The Transaction that will be deep copied.
     */
    public Transaction(Transaction transaction) {
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
    }

    /**
     * Returns amount of the Transaction
     *
     * @return Returns amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the date of the Transaction
     *
     * @return Returns date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the description of the Transaction.
     *
     * @return Returns description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the amount field to specified amount.
     *
     * @param amount The numeric cost of the Transaction.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Sets the date field to specified date.
     *
     * @param date The date of the Transaction.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the description to specified details.
     *
     * @param description The description of the Transaction.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a deep clone of the same Transaction object by using the copy constructor.
     *
     * @return Returns deep copy of Transaction.
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        Transaction trans = new Transaction(getDate(), getAmount(), getDescription());
        Transaction cloned = new Transaction(trans);
        return cloned;
    }

    /**
     * Checks if another Object is type of Transaction and is the same as the current Transaction
     *
     * @param o Object that will be compared to the current Transaction.
     * @return Returns true if the Object is a Transaction and/or is the same as the current Transaction
     * Returns false if the Object is not a Transaction and/or is not the same as the current Transaction.
     */
    public boolean equals(Object o) {
        return this.description.equals(((Transaction) o).getDescription()) &&
                this.date.equals(((Transaction) o).getDate()) &&
                this.amount == ((Transaction) o).getAmount();
    }
}
