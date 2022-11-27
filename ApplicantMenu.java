import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;


/**
 * <h3> COMP90041, Sem2, 2022: Final Project </h3>
 * <p> Class ApplicantMenu has methods to take user input for tag applicant and call the required methods. 
 * @author Gia Han Ly
 * 
 */

public class ApplicantMenu {
    private static final Set<String> CHOICE = Set.of("create", "jobs", "quit", "c", "j", "q");
    private Jobs jobs = new Jobs();
    private Applicant applications = new Applicant();
    private ArrayList<Long> jobsApp = new ArrayList<>();
    private Scanner keyboard = null;

    // Constructors
    /**
     * Constructor for ApplicantMenu
     * @param jobsFile jobs file name
     * @param applicationsFile applications file name
     */
    public ApplicantMenu(String jobsFile, String applicationsFile){
        jobs.read(jobsFile);
        applications.setApplicantionsFile(applicationsFile);
        
    }

    /**
     * Get user input from command line input. Call the required methods of jobs and applicant type.
     * Available input are: create application, quit, list available jobs and apply.
     */
    public void getInput(){
        keyboard = new Scanner(System.in);
        
        menu(0);
    
        String input;

        while (true){

            input = keyboard.nextLine();
            if (!CHOICE.contains(input)){
                System.out.print("Invalid input! Please enter a valid command to continue:\n> ");
            }
            if (input.equals("c") || input.equals("create")){
                applications = applications.create(keyboard);
                Applicant.saveApplicant(applications);
                jobs.writeName(applications.getLastname(), applications.getFirstname(), applications.getCreatedAt());
                menu(1);
            }
            if (input.equals("q") || input.equals("quit")){
                jobs.writeJobs();
                jobs.writeEnd();
                return;
            }
            if (input.equals("j") || input.equals("jobs")){
                jobs.jobsAvailable();
                if (applications.getLastname() == null){
                    menu(0);
                    continue;
                }
                if (Jobs.getNumberofJobs() == 0){
                    menu(1);
                    continue;
                }
                
                System.out.print("Please enter the jobs you would like to apply for (multiple options are possible): ");
                while (true){
                    input = keyboard.nextLine();
                    String[] jchoices = input.split(",");
                    try {
                        for (String c : jchoices){
                            int num = Integer.parseInt(c);
                            if (num > Jobs.getNumberofJobs()){
                                throw new NumberFormatException();
                            }
                            jobsApp.add(jobs.getCreatedAt(num-1));
                            
                        }
                        break;
                    }
                    catch (NumberFormatException e){
                        System.out.print("Invalid input! Please enter a valid number to continue: ");
                    }
                }
                jobs.jobsApplied(jobsApp);
                menu(1);
            }
        }
    }



    
    /** 
     * Display main menu for applicant tag
     * @param created - whether the application has been created or not, 0 for not yet, 1 for created
     */
    private void menu(int created){
        String menu = Jobs.getNumberofJobs()+" jobs available. "+ jobsApp.size() +" applications submitted.\n" +
        "Please enter one of the following commands to continue:\n";

        String create = "- create new application: [create] or [c]\n";

        String next = "- list available jobs: [jobs] or [j]\n" +
        "- quit the program: [quit] or [q]\n" +
        "> ";

        System.out.print(menu);

        if (created == 0){
            System.out.print(create);
        }

        System.out.print(next);
    }
    
    
}
