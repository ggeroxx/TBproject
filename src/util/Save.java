package util;

import java.sql.*;
import java.util.*;
import java.util.Map.*;
import projectClass.*;

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

    public static void saveConversionFactors ( ConversionFactors toSave ) throws SQLException
    {
        for ( Entry<Integer, ConversionFactor> entry : toSave.getList().entrySet() )
        {
            int leaf1 = entry.getValue().getLeaf_1().getID();
            int leaf2 = entry.getValue().getLeaf_2().getID();
            Double value = entry.getValue().getValue();

            String query = "INSERT IGNORE INTO conversionFactors (ID_leaf_1, ID_leaf_2, value) VALUES (?, ? ,?)";

            ArrayList<String> parameters = new ArrayList<String>();
            parameters.add( Integer.toString( leaf1 ) );
            parameters.add( Integer.toString( leaf2 ) );
            parameters.add( Double.toString( value ) );

            Conn.queryUpdate( query, parameters );
        }

        toSave = new ConversionFactors();
    }

    public static void saveAll ( ConversionFactors toSave ) throws SQLException
    {
        saveDistricts();
        saveCategories();
        saveConversionFactors( toSave );
    }

}
