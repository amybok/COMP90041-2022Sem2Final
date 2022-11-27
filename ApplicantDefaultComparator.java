import java.util.Comparator;

/**
 * <h3>COMP90041, Sem2, 2022: Final Project</h3>
 * <p>Class ApplicantDefaultComparator is used to sort applicant based on availabilitty and name
 * 
 * @author Gia Han Ly
 */

public class ApplicantDefaultComparator implements Comparator<Applicant> {

    /** 
     * Return order of two applicants based on 
     * <p> &emsp; Date: If there are ties, break by comparing lastname<br>
     * <p> &emsp; Lastname: If there are ties, break by comparing firstname<br></p>
     * <p> Null date are listed last, with its order based on lastname and firstname if there are ties</p>
     * 
     * @param a - Applicant a
     * @param b - Applicant b
     * @return int
     */
    @Override
    public int compare(Applicant a, Applicant b) {

        int dateResult;

        // If both a and b start date are null, return tie
        if (a.getStartDate() == null && b.getStartDate() == null){
            dateResult = 0;
        } 
        // If b start date is null
        else if (b.getStartDate() == null){
            dateResult = -1;
        }
        // If a start date is null
        else if (a.getStartDate() == null){
            dateResult = 1;
        }
        // Other case compare two dates
        else {
            dateResult = a.getStartDate().compareTo(b.getStartDate());
        }

        // If there is tie in date, resolve by comparing lastname
        if (dateResult == 0){
            int lastnameResult = a.getLastname().compareToIgnoreCase(b.getLastname());

            // If there is tie in lastname, resolve by comparing first name
            if (lastnameResult == 0){
                return a.getFirstname().compareToIgnoreCase(b.getFirstname());
            }
            else {
                return lastnameResult;
            }
        }
        else {
            return dateResult;
        }
    }
}