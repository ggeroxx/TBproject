package util;

import java.sql.*;
import java.util.*;
import projectClass.*;

public class Conn
{
    
    private static final String URL = "jdbc:mysql://localhost/tbdb";
    private static final String USER = "root";
    private static final String PASS = "";
    private static Connection conn;
    private static DistrictJDBC districtJDBC = new DistrictJDBCImpl();
    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
    private static DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC = new DistrictToMunicipalitiesJDBCImpl();
    private static RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();

    public static void openConnection () throws SQLException
    {
        conn = DriverManager.getConnection( URL, USER, PASS );
    }
        
    public static void creationTmpDistrictTable () throws SQLException
    {
        districtJDBC.createTmpTable();

        Integer max_id = districtJDBC.getMaxID();
        if( max_id == null ) districtJDBC.setIDValueAutoIncrement( 1 );
        districtJDBC.setTmpIDValueAutoIncrement( max_id );

        districtToMunicipalitiesJDBC.createTmpTable();
    }

    public static void creationTmpCategoryTable () throws SQLException
    {
        categoryJDBC.createTmpTable();

        Integer max_id = categoryJDBC.getMaxID();
        if( max_id == null ) categoryJDBC.setIDValueAutoIncrement( 1 );
        categoryJDBC.setTmpIDValueAutoIncrement( max_id );

        relationshipsBetweenCategoriesJDBC.createTmpTable();
    }

    public static void eliminationTmpTable () throws SQLException
    {
        districtToMunicipalitiesJDBC.dropTmpDistrictToMunicipalitiesTable();

        districtJDBC.dropTmpDistrictsTable();

        relationshipsBetweenCategoriesJDBC.dropTmpRelationshipsBetweenCategoriesTable();

        categoryJDBC.dropTmpCategoriesTable();
    }

    public static ResultSet exQuery ( String query ) throws SQLException
    {
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery( query );

        return rs;
    }

    public static <T> ResultSet exQuery ( String query, List<T> parameters ) throws SQLException
    {
        int i = 1;
        
        PreparedStatement stmt = conn.prepareStatement( query );
        for ( T parameter : parameters ) 
        {
            stmt.setString( i, ( String ) parameter );
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

    public static <T> void queryUpdate ( String query, List<T> parameters ) throws SQLException
    {
        int i = 1;
        
        PreparedStatement stmt = conn.prepareStatement( query );
        for ( T parameter : parameters ) 
        {
            stmt.setString( i, ( String ) parameter );
            i++;    
        }
        stmt.executeUpdate();
    }

    public static void closeConnection () throws SQLException
    {
        conn.close();
    }

}
