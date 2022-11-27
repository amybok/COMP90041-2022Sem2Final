import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * <h3>COMP90041, Sem2, 2022: Final Project</h3>
 * <p>Class HRAssistant is used to take in command flags from running HRAssistant. Run the approriate type of menu, functionalities and 
 * display welcome messages.
 * @author Gia Han Ly
 */
public class HRAssistant {

    public static void main(String[] args){

        // Read command line option
        Command command = new Command();
        command.readCommand(args);

        String appFile = command.getApplicationsFile();
        String jobsFile = command.getJobsFile();

        // Go to HR menu if role flag is set to hr
        if(command.getRole().equals("hr")){
            displayWelcomeMessage("hr");
            HRmenu hrMenu = new HRmenu(jobsFile, appFile);
            hrMenu.getInput();
            System.out.println();
        }

        // Go to applicant menu if role flag is set to applicant
        else if(command.getRole().equals("applicant")){
            displayWelcomeMessage("applicant");
            ApplicantMenu menu = new ApplicantMenu(jobsFile, appFile);
            menu.getInput();
            System.out.println();
        }

        else if (command.getRole().equals("audit")){
            Audit audit = new Audit(jobsFile, appFile);
            audit.printStatistics();
        }
        
    }

    /**
     * Display welcome messages depends on role input
     * @param role
     */
    private static void displayWelcomeMessage(String role) {

        Scanner inputStream = null;
        String filename = null;

        if(role.equals("hr")){
            filename = "welcome_hr.ascii";
        }
        else if(role.equals("applicant")){
            filename = "welcome_applicant.ascii";
        }

        try{
            inputStream = new Scanner(new FileInputStream(filename));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Welcome File not found.");
        }

        while(inputStream.hasNextLine())
        {
            System.out.println(inputStream.nextLine());
        }
    }

}
