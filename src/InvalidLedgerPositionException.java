//Rezvan Nafee
//11293468
//Recitation Section: 04

/**
 * This class represent a custom error created to help manage the GeneralLedger. This custom error is thrown only when
 * the position does not exist in the GeneralLedger and prints out an error message to the console.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Section 04
 */
public class InvalidLedgerPositionException extends Exception {
    /**
     * Constructs the error message for the custom error
     *
     * @param error The custom error message to be displayed on the console.
     */
    public InvalidLedgerPositionException(String error) {
        super(error);
    }
}
