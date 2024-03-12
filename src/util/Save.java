package util;

import java.sql.SQLException;

public class Save {
    
    public static void saveCategories () throws SQLException
    {
        String query;

        query = "INSERT INTO categories (id, name, field, description,hierarchyid, idconfigurator,root) " +
                "SELECT id, name, field, description, hierarchyid,idconfigurator,root " +
                "FROM tmp_categories";
        Conn.queryUpdate( query );

        query = "INSERT INTO relationshipsbetweencategories (parentid, childid, fieldtype) " +
                "SELECT parentid, childid, fieldtype " +
                "FROM tmp_relationshipsbetweencategories";
        Conn.queryUpdate( query );

        query = "DELETE FROM tmp_relationshipsbetweencategories";
        Conn.queryUpdate(query);

        query = "DELETE FROM tmp_categories";
        Conn.queryUpdate(query);
    }

    public static void saveDistricts () throws SQLException
    {
        String query;

        query = "INSERT INTO districts (name, idconfigurator) " +
                "SELECT name, idconfigurator " +
                "FROM tmp_districts";
        Conn.queryUpdate( query );

        query = "INSERT INTO districttomunicipalities (iddistrict, idmunicipality) " +
                "SELECT iddistrict, idmunicipality " +
                "FROM tmp_districttomunicipalities";
        Conn.queryUpdate( query );

        query = "DELETE FROM tmp_districttomunicipalities";
        Conn.queryUpdate(query);

        query = "DELETE FROM tmp_districts";
        Conn.queryUpdate(query);
    }

    public static void saveAll () throws SQLException
    {
        saveDistricts();
        saveCategories();
    }

}
