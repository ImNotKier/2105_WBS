import java.sql.*;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Replace with your database credentials
        String url = "jdbc:mysql://localhost:3306/WBS";
        String username = "WBSAdmin";
        String password = "WBS_@dmn.root";

        // Load the JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        // Establish a connection
        Connection con = DriverManager.getConnection(url, username, password);

        return con;
    }

    public static void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
            System.out.println("Database connection closed.");
        }
    }
}