import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * <h3> COMP90041, Sem2, 2022: Final Project </h3>
 * <p>Class Match contains the logic for matchmaking algorithm
 * @author Gia Han Ly
 */
public class Matchmaker {
    private Jobs job;
    private Applicant applicant;
    private Applied applied = new Applied();
    private static List<List<Long>> applicantsApplied = new ArrayList<>();
    private String requiredDegree;
    private double averageWAMThreshold = 0;
    private List<Applicant>  selected = new ArrayList<>();
    // Contains Match(Jobs, Applicant)
    protected static List<Matchmaker> listOfMatches = new ArrayList<>();


    public Matchmaker(){};

    /**
     * Constructor for Match
     * @param job job matched
     * @param applicant applicant matched
     */
    public Matchmaker(Jobs job, Applicant applicant){
        this.job = job;
        this.applicant = applicant;
    }

    // Setters and getters
    /**
     * Get matched applicant
     * @return matched applicant
     */
    protected Applicant getApplicant(){
        return this.applicant;
    }


    /**
     * Copy list of jobs and its applications
     */
    private void copyOfAppliedList() {
        applied.loadApplied();
        
        for (ArrayList<Long> row : Applied.getAppliedList()){
            applicantsApplied.add(row);
        }
    }

    /**
     * Save matches
     */
    public void saveMatches(){
        copyOfAppliedList();
        
        for (List<Long> row : applicantsApplied){
            
            // Create new Match
            Matchmaker match = new Matchmaker();
            match.matchMaker(row);
            // Add match to list of matches
            listOfMatches.add(match);
        }
    }

    /**
     * Print applicant matched for each job
     */
    protected static void printMatches(){
        int listItem = 1;
        for (Matchmaker match : listOfMatches){
            Jobs matchJob = match.job;
            Applicant matchApplicant = match.applicant;

            // If there is no match, conitnue to next job
            if (matchApplicant == null){
                continue;
            }
            System.out.print("["+ listItem +"] ");
            Jobs.printJob(matchJob);
            System.out.print("    Applicant match: ");
            Applicant.printApplicant(matchApplicant);

            listItem++;
        }
    }

    
    /** 
     * Select applicant based on WAM threshold (average WAM of all applications for the job) and minimum degree requirement
     * @param row - job with its applications
     */
    public void matchMaker(List<Long> row){

        // Calculate WAM for applications
        Applicant.wamCalc();
        // Get requirements for the job (degree, WAM threshold)
        jobRequirements(row);

        for (int i = 1; i<row.size(); i++){
            Applicant temp = Applicant.getApplicant(row.get(i));
            // Check if applicant meets minium degree requirement
            int check = new ApplicantDegreeComparator().compare(temp, requiredDegree);

            // If applicant pass the WAM threshold and degree requirement, add to list of potential applicants
            if (check <= 1 && temp.wam >= this.averageWAMThreshold){
                this.selected.add(temp);
            }
        }

        // Sort for wam, followed by oldest applicant
        Collections.sort(selected, Comparator.comparing(Applicant :: getAge, Comparator.reverseOrder()).thenComparing(new ApplicantWAMComparator()));


        // Select the highest rank applicant
        if (selected.size() > 0){
            this.applicant = selected.get(0);
        }
        else {
            this.applicant = null;
        }
    }


    
    /** 
     * Get requirements for job (WAM, degree)
     * @param row - job with applications
     */
    private void jobRequirements(List<Long> row){
        // Get job object from timestamp and get minimum degree required
        this.job = Jobs.getJob(row.get(0));
        this.requiredDegree = job.degree;
        double sumWAM = 0;
        

        for (int i = 1; i<row.size(); i++){
            Applicant temp = Applicant.getApplicant(row.get(i));

            // Check if applicant pass the minimum degree requirement, if yes, add to average WAM threshold calculation
            int check = new ApplicantDegreeComparator().compare(temp, requiredDegree);
            if (check >= 0){
                sumWAM += temp.wam;
            }
        }

        if (row.size()-1 > 0){
        this.averageWAMThreshold = sumWAM/(row.size()-1);
        }
    }
}
