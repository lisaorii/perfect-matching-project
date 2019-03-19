# perfect-matching-project

The perfect matching program, to be used by school administrators, processes students’ unranked course preferences 
(students simply select a number of courses that interest them in no particular ranked order) and assigns each student 
to a course so that the maximum number of students get assigned to one of their course preferences.

The administrator will interact with the program through the interface that consists of four panels: 
About, Enter Courses, Read Students’ Preferences, and Get Assignment. The “About” tab provides the administrator with 
instructions about how to use the program. The “Enter Courses” tab enables administrators to enter the course name and the 
maximum capacity of the course. Clicking the “Add Course” button creates a course object, adds the course object to the 
collection of courses, and displays the course information to the administrator. The “Students’ Preferences” tab prompts 
the administrator to choose the folder on their computer that contains the files with the information about each student 
(username, B-number, and course preferences). Once the folder is chosen, the program reads each of the files within the 
chosen directory, creates each student object, adds each student object to the collection of students, and displays the 
student information to the administrator. Clicking “Submit” sets up the underlying bipartite graph representation of the 
students’ course preferences, runs the matching algorithm to assign students to courses, displays the number of successful 
student-course matches, and populates the information of the “Get Assignment” panel. The drop-down box in the “Get Assignment” 
tab allows the administrator to select a course and view the roster of students assigned to that course in the 
“Registered Students” section of the panel. In addition, the list of leftover students who could not be assigned to any 
of their course preferences is displayed in the “Unregistered Students” section. 

Instructions: Running the Program 
1. Click the “Enter Courses” tab, fill in the “Class name” and “Maximum size,” and click “Add Course” in order to create 
a course that students can request. (Note: The name of the class name cannot contain any spaces.) If the courses were 
correctly processed, they will be displayed on the screen. 
2. Once the courses have been finalized, click the “Read Students’ Preferences” tab. Click “Select folder” to choose the 
folder containing the student text files. If the students were correctly processed, they will be displayed on the screen. 
3. Note regarding text files: The student’s username must appear first (no spaces). Then, the student’s B-number must be an 
integer value. Lastly, each of the student’s course preferences must be identical to how the course name was entered 
(with no spaces). 
4. Once the courses and students have been finalized, click the “Submit” button to obtain the students’ course assignments. 
(Note: once the “Submit” button has been clicked, no additional students or courses can be added or removed.)
5. Click the “Get Assignment” tab to view the student assignments and select a course using the drop-down menu. 
This will display the roster of students registered for that course. The students who were not matched to any of their 
course preferences will be displayed in the “Unregistered Students” section.
6. At this stage, to change any of the courses or students entered, please restart the program.
