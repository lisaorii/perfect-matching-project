import java.util.Vector;
import javafoundations.*;
import javafoundations.exceptions.*; 
import java.util.*;
import java.io.*;
/**
 * The Organizer manages a weighted bipartite graph containing Student and 
 * Course vertices. An organizer object undergoes the perfect matching 
 * algorithm in order to assign the maximum number of students to courses. 
 *
 * @author Lisa Orii, Mileva Van Tuyl, Jade Zhu
 * @version December 13, 2018
 */
public class Organizer // does not implement Graph<T>, don't need Ts
{
    // instance variables
    private Vector<Student> students; // Student vertices
    private Vector<Course> courses; // Course vertices
    private boolean[][] options; // T if course is preferred, F otherwise
    private int stVCount; // student vertices added so far
    private int courVCount; // course vertices added so far
    private final int DEFAULT_CAPACITY = 10; // just for the constructor
    private final int NOT_FOUND = -1; // indexOf returns -1 if vector does not contain element   
    private Vector<Student> leftover; // unassigned students

    /**
     * Constructor for objects of class Organizer.
     */
    public Organizer()
    {
        // initialise instance variables
        this.students = new Vector<Student>();
        this.courses = new Vector<Course>();
        this.options = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY]; // temporary

        this.leftover = new Vector<Student>(); 

