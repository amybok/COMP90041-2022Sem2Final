/**
 * <h3> COMP90041, Sem2, 2022: Final Project </h3>
 * <p> Class InvalidCharacteristicFormatException is used to handle invalid characteris read from applications / jobs file.
 * Print warning and the line it occurs.
 * @author Gia Han Ly
 * 
 */

public class InvalidCharacteristicException extends Exception {
    private int line;

    /**
     * Print out warning for invalid characteristic and where it occurs
     * @param line - line where error occured
     * @param fileType - file type
     */
    public InvalidCharacteristicException(int line, String fileType){
        super("WARNING: invalid characteristic in "+ fileType +" file in line {"+ line +"}");
    }

    /**
     * Get line where error occured
     * @return line where error occured
     */
    public int getLine(){
        return line;
    }
}
