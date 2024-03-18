# COMP3005-A3-Q1

Video Link: https://youtu.be/2J0YuY9c3Bo

For this application I will be using IntelliJ IDEA.

Explanation of functions:

    getAllStudents():
        This function executes a query that selects all the tuples for each row, like the student_id
        first_name, last_name, email, and enrollment_date and outputs it.

    addStudent(first_name, last_name, email, enrollment_date):
        This function takes these 4 parameters and stores it into a prepared statement where the they are
        set in students table by index and then the prepared statement updated to the table where we refresh
        the database in pgadmin4 and the new student is in the table.

    updateStudentTable(student_id, new_email):
        This functions takes the 2 parameters and updates the email address for a student with the specific
        student_id by using the SQL syntax UPDATE and WHERE and setting the values into the prepared statement
        which is the executed to update the database for the specific student_id.

    deleteStudent(student_id):
        This functions uses the student_id parameter to identify which record to delete in the students
        table using the SQL syntax DELETE and WHERE.


### Prerequisite
Ensure you have installed:
* IntelliJ IDEA, 
* Java Development Kit 21 or 17(JDK),
* PostgreSQL, 
* pgadmin4

### Database set up
1. Open pgadmin4
2. Create a PostgreSQL database name Assignment 3 in pgadmin4
3. Create the students table in database:
    ```
    CREATE TABLE students (
        student_id SERIAL PRIMARY KEY,
        first_name TEXT NOT NULL,
        last_name TEXT NOT NULL,
        email TEXT NOT NULL UNIQUE,
        enrollment_date DATE
    );
    ```
4. Insert data into the table:
    INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
    ('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
    ('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
    ('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');
    
### Compiling and Running with IntelliJ IDEA:
1. With IntelliJ IDEA, it automatically compiles your project, but you can manually compile it be selecting 'Build Project'
from the menu.
2. To run the application, right-click on the play button beside the line 'public class Main {' and select 'Run 'Main.main()''.
3. IntelliJ IDEA then will execute the application.
    
    


### Details to note when running the application:
* Very important is to comment out any functions in the public static void main(String[] args) that you are not using when testing 
this application. 
* When deleting a student record, check in pgadmin4 for the student_id and add that integer to parameter for deleteStudent function.

* Replace the function parameters for functions in the main class for addStudent(), updateStudentEmail() and deleteStudent()
with whatever you wish to add, such as addStudent("Winnie", "Pooh", "WinnieThePooh@gmail.com", java.sql.Date.valueOf("2009-12-19"))
or updateStudentEmail(10, "ThePDogReturnsToWarg@outlook.com") for the updateStudentEmail function.

* As you add, update and delete records within the application, you will see it affect the students table in pgadmin4, although you may need to
SQL command: SELECT * FROM students; in the query if the result does not appear in the result output.Or you can simply run the
getAllStudents function in the main class everytime you run one of the other functions to determine if the functions work as intended.