        this.stVCount = 0;
        this.courVCount = 0;
    }

    /**
     * Reads in and processes all the student information from a folder of 
     * files and adds all the students to the students vector. 
     * After this method is executed the students vector is complete.
     * 
     * Assumes the student text file is properly formatted (BNum must be an integer)
     * 
     * @param dirName name of the directory containing all the students' information
     * @exception ElementNotFoundException Invalid course entered by student
     */
    public void createAllStudents(String dirName){
        // Indicates whether the student course was found in courses/ is valid
        boolean found; 

        // Creates an object for the directory of students 
        File studentDir = new File(dirName); 

        // Executes if given dirName is a directory
        if (studentDir.isDirectory()){ 
            //System.out.println ("Is a directory");

            // Creates an array of File objects for each student within the directory
            File[] studentFiles = studentDir.listFiles(); 
            // Iterates through each file (Student textfile) in the directory
            for (File stFile: studentFiles){
                //System.out.println(stFile.getName());
                //System.out.println(stFile.getAbsolutePath()); 
                try{
                    // Create a scanner to read the txt file containing the student info
                    Scanner scan = new Scanner(stFile);
                    // Creates a student object with the username and bmum info
                    if (scan.hasNext()) {
                        String username = scan.next(); 
                        int bNumber = scan.nextInt(); 
                        Student currentSt = new Student(username, bNumber);  

                        // Reads in all the student's course preferences
                        while (scan.hasNext()){
                            // Gets student's course preference (string)
                            String currentCourse = scan.next();

                            //System.out.println(currentCourse); 

                            found = false; 
                            // Iterates through all the courses
                            for (Course course: courses){ 
                                // Checks whether the course (String) is in courses vector 
                                if (!found && currentCourse.equals(course.getCourseName())){
                                    //System.out.println(course.getCourseName() + "found"); 

                                    // Adds the course object to the student choices in student object. 
                                    currentSt.addChoice(course);
                                    // Indicates the course (String) had been found
                                    found = true; 
                                }
                            }
                            // Student's course was not in the courses vector
                            if (!found) {
                                throw new ElementNotFoundException("Error:" + stFile.getName() + " - invalid course.");
                            }
                        }
                        // Adds the student to the students vector
                        addStudent(currentSt);
                        scan.close();
                    }

                } catch (FileNotFoundException ex){
                    System.out.println("Student file does not exist"); 
                    // Stops functioning of entire program
                    System.exit(0); 
                } catch (InputMismatchException ex){
                    System.out.println("Error: " + stFile.getName() + "is incorrectly formatted."); 
                    // Stops functioning of entire program
                    System.exit(0); 
                }
            }
        } else {
            System.out.println ("Given pathname is not a directory."); 
        }
    }

    /**
     * Adds a student to the students collection. 
     * If the student vertex already exists, nothing is inserted.
     * Note: The organizer is created only after the vertices have been finalized, 
     * so this method only applies before the organizer object has been instantiated.
     * 
     * @param newStudent the student to be added
     */
    public void addStudent(Student newStudent)
    {
        if (!students.contains(newStudent)) { // if vertex not already there
            // add it to students vector
            students.add(newStudent);
            // add to options[][] - implicit via indices
            stVCount++;
        }
        else {
            System.out.println("Student already exists. Student was not added."); 
        }
    }
    
    /**
     * Adds a course to the courses collection. 
     * If the course vertex already exists, nothing is inserted.
     * Note: The organizer is created only after the vertices have been finalized, 
     * so this method only applies before the organizer object has been instantiated.
     * 
     * @param newCourse the course to be added
     */
    public void addCourse(Course newCourse)
    {
        if (!courses.contains(newCourse)) { // if vertex not already there
            // add it to courses vector
            courses.add(newCourse);
            // add to options[][] - implicit via indices
            courVCount++;
        }
        else {
            System.out.println("Course already exists. Course was not added."); 
        }
    }

    /* The removeStudent and removeCourse methods are not currently in use. 
     * When we build upon the user interface, these methods will be used. */
    /**
     * Removes a student vertex from the graph. If the student vertex does not exist, it does not change the graph.
     * Note: The organizer is created only after the vertices have been finalized, so this method 
     * only applies before the organizer object has been instantiated
     * 
     * @param target the student to be removed
     */
    public void removeStudent(Student target)
    {
        int index = students.indexOf(target); 

        if (index != NOT_FOUND){ // If the student exists
            students.remove(target); 
            stVCount--; 
        } else { // If the student does not exist 
            System.out.println("Student does not exists"); 
        }
    }

    /**
     * Removes a course vertex from the graph. If the course vertex does not exist, it does not change the graph.
     * Note: The organizer is created only after the vertices have been finalized, so this method 
     * only applies before the organizer object has been instantiated
     * 
     * @param target the course to be removed
     */
    public void removeCourse(Course target)
    {
        int index = courses.indexOf(target);

        if (index != NOT_FOUND){ // If the course exists, remove course
            courses.remove(target); 
            courVCount--; 
        } else { // If the course does not exist 
            System.out.println("Course does not exists"); 
        }
    }

    /** 
     * Checks to see if the course name already exists in the collection. 
     * 
     * @param courseName the name of the target course
     * @return true if the course name already exists and false otherwise
     */
    public boolean nameExists(String courseName)
    {
        for (Course course: courses) {
            if (course.getCourseName().equals(courseName)) {
                // Course has been found
                return true;
            }
        }
        // Course has not been found
        return false;
    }

    /**
     * Gets the course names (Strings) of each course in the graph.
     * 
     * @return a vector of Strings of all the course names of the courses in the graph
     */
    public Vector<String> getCourseNames()
    {
        Vector<String> courseNames = new Vector<String>();
        // Iterate through all the courses and adds all the course names to the result vector 
        for (int i = 0; i < courVCount; i++) {
            courseNames.add(courses.get(i).getCourseName());
        }
        return courseNames;
    }

    /**
     * Populates options[][] by inserting a "choice" (an edge) between 
     * two vertices (one student to one course), for all students.
     * If the student has listed the course as one of their choices, then cell is T.
     * By default, cells in options[][] are false.
     * If one or both vertices do not exist, ignores the addition.
     */
    public void addOptions() 
    {
        // Only need as many spaces in the 2D array as necessary
        // Student and Course vertices have been finalized at this point
        boolean[][] updatedOptions = new boolean[stVCount][courVCount];
        this.options = updatedOptions;

        // for each student in the students vertex vector
        for (int i = 0; i < stVCount; i++) {
            Student current = students.get(i); // the current student of interest
            //System.out.println("current student: " + current);

            // get this student's choices
            Vector<Course> currentChoices = current.getChoices();
            //System.out.println("current student's choices (vector of course objects): " + currentChoices);

            // for each course in the courses vertex vector
            for (int j = 0; j < courVCount; j++) {
                // if the course is in this student's choices, set as true
                if (currentChoices.contains(courses.get(j))) {
                    options[i][j] = true;
                }
                // else, options[i][j] = false
            }
        }
    }

    /**
     * A recursive function that attempts to find a match between a student target
     * and a course he/she selected as one of his/her "choices".
     * Iterates through each possible course. If the course is not full, the target
     * student is directly added to the roster. 
     * If all the courses this student has selected are full, recursively calls 
     * this method for each student on the roster of one of this student's choices,
     * for each of this student's choices.
     * 
     * @param target the Student trying to be placed into a course
     * @param seen[] an array of booleans that keeps track of which courses the student has been attempted to be placed into
     * @return true if a match can be found between a student and a course he/she chose,
     *         false if a match cannot be made
     */
    public boolean findMatch(Student target, boolean seen[])
    {
        int studentInd = students.indexOf(target); 
        //System.out.println("\nfindMatch for student " + target.getName() + " at index: " + studentInd);
        //System.out.println("before, is "  + target.getName() + " enrolled? " + target.getEnrolled());

        boolean foundM = false; // have not found a match for this student yet
        boolean triedAllCourses = false; // have not iterated through all courses yet

        while (!foundM && !triedAllCourses) {
            // for each course i in courses vertex vector
            for (int i = 0; i < courVCount; i++) {
                // if this course is one of the student's choices
                if (options[studentInd][i]) {
                    Course currentCourse = courses.get(i);

                    // Note: these two if/else if conditionals contain the same actions, but separated for testing/printing purposes
                    // if course is empty, directly add student
                    if (currentCourse.getNumStudents() == 0) {
                        currentCourse.addStudent(target);
                        target.enrollStudent(currentCourse.getCourseName());
                        //System.out.println(target.getName() + " gets into empty class " + currentCourse.getCourseName());
                        //System.out.println("is " + target.getName() + " enrolled? " + target.getEnrolled());
                        foundM = true; // found a match
                        return true; // exit, returning true
                    }
                    // if course has space (not empty)
                    else if (!currentCourse.isFull()) {
                        currentCourse.addStudent(target);
                        target.enrollStudent(currentCourse.getCourseName());
                        //System.out.println(currentCourse.getCourseName() + " not yet full, added " + target.getName());
                        //System.out.println("is " + target.getName() + " enrolled? " + target.getEnrolled());
                        foundM = true; // found a match
                        return true; // exit, returning true
                    }
                }
                // move onto next course

                if (i == courVCount-1) {
                    triedAllCourses = true;
                }
            }
        }

        // if we still haven't a match, recurse on each of the previous students in roster
        while (!foundM && !target.getEnrolled()) {
            // for each course i in courses vertex vector
            for (int i = 0; i < courVCount; i++) {
                if (options[studentInd][i] && !seen[i]) {
                    //System.out.println("no straightfoward seats open, about to recurse");

                    // mark i as visited
                    seen[i] = true;
                    Course currentCourse = courses.get(i);

                    // try recursing on each previous student
                    for (int j = 0; j < currentCourse.getNumStudents(); j++) {
                        Student aPrevStud = currentCourse.getRoster().get(j);
                        //System.out.println("trying to recurse on " + aPrevStud);

                        if (findMatch(aPrevStud, seen)) { 
                            //System.out.print("---successfully recursed---\n");

                            // remove previous student from this course to make room for current target student
                            currentCourse.removeStudent(aPrevStud); 
                            //System.out.println("roster after removing prevStudent: " + currentCourse.getRoster());

                            // add current target student to this course's roster (no longer full)
                            currentCourse.addStudent(target);
                            // update the current target student's status
                            target.enrollStudent(currentCourse.getCourseName());
                            
                            //System.out.println(target.getName() + " replaces " + aPrevStud.getName() + " in " + currentCourse.getCourseName());
                            //System.out.println("is " + aPrevStud.getName() + " still enrolled? " + target.getEnrolled());
                            //System.out.println(target.getName() + " was annoying but now enrolled? " + target.getEnrolled());
                            foundM = true;
                            return true;
                        }
                        // don't return false here because need to loop through all students first
                    }
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Method that runs the recursive matching algorithm, updates each courses' roster, 
     * and counts the number of matches made by the findMatch() method.
     * 
     * @return the number of successful matches
     */
    public int maxMatch(){
        // Note: Before the method executes, each course in the courses vector has an empty roster. 
        // This method will update the course vector as the algorithm runs and will enroll the students into courses. 
        // The courses vector once this method is complete will contain the ideal student-course assignments. 

        // Tracks how many students have been enrolled in a class
        int numEnrollments = 0; 

         // Runs the matching algorithm for each student
        for (Student student: students){
            // Manages which courses the student has been attempted to be placed into. 
            // All initially set to unseen (false).
            boolean[] seen = new boolean[getNumCourses()]; 

            // Determines if the student can get a spot in a course
            if (findMatch(student, seen)){
                // System.out.println("+1, found a match for " + student);
                numEnrollments++; 
            }
        }

        return numEnrollments; 
    }

    
    /**
     * Updates the vector collection of students who could not be assigned to any of 
     * their preferred courses.
     */
    public void leftoverStudents(){
        // Iterates through all the students
        for (Student student: students){
            // If the student is not enrolled in a course
            if (!student.getEnrolled()){
                // Adds the student to the list of students who were not assigned a course
                leftover.add(student); 
            }
        }
    }

    /**
     * Returns the number of student vertices in this graph.
     * 
     * @return the number of students in the organizer
     */
    public int getNumStudents()
    {
        return stVCount;
    }

    /**
     * Returns the number of course vertices in this graph.
     * 
     * @return the number of courses in the organizer
     */
    public int getNumCourses()
    {
        return courVCount;
    }

    /**
     * Gets the students vector.
     * 
     * @return a vector of the student vertices in this graph
     */
    public Vector<Student> getStudents()
    {
        return students;
    }
    
    /**
     * Gets the courses vector.
     * 
     * @return a vector of the course vertices in this graph
     */
    public Vector<Course> getCourses()
    {
        return courses;
    }
    
    /**
     * Gets the students who could not be placed into any of their choices.
     * 
     * @return a vector of student objects that are leftover from the algorithm
     */
    public Vector<Student> getLeftovers()
    {
        return leftover;
    }

    /**
     * Main method for testing purposes
     */
    public static void main(String[] args)
    {
        Organizer m = new Organizer();

        // hard code the courses, usually the admin would enter these in the GUI
        m.addCourse(new Course("WRIT123", 2)); 
        m.addCourse(new Course("WRIT456", 2)); 
        m.addCourse(new Course("WRIT789", 2)); 

        m.createAllStudents("Students4Matches");

        Vector<Student> t1= m.students; 
        for (Student student: t1){
            System.out.println(student);
        }

        m.addOptions();
        int numEdges = m.maxMatch(); 
        System.out.println("\nnumber of matches: " + numEdges); 
        for (int i = 0; i < m.courVCount; i++) {
            System.out.println(m.courses.get(i));
        }

        System.out.println("\nleftover students (if any):\n");
        m.leftoverStudents();
        for (int i = 0; i < m.leftover.size(); i++) {
            System.out.println(m.leftover.get(i));
        }
    }
}