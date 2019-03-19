import java.util.Scanner;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector.*;
/**
 * The StudentPanel allows the user to select a folder on their computer that contains
 * the text files of all the students' information, which is then displayed. When the
 * user clicks the submit button, the matching algorithm is invoked and it reports back
 * how many successful matches were made.
 *
 * @author Lisa Orii, Mileva Van Tuyl, Jade Zhu
 * @version December 13, 2018
 */
public class StudentPanel extends JPanel
{
    private JPanel selectFolderPanel, textPanel, submitPanel;
    private JLabel folderLabel, choicesLabel, infoLabel;
    private JTextArea studentsTextArea;
    private JButton folderButton, submitButton;
    private JFileChooser chooser;
    private String choosertitle;
    private Organizer org;
    private String info;
    private String folderName;

    /**
     * Constructer for StudentPanel class.
     * Creates three panels that allow user to select a folder to read information, gives instructions, and displays information that is read from the folder.
     * 
     * @param Organizer that stores and organizes information about students and courses
     * 
     */
    public StudentPanel(Organizer myOrganizer)
    {
        this.org = myOrganizer;
        folderName = "";

        selectFolderPanel = new JPanel();
        selectFolderPanel.setBackground(Color.white);

        JLabel folderLabel = new JLabel("Select a folder to read the information.");

        ButtonListener bListener = new ButtonListener(); 
        folderButton = new JButton("Select folder");
        folderButton.addActionListener(bListener);

        selectFolderPanel.add(folderLabel);
        selectFolderPanel.add(folderButton);

        //
        textPanel = new JPanel();
        textPanel.setBackground(Color.white);

        choicesLabel = new JLabel ("Students have selected their preferences based on the courses that were entered on the previous tab. "
                                 + "Students and their preferences appear below.");
        studentsTextArea = new JTextArea(300, 200);
        studentsTextArea.setEditable(false);

        textPanel.add(choicesLabel);
        textPanel.add(studentsTextArea);

        //
        submitPanel = new JPanel();
        submitPanel.setBackground(Color.white);

        // scrollable panel
        JScrollPane areaScrollPane = new JScrollPane(studentsTextArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        infoLabel = new JLabel();
        submitPanel.add(infoLabel);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(bListener); //new ButtonListener()
        submitPanel.add(submitButton);

        //
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.white);
        add(selectFolderPanel);
        add(textPanel);
        add(submitPanel);
        add(areaScrollPane);
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
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == folderButton)
            {
                String result = "";

                chooser = new JFileChooser(); 
                try
                {
                    chooser.setCurrentDirectory(new java.io.File(""));
                }
                catch (NullPointerException ex)
                {
                    System.out.println(ex);
                }
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                // disable the "All files" option.
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) { 
                    System.out.println("getCurrentDirectory(): " 
                        +  chooser.getCurrentDirectory());
                    System.out.println("getSelectedFile() : " 
                        +  chooser.getSelectedFile());
                    StudentPanel.this.folderName = chooser.getSelectedFile().getName();
                }

                //array of all files in the selected file
                File[] files = chooser.getSelectedFile().listFiles();
                for (int i =0 ; i < files.length; i++)
                {
                    System.out.println(files[i]);
                }

                try
                {
                    for (File file : files) 
                    {
                        Scanner scan = new Scanner (file);
                        while (scan.hasNext())
                        {
                            result += scan.nextLine() + "\n";                        
                        }
                    }
                    System.out.println("result: " + result);
                    studentsTextArea.setText (result); //set textarea to the string read through each file
                    infoLabel.setText("<html>Click Submit to match the students' preferences to the courses above.<html>");
                }
                catch (IOException ex)
                {
                    System.out.println("No Folder Selected.");
                }
            }

            if (e.getSource() == submitButton) 
            {
                System.out.println("submit button pressed");

                org.createAllStudents(StudentPanel.this.folderName); //match the students' preferences to courses

                System.out.println("have all students been added to students vertex vector:");
                System.out.println(org.getStudents());

                org.addOptions(); //store the information about preferences for each student to organizer
                int numMatches = org.maxMatch();

                infoLabel.setText(numMatches + " students have been matched with a course.");

                for (int i = 0; i < org.getNumCourses(); i++) {
                    System.out.println(org.getCourses().get(i));
                }

                //unregsitered students
                System.out.println("\nleftover students:");
                org.leftoverStudents();
                for (int i = 0; i < org.getLeftovers().size(); i++) {
                    System.out.println(org.getLeftovers().get(i));
                }

                // set the leftover students to appear in GetAssignPanel
                if (org.getLeftovers().size() == 0) {
                    GetAssignPanel.unregisteredBox.setText("Everyone was successfully matched to a course!");
                }
                else {
                    String s = "";
                    for (int i = 0; i < org.getLeftovers().size(); i++) {
                        s += org.getLeftovers().get(i) + "\n";
                    }
                    GetAssignPanel.unregisteredBox.setText(s);
                }
            }
        }
    }
}