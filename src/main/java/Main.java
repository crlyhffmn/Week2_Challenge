import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static Scanner scanner;

    public static void main(String[] args) throws SQLException {
        //Create tables
        connection = ConnectionFactory.getConnection();
        createTables();

        //Initialize scanner
        scanner = new Scanner(System.in);

    }

    //Initializes needed tables in the database (if they aren't already)
    public static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        if(!tableExists("books")) {
            String createEmp = "CREATE TABLE books (bookID INTEGER PRIMARY KEY AUTO_INCREMENT, title VARCHAR(50), author VARCHAR(100), isbn INTEGER, price INTEGER, category VARCHAR(50), description VARCHAR(500););";
            statement.executeUpdate(createEmp);
        }

        if(!tableExists("users")) {
            String createUsers = "CREATE TABLE users (userID INTEGER PRIMARY KEY AUTO_INCREMENT, firstName VARCHAR(50), lastName VARCHAR(50), userName VARCHAR(50), password VARCHAR(50));";
            statement.executeUpdate(createUsers);
        }
    }

    //Checks if a table is already present in the database
    //Returns: true if it exists, false otherwise
    public static boolean tableExists(String tableName) throws SQLException {
        String query = "select count(*) from information_schema.tables where table_schema = database() and table_name = \"" + tableName + "\";";
        Statement statement = connection.createStatement();
        ResultSet r = statement.executeQuery(query);
        r.next();
        int exists = r.getInt("count(*)");
        return exists == 1;
    }
}
