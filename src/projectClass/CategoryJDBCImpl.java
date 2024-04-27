package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class CategoryJDBCImpl implements CategoryJDBC {

    String GET_CATEGORY_BY_ID_QUERY = "SELECT * FROM categories WHERE id = ? UNION SELECT * FROM tmp_categories WHERE id = ?";
    String IS_PRESENT_INTERNAL_CATEGORY_QUERY = "SELECT name FROM categories WHERE name = ? AND hierarchyID = ? UNION SELECT name FROM tmp_categories WHERE name = ? AND hierarchyID = ?";
    String IS_VALID_PARENT_ID_QUERY = "SELECT name FROM categories WHERE id = ? AND hierarchyID = ? AND field IS NOT NULL AND id != LAST_INSERT_ID() UNION SELECT name FROM tmp_categories WHERE id = ? AND hierarchyID = ? AND field IS NOT NULL AND id != LAST_INSERT_ID()";
    String GET_CATEGORY_BY_NAME_AND_HIERARCHY_ID_QUERY = "SELECT * FROM categories WHERE name = ? AND hierarchyID = ? UNION SELECT id FROM tmp_categories WHERE name = ? AND hierarchyID = ?";
    String GET_ALL_LEAF_QUERY = "SELECT * FROM categories WHERE field IS NULL UNION SELECT * FROM tmp_categories WHERE field IS NULL";
    String GET_ALL_SAVED_LEAF_QUERY = "SELECT id FROM categories WHERE field IS NULL";
    String GET_ALL_NOT_SAVED_LEAF_QUERY = "SELECT id FROM tmp_categories WHERE field IS NULL";
    String GET_ALL_SAVED_ROOT = "SELECT id FROM categories WHERE root = ?";
    String GET_ALL_NOT_SAVED_ROOT = "SELECT id FROM tmp_categories WHERE root = ?";
    String GET_PARENT_CATEGORIES_QUERY = "SELECT id FROM categories WHERE hierarchyID = ? AND field IS NOT NULL AND id NOT IN (SELECT MAX(id) FROM categories) UNION SELECT * FROM tmp_categories WHERE hierarchyID = ? AND field IS NOT NULL AND id NOT IN (SELECT MAX(id) FROM tmp_categories)";
    String CREATE_CATEGORY_QUERY_1 = "SELECT MAX(id) AS max_id FROM ( SELECT id FROM categories UNION SELECT id FROM tmp_categories ) AS combinedTables";
    String CREATE_CATEGORY_QUERY_2 = "INSERT INTO tmp_categories (name, field, description, root, hierarchyid, idconfigurator) VALUES (?, ?, ?, ?, ?, ?)";
    String GET_ROOT_BY_LEAF_QUERY = "SELECT id FROM categories WHERE id = ( SELECT hierarchyid FROM categories WHERE id = ? AND name = ? UNION SELECT hierarchyid FROM tmp_categories WHERE id = ? AND name = ?) UNION SELECT id FROM tmp_categories WHERE id = ( SELECT hierarchyid FROM categories WHERE id = ? AND name = ? UNION SELECT hierarchyid FROM tmp_categories WHERE id = ? AND name = ?)";
    String GET_NUMBER_OF_EQUALS_CATEGORIES_QUERY = "SELECT ( SELECT COUNT(*) FROM categories WHERE name = ? ) + ( SELECT COUNT(*) FROM tmp_categories WHERE name = ? )";;
    String SAVE_TMP_CATEGORIES_QUERY = "INSERT INTO categories (id, name, field, description ,hierarchyid, idconfigurator,root) SELECT id, name, field, description, hierarchyid,idconfigurator,root FROM tmp_categories";
    String DELETE_TMP_CATEGORIES_QUERY = "DELETE FROM tmp_categories";

    @Override
    public Category getCategoryByID ( int ID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( GET_CATEGORY_BY_ID_QUERY, new ArrayList<>( Arrays.asList( ID, ID ) ) );
        return rs.next() ? new Category( ID, rs.getString( "name" ), rs.getString( "field" ), rs.getString( "description" ), rs.getBoolean( "root" ), rs.getInt( "hierarchyid" ), rs.getInt( "idconfigurator" ) ) : null;
    }

    @Override
    public boolean isPresentInternalCategory ( int hierarchyID, String nameToCheck ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( IS_PRESENT_INTERNAL_CATEGORY_QUERY, new ArrayList<>( Arrays.asList( hierarchyID, nameToCheck, hierarchyID, nameToCheck ) ) );
        return rs.next();
    }

    @Override
    public boolean isValidParentID ( int hierarchyID, int IDToCheck ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( IS_VALID_PARENT_ID_QUERY, new ArrayList<>( Arrays.asList( hierarchyID, IDToCheck, hierarchyID, IDToCheck ) ) );
        return rs.next();
    }

    @Override
    public Category getCategoryByNameAndHierarchyID ( String name, int hierarchyID ) throws SQLException
    {
        ResultSet rs = Conn.exQuery( GET_CATEGORY_BY_NAME_AND_HIERARCHY_ID_QUERY, new ArrayList<>( Arrays.asList( name, hierarchyID, name, hierarchyID ) ) );
        return rs.next() ? new Category( rs.getInt( 1 ), name, rs.getString( 3 ), rs.getString( 4 ), rs.getBoolean( 5 ), hierarchyID, rs.getInt( 7 ) ) : null;
    }

    @Override
    public List<Category> getAllLeaf () throws SQLException 
    {
        return getAll( GET_ALL_LEAF_QUERY );
    }

    @Override
    public List<Category> getAllSavedLeaf () throws SQLException 
    {
        return getAll( GET_ALL_SAVED_LEAF_QUERY );
    }

    @Override
    public List<Category> getAllNotSavedLeaf () throws SQLException 
    {
        return getAll( GET_ALL_NOT_SAVED_LEAF_QUERY );
    }

    @Override
    public List<Category> getAllSavedRoot () throws SQLException 
    {
        return getAll( GET_ALL_SAVED_ROOT );
    }

    @Override
    public List<Category> getAllNotSavedRoot () throws SQLException 
    {
        return getAll( GET_ALL_NOT_SAVED_ROOT );
    }

    @Override
    public List<Category> getParentCategories ( int hierarchyID ) throws SQLException 
    {
        List<Category> toReturn = new ArrayList<Category>();
        ResultSet rs = Conn.exQuery( GET_PARENT_CATEGORIES_QUERY, new ArrayList<>( Arrays.asList( hierarchyID, hierarchyID ) ) );
        while ( rs.next() ) toReturn.add( new CategoryJDBCImpl().getCategoryByID( rs.getInt( 1 ) ) );
        return toReturn;
    }

    @Override
    public Category createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID, int configuratorID ) throws SQLException 
    {
        if ( isRoot )
        {
            ResultSet rs = Conn.exQuery( CREATE_CATEGORY_QUERY_1 );
            rs.next();
            hierarchyID = rs.getInt( 1 ) + 1;
        }

        Conn.queryUpdate( CREATE_CATEGORY_QUERY_2, new ArrayList<>( Arrays.asList( name, field, description, ( isRoot ? 1 : 0 ), hierarchyID, configuratorID ) ) );
        return new CategoryJDBCImpl().getCategoryByNameAndHierarchyID( name, hierarchyID );
    }

    @Override
    public Category getRootByLeaf ( Category leaf ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( GET_ROOT_BY_LEAF_QUERY, new ArrayList<>( Arrays.asList( leaf.getID(), leaf.getName(), leaf.getID(), leaf.getName(), leaf.getID(), leaf.getName(), leaf.getID(), leaf.getName() ) ) );
        return getCategoryByID( rs.getInt( 1 ) );
    }

    @Override
    public int getNumberOfEqualsCategories ( Category category ) throws SQLException {
        int toReturn = 0;
        ResultSet rs = Conn.exQuery( GET_NUMBER_OF_EQUALS_CATEGORIES_QUERY, new ArrayList<>( Arrays.asList( category.getName(), category.getName() ) ) );
        while ( rs.next() ) toReturn++;
        return toReturn;
    }

    @Override
    public void saveTmpCategories () throws SQLException 
    {
        Conn.exQuery( SAVE_TMP_CATEGORIES_QUERY );
    }

    @Override
    public void deleteTmpCategories () throws SQLException 
    {
        Conn.exQuery( DELETE_TMP_CATEGORIES_QUERY );
    }

    private List<Category> getAll ( String query ) throws SQLException
    {
        List<Category> toReturn = new ArrayList<Category>();
        ResultSet rs = Conn.exQuery( query );
        while ( rs.next() ) toReturn.add( getCategoryByID( rs.getInt( 1 ) ) );
        return toReturn;
    }

}
