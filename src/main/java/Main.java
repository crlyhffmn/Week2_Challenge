import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static Scanner scanner;
    private static UserDAO uDAO = DAOFactory.getUserDAO();
    private static BookDAO bDAO = DAOFactory.getBookDAO();

    public static void main(String[] args) throws SQLException {
        //Create tables
        connection = ConnectionFactory.getConnection();
        createTables();

        //Initialize scanner
        scanner = new Scanner(System.in);

        startScreen();
    }

    public static void startScreen() throws SQLException {
        System.out.println("Welcome to Carly's Bookstore!\nPlease select an option: \n1: Login\n2: Create Account\n3: Quit");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("You have entered an invalid value. Please enter an integer from 1 to 3.");
            System.out.println("Press Enter to continue");
            try{System.in.read();}
            catch(Exception e2){}
        }

        switch (choice) {
            case 1: //Login
                loginScreen();
                break;
            case 2: //Create Account
                createAccount();
                break;
            case 3: //Quit
                break;
            default: //Invalid
                System.out.println("You have entered an invalid value. Redirecting to login screen...");
                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e3){}
                break;
        }
    }

    public static void loginScreen() throws SQLException {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        User user = uDAO.getUserByUsername(username);
        while(user == null) {
            System.out.println("Sorry, that username does not exist.");
            System.out.println("Please try again.");
            System.out.print("Username: ");
            username = scanner.nextLine();
            user = uDAO.getUserByUsername(username);
        }
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        while(!pass.equals(user.getPassword())) {
            System.out.println("Incorrect password");
            System.out.println("Please try again.");
            System.out.print("Password: ");
            pass = scanner.nextLine();
        }
        mainMenu(user);
    }

    public static void mainMenu(User currentUser) {
        System.out.println("Welcome back " + currentUser.getFirstName() + "!");

    }

    public static void createAccount() throws SQLException {
        System.out.print("First name: ");
        String fName = scanner.nextLine();
        System.out.print("Last name: ");
        String lName = scanner.nextLine();
        System.out.print("Username: ");
        String user = scanner.nextLine();
        String repeats = "select count(*) from users where userName = \"" + user + "\";";
        Statement statement = connection.createStatement();
        ResultSet r = statement.executeQuery(repeats);
        r.next();
        int exists = r.getInt("count(*)");
        while(exists > 0) {
            System.out.print("Sorry, that username is taken. Please choose a different one: ");
            user = scanner.nextLine();
            repeats = "select count(*) from users where userName = \"" + user + "\";";
            r = statement.executeQuery(repeats);
            r.next();
            exists = r.getInt("count(*)");
        }
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        User nUser = new User();
        nUser.setUserName(user);
        nUser.setPassword(pass);
        nUser.setFirstName(fName);
        nUser.setLastName(lName);
        uDAO.addUser(nUser);
        User addedUser = uDAO.getUserByUsername(user);
        System.out.println("You have successfully created an account!");
        System.out.println("Press Enter to continue");
        try{System.in.read();}
        catch(Exception e){}
        mainMenu(addedUser);
    }

    //Initializes needed tables in the database (if they aren't already)
    public static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        if(!tableExists("books")) {
            String createEmp = "CREATE TABLE books (bookID INTEGER PRIMARY KEY AUTO_INCREMENT, title VARCHAR(50), author VARCHAR(100), isbn INTEGER, price INTEGER, category VARCHAR(50), description VARCHAR(500));";
            statement.executeUpdate(createEmp);
        }

        if(!tableExists("users")) {
            String createUsers = "CREATE TABLE users (userID INTEGER PRIMARY KEY AUTO_INCREMENT, firstName VARCHAR(50), lastName VARCHAR(50), username VARCHAR(50), password VARCHAR(50));";
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
