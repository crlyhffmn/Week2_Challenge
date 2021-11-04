import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    void addBook(Book book) throws SQLException;

    void updateBook(Book book) throws SQLException;

    void deleteBook(int id) throws SQLException;

    List<Book> getBooks() throws SQLException;

    Book getBookById(int id) throws SQLException;

    Book getBookByTitle(String bookTitle) throws SQLException;

    List<Book> getBooksByCategory(String category) throws SQLException;
}
