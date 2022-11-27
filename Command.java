

/**
 * <h3> COMP90041, Sem2, 2022: Final Project </h3>
 * <p>Class Command contains method to take in command line flags and call approriate menu
 * @author Gia Han Ly
 */
public class Command {
    private String jobsFile = "jobs.csv";
    private String applicantFile = "applications.csv";
    private String role;

    // Setter and getter
    /**
     * Get user role
     * @return role
     */
    public String getRole(){
        return role;
    }
    /**
     * Get applications file name
     * @return file name
     */
    public String getApplicationsFile(){
        return applicantFile;
    }
    /**
     * Get jobs file name
     * @return file name
     */
    public String getJobsFile(){
        return jobsFile;
    }



    /**
     * <p> Read command line flags
     * @param args - command line flag
     * @author Gia Han Ly
     */
    public void readCommand(String[] args){
        if (args.length == 0){
            this.printHelp();
        }
        // Read flag from command line input
        for (int c =0; c< args.length; c++){
            
            if (args[c].charAt(0) == '-'){

                if(args[c].equals("-h") || args[c].equals("--help")){
                    this.printHelp();
                }
                else if(args[c].equals("-r") || args[c].equals("--role")){

                    if(c+1 >= args.length){
                        System.out.println("ERROR: no role defined");
                        this.printHelp();
                    }
                    else if (args[c+1].equals("applicant") || args[c+1].equals("hr") || args[c+1].equals("audit")){
                        this.role = args[c+1];
                    }
                    else if (args[c+1].equals("") || args[c+1].charAt(0) == '-'){
                        System.out.println("ERROR: no role defined.");
                        this.printHelp();
                    }
                    else{
                        System.out.println("ERROR: " + args[c+1] + " is not a valid role.");
                        this.printHelp();
                    }
                    c++;
                }
                else if(args[c].equals("-j") || args[c].equals("--jobs")){
                    if (c+1 >= args.length){
                        this.printHelp();
                    }
                    else if (args[c+1].equals("") || args[c+1].charAt(0) == '-' || args[c+1].equals(" ")){
                        this.printHelp();
                    }
                    else{
                        this.jobsFile = args[c+1];
                        c++;
                    }
                }
                else if(args[c].equals("-a") || args[c].equals("--applications")){
                    if (c+1 >= args.length){
                        this.printHelp();
                    }
                    else if (args[c+1].equals("") || args[c+1].charAt(0) == '-' || args[c+1].equals(" ")){
                        this.printHelp();
                    }
                    else{
                        this.applicantFile = args[c+1];
                        c++;
                    }
                }
            }
            else {
                this.printHelp();
            }
        }
        if (this.role == null){
            this.printHelp();
        }
    }

    /**
     * <p> Print help menu and exit the program
     */
    private void printHelp(){
        String help = "HRAssistant - COMP90041 - Final Project\n\n" +
        "Usage: java HRAssistant [arguments]\n\n" +
        "Arguments:\n" +
        "    -r or --role            Mandatory: determines the user's role\n" +
        "    -a or --applications    Optional: path to applications file\n" +
        "    -j or --jobs            Optional: path to jobs file\n" +
        "    -h or --help            Optional: print Help (this message) and exit\n";

        System.out.print(help);
        System.exit(0);
    }

}
