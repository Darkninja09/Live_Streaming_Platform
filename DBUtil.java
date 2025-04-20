import java.sql.*;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/yourdbname";
    private static final String USER = "yourusername";
    private static final String PASSWORD = "yourpassword";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
