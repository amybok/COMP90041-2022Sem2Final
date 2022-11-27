import java.util.Set;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * <h3>COMP90041, Sem2, 2022: Final Project</h3>
 * <p>Class Filter is used to take in filter requirement and call the approriate method.
 * @author Gia Han Ly
 */

public class Filter {
    private static final Set<String> FILTERS = Set.of("lastname", "degree", "wam");
    private List<Applicant> applications = Applicant.getApplicantsList();

    
    /** 
     * Take user input and sort based on filter selected
     * @param keyboard
     */
    public void getInput(Scanner keyboard){
        String input;

        while(true){
            input = keyboard.nextLine();

            if (!FILTERS.contains(input)){
                System.out.print("Invalid input! Please enter a valid command to continue:\n> ");
            }
            if (input.equals("lastname")){
                sortLastName();
                return;
            }
            if (input.equals("degree")){
                sortDegree();
                return;
            }
            if (input.equals("wam")){
                sortWAM();
                return;
            }
        }
    }

    /**
     * Sort applicants based on last name
     */
    private void sortLastName(){
        int i = 1;
        List<Applicant>  temp = Applicant.getApplicantsList();
        Collections.sort(temp,new ApplicantNameComparator());
        for (Applicant a : applications){
            System.out.print("["+ i +"] ");
            Applicant.printApplicant(a);
            i++;
        }
    }

    /**
     * Sort applicants based on degree
     */
    private void sortDegree(){
        int i = 1;
        List<Applicant>  temp = Applicant.getApplicantsList();
        Collections.sort(temp, new ApplicantDegreeComparator());
        for (Applicant a : applications){
            System.out.print("["+ i +"] ");
            Applicant.printApplicant(a);
            i++;
        }
    }


    /**
     * Sort applicants based on WAM
     */
    private void sortWAM(){
        int i = 1;
        Applicant.wamCalc();
        List<Applicant>  temp = Applicant.getApplicantsList();
        Collections.sort(temp, new ApplicantWAMComparator());
        for (Applicant a : applications){
            System.out.print("["+ i +"] ");
            Applicant.printApplicant(a);
            i++;
        }
    }

    /**
     * Sort applicants based on start date, cut ties by comparing last name, first name
     */
    public void sortDefault(){
        Collections.sort(applications, new ApplicantDefaultComparator());
    }
}
