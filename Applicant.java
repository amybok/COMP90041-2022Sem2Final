
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.io.PrintWriter; 
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <h3> COMP90041, Sem2, 2022: Final Project </h3>
 * <p> Class Applicant contains methods to read in applications file, parse and store into applicants list.
 * @author Gia Han Ly
 * 
 */
public class Applicant extends Entity{
    private Scanner input = new Scanner(System.in);
    private String lastname;
    private String firstname;
    private int age;
    private String gender;
    private int COMP90041, COMP90038, COMP90007, INFO90002;
    private LocalDate availability;
    private static final int FIELDS = 13;
    private static String fileName = "applications.csv";
    
    private static ArrayList<Applicant> applicantsList = new ArrayList<>(); 


    // Constructors
    public Applicant(){}

    /**
     * Constructor for Applicant object
     * @param created timestamp
     * @param lastname lastname
     * @param firstname firstname
     * @param careerSummary career summary
     * @param age age
     * @param gender gender
     * @param highestDegree highest degree
     * @param COMP90041 mark for COMP90041
     * @param COMP90038 mark for COMP90038
     * @param COMP90007 mark for COMO90007
     * @param INFO90002 mark for INFO90002
     * @param salaryExpectations salaray expectations
     * @param availability availability/start date
     */
    public Applicant(long created, String lastname, String firstname, String careerSummary, int age, String gender, 
        String highestDegree, int COMP90041, int COMP90038, int COMP90007, int INFO90002, int salaryExpectations, LocalDate availability){
            this.createdAt = created;
            this.lastname =lastname;
            this.firstname = firstname;
            this.summary = careerSummary;
            this.age = age;
            this.gender = gender;
            this.degree = highestDegree;
            this.COMP90041 = COMP90041;
            this.COMP90038 = COMP90038;
            this.COMP90007 = COMP90007;
            this.INFO90002 = INFO90002;
            this.salary = salaryExpectations;
            this.availability = availability;
    }

    // Setters and getters
    /** 
     * Set applications file name
     * @param name - file name
     */
    public void setApplicantionsFile(String name){
        fileName = name;
    }

    /**
     * Get Applicant object from timestamp
     * @param timestamp
     * @return Applicant 
     */
    public static Applicant getApplicant(long timestamp){
        for (Applicant applicant : applicantsList){
            if (applicant.createdAt == timestamp){
                return applicant;
            }
        }
        return null;
    }

    /** 
     * Get list of available appilcants
     * @return list of applicants
     */
    public static ArrayList<Applicant> getApplicantsList(){
        return applicantsList;
    }

    
    /** 
     * Get number of available applicants
     * @return size of applicants list - number of applicants
     */
    public static int getNumberOfApplicant(){
        return applicantsList.size();
    }

    /**
     * Get applicant gender 
     * @return applicant gender
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * Get applicant age
     * @return Applicant age
     */
    public int getAge(){
        return this.age;
    }

    /** 
     * Get applicant last name
     * @return applicant last name
     */
    public String getLastname(){
        return this.lastname;
    }

    
    /** 
     * Get applicant first name
     * @return applicant first name
     */
    public String getFirstname(){
        return this.firstname;
    }

    
    /** 
     * Get applicant unix timestamp
     * @return applicant unix timestamp
     */
    public long getCreatedAt(){
        return this.createdAt;
    }

    
    /** 
     * Get applicant mark for COMP90007
     * @return int mark for COMP90007
     */
    public int getCOMP90007(){
        return this.COMP90007;
    }

    
    /** 
     * Get applicant mark for COMP90038
     * @return int mark for COMP90038
     */
    public int getCOMP90038(){
        return this.COMP90038;
    }

    
    /** 
     * Get applicant mark for COMP90041
     * @return int mark for COMP90041
     */
    public int getCOMP90041(){
        return this.COMP90041;
    }

    
    /** 
     * Get applicant mark for INFO90002
     * @return int mark for INFO90002
     */
    public int getINFO90002(){
        return this.INFO90002;
    }

    /**
     * Get applicant availability
     * @return
     */
    public LocalDate getStartDate(){
        return this.availability;
    }
    
    // Methods
    /** 
     * Print applicant details
     * @param a - applicant object
     */
    public static void printApplicant(Applicant a){
        System.out.print(a.lastname +", " + a.firstname +" (");
        String temp;

            temp = a.degree;

            // Print n/a if degree is empty
            if (temp == null || temp.isBlank()){
                System.out.print("n/a");
            }
            else {
                System.out.print(temp);
            }

            System.out.print("): " + a.summary + ". Salary Expectations: "+ a.salary +". Available: ");

            // Print n/a if there is no availability
            try {
                temp = a.availability.format(formatter);
            }
            catch (NullPointerException e){
                temp = "n/a";
            }

            System.out.println(temp +".");
    }


