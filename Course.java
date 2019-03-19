import java.util.*; 

/**
 * Creates a course object. Each course is characterized by the course name, course capacity, student roster, and 
 * number of enrolled students. 
 *
 * @author Lisa Orii, Mileva Van Tuyl, Jade Zhu
 * @version December 13, 2018
 */
public class Course
{
    private String courseName; 
    private int capacity, numStudents; 
    private LinkedList<Student> roster; // Students enrolled in the course

    /**
     * Constructor: instantiates an instance of the Course class. 
     * 
     * @param theCourseName the String name of the course
     * @param maxSize the maximum number of seats in this course
     */
    public Course(String theCourseName, int maxSize)
    {
        courseName = theCourseName; 
        capacity = maxSize; 
        
        roster = new LinkedList<Student>(); 
        numStudents = 0; // counter
    }

    /**
     * Adds a student to the course and updates student information accordingly. 
     * This method is called when the recursive case in findMatch() is entered, so
     * the target student may already be enrolled. The actions performed in either case
     * are the same, but separated to make printing clearer.
     * 
     * @param target Student to add to the course.
     */
    public void addStudent(Student target){
        // if the student is already enrolled in a different class
        if (target.getEnrolled()) {
            // System.out.println("Enter recursively - somebody's going to be kicked out");
            
            // System.out.println("*unenroll and re enroll previous student*");
            roster.add(target); // this entire method is called after the previous student is removed from this course
            // System.out.println("is " + target.getName() + " enrolled? " + target.getEnrolled());
            
            numStudents++;
        }
        else {
            // If the course still has space: adds student to course, updates student object
            if (!isFull()){
                // System.out.println("Enter normally");
                roster.add(target); 
                numStudents++; 
            }
            else {
                System.out.println("Course is full: " + target.getName() + " was not added."); 
            }
        }

    }
    
    /**
     * Removes a student from the course roster. 
     * This does NOT update the student's enrollment status.
     * 
     * @param target Student to be removed from the course 
     */
    public void removeStudent(Student target){
        // Removes student from course roster
        if (isStudent(target)){
            roster.remove(target); 
            numStudents--; 
        }
        else {
            System.out.println("Student is not in the course."); 
        }
    }

    /**
     * Determines whether the class is full. 
     * 
     * @return true if the class has reached maxCapacity. 
     */
    public boolean isFull(){
        // Checks if numStudents is equal to capacity 
        return numStudents == capacity; 
    }

    /**
     * Determines whether the student is in the course.
     * 
     * @param target the student in/not in course
     * @return true if the student is in the course, false otherwise
     */
    private boolean isStudent(Student target){
        // Checks if the student is registered in the course
        return roster.contains(target); 
    }

    // ******************** Getters and Setters ****************************************

    /**
     * Returns the name of the course
     * 
     * @return the name of the course
     */
    public String getCourseName(){
        return courseName; 
    }

    /**
     * Sets the name of the class. 
     * This method should not be needed because we are not providing the user with 
     * an option to modify the name of the class. Will only be done during time
     * of instantiation.
     * 
     * @param course Name of the course
     */
    public void setCourseName(String course){
        courseName = course; 
    }

    /**
     * Returns the capacity of the course.
     * 
     * @return maximum capacity of the course
     */
    public int getCapacity(){
        return capacity; 
    }

    /**
     * Sets the capacity of the class.
     * This method should not be needed because we are not providing the user with 
     * an option to modify the capacity of the class. Will only be done during time
     * of instantiation. 
     * 
     * @param maxSize integer indicating the capacity of the class
     */
    public void setCapacity(int maxSize){
        capacity = maxSize; 
    }

    /**
     * Gets the current number of students within the course.
     * 
     * @return the number of students currently in the course roster
     */
    public int getNumStudents(){
        return numStudents; 
    }

    /**
     * Gets the collection of students in the course.
     * 
     * @return a LinkedList of the students enrolled in the course
     */
    public LinkedList<Student> getRoster(){
        return roster; 
    }

    /**
     * Contains information (name, capacity, current enrollment, roster) about the course.
     * 
     * @return a nicely formatted String with information about the course
     */
    public String toString(){
        return "Course: " + courseName + "\tCapacity: " + capacity + 
        "\tCurrent Enrollment: " + numStudents + "\tRoster:" + roster + "\n";
    }

    /**
     * Driver method: basic testing for methods within course class. 
     */
    public static void main(String[] args){
        Student s1 = new Student("Mileva", 1234567890);
        //s1.readRankings("studentA.txt");
        System.out.println(s1); 

        Course c1 = new Course("CS230", 25); 
        Course c2 = new Course("CS111", 25);
        System.out.println(c1);

        System.out.println("add to cs230"); 
        c1.addStudent(s1); 
        System.out.println(c1);
        System.out.println(s1); 

        System.out.println("add to cs111?"); 
        c2.addStudent(s1);
        System.out.println(c1);
        System.out.println(s1); 

        c1.removeStudent(s1); 

        System.out.println(c1); 
        System.out.println(s1); 

    }
}