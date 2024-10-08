package Server.repository.JDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Server.model.Category;
import Server.model.util.Conn;
import Server.model.util.Queries;
import Server.repository.CategoryRepository;
import Server.repository.RelationshipsBetweenCategoriesRepository;

public class JDBCRelationshipsBetweenCategoriesRepository implements RelationshipsBetweenCategoriesRepository {

    private static CategoryRepository categoryRepository = new JDBCCategoryRepository();

    @Override
    public void createRelationship ( int parentID, int childID, String fieldType ) throws SQLException
    {
        Conn.queryUpdate( Queries.CREATE_RELATIONSHIP_QUERY, new ArrayList<>( Arrays.asList( parentID, childID, fieldType ) ) );
    }

    @Override
    public List<String> getFieldValuesFromParentID ( int parentID ) throws SQLException 
    {
        List<String> toReturn = new ArrayList<String>();
        ResultSet rs = Conn.exQuery( Queries.GET_FIELD_VALUES_FROM_PARENT_ID_QUERY, new ArrayList<>( Arrays.asList( parentID, parentID ) ) );
        while ( rs.next() ) toReturn.add( rs.getString( 1 ) );
        return toReturn;
    }

    @Override
    public String getFieldValueFromChildID ( int childID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_FIELD_VALUE_FROM_CHILD_ID_QUERY, new ArrayList<>( Arrays.asList( childID, childID ) ) );
        return rs.next() ? rs.getString( 1 ) : null;
    }

    @Override
    public List<Integer> getChildIDsFromParentID ( int parentID ) throws SQLException 
    {
        List<Integer> toReturn = new ArrayList<Integer>();
        ResultSet rs = Conn.exQuery( Queries.GET_CHILD_IDS_FROM_PARENT_ID_QUERY, new ArrayList<>( Arrays.asList( parentID, parentID ) ) );
        while ( rs.next() ) toReturn.add( rs.getInt( 1 ) );
        return toReturn;
    }

    @Override
    public void saveTmpRelationshipsBetweenCategories () throws SQLException 
    {
        Conn.queryUpdate( Queries.SAVE_TMP_RELATIONSHIPS_BETWEEN_CATEGORIES_QUERY );
    }

    @Override
    public void deleteTmpRelationshipsBetweenCategories () throws SQLException 
    {
        Conn.queryUpdate( Queries.DELETE_TMP_RELATIONSHIPS_BETWEEN_CATEGORIES_QUERY );
    }

    @Override
    public Category getChildCategoryByFieldAndParentID ( String field, Category parent ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CHILD_CATEGORY_BY_FIELD_QUERY, new ArrayList<>( Arrays.asList( field, parent.getID(), field, parent.getID() ) ) );
        return rs.next() ? categoryRepository.getCategoryByID( rs.getInt( 1 ) ) : null;
    }

}
