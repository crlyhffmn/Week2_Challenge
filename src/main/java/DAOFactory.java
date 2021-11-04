public class DAOFactory {
    private static BookDAO bDAO;
    private static UserDAO uDAO;

    private DAOFactory() {
    }
    public static UserDAO getUserDAO() {
        if(uDAO == null){
            uDAO = new UserDAOImpl();
        }
        return uDAO;
    }

    public static BookDAO getBookDAO() {
        if(bDAO == null){
            bDAO = new BookDAOImpl();
        }
        return bDAO;
    }
}
