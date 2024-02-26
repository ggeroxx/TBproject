import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {
        
        String url = "jdbc:mysql://localhost/TBDB";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connessione riuscita!");

            String query = "SELECT * FROM configurators";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while ( rs.next() )
            {
                String f1 = rs.getString(1);
                String f2 = rs.getString(2);
                String f3 = rs.getString(3);
                String f4 = rs.getString(4);
                System.out.println(f1 + " " + f2 + " " + f3 + " " + f4);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
