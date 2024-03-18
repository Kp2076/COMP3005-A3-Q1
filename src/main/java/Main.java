import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.text.ParseException;
import java.util.Date;



public class Main {
    //The URL, user, and password are needed to establish a connection to the SQL database that we are using.
    private static final String url = "jdbc:postgresql://localhost:5432/Assignment3";
    private static final String user = "postgres";
    private static final String password = "postgre";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the student id to which you wish to update email: ");
        int updateStudentEmail = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the student id to delete student record of the id: ");
        int deleteStudent = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter first name: ");
        String first_name = scanner.nextLine();

        System.out.println("Enter last name: ");
        String last_name = scanner.nextLine();

        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Enter new email that you wish to update: ");
        String updateEmail = scanner.nextLine();

        System.out.println("Enter enrollment date(yyyy-MM-dd): ");
        String enrollment_date = scanner.nextLine();

        java.sql.Date enrollmentDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(enrollment_date); // Parse to java.util.Date
            enrollmentDate = new java.sql.Date(date.getTime()); // Convert to java.sql.Date
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            scanner.close();
            return; // Exit the method or ask for input again
        }
        //Comment out functions you are not using at the moment
        getAllStudents();
        addStudent(first_name, last_name, email, enrollmentDate);
        getAllStudents();
        updateStudentEmail(updateStudentEmail, updateEmail);
        getAllStudents();
        //Replace the parameter in deleteStudent with whatever student_id is assigned to the record you want to delete.
        deleteStudent(deleteStudent);
        getAllStudents();
    }

    public static void getAllStudents() {
        /*The try block is best for catching exceptions. It will allow the code to run but when an error
        occurs, it will catch the exception and output the error for the user to identify and correct.
        */
        try {
            //The Class.forName method is used to load the postgreSQL JDBC driver class
            Class.forName("org.postgresql.Driver");
            //connect to the database
            Connection connection = DriverManager.getConnection(url, user, password);
            //Creating an instance of Statement that is linked to the Connection object
            Statement statement = connection.createStatement();
            //Executes the query
            statement.executeQuery("SELECT * FROM students");
            //This line retireves the result of the ResultSet object which contains the data returned by the query
            ResultSet resultSet = statement.getResultSet();
            //The while loop iterates through the resultSet
            while(resultSet.next()) {
                //These variables are holding the values of specific columns in the current row of the ResultSet
                int id = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                Date date = resultSet.getDate("enrollment_date");

                //Outputs the result
                System.out.println("ID: " + id +
                        ", First Name: " + firstName +
                        ", Last Name: " + lastName +
                        ", Email: " + email +
                        ", Date of Birth: " + date);

            }
            //These methods need to be closed to prevent any leaks
            resultSet.close();
            statement.close();
            connection.close();
        }
        // These catch functions are mainly used to handle exceptions
        catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failure or SQL error.");
            e.printStackTrace();
        }
    }

    public static void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        //Set the connection to the database to null so that the user can determine if the connection was made
        Connection connection = null;
        // Set to null to avoid any NullPointerException
        PreparedStatement preparedStatement = null;

        //connect to the database
        try {
            //Load postgreSQL JDBC
            Class.forName("org.postgresql.Driver");
            //Connect to database
            connection = DriverManager.getConnection(url, user, password);
            //This line is an insert statement that will add a new row to the students table.
            String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
            //this line initializes a new PreparedStatement  called pstmt which stores the connection.prepareStatement(insertSQL) which prepares the SQL statement with the database
            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                //pstmt.set, sets the data to the students table
                pstmt.setString(1, first_name);
                pstmt.setString(2, last_name);
                pstmt.setString(3, email);
                pstmt.setDate(4, new java.sql.Date(enrollment_date.getTime()));
                //pstmt.executeUpdate(); executes the update to the table
                pstmt.executeUpdate();
                System.out.println("Student added to students table.");

            }

            // These catch functions are mainly used to handle exceptions
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failure or SQL error.");
            e.printStackTrace();
        }
    }

    public static void updateStudentEmail( int student_id, String new_email) {
        //Set the connection to the database to null so that the user can determine if the connection was made
        Connection connection = null;
        // Set to null to avoid any NullPointerException
        PreparedStatement pstmt = null;

        try {
            //Load postgreSQL JDBC
            Class.forName("org.postgresql.Driver");
            //Connect to database
            connection = DriverManager.getConnection(url, user, password);
            //The update statement where an email will be updated for a specific student_id
            String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";
            //Initializes the preparedStatement and stores the connection.prepareStatement(updateSQL)
            pstmt = connection.prepareStatement(updateSQL);
            //Sets the data to the to the students table via the preparedStatement
            pstmt.setString(1,new_email);
            pstmt.setInt(2, student_id);
            //Executes the update to the table
            int recordsAffected = pstmt.executeUpdate();

            if (recordsAffected > 0) {
                System.out.println("Student id: " + student_id + ", updated.");
            } else {
                System.out.println("Student id: " + student_id + " does not exist.");
            }
            // These catch functions are mainly used to handle exceptions
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failure or SQL error.");
            e.printStackTrace();
        }
    }

    public static void deleteStudent(int student_id) {
        //Set the connection to the database to null so that the user can determine if the connection was made
        Connection connection = null;
        // Set to null to avoid any NullPointerException
        PreparedStatement pstmt = null;

        try {
            //Load postgreSQL JDBC
            Class.forName("org.postgresql.Driver");
            //Connect to database
            connection = DriverManager.getConnection(url, user, password);
            /*This sql variable will delete a record from the students table where condition is satisfied
            where ? is replaced with the student_id in the parameter.
             */
            String sql = "DELETE FROM students WHERE student_id = ?";
            pstmt = connection.prepareStatement(sql);

            // Set the ID parameter in the PreparedStatement
            pstmt.setInt(1, student_id);

            // Execute the delete operation
            int recordsAffected = pstmt.executeUpdate();

            if (recordsAffected > 0) {
                System.out.println("Student id: " + student_id + ", deleted.");
            } else {
                System.out.println("Student id: " + student_id + " does not exist.");
            }
            // These catch functions are mainly used to handle exceptions
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        }catch (SQLException e) {
            System.out.println("Connection failure or SQL error.");
            e.printStackTrace();
        }

    }
}