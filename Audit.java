import java.util.ArrayList;

/**
 * <h3>COMP90041, Sem2, 2022: Final Project</h3>
 * <p>Class Audit has methods to calculate statistics of matches.
 * 
 * @author Gia Han Ly
 */
public class Audit {
    private Jobs jobs = new Jobs();
    private Applicant applications = new Applicant();
    private Applied applied = new Applied();
    private ArrayList<Applicant> removed = new ArrayList<>();
    private int jobsAvail;
    private int applicantAvail;
    private int numOfSuccessMatch = 0;

    //Constructor
    /**
     * Constructor for Audit
     * @param jobsFile - jobs file name
     * @param applicationsFile - applicantions file name
     */
    public Audit(String jobsFile, String applicationsFile){
        jobs.read(jobsFile);
        applications.read(applicationsFile);
    }

    // Methods
    /**
     * Print out the statistics
     */
    public void printStatistics(){
        setUp();
        printHeader();
        availJobs();
        numOfApplicants();
        successfulMatches();
        avgAgeSuccessful();
        avgWAMSuccessful();
        numOfGender("male");
        numOfGender("female");
        degreeStats("PHD");
    }

    /**
     * Match all applicants available with all jobs available and select an applicant to move forward.
     * 
     */
    private void setUp(){
        applied.initialiseList();
        for (ArrayList<Long> row : Applied.getAppliedList()){
            for (Applicant a : Applicant.getApplicantsList()){
                if (removedApplicant(a.createdAt) == true){
                    continue;
                }
                row.add(a.createdAt);
            }
            matchGenerate(row);
        }
    }

    /**
     * Generate match for each jobs, applicant that had been considered will not be considered for the next job
     * @param row - job and its applications
     */
    private void matchGenerate(ArrayList<Long> row){
        Matchmaker matches = new Matchmaker();
        matches.matchMaker(row);
        Matchmaker.listOfMatches.add(matches);
        removed.add(matches.getApplicant());
    }

    /**
     * Check whether an applicant has been considered
     * @param timestamp - timestamp of applications
     * @return boolean true if the applicant has been considered, false if no
     */
    private boolean removedApplicant(long timestamp){
        for (Applicant applicant : removed){
            if (timestamp == applicant.createdAt){
                return true;
            }
        }
        return false;
    }

    /**
     * Display number of  available jobs
     */
    private void availJobs(){
        this.jobsAvail = Jobs.getNumberofJobs();
        if (jobsAvail == 0){
            System.out.println("No jobs available for interrogation.");
        }
        System.out.println("Available jobs: " + jobsAvail);
    }

    /**
     * Get the number of successful applicants
     */
    private void successfulMatches(){
        for (int i = 0; i < Matchmaker.listOfMatches.size(); i++){
            Matchmaker match = Matchmaker.listOfMatches.get(i);
            if (match.getApplicant() != null){
                this.numOfSuccessMatch += 1;
            }
        }
        System.out.println("Number of successful matches: " + numOfSuccessMatch);
    }

    /**
     * Display number of available applicants
     */
    private void numOfApplicants(){
        this.applicantAvail = Applicant.getNumberOfApplicant();
        if (applicantAvail == 0){
            System.out.println("No applicants available for interrogation.");
        }
        System.out.println("Total number of applicants: " + applicantAvail);
    }

    /**
     * Calculate and print the average age of successful applicants
     */
    private void avgAgeSuccessful(){
        int totalAge = 0;
        double averageAge;
        for (int i = 0; i < Matchmaker.listOfMatches.size(); i++){
            Matchmaker match = Matchmaker.listOfMatches.get(i);
            if (match.getApplicant() == null){
                break;
            }
            totalAge += match.getApplicant().getAge();
        }
        averageAge = totalAge/numOfSuccessMatch;
        System.out.printf("Average age: %.2f ",averageAge);
        avgAgeAll();
    }

    /**
     * Calculate and print the age of all applicants
     */
    private void avgAgeAll(){
        int totalAge = 0;
        double averageAge;
        for (Applicant applicant : Applicant.getApplicantsList()){
            totalAge += applicant.getAge();
        }
        averageAge = totalAge/Applicant.getNumberOfApplicant();
        System.out.printf("(average age of all applicants: %.2f) \n",averageAge);
    }

    /**
     * Calculate and print the average WAM of successful applicants
     */
    private void avgWAMSuccessful(){
        double totalWAM = 0;
        double averageWAM;
        for (int i = 0; i < Matchmaker.listOfMatches.size(); i++){
            Matchmaker match = Matchmaker.listOfMatches.get(i);
            if (match.getApplicant() == null){
                break;
            }
            totalWAM += match.getApplicant().wam;
        }
        averageWAM = totalWAM/numOfSuccessMatch;
        System.out.printf("Average WAM: %.2f ", averageWAM);
        avgWAMAll();
    }

    /**
     * Calculate and print the average WAM of all applicants
     */
    private void avgWAMAll(){
        double totalWAM = 0;
        double averageWAM;
        for (Applicant applicant : Applicant.getApplicantsList()){
            totalWAM += applicant.wam;
        }
        averageWAM = totalWAM/Applicant.getNumberOfApplicant();
        System.out.printf("(average WAM of all applicants: %.2f) \n", averageWAM);
    }

    /**
     * Calculate and print out the gender ratio between successful and all applicants
     * @param gender - gender for ratio to be calculated
     */
    private void numOfGender(String gender){
        double numOfGenderSuccessful = 0;
        double numOfGenderTotal = 0;

        for (Applicant a : Applicant.getApplicantsList()){
            if (a.getGender().equals(gender)){
                numOfGenderTotal += 1;
            }
        }

        for (Matchmaker match : Matchmaker.listOfMatches){
            if (match.getApplicant() == null){
                continue;
            }
            if (match.getApplicant().getGender().equals(gender)){
                numOfGenderSuccessful += 1;
            }
        }

        double stat = numOfGenderSuccessful/numOfGenderTotal;

        System.out.printf("%s: %.1f\n", gender, stat);
    }

    /**
     * Calculate degree statistics
     * @param degree - degree type for ratios to be calculated
     */
    private void degreeStats(String degree){
        double numOfDegreeSuccessful = 0;
        double numOfDegreeTotal = 0;

        for (Applicant a : Applicant.getApplicantsList()){
            if (a.degree.equals(degree)){
                numOfDegreeTotal += 1;
            }
        }

        for (Matchmaker match : Matchmaker.listOfMatches){
            if (match.getApplicant() == null){
                continue;
            }
            if (match.getApplicant().degree.equals(degree)){
                numOfDegreeSuccessful += 1;
            }
        }

        if (numOfDegreeTotal != 0){
            double stat = numOfDegreeSuccessful/numOfDegreeTotal;
            System.out.printf("%s: %.1f\n",degree, stat);
        }
        
    }

    /**
     * Print header for audit menu
     */
    private void printHeader(){
        String header = "======================================\n" +
        "# Matchmaking Audit\n" +
        "======================================\n";

        System.out.print(header);
    }
}

