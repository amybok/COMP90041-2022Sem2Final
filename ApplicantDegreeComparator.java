import java.util.Comparator;
import java.text.RuleBasedCollator;
import java.text.ParseException;

/**
 * <h3>COMP90041, Sem2, 2022: Final Project</h3>
 * <p>Class ApplicantDegreeComparator is used to sort applicants based on degree
 * 
 * @author Gia Han Ly
 */
public class ApplicantDegreeComparator implements Comparator<Applicant> {
    private static final String RULES = "< 'Diploma'< 'Bachelor'< 'Master'< 'PHD'";

    
    /** 
     * Return order for two applicants based on degree
     * @param a - Applicant a
     * @param b - Applicant b
     * @return int - result
     */
    @Override
    public int compare(Applicant a, Applicant b) {

        RuleBasedCollator rule;
        try {
            // Return order based on rule
            rule = new RuleBasedCollator(RULES);
            

            if (a.degree == null || a.degree.isBlank()){
                return 1;
            }
            else if (b.degree == null || b.degree.isBlank()){
                return -1;
            }
            else {
                return rule.compare(b.degree, a.degree);
            }
            
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }


    public int compare(Applicant a, String b) {

        RuleBasedCollator rule;
        try {
            // Return order based on rule
            rule = new RuleBasedCollator(RULES);
            

            if (a.degree == null || a.degree.isBlank()){
                return -1;
            }
            else if (b == null || b.isBlank()){
                return 1;
            }
            else {
                return rule.compare(a.degree, b);
            }
            
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }
}