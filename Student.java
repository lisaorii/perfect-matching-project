import java.util.*;
import javafoundations.*;
import javafoundations.exceptions.*;
import java.io.*;
/**
 * Represents a Student object. Each student is characterized by their username, B-number, course preferences, 
 * enrollment status, and course assignment.
 *
 * @author Lisa Orii, Mileva Van Tuyl, Jade Zhu
 * @version December 13, 2018
 */
public class Student
{
    private String username;
    private int Bnum;
    private Vector<Course> choices; // student's course preferences
    private String course; // The name of the course they have been assigned
    private boolean enrolled; // Student's enrollment status

    /**
     * Constructor: instantiates an instance of the Student class. 
     * 
     * @param theUsername the student's name
     * @param Bnumber the student's B number
     */
    public Student(String theUsername, int Bnumber)
    {
        username = theUsername;
        Bnum = Bnumber;
        
        choices = new Vector<Course>();
        
        course = "";
        enrolled = false; 
    }
    
    /**
     * Updates the student to display which course they have been enrolled 
     * and that they have been enrolled. 
     * 
     * @param courseName the name of the course the student is enrolled in
     */
    public void enrollStudent(String courseName){
        course = courseName; 
        enrolled = true; 
    }
    
    /**
     * Updates the student to display that he/she is no longer enrolled in a course. 
     * 
     * @return the string name of the old course the student used to be in.
     */
    public String unenrollStudent(){
        // Stores the course the student was previousl enrolled in
        String oldCourse = course;
        
        // Updates student information
        course = null; 
        enrolled = false; 
        
        return oldCourse;
    }
    
    /**
     * Adds a choice (read in) to the student's vector of choices. 
     * Used by createAllStudents() method of the Organizer after validating the
     * String course in the txt file is a valid course that the admin entered.
     * 
     * @param newChoice another course option that the student selected
     */
    public void addChoice(Course newChoice)
    {
        choices.add(newChoice);
    }
    
    // ******************** Getters and Setters ****************************************
    
    /**
     * Returns the student's username.
     * 
     * @return the student's username
     */
    public String getName()
    {
        return username;
    }
    
    /**
     * Returns the student's B number.
     * 
     * @return the student's B number
     */
    public int getBnum()
    {
        return Bnum;
    }
    
    /**
     * Returns a vector collection containing the student's choices
     * 
     * @return a vector containing student's courses choices
     */
    public Vector<Course> getChoices(){
        return choices; 
    }
    
    /* Purposefully did not include methods setCourse() and setEnrolled() because those 
    cases are handled with enrollStudent() and unenrollStudent(), which make sure 
    that when a student is added to a course or removed from a course the instance
    variable enrolled is modified accordingly. */
    
    /**
     * Gets the string name of the course the student has been enrolled in. 
     * 
     * @return the course the student is enrolled in
     */
    public String getCourse(){
        return course; 
    }
    
    /**
     * Returns whether the student is enrolled in a course.  
     * 
     * @return true if the student is assigned to a course object
     */
    public boolean getEnrolled(){
        return enrolled; 
    }
    
    /**
     * Returns a string with information (name, Bnum, enrollment status, 
     * course enrolled in) about the student.
     * 
     * @return a nicely formatted string with student information
     */
    public String toString(){
        String result = ""; 
        result += "Student: " + username + "(" + Bnum + ")" + "\t Enrolled: " + enrolled + 
            "\t Course: " + course + "\t Choices: "; 
        for (Course course: choices)
            result += course.getCourseName() + " "; 
        
        return result; 
    }
    
    /**
     * Driver method: Basic testing for Student method.
     */
    public static void main(String[] args){
        // Creates student
        Student student1 = new Student("Mileva", 1234567890);
        //student1.readRankings("student1.txt");
        System.out.println(student1); 
        
        // Enrolls and unenrolls student from course
        // student1.enrollStudent(course1); 
        System.out.println(student1); 
        student1.unenrollStudent(); 
        System.out.println(student1); 
       
    }
}