import java.util.Comparator;

/**
 * <h3>COMP90041, Sem2, 2022: Final Project</h3>
 * <p>Class ApplicantNameComparator is used to sort applicants based on last name
 * 
 * @author Gia Han Ly
 */
public class ApplicantNameComparator implements Comparator<Applicant> {
    
    
    /** 
     * Return order of two applicants based on lastname, resolving ties by comparing first name
     * @param a - Applicant a
     * @param b - Applicant b
     * @return int - result
     */
    @Override
    public int compare(Applicant a, Applicant b) {
        int result = a.getLastname().compareToIgnoreCase(b.getLastname());

        if (result == 0){
            return a.getFirstname().compareToIgnoreCase(b.getFirstname());
        }
        return result;
    }

}
