import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    void addBook(Book book) throws SQLException;

    void updateBook(Book book) throws SQLException;

    void deleteBook(int id) throws SQLException;

    List<User> getBooks() throws SQLException;

    User getBookById(int id) throws SQLException;

    User getBookByTitle(String bookTitle) throws SQLException;
}
