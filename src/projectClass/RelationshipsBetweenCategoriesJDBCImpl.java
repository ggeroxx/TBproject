package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class RelationshipsBetweenCategoriesJDBCImpl implements RelationshipsBetweenCategoriesJDBC {
    
    String CREATE_RELATIONSHIP_QUERY = "INSERT INTO tmp_relationshipsBetweenCategories (parentid, childid, fieldtype) VALUES (?, ?, ?)";
    String GET_FIELD_VALUES_FROM_PARENT_ID_QUERY = "SELECT fieldtype FROM relationshipsbetweencategories WHERE parentid = ? UNION SELECT fieldtype FROM tmp_relationshipsbetweencategories WHERE parentid = ?";
    String GET_FIELD_VALUE_FROM_CHILD_ID_QUERY = "SELECT fieldtype FROM relationshipsbetweencategories WHERE childid = ? UNION SELECT fieldtype FROM tmp_relationshipsbetweencategories WHERE childid = ?";
    String GET_CHILD_IDS_FROM_PARENT_ID_QUERY = "SELECT childid FROM relationshipsBetweenCategories WHERE parentid = ? UNION SELECT childid FROM tmp_relationshipsBetweenCategories WHERE parentid = ?";
    String SAVE_TMP_RELATIONSHIPS_BETWEEN_CATEGORIES_QUERY = "INSERT INTO relationshipsbetweencategories (parentid, childid, fieldtype) SELECT parentid, childid, fieldtype FROM tmp_relationshipsbetweencategories";
    String DELETE_TMP_RELATIONSHIPS_BETWEEN_CATEGORIES_QUERY = "DELETE FROM tmp_relationshipsbetweencategories";

    @Override
    public void createRelationship ( int parentID, int childID, String fieldType ) throws SQLException
    {
        Conn.queryUpdate( CREATE_RELATIONSHIP_QUERY, new ArrayList<>( Arrays.asList( parentID, childID, fieldType ) ) );
    }

    @Override
    public List<String> getFieldValuesFromParentID ( int parentID ) throws SQLException 
    {
        List<String> toReturn = new ArrayList<String>();
        ResultSet rs = Conn.exQuery( GET_FIELD_VALUES_FROM_PARENT_ID_QUERY, new ArrayList<>( Arrays.asList( parentID, parentID ) ) );
        while ( rs.next() ) toReturn.add( rs.getString( 1 ) );
        return toReturn;
    }

    @Override
    public String getFieldValueFromChildID ( int childID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( GET_FIELD_VALUE_FROM_CHILD_ID_QUERY, new ArrayList<>( Arrays.asList( childID, childID ) ) );
        return rs.next() ? rs.getString( 1 ) : null;
    }

    @Override
    public List<Integer> getChildIDsFromParentID ( int parentID ) throws SQLException 
    {
        List<Integer> toReturn = new ArrayList<Integer>();
        ResultSet rs = Conn.exQuery( GET_CHILD_IDS_FROM_PARENT_ID_QUERY, new ArrayList<>( Arrays.asList( parentID, parentID ) ) );
        while ( rs.next() ) toReturn.add( rs.getInt( 1 ) );
        return toReturn;
    }

    @Override
    public void saveTmpRelationshipsBetweenCategories () throws SQLException 
    {
        Conn.exQuery( SAVE_TMP_RELATIONSHIPS_BETWEEN_CATEGORIES_QUERY );
    }

    @Override
    public void deleteTmpRelationshipsBetweenCategories () throws SQLException 
    {
        Conn.exQuery( DELETE_TMP_RELATIONSHIPS_BETWEEN_CATEGORIES_QUERY );
    }

}
