import java.awt.*;
import javax.swing.*;
/**
 * When the program starts, a tab named "About" appears that contains 
 * information about the creator of the program (credits) and instructions 
 * on how to use it. 
 *
 * @author Lisa Orii, Mileva Van Tuyl, Jade Zhu
 * @version December 13, 2018
 */
public class AboutPanel extends JPanel
{
    /**
     * Constructor for objects of class AboutPanel.
     * Sets up this panel with 5 labels.
     */
    public AboutPanel()
    {
        setBackground(Color.white);
        setMaximumSize(new Dimension(300,100));

        String s = "<html>PERFECT MATCHING - FIRST YEAR WRITING COURSES<br>CS230 Lisa Orii, Mileva Van Tuyl, Jade Zhu<br><br>";
        s += "This interface allows the user to register a course to a collection of courses, select a folder to read from a folder that ";
        s += "contains information regarding students' preferences of courses, and attempts to optimize the number of students who are matched with a ";
        s += "course that they prefer.<br>";
        
        
        s += "-------------<br>";
        s += "1. Enter Classes<br>";
        s += "   Input the course name and the maximum capacity.<br>";
        s += "   Click Add Course to add the course to the collection of courses.<br>";
        
        s += "-------------<br>";
        s += "2. Read Students' Preferences<br>";
        s += "   Choose the folder you want to read the information from.<br>";
        s += "   Click Submit to match students' preferences to available courses.<br>";
        
        s += "-------------<br>";
        s += "3. Get Assignment<br>";
        s += "   To view the roster of registered students in a course, choose the course from the ";
        s += "drop-down menu.<br>";

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label= new JLabel(s, SwingConstants.CENTER); // SwingConstants.RIGHT
        add(label);
    }
}