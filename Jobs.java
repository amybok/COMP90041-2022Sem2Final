import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

/**
 * <h3> COMP90041, Sem2, 2022: Final Project </h3>
 * <p> Class Jobs has methods to create, save, print and read jobs object as well as methods to save applications for jobs.
 * @author Gia Han Ly
 * 
 */
public class Jobs extends Entity{
    private Scanner input = null;
    private String title;
    private LocalDate startDate;
    private static final int FIELDS = 6;
    private static ArrayList<Jobs> jobsList = new ArrayList<Jobs>(); 
    private ArrayList<Jobs> toBeRemoved = new ArrayList<Jobs>();
    private String fileName = "jobs.csv";
    private static String appliedFile = "applied.csv";

    // Constructors
    public Jobs(){}

    /**
     * Constructor for Jobs
     * @param createdAt timestamp
     * @param tilte job titlte
     * @param description job description
     * @param degree minimum degree required
     * @param salary salary
     * @param startDate start date
     */
    public Jobs(long createdAt, String tilte, String description, String degree, int salary, LocalDate startDate){
        this.createdAt = createdAt;
        this.title = tilte;
        this.summary = description;
        this.degree = degree;
        this.salary = salary;
        this.startDate = startDate;
    }

    
    //Setter and getter
    /**
     * Get Jobs object from timestamp
     * @param timestamp
     * @return Job - job
     */
    public static Jobs getJob(long timestamp){
        for (Jobs job : jobsList){
            if (job.createdAt == timestamp){
                return job;
            }
        }
        return null;
    }
    /** 
     * Get list of jobs available
     * @return ArrayList<Jobs> List of jobs available
     */
    public static ArrayList<Jobs> getJobsList(){
        return jobsList;
    }

    
    /** 
     * Get number of jobs
     * @return int - number of jobs available
     */
    public static int getNumberofJobs(){
        return jobsList.size();
    }

    
    /** 
     * Get unix timestamp of a job
     * @param index - index of job in jobs list
     * @return long - job timestamp
     */
    public long getCreatedAt(int index){
        return jobsList.get(index).createdAt;
    }

    
    /** 
     * Get unix timestamp of a job
     * @return long - timestamp
     */
    public long getCreatedAt(){
        return this.createdAt;
    }

    // Methods
    /** 
     * Print job title, summary, required degree, salary and start date
     * @param j - job
     */
    public static void printJob(Jobs j){
        System.out.println(j.title +" (" +j.summary+"). " + j.degree + ". Salary: " + j.salary + 
        ". Start Date: "+ j.startDate.format(formatter));
    }

    
    /** 
     * Save job to jobs file
     * @param newJob
     */
    public void saveJobs(Jobs newJob){
        PrintWriter outputStream = null;
        try{
            outputStream = new PrintWriter(new FileOutputStream(fileName, true));
        }
        catch (FileNotFoundException e){
            System.out.println("Applications File not found.");
        }

        outputStream.println(Long.toString(newJob.createdAt) + "," + newJob.title + "," + newJob.summary + "," +
        newJob.degree + "," + newJob.salary + "," + newJob.startDate);

        outputStream.close();
    }

    
    /** 
     * Take in user input and create new job
     * @param keyboard
     * @return Jobs
     */
    @Override
    public Jobs create(Scanner keyboard){

        Jobs job = new Jobs();

        System.out.print("# Create new Job\n"+ "Position Title: ");
        String temp = keyboard.nextLine();

        // Take in job title and check for valid input
        if (temp.equals("")){
            System.out.print("Ooops! Position Title must be provided: ");
            while(keyboard.hasNextLine()){
                temp = keyboard.nextLine();
                if (!temp.equals("")){
                    job.title = temp;
                    break;
                }
                System.out.print("Ooops! Position Title must be provided: ");
            }
        }
        else {
            job.title = temp;
        }

        // Take in job summary
        System.out.print("Position Description: ");
        job.summary = keyboard.nextLine();

        // Take in minimum degree requirement and check for valid input
        System.out.print("Minimum Degree Requirement: ");
        temp = keyboard.nextLine();
        while (!DEGREE.contains(temp)){
            System.out.print("Invalid input! Please specify Minimum Degree Requirement: ");
            temp = keyboard.nextLine();
        }
        job.degree = temp;

        // Take in salary and check for valid input
        System.out.print("Salary ($ per annum): ");
        while(true){
            try{
                temp = keyboard.nextLine();
                job.salary = Integer.parseInt("0" + temp);
                if (job.salary > 0){
                    break;
                }
                else{
                    System.out.print("Ooops! A valid Salary above 0 must be provided: ");
                }
            }
            catch (NumberFormatException e){
                System.out.print("Invalid input! Please specify Salary expectation: ");
                continue;
            }
        }

        // Take in start date and check for valid input
        System.out.print("Start Date: ");
        LocalDate current = LocalDate.of(2022, 11, 8);
        while(true){
            try{
                temp = keyboard.nextLine();
                job.startDate = LocalDate.parse(temp, formatter);
                if (job.startDate.isAfter(current)){
                    break;
                }
                else{
                    System.out.print("Ooops! A valid Start Date must be provided: ");
                }
            }
            catch (DateTimeParseException e){
                System.out.print("Invalid input! Please specify Start Date: ");
                continue;
            }
        }

        // Add unix timestamp
        job.createdAt = System.currentTimeMillis() / 1000L;

        return job;
    }

    
    /** 
     * Read jobs from jobs file and add to list of jobs
     * @param fileName
     */
    @Override
    public void read(String fileName){
        
        this.fileName = fileName;

        File file = new File(this.fileName);

        // If file doesnt exist, create new file
        if (!file.exists()){
            try {
                file.createNewFile();
                return;
            } 
            catch (IOException e) {
                this.fileName = "jobs.csv";
            }
        }

        // Read file and catch file not found exception
        try{
            input = new Scanner(new FileInputStream(this.fileName));
            if (!input.hasNextLine()){
                return;
            }
            input.nextLine();
        }
        catch (FileNotFoundException e){
            System.out.println("Jobs file not found.");
            return;
        }

        int row = 0;
        while (input.hasNextLine()){
            // Check for number of fields
            String[] line = input.nextLine().split(",");
            row ++;
            try{
                if(line.length != FIELDS){
                    throw new InvalidDataFormatException(row,"jobs");
                }
            }
            // Catch invalid row exception and print out the invalid row, continue parsing
            catch (InvalidDataFormatException e){
                System.out.println(e.getMessage());
                continue;
            }

            
            long create = Long.parseLong(line[0]);
            String tempTitle = line[1];
            String desc = line[2];
            String deg = line[3];
            int tempSalary = 0;
            LocalDate date = LocalDate.parse(line[5], formatter);

            // Read degree, throw invalid characteristic if invalid, continue parsing
            try {   
                if(!DEGREE.contains(deg)){
                    throw new InvalidCharacteristicException(row, "jobs");
                }
            }
            catch (InvalidCharacteristicException e) {
                System.out.println(e.getMessage());
            }
            
            // Read salary, throw invalid characteristic if invalid, continue parsing
            try {
                tempSalary = Integer.parseInt("0" + line[4]);
                if(tempSalary < 0){
                    throw new InvalidCharacteristicException(row, "jobs");
                }
            }
            catch (NumberFormatException e){
                System.out.println("WARNING: invalid number format in applications file in line {" + row + "}");
            }
            catch (InvalidCharacteristicException e) {
                System.out.println(e.getMessage());
            }

            // Create job object and add to list
            Jobs job = new Jobs(create, tempTitle, desc, deg, tempSalary, date);

            jobsList.add(job);
        }
        input.close();
    }


