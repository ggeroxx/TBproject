package util;
import java.sql.*;
import java.util.ArrayList;

public class Conn
{
    
    private static String url = "jdbc:mysql://localhost/tbdb";
    private static String user = "root";
    private static String pass = "";
    private static Connection conn;

    public static void openConnection () throws SQLException
    {
        conn = DriverManager.getConnection(url, user, pass);
        creationTmpTable();
    }

    public static void creationTmpTable () throws SQLException
    {
        String query;
        ResultSet rs;

        query = "CREATE TABLE tmp_districts (" +
                            "ID int NOT NULL AUTO_INCREMENT," +
                            "name varchar(50) NOT NULL," +
                            "IDConfigurator int DEFAULT NULL," +
                            "PRIMARY KEY (ID)," +
                            "KEY IDConfigurator (IDConfigurator)," +
                            "CONSTRAINT fk_tmp_districts FOREIGN KEY (IDConfigurator) REFERENCES configurators (ID)" + 
                       ")";
        Conn.queryUpdate( query );

        query = "CREATE TABLE tmp_districtToMunicipalities (" +
                    "ID int NOT NULL AUTO_INCREMENT," +
                    "IDDistrict int DEFAULT NULL," +
                    "IDMunicipality int DEFAULT NULL," +
                    "PRIMARY KEY (ID)," +
                    "KEY IDDistrict (IDDistrict), " +
                    "KEY IDMunicipality (IDMunicipality)," +
                    "CONSTRAINT fk1_tmp_districttomunicipalities FOREIGN KEY (IDDistrict) REFERENCES tmp_districts (ID)," +
                    "CONSTRAINT fk2_tmp_districttomunicipalities FOREIGN KEY (IDMunicipality) REFERENCES municipalities (ID)" +
                ")";
        Conn.queryUpdate( query );

        query = "SELECT MAX(id) + 1 AS max_id FROM districts";
        rs = Conn.exQuery( query );
        rs.next();
        query = "ALTER TABLE tmp_districts AUTO_INCREMENT = " + Integer.toString( rs.getInt(1) );
        Conn.queryUpdate( query );

        query = "CREATE TABLE tmp_categories(" +
                    "ID int NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL," +
                    "field VARCHAR(25)," +
                    "description VARCHAR(100)," +
                    "root BOOLEAN NOT NULL," +
                    "hierarchyID int NOT NULL," +
                    "IDConfigurator int NOT NULL," +
                    "CONSTRAINT fk_tmp_categories FOREIGN KEY (IDConfigurator) REFERENCES configurators(ID)" +
                ")"; 
        Conn.queryUpdate( query );

        query = "SELECT MAX(id) + 1 AS max_id FROM categories";
        rs = Conn.exQuery( query );
        rs.next();
        query = "ALTER TABLE tmp_categories AUTO_INCREMENT = " + Integer.toString( rs.getInt(1) );
        Conn.queryUpdate( query );

        query = "CREATE TABLE tmp_relationshipsBetweenCategories(" +  
                    "parentID int NOT NULL," +
                    "childID int NOT NULL," +
                    "fieldType VARCHAR(25) NOT NULL," +
                    "PRIMARY KEY (parentID, childID)," +
                    "CONSTRAINT fk1_tmp_relationshipsBetweenCategories FOREIGN KEY (parentID) REFERENCES tmp_categories(ID)," +
                    "CONSTRAINT fk2_tmp_relationshipsBetweenCategories FOREIGN KEY (childID) REFERENCES tmp_categories(ID)" + 
                ")";
        Conn.queryUpdate( query );
    }

    public static void eliminationTmpTable () throws SQLException
    {
        String query;

        query = "DROP TABLE tmp_districtToMunicipalities, tmp_districts";
        Conn.queryUpdate( query );

        query = "DROP TABLE tmp_relationshipsBetweenCategories, tmp_categories";
        Conn.queryUpdate( query );
    }

    public static ResultSet exQuery ( String query ) throws SQLException
    {
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery( query );

        return rs;
    }

    public static ResultSet exQuery ( String query, ArrayList<String> parameters ) throws SQLException
    {
        int i = 1;
        
        PreparedStatement stmt = conn.prepareStatement( query );
        for ( String parameter : parameters ) 
        {
            stmt.setString( i, parameter );
            i++;    
        }
        ResultSet rs = stmt.executeQuery();

        return rs;
    }

    public static void queryUpdate ( String query ) throws SQLException
    {
        Statement stmt = conn.createStatement();
        
        stmt.executeUpdate( query );
    }

    public static void queryUpdate ( String query, ArrayList<String> parameters ) throws SQLException
    {
        int i = 1;
        
        PreparedStatement stmt = conn.prepareStatement( query );
        for ( String parameter : parameters ) 
        {
            stmt.setString( i, parameter );
            i++;    
        }
        stmt.executeUpdate();
    }

    public static void closeConnection () throws SQLException
    {
        eliminationTmpTable();   
        conn.close();
    }

}
