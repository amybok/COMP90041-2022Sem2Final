import java.util.Set;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * COMP90041, Sem2, 2022: Final Project
 * <p>Class Entity has variables related to timestamp, degree, salary, summary for jobs and applicants objects. Methdd included is create object
 * taking command line input
 * @author Gia Han Ly
 */
public abstract class Entity {
    // Valid choices for Gender and Degree
    protected static final Set<String> GENDER_LIST = Set.of("female", "other", "male");
    protected static final Set<String> DEGREE = Set.of("Bachelor", "Master", "PHD");
    // Format for parsing date time
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

    protected long createdAt;
    protected String degree;
    protected int salary;
    protected String summary;
    protected double wam;

    /**
     * Abstract method to create object of type Entity using user input
     * @param scanner
     * @return Entity
     */
    protected abstract Entity create(Scanner scanner);

    /**
     * Abstract method to read object of type Entity from file
     * @param fileName - file name
     */
    protected abstract void read(String fileName);


    
}