    /**
     * Display available jobs
     */
    public void jobsAvailable(){

        if(jobsList.size() == 0){
            System.out.println("No jobs available.");
            return;
        }
        
        int row = 1;

        for (Jobs j : jobsList){
            System.out.print("["+ row +"] ");
            printJob(j);
            row++;
        }
    }

    
    /** 
     * Write last name, first name and timestamp to applied file when applicant applied for a job
     * @param lastname
     * @param firstname
     * @param appCreatedAt
     */
    public void writeName(String lastname, String firstname, long appCreatedAt){
        String fileName = "applied.csv";

        PrintWriter outputStream = null;

        try{
            outputStream = new PrintWriter(new FileOutputStream(fileName, true));
        }
        catch (FileNotFoundException e){
            System.out.println("Applied File not found.");
        }

        outputStream.print(Long.toString(appCreatedAt) + "," + lastname + "," + firstname);
        outputStream.close();
    }

    /**
     *   End writing to applied file when applicant applied to a job
     */
    public void writeEnd(){
        String fileName = "applied.csv";

        PrintWriter outputStream = null;

        try{
            outputStream = new PrintWriter(new FileOutputStream(fileName, true));
        }
        catch (FileNotFoundException e){
            System.out.println("Applied File not found.");
        }

        outputStream.println();
        outputStream.close();
    }

    
    /** 
     * Remove job from jobs list after applied
     * @param jobsApplied - list of timestamp of jobs that applicant applied to 
     */
    public void jobsApplied(ArrayList<Long> jobsApplied){

        PrintWriter outputStream = null;

        try{
            outputStream = new PrintWriter(new FileOutputStream(appliedFile, true));
        }
        catch (FileNotFoundException e){
            System.out.println("Applied File not found.");
        }
        

        // Add job to remove list
        for (long choice : jobsApplied){
            removeJobs(choice);
        }

        // Remove jobs from list of jobs
        jobsList.removeAll(toBeRemoved);
        outputStream.close();
    }

    /**
     * Write jobs timestamps for jobs that applicant applied to, to applied file
     */
    public void writeJobs(){
        String fileName = "applied.csv";

        PrintWriter outputStream = null;

        try{
            outputStream = new PrintWriter(new FileOutputStream(fileName, true));
        }
        catch (FileNotFoundException e){
            System.out.println("Applied File not found.");
        }

        // Write applied jobs to file
        for (Jobs job : toBeRemoved){
            outputStream.print("," + job.createdAt);
        }
        outputStream.close();
    }

    /** 
     * Add jobs to remove list
     * @param createID - timestamp
     */
    private void removeJobs(long createID){
        for (Jobs job : jobsList){
            if (job.createdAt == createID){
                toBeRemoved.add(job);
            }
        }
    }


}