    /**
     * Print out the available applicants in applications file
     */
    public void applicantAvailable(){

        if(applicantsList.size() == 0){
            System.out.println("No applicants available.");
            return;
        }

        for (int i =0; i<applicantsList.size(); i++){

            Applicant a = applicantsList.get(i);

            int num = i+1;

            System.out.print("["+num+"] "); 

            printApplicant(a);
        }
    }

    
    /** 
     * Save newly created applicant and write into applications file
     * @param newApp - applicant object
     */
    public static void saveApplicant(Applicant newApp){

        PrintWriter outputStream = null;
        try{
            outputStream = new PrintWriter(new FileOutputStream(fileName, true));
        }
        catch (FileNotFoundException e){
            System.out.println("Applications File not found.");
        }
        outputStream.println(Long.toString(newApp.createdAt)+","+ newApp.lastname+","+ newApp.firstname +","+ newApp.summary+
        "," + newApp.age + "," + newApp.gender + "," + newApp.degree + "," + newApp.COMP90041 + "," + newApp.COMP90038 + "," + 
        newApp.COMP90007 + "," + newApp.INFO90002 + "," + newApp.salary + "," + newApp.availability.format(formatter));

        outputStream.close();
    }

    
    /** 
     * Take in user input and create new applicant object
     * @param keyboard
     * @return Applicant
     */
    @Override
    public Applicant create(Scanner keyboard){

        Applicant applicant = new Applicant();

        System.out.print("# Create new Application\n"+ "Lastname: ");

        // Take in applicant last name, check for valid input
        String temp = keyboard.nextLine();
        if (temp.equals("")){
            System.out.print("Ooops! Lastname must be provided: ");
            while(keyboard.hasNextLine()){
                temp = keyboard.nextLine();
                if (!temp.equals("")){
                    applicant.lastname = temp;
                    break;
                }
                System.out.print("Ooops! Lastname must be provided: ");
            }
        }
        else {
            applicant.lastname = temp;
        }
        
        // Take in applicant first name, check for valid input
        System.out.print("Firstname: ");
        temp = keyboard.nextLine();
        if (temp.equals("")){
            System.out.print("Ooops! Firstname must be provided: ");
            while(keyboard.hasNextLine()){
                temp = keyboard.nextLine();
                if (!temp.equals("")){
                    applicant.firstname = temp;
                    break;
                }
                System.out.print("Ooops! Firstname must be provided: ");
            }
        }
        else {
            applicant.firstname = temp;
        }

        // Take in career summary
        System.out.print("Career Summary: ");
        applicant.summary = keyboard.nextLine();

        // Take in age and check for valid input
        System.out.print("Age: ");
        while(true){
            try{
                temp = keyboard.nextLine();
                applicant.age = Integer.parseInt(temp);
                if (applicant.age > 17 && applicant.age <101){
                    break;
                }
                else{
                    System.out.print("Ooops! A valid Age between 18 and 100 must be provided: ");
                }
            }
            catch (NumberFormatException e){
                System.out.print("Invalid input! Please specify Age: ");
                continue;
            }
        }

        // Take in gender and check for valid input
        System.out.print("Gender: ");
        String check = keyboard.nextLine();
        while (!GENDER_LIST.contains(check)){
            System.out.print("Invalid input! Please specify Gender: ");
            check = keyboard.nextLine();
        }
        applicant.gender = check;
        
        // Take in degree and check for valid input
        System.out.print("Highest Degree: ");
        check = keyboard.nextLine();
        while (!DEGREE.contains(check)){
            System.out.print("Invalid input! Please specify Highest Degree: ");
            check = keyboard.nextLine();
        }
        applicant.degree = check;
        
        // Take in makr and check for valid input
        System.out.print("Coursework: \n" + "- COMP90041: ");
        while(true){
            try{
                temp = keyboard.nextLine();
                applicant.COMP90041 = Integer.parseInt(temp);
                if (applicant.COMP90041 > 48 && applicant.COMP90041 <101){
                    break;
                }
                else{
                    System.out.print("Ooops! A valid Mark between 49 and 100 must be provided: ");
                }
            }
            catch (NumberFormatException e){
                System.out.print("Invalid input! Please specify Coursework: ");
                continue;
            }
        }

        System.out.print("- COMP90038: ");
        while(true){
            try{
                temp = keyboard.nextLine();
                applicant.COMP90038 = Integer.parseInt(temp);
                if (applicant.COMP90038 > 48 && applicant.COMP90038 <101){
                    break;
                }
                else{
                    System.out.print("Ooops! A valid Mark between 49 and 100 must be provided: ");
                }
            }
            catch (NumberFormatException e){
                System.out.print("Invalid input! Please specify Coursework: ");
                continue;
            }
        }


        System.out.print("- COMP90007: ");
        while(true){
            try{
                temp = keyboard.nextLine();
                applicant.COMP90007 = Integer.parseInt(temp);
                if (applicant.COMP90007 > 48 && applicant.COMP90007 <101){
                    break;
                }
                else{
                    System.out.print("Ooops! A valid Mark between 49 and 100 must be provided: ");
                }
            }
            catch (NumberFormatException e){
                System.out.print("Invalid input! Please specify Coursework: ");
                continue;
            }
        }

        System.out.print("- INFO90002: ");
        while(true){
            try{
                temp = keyboard.nextLine();
                applicant.INFO90002 = Integer.parseInt(temp);
                if (applicant.INFO90002 > 48 && applicant.INFO90002 <101){
                    break;
                }
                else{
                    System.out.print("Ooops! A valid Mark between 49 and 100 must be provided: ");
                }
            }
            catch (NumberFormatException e){
                System.out.print("Invalid input! Please specify Coursework: ");
                continue;
            }
        }

        System.out.print("Salary Expectations ($ per annum): ");
        while(true){
            try{
                temp = keyboard.nextLine();
                applicant.salary = Integer.parseInt(temp);
                if (applicant.salary > 0){
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

        // Take in date and check for valid input and format
        System.out.print("Availability: ");
        LocalDate current = LocalDate.of(2022, 11, 8);
        while(true){
            try{
                temp = keyboard.nextLine();
                applicant.availability = LocalDate.parse(temp, formatter);
                if (applicant.availability.isAfter(current)){
                    break;
                }
                else{
                    System.out.print("Ooops! A valid Availability must be provided: ");
                }
            }
            catch (DateTimeParseException e){
                System.out.print("Invalid input! Please specify Availabity: ");
                continue;
            }
        }

        // Add unix timestamp
        applicant.createdAt = System.currentTimeMillis() / 1000L;
        
        return applicant;
    }


    /**
     * Read in applications file and parse it into applicant object. Applicant objects are then stored in application list.
     * @param filename
     *
     */
    @Override
    public void read(String filename){
        
        // Read file and catch file not found exception

        File file = new File(filename);

        // If file doesnt exist, create new file
        if (!file.exists()){
            try {
                file.createNewFile();
                return;
            } 
            catch (IOException e) {
                filename = fileName;
            }
        }

        // Read file and catch file not found exception
        try{
            input = new Scanner(new FileInputStream(filename));
            if (!input.hasNextLine()){
                return;
            }
            input.nextLine();
        }
        catch (FileNotFoundException e){
            System.out.println("Applications file not found.");
            return;
        }

        // Check for valid row
        int row = 0;
        while (input.hasNextLine()){
            // Regex used to catch comma in quote
            String[] line = input.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            row ++;
            try{
                if(line.length != FIELDS){
                    throw new InvalidDataFormatException(row,"applications");
                }
            }
            // Catch invalid row exception and print out the invalid row, continue parsing
            catch (InvalidDataFormatException e){
                System.out.println(e.getMessage());
                if (line.length < FIELDS-1){
                    continue;
                }
            }

            // Fields in applications
            int apCreated = Integer.parseInt(line[0]);
            String lname = line[1];
            String fname = line[2];
            int apAge;
            String apGender = line[5].toLowerCase();
            String degreeParse = line[6];

            // Initialise to prevent null exception
            int C941 = 0;
            int C938 = 0;
            int C907 = 0;
            int I902 = 0;
            int salaryExpect = 0;
            LocalDate avail;
            
            try{
                apAge = Integer.parseInt(line[4]);
            }
            catch(NumberFormatException e){
                System.out.println("WARNING: invalid number format in applications file in line {" + row + "}");
                continue;
            }

            try{
                C941 = Integer.parseInt("0" + line[7]);
                C938 = Integer.parseInt("0" + line[8]);
                C907 = Integer.parseInt("0" + line[9]);
                I902 = Integer.parseInt("0" + line[10]);
                salaryExpect = Integer.parseInt(line[11]);
            }
            catch(NumberFormatException e){
                System.out.println("WARNING: invalid number format in applications file in line {" + row + "}");
            }
            
            try {   
                if(!DEGREE.contains(degreeParse)){
                    throw new InvalidCharacteristicException(row, "applications");
                }
                if(!GENDER_LIST.contains(apGender)){
                    throw new InvalidCharacteristicException(row, "applications");
                }
                if(C941 < 49 || C941 > 100){
                    throw new InvalidCharacteristicException(row, "applications");
                }
                if(C907 < 49 || C907 > 100){
                    throw new InvalidCharacteristicException(row, "applications");
                }
                if(C938 < 49 || C938 > 100){
                    throw new InvalidCharacteristicException(row, "applications");
                }
                if(I902 < 49 || I902 > 100){
                    throw new InvalidCharacteristicException(row, "applications");
                }
            }
            catch(InvalidCharacteristicException e){
                System.out.println(e.getMessage());
            }
            
            try {
                avail = LocalDate.parse(line[12], formatter);
            }
            catch (ArrayIndexOutOfBoundsException e){
                avail = null;
            }

            Applicant applicant = new Applicant(apCreated, lname, fname, line[3], apAge, apGender, degreeParse, C941, C938, C907,
            I902, salaryExpect, avail);

            applicantsList.add(applicant);
        }
    }

    /**
     * Calculate wam for each applicants
     */
    public static void wamCalc(){
        for (Applicant a : applicantsList){
            a.wam = ((a.getCOMP90007() + a.getCOMP90038() + a.getCOMP90041() +a.getINFO90002())/4);
        }
    }
}
