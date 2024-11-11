import java.sql.*;

public class DatabaseProcess {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/WBS";
            String username = "WBSAdmin";
            String password = "WBS_@dmn.root";
   
            Connection con = DriverManager.getConnection(url, username, password);
            
            // Create a statement
            Statement st = con.createStatement();

            // Execute a query
            ResultSet rs = st.executeQuery("SELECT * FROM your_table");

            // Process the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
               System.out.println("ID: " + id + ", Name: " + name);
            }

                // Close resources
                rs.close();
                st.close();
                con.close();

                System.out.println("Database connection closed.");
            } 
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
