import java.util.Scanner;
import java.util.Set;

/**
 * <h3> COMP90041, Sem2, 2022: Final Project </h3>
 * <p> Class HRmenu has methods to get user input from command line input and to display option menu
 * @author Gia Han Ly
 * 
 */

public class HRmenu {
    // Set of available inputs
    private static final Set<String> CHOICE = Set.of("create", "jobs", "quit", "c", "j", "q", "applications", "a",
    "matchmaking", "m", "filter", "f");
    private Jobs jobs = new Jobs();
    private Applicant applications = new Applicant();
    private Scanner keyboard = new Scanner(System.in);
    private Applied applied = new Applied();
    private Filter filter = new Filter();
    private Matchmaker match = new Matchmaker();

    // Constructors
    /**
     * Constructor for HRMenu object
     * @param jobsFile jobs file name
     * @param applicationsFile applications file name
     */
    public HRmenu(String jobsFile, String applicationsFile){
        jobs.read(jobsFile);
        applications.read(applicationsFile);
    }

    /**
     * Take input from user and run the required methods. Methods options are: quit, create jobs, list jobs, list applicants, filter and matchmaking
     */
    public void getInput(){

        String input;

        menu();

        // Take in user input and run the required method
        while (true){

            input = keyboard.nextLine();
            if (!CHOICE.contains(input)){
                System.out.print("Invalid input! Please enter a valid command to continue: \n> ");
            }
            if (input.equals("c") || input.equals("create")){
                //Create new job and save in jobs file
                jobs = jobs.create(keyboard);
                jobs.saveJobs(jobs);
                menu();

            }
            if (input.equals("q") || input.equals("quit")){
                return;
            }
            if (input.equals("j") || input.equals("jobs")){
                // Display jobs with applicants applied for the job
                applied.display();
                menu();
            }
            if (input.equals("a") || input.equals("applicants")){
                // Display applicants
                filter.sortDefault();
                applications.applicantAvailable();
                menu();
            }
            if (input.equals("f") || input.equals("filter")){
                // Take in required filter and output applicants
                System.out.print("Filter by: [lastname], [degree] or [wam]: ");
                filter.getInput(keyboard);
                menu();
            }
            if (input.equals("m") || input.equals("matchmaking")){
                if (Applicant.getNumberOfApplicant() == 0 || Applied.getAppliedList().size() == 0){
                    System.out.println("No applicants available.");
                }
                else if (Jobs.getNumberofJobs() == 0){
                    System.out.println("No jobs available.");
                }
                match.saveMatches();
                Matchmaker.printMatches();
                menu();
            }
        }
    }

    /**
     * Print menu options
     */
    private void menu(){
        String menu = Applicant.getNumberOfApplicant() +" applications received.\n" +
        "Please enter one of the following commands to continue:\n" +
        "- create new job: [create] or [c]\n" +
        "- list available jobs: [jobs] or [j]\n" +
        "- list applicants: [applicants] or [a]\n" +
        "- filter applications: [filter] or [f]\n" +
        "- matchmaking: [match] or [m]\n" +
        "- quit the program: [quit] or [q]\n" +
        "> ";

        System.out.print(menu);
    }
}

