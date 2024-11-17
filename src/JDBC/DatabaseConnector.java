package JDBC;

import java.sql.*;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Replace with your database credentials
        admininfo admin = new admininfo();
        
        String url = "jdbc:mysql://localhost:3306/WBS";
        String user = admin.getUser();
        String pass =  admin.getPassword();

        // Load the JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        // Establish a connection
        Connection con = DriverManager.getConnection(url, user, pass);

        return con;
    }

    public static void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
            System.out.println("Database connection closed.");
        }
    }
}