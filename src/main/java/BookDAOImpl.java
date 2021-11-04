import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    Connection connection;

    public BookDAOImpl() { this.connection = ConnectionFactory.getConnection(); }

    @Override
    public void addBook(Book book) throws SQLException {
        String sql = "insert into books (title, author, isbn, price, category, description) values (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getISBN());
        preparedStatement.setInt(4, book.getPrice());
        preparedStatement.setString(5, book.getCategory());
        preparedStatement.setString(6, book.getDescription());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("Book saved");
        else
            System.out.println("Oops! something went wrong");
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        String sql = "update books set title = ?, author = ?, isbn = ?, price = ?, category = ?, description = ? where bookID = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getISBN());
        preparedStatement.setInt(4, book.getPrice());
        preparedStatement.setString(5, book.getCategory());
        preparedStatement.setString(6, book.getDescription());
        preparedStatement.setInt(7, book.getId());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("Book updated");
        else
            System.out.println("Oops! something went wrong");
    }

    @Override
    public void deleteBook(int id) throws SQLException {
        String sql = "delete from books where bookID = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if(count > 0){
            System.out.println("Book deleted");
        }else{
            System.out.println("Oops! something went wrong");
        }
    }

    @Override
    public List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            String isbn = resultSet.getString(4);
            int price = resultSet.getInt(5);
            String category = resultSet.getString(6);
            String description = resultSet.getString(7);
            Book book = new Book(id, title, author, isbn, price, category, description);
            books.add(book);
        }
        return books;
    }

    @Override
    public Book getBookById(int bookID) throws SQLException {
        Book book = new Book();
        String sql = "select * from books where bookID = " + bookID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if (resultSet != null) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            String isbn = resultSet.getString(4);
            int price = resultSet.getInt(5);
            String category = resultSet.getString(6);
            String description = resultSet.getString(7);
            book = new Book(id, title, author, isbn, price, category, description);
        } else {
            System.out.println("no record found");
            return null;
        }
        return book;
    }

    @Override
    public Book getBookByTitle(String bookTitle) throws SQLException {
        Book book = new Book();
        String sql = "select * from books where title = \'" + bookTitle + "\';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if (resultSet != null) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            String isbn = resultSet.getString(4);
            int price = resultSet.getInt(5);
            String category = resultSet.getString(6);
            String description = resultSet.getString(7);
            book = new Book(id, title, author, isbn, price, category, description);
        } else {
            System.out.println("no record found");
            return null;
        }
        return book;
    }

    @Override
    public List<Book> getBooksByCategory(String category) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "select * from books where category = \'" + category + "\';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            String isbn = resultSet.getString(4);
            int price = resultSet.getInt(5);
            String cat = resultSet.getString(6);
            String description = resultSet.getString(7);
            Book book = new Book(id, title, author, isbn, price, cat, description);
            books.add(book);
        }
        return books;
    }
}
