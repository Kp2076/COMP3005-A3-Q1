import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class Main {
    //The URL, user, and password are needed to establish a connection to the SQL database that we are using.
    private static final String url = "jdbc:postgresql://localhost:5432/Assignment3";
    private static final String user = "postgres";
    private static final String password = "postgre";
    public static void main(String[] args) {
        //Comment out functions you are not using at the moment
        getAllStudents();
        addStudent("Mary", "Smith", "MSmith009@gmail.com", java.sql.Date.valueOf("2009-12-19"));
        updateStudentEmail(13, "MarySmith.912@outlook.com");
        //Replace the parameter in deleteStudent with whatever student_id is assigned to the record you want to delete.
        deleteStudent(14);
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
                pstmt.setDate(4, enrollment_date);
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
            pstmt.executeUpdate();
            System.out.println("Data updated.");
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
            pstmt.executeUpdate();
            System.out.println("Student id: " + student_id + ", deleted.");
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
