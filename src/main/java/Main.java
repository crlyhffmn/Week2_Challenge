import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static Scanner scanner;
    private static UserDAO uDAO = DAOFactory.getUserDAO();
    private static BookDAO bDAO = DAOFactory.getBookDAO();
    private static List<Book> cart;

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
                System.out.println("You have entered an invalid value. Redirecting to option menu...");
                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e3){}
                startScreen();
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
        System.out.println("Welcome " + user.getFirstName() + "!");
        cart = new ArrayList<>();
        mainMenu(user);
    }

    public static void mainMenu(User currentUser) throws SQLException {
        List<Book> listOfBooks;
        System.out.println("Here is a list of book categories to browse and account actions: ");
        System.out.println("1: Fiction\n2: Non-Fiction\n3: Historical Fiction\n4: Science Fiction\n5: Self Help\n6: Horror\n7: Cookbooks\n8: Comic Books & Manga\n9: View Cart\n10: Check Out\n11: Log out\nPlease enter the number of the category you would like to browse or the action you would like to perform: ");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("You have entered an invalid value. Please enter an integer from 1 to 9.");
            System.out.println("Press Enter to continue");
            try{System.in.read();}
            catch(Exception e2){}
        }
        switch (choice) {
            case 1: //Fiction
                listOfBooks = bDAO.getBooksByCategory("Fiction");
                for(Book b : listOfBooks) {
                    System.out.println(b);
                }
                chooseBook("Fiction", currentUser);
                mainMenu(currentUser);
                break;
            case 2: //Non-fiction
                listOfBooks = bDAO.getBooksByCategory("Non-Fiction");
                for(Book b : listOfBooks) {
                    System.out.println(b);
                }
                chooseBook("Non-Fiction", currentUser);
                mainMenu(currentUser);
                break;
            case 3: //Historical fiction
                listOfBooks = bDAO.getBooksByCategory("Historical Fiction");
                for(Book b : listOfBooks) {
                    System.out.println(b);
                }
                chooseBook("Historical Fiction", currentUser);
                mainMenu(currentUser);
                break;
            case 4: //Science fiction
                listOfBooks = bDAO.getBooksByCategory("Science Fiction");
                for(Book b : listOfBooks) {
                    System.out.println(b);
                }
                chooseBook("Science Fiction", currentUser);
                mainMenu(currentUser);
                break;
            case 5: //Self help
                listOfBooks = bDAO.getBooksByCategory("Self Help");
                for(Book b : listOfBooks) {
                    System.out.println(b);
                }
                chooseBook("Self Help", currentUser);
                mainMenu(currentUser);
                break;
            case 6: //Horror
                listOfBooks = bDAO.getBooksByCategory("Horror");
                for(Book b : listOfBooks) {
                    System.out.println(b);
                }
                chooseBook("Horror", currentUser);
                mainMenu(currentUser);
                break;
            case 7: //Cookbooks
                listOfBooks = bDAO.getBooksByCategory("Cookbooks");
                for(Book b : listOfBooks) {
                    System.out.println(b);
                }
                chooseBook("Cookbooks", currentUser);
                mainMenu(currentUser);
                break;
            case 8: //Comic books and Manga
                listOfBooks = bDAO.getBooksByCategory("Comics & Manga");
                for(Book b : listOfBooks) {
                    System.out.println(b);
                }
                chooseBook("Comics & Manga", currentUser);
                mainMenu(currentUser);
                break;
            case 9: //View Cart
                System.out.println("Cart Contents: ");
                for(Book b : cart) {
                    System.out.println(b);
                }
                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e2){}
                mainMenu(currentUser);
                break;
            case 10: //Check Out
                System.out.println("Cart Contents: ");
                for(Book b : cart) {
                    System.out.println(b);
                }
                System.out.println("Options: \n1: Buy All\n2: Remove Item(s) From Cart");
                int choice2 = 0;
                try {
                    choice2 = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("You have entered an invalid value. Please enter an integer from 1 to 2.");
                    System.out.println("Press Enter to continue");
                    try{System.in.read();}
                    catch(Exception e2){}
                }
                switch (choice2) {
                    case 1:
                        for(Book b : cart){
                            System.out.println("Taking book: \'" + b.getTitle() + "\' off of the shelf...");
                            bDAO.deleteBook(b.getId());
                        }
                        cart = new ArrayList<>();
                        System.out.println("Thank you for your patronage!\n");
                        mainMenu(currentUser);
                        break;
                    case 2:
                        System.out.println("Please enter the ID of the book you would like to remove from your cart: ");
                        int choice3 = 0;
                        try {
                            choice3 = Integer.parseInt(scanner.nextLine());
                        } catch (Exception e) {
                            System.out.println("You have entered an invalid value.");
                            System.out.println("Press Enter to continue");
                            try{System.in.read();}
                            catch(Exception e2){}
                        }
                        Book remove = null;
                        for(Book b : cart) {
                            if(choice3 == b.getId()) {
                                System.out.println("Book: \'" + b.getTitle() + "\' has been removed from your cart.");
                                remove = b;
                                System.out.println("Press Enter to continue");
                                try{System.in.read();}
                                catch(Exception e2){}
                            }
                        }
                        if(remove != null)
                            cart.remove(remove);
                        break;
                    default:
                        System.out.println("You have entered an invalid value.");
                        break;
                }
                mainMenu(currentUser);
                break;
            case 11: //Log out
                startScreen();
                break;
            default: //Invalid
                System.out.println("You have entered an invalid value. Redirecting to option menu...");
                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e3){}
                mainMenu(currentUser);
                break;
        }
    }

    public static void chooseBook(String category, User user) throws SQLException {
        System.out.println("\nIf there is a book you would like here, enter its ID to add it to your cart:\nID: ");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("You have entered an invalid value. Please enter an integer from 1 to 9.");
            System.out.println("Press Enter to continue");
            try{System.in.read();}
            catch(Exception e2){}
        }
        Book chosenBook = bDAO.getBookById(choice);
        if(chosenBook.getCategory().equals(category)) {
            //Add to cart
            cart.add(chosenBook);
            System.out.println(chosenBook.getTitle() + " has been added to your cart!");
            System.out.println("Press Enter to continue");
            try{System.in.read();}
            catch(Exception e3){}
        } else {
            System.out.println("The ID you have entered is either invalid, or from a different category. ");
        }
        mainMenu(user);
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
        loginScreen();
    }

    //Initializes needed tables in the database (if they aren't already)
    public static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        if(!tableExists("books")) {
            String createEmp = "CREATE TABLE books (bookID INTEGER PRIMARY KEY AUTO_INCREMENT, title VARCHAR(50), author VARCHAR(100), isbn CHAR(13) UNIQUE, price INTEGER, category VARCHAR(50), description VARCHAR(500));";
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
