import javax.swing.*;
/**
 * MatchingGUI has four tabs. 
 * AboutPanel gives instructions to user. 
 * EnterClassesPanel allows the user to enter/register courses to the collection of courses.
 * StudentPanel allows the user to select a folder in the directory to read the information regarding students' course preferences. This panel also 
 * matches students' course preferences to courses.
 * GetAssignPanel displays the roster for registered students in a selected course. This also displays students who are unregistered.
 *
 * @author Lisa Orii, Mileva Van Tuyl, Jade Zhu
 * @version December 13, 2018
 */
public class MatchingGUI
{
    //-----------------------------------------------------------------
    //  Sets up a frame containing a tabbed pane. The panel on each
    //  tab demonstrates a different tab.
    //-----------------------------------------------------------------
    public static void main (String[] args)
    {

        JFrame frame = new JFrame ("Course Registration Information");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        //construct Organizer object and pass it as a parameter
        Organizer organizer = new Organizer();

        JTabbedPane tp = new JTabbedPane();
        tp.addTab ("About", new AboutPanel());
        tp.addTab ("Enter Courses", new EnterCoursesPanel(organizer));
        tp.addTab ("Read Students' Preferences", new StudentPanel(organizer));
        tp.addTab ("Get Assignment", new GetAssignPanel(organizer));

        frame.getContentPane().add(tp);
        frame.pack();
        frame.setVisible(true);
    }
}