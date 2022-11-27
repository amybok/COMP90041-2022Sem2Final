/**
 * <h3> COMP90041, Sem2, 2022: Final Project </h3>
 * <p> Class InvalidDataFormatException is used to handle data fields not confront to type standard. Print warning and the line it occurs.
 * @author Gia Han Ly
 * 
 */

public class InvalidDataFormatException extends Exception {
    private int line;

    /**
     * Print warning for invalid data format and where it occurs.
     * @param line - line where error occured 
     * @param fileType - type of file
     */
    public InvalidDataFormatException(int line, String fileType){
        super("WARNING: invalid data format in " + fileType + " file in line {" + line + "}");
        this.line = line;
    }

    /**
     * Get line where error occured
     * @return line where error occured
     */
    public int getLine(){
        return line;
    }
}
