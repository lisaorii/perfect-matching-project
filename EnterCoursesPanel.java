import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * The EnterClassesPanel is a space for the user to enter all the possible courses that 
 * the students could have listed as one of their choices. Each time the user clicks
 * the add button, a new course object is instantiated using the course name and the 
 * maximum number of seats in the course, and are displayed as they are added.
 *
 * @author Lisa Orii, Jade Zhu, Mileva Van Tuyl
 * @version December 11, 2018
 */
public class EnterCoursesPanel extends JPanel
{
    // instance variables - replace the example below with your own
    private JPanel instructionsPanel, addCoursePanel, appearPanel, displayClassesPanel;
    private JTextField courseName, maxSize;
    private JButton addButton;
    private JLabel instructions, info, result; // change from JLabel to JScrollPanel?
    private JTextArea boxTextArea;
    private String name;
    private int max;
    private Organizer org;

    /**
     * Constructor for objects of class EnterClassesPanel
     * 
     * @param Organizer stores the information about the courses that have been entered by user
     */
    public EnterCoursesPanel(Organizer myOrganizer) 
    {
        name = "";
        max = 0;
        this.org = myOrganizer;

        // initialise instance variables
        instructionsPanel = new JPanel();
        instructionsPanel.setBackground(Color.white);
        instructions = new JLabel("<html>Fill in the information to add a course, then click \"Add Course\".<br/><html>");
        instructionsPanel.add(instructions);

        //
        addCoursePanel = new JPanel();
        addCoursePanel.setBackground(Color.white);

        JLabel classLabel = new JLabel("Class name");
        courseName = new JTextField(5);

        JLabel maxLabel = new JLabel("Maximum size");
        maxSize = new JTextField(5);

        addButton = new JButton("Add Course");
        addButton.addActionListener(new ButtonListener());

        addCoursePanel.add(classLabel);
        addCoursePanel.add(courseName);
        addCoursePanel.add(maxLabel);
        addCoursePanel.add(maxSize);
        addCoursePanel.add(addButton);

        //
        appearPanel = new JPanel();
        appearPanel.setBackground(Color.white);

        courseName.addFocusListener(new TextListener());
        maxSize.addFocusListener(new TextListener());

        appearPanel = new JPanel();
        appearPanel.setBackground(Color.white);
        info = new JLabel("Information on all courses will appear here.");
        appearPanel.add(info);

        //
        displayClassesPanel = new JPanel();
        displayClassesPanel.setBackground(Color.white);

        result = new JLabel();
        appearPanel.add(result);

        boxTextArea = new JTextArea(100, 10); //empty as of now
        boxTextArea.setEditable(false);
        displayClassesPanel.add(boxTextArea);

        // scrollable panel
        JScrollPane areaScrollPane = new JScrollPane(boxTextArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        add(instructionsPanel);
        add(addCoursePanel);
        add(appearPanel);
        add(displayClassesPanel);
        add(areaScrollPane);
    }

    /**
     * Determines when the cursor is above the textfield. Performs the appropriate action when a textfield is entered.
     */
    private class TextListener implements FocusListener
    {
        /**
         * Reads in string that is entered into textfield by user when the user places the cursor in the textfield.
         * 
         * @param FocusEvent the event by the cursor
         */
        public void focusGained(FocusEvent event)
        {
            try
            {
                EnterCoursesPanel.this.name = courseName.getText();
                EnterCoursesPanel.this.max = Integer.parseInt(maxSize.getText());
            }
            catch (NumberFormatException e)
            {
                System.out.println(e);
            }
        }

        /**
         * Reads in string that is entered into textfield by user when the user removes the cursor in the textfield.
         * 
         * @param FocusEvent the event by the cursor
         */
        public void focusLost(FocusEvent event)
        {
            try
            {
                EnterCoursesPanel.this.name = courseName.getText();
                EnterCoursesPanel.this.max = Integer.parseInt(maxSize.getText());
            }
            catch (NumberFormatException e)
            {
                System.out.println(e);
            }
            //System.out.println("lost:" + name);
            //System.out.println("Course name entered: " + name);
        }
    }

    /**
     * ButtonListener class that implements ActionListener to implement specific actions when a button is pressed.
     */
    private class ButtonListener implements ActionListener
    {
        /**
         * Performs the action when the appropriate button is clicked.
         * 
         * @param ActionEvent event that occurs by clicking the button.
         */
        public void actionPerformed(ActionEvent event)
        {
            //System.out.println(EnterClassesPanel.this.name);
            //System.out.println(EnterClassesPanel.this.max);
            if (EnterCoursesPanel.this.name.equals("") || EnterCoursesPanel.this.max == 0) //user did not fill out all enteries necessary to add a course
            {
                info.setText("Please fill out all entries.");
                //System.out.println("User did not provide all the data needed to add a course.");
            }
            else if (event.getSource() == addButton) //user entered all information and clicks the Add Course button
            {
                if (org.nameExists(name)) //the name of the course is already registered
                {
                    info.setText("Course with this name already exists. Please enter a course with a different name.");
                    //System.out.println("Entered existing course.");
                }
                else //new course being registered
                {
                    Course c1 = new Course(name, max);
                    //System.out.println(c1);
                    //System.out.println("New course created and added to Organizer:\n" + c1.toString());

                    org.addCourse(c1); //add to collection of courses
                    //System.out.println(allCourses);

                    String result = "";
                    for (int i=0; i <org.getNumCourses(); i++)
                    {
                        result += org.getCourses().elementAt(i) + "\n";
                    }

                    boxTextArea.setText(result);
                    //System.out.println(org.toString());

                    //System.out.println(org.getNumCourses());

                    // add it to the combo box
                    GetAssignPanel.rosterCombo.addItem(c1.getCourseName());
                }
            }
        }
    }
}
