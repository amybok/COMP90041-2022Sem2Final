import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * <h3>COMP90041, Sem2, 2022: Final Project</h3>
 * <p>Class Applied has methods to store jobs and applications for it, as well as display jobs and its applications
 * 
 * @author Gia Han Ly
 */
public class Applied {
    private String fileName = "applied.csv";
    private Scanner input = null;
    protected static ArrayList<ArrayList<Long>> listA = new ArrayList<>();

    //Setters and getters
    /**
     * Get list of jobs and its applications
     * @return list contains jobs and its applications
     */
    public static ArrayList<ArrayList<Long>> getAppliedList(){
        return listA;
    }

    //Methods
    /**
     * Load jobs and applications for it 
     */
    public void loadApplied(){
        initialiseList();
        createJobsApplicants();
    }

    /**
     * Read in applied file and store jobs with corresponding applications
     * The first is always jobID followed by applications
     * <table><tr><td>Job1:</td><td>Applicant1, Applicant2,...</td></tr>
     * <tr><td>Job2:</td><td>Applicant2</td></tr>
    */
    private void createJobsApplicants(){
        try{
            input = new Scanner(new FileInputStream(fileName));
        }
        catch (FileNotFoundException e){
            return;
        }

        if(Jobs.getJobsList().size() == 0){
            System.out.println("No jobs available.");
            return;
        }

        if (!input.hasNextLine()){
            return;
        }

        String line;
        Long app;

        while(input.hasNextLine()){
            line = input.nextLine();
            String[] element = line.split(",");

            // Application timestamp
            app = Long.parseLong(element[0]);

            for (int i = 3; i< element.length; i++ ){

                // Job timestamp
                long jobCreatedAt = Long.parseLong(element[i]);


                for (ArrayList<Long> item : listA){

                    // If job timestamp matches with job row, add application timestamp
                    if (jobCreatedAt == item.get(0)){
                        item.add(app);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Initialise jobs applications list by adding jobs rows
     */
    public void initialiseList(){
        ArrayList<Jobs>  listJobs = Jobs.getJobsList();
        

        if (listJobs.size() == 0){
            return;
        }
        
        for(Jobs j : listJobs ){
            ArrayList<Long> apApplied = new ArrayList<>();
            apApplied.add(j.getCreatedAt());
            listA.add(apApplied);
        }
    }

    /**
     * Print out jobs along with applications for it
     */
    public void display(){
        loadApplied();
        int row = 1;
        
        // Print job details
        for (ArrayList<Long> item : listA){
            for (Jobs j : Jobs.getJobsList()){
                if (j.getCreatedAt() == item.get(0)){
                    System.out.print("["+ row + "] ");
                    Jobs.printJob(j);
                }
            }
            int c = 0;

            // Print applications details
            for (int i = 1; i< item.size(); i++){
                int num = 0;
                for (Applicant a : Applicant.getApplicantsList()){
                    if (item.get(i) == a.createdAt){
                        // Print alpha numeric ordering
                        if (num == 0){
                            System.out.print("    ["+ Character.toString(97+(c%26))+"] ");
                        }
                        else{
                            System.out.print("    ["+ Character.toString(97+(c%26))+num+"] ");
                            if (c%26 == 25){
                                num++;
                            }
                        }
                        Applicant.printApplicant(a);
                        c++;
                    }
                }
            }

            row++;
        }
    }
}
