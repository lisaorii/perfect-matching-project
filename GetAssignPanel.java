import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
/**
 * The GetAssignPanel displays the results of the matching algorithm. The user is able
 * to select a course from the drop down menu, and the roster containing students who
 * are registered for this course will be displayed. A box on the bottom displays the 
 * leftover students who were absolutely unable to be placed into any of their choices.
 *
 * @author Lisa Orii, Mileva Van Tuyl, Jade Zhu
 * @version December 13, 2018
 */
public class GetAssignPanel extends JPanel
{
    // instance variables 
    private JPanel showRosterPanel, registeredPanel, unregisteredPanel;
    private JTextArea registeredBox;
    public static JTextArea unregisteredBox; // StudentPanel needs access
    public static JComboBox rosterCombo; // EnterClassesPanel needs access
    private Organizer org;
    private Vector<String> comboxList;

    /**
     * Constructor for objects of class EnterClassesPanel
     * 
     * @param Organizer stores the information about the courses that have been entered by user
     */
    public GetAssignPanel(Organizer myOrganizer)
    {
        this.org = myOrganizer;

        // initialise instance variables
        showRosterPanel = new JPanel();
        showRosterPanel.setBackground(Color.white);

        comboxList = new Vector<String>();
        comboxList.add("...");
        JLabel rosterLabel = new JLabel("Show roster for");
        rosterCombo = new JComboBox(comboxList); 
        rosterCombo.addActionListener(new ComboListener());

        showRosterPanel.add(rosterLabel);
        showRosterPanel.add(rosterCombo);

        //
        registeredPanel = new JPanel();
        registeredPanel.setBackground(Color.white);

        JLabel resultsLabel = new JLabel("Registered students");
        registeredBox = new JTextArea(100,10);
        registeredBox.setEditable(false);

        registeredPanel.add(resultsLabel);
        registeredPanel.add(registeredBox);

        //
        unregisteredPanel = new JPanel();
        unregisteredPanel.setBackground(Color.white);
        JLabel unregisteredLabel = new JLabel("Unregistered students");

        unregisteredBox = new JTextArea(100,10);
        unregisteredBox.setEditable(false);

        unregisteredPanel.add(unregisteredLabel);
        unregisteredPanel.add(unregisteredBox);

        // scrollable panel
        JScrollPane pane1 = new JScrollPane(registeredBox);
        pane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollPane pane2 = new JScrollPane(unregisteredBox);
        pane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        add(showRosterPanel);
        add(registeredPanel);
        add(pane1);
        add(unregisteredPanel);
        add(pane2);

    }

    /**
     * Class that performs appropriate action when something is selected in ComboBox.
     */
    private class ComboListener implements ActionListener
    {
        /**
         * Performs the action when the appropriate button is clicked.
         * 
         * @param ActionEvent event that occurs by clicking the button.
         */
        public void actionPerformed(ActionEvent event)
        {
            if (rosterCombo.getSelectedIndex() != 0) //if a course is selected from the ComboBox
            {
                Course selectedCourse = (Course) org.getCourses().get(rosterCombo.getSelectedIndex()-1); //course that is selected from ComboBox

                LinkedList<Student> selectedRoster = selectedCourse.getRoster(); //roster of students in the course

                String s = "";
                for (int i = 0; i < selectedRoster.size(); i++) {
                    s += selectedRoster.get(i) + "\n";
                }
                System.out.println(s);

                registeredBox.setText(s);
            }
        }
    }
}