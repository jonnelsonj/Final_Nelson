/* Project Name: HomeworkSchedule
 * 
 * Contributors: Jonathan Nelson
 * 
 * Tasks Performed: All
 * 
 * Purpose: To help organize upcoming assignments
 * 
 * How to: To begin, either open an existing file or go to 
 * 'Edit/Add' and add new assignment. The program will ask for
 * the name of the assignment, the subject, a brief description,
 * and the due date. 
 * After entering an assignment(s), you can go to 'View' and choose
 * to 'View All' assignments, or sort by due date. Sorting by due date
 * will bring up a calendar dialog allowing you to enter the month, year
 * and select the date. 
 * When you are finished adding assignments, you may choose to save
 * in either .txt format or binary
 * 
 * Model classes: Homework
 * 
 * View classes: StatPanel, StatusPanel, EditAssignment, Calendar, 
 * configureUI(in MainApp, EditAssignment and Calendar)
 * 
 * Controller classes: HomeworkIO
 * 
 * Types of serialization: Text - I tried to do binary too but I needed to get it turned in and cannot figure it out.
 * 
 * Future Enhancements: 
 * 1. Update calendar to include leap year, and to display the 
 * days of the month (ie Mon, Tues, ..) not just the date.
 * 2. Include a boolean isComplete and isOverdue to set an 
 * assignment to complete or overdue
 * 3. When viewing the calendar, days with assignments highlighted
 * in a different color.
 * 4. Clean up the code a bit
 * 5. Add other serialization
 * 
 */








public class FinalNelson {
	public static void main(String[] args) {
		MainApp ma = new MainApp();
		ma.setVisible(true);
	}
}

