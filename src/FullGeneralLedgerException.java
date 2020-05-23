//Rezvan Nafee
//11293468
//Recitation Section: 04

/**
 * This class represent a custom error created to help manage the GeneralLedger. This custom error is thrown only when
 * the GeneralLedger has reached it's maximum number of Transaction that it can hold and prints out an error message
 * to the console.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class FullGeneralLedgerException extends Exception {
    /**
     * Constructs the error message for the custom error
     *
     * @param error The custom error message to be displayed on the console.
     */
    public FullGeneralLedgerException(String error) {
        super(error);
    }
}

