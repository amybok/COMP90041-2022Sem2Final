import java.util.Comparator;

/**
 * <h3>COMP90041, Sem2, 2022: Final Project</h3>
 * <p>Class ApplicantWAMComparator is used to sort applicants based on wam
 * 
 * @author Gia Han Ly
 */
public class ApplicantWAMComparator implements Comparator<Applicant>{
    
    /** 
     * Return order of two applicants
     * @param a - Applicant a
     * @param b - Applicant b
     * @return int - result
     */
    @Override
    public int compare(Applicant a, Applicant b) {
        return Double.compare(b.wam, a.wam);
    }

}
