package model;

import java.sql.*;
import java.util.*;

import util.*;

public class CategoryJDBCImpl implements CategoryJDBC {

    @Override
    public Category getCategoryByID ( int ID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CATEGORY_BY_ID_QUERY, new ArrayList<>( Arrays.asList( ID, ID ) ) );
        return rs.next() ? new Category( ID, rs.getString( 2 ), rs.getString( 3 ), rs.getString( 4 ), rs.getBoolean( 5 ), rs.getInt( 6 ), rs.getInt( 7 ) ) : null;
    }

    @Override
    public Category getRootCategoryByID ( int ID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_ROOT_CATEGORY_BY_ID_QUERY, new ArrayList<>( Arrays.asList( ID, 1, ID, 1 ) ) );
        return rs.next() ? new Category( ID, rs.getString( 2 ), rs.getString( 3 ), rs.getString( 4 ), rs.getBoolean( 5 ), rs.getInt( 6 ), rs.getInt( 7 ) ) : null;
    }

    @Override
    public Category getRootCategoryByName ( String name ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_ROOT_CATEGORY_BY_NAME_QUERY, new ArrayList<>( Arrays.asList( name, 1, name, 1 ) ) );
        return rs.next() ? new Category( rs.getInt( 1 ), name, rs.getString( 3 ), rs.getString( 4 ), rs.getBoolean( 5 ), rs.getInt( 6 ), rs.getInt( 7 ) )  : null;
    }

    @Override
    public boolean isPresentInternalCategory ( int hierarchyID, String nameToCheck ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.IS_PRESENT_INTERNAL_CATEGORY_QUERY, new ArrayList<>( Arrays.asList( nameToCheck, hierarchyID, nameToCheck, hierarchyID ) ) );
        return rs.next();
    }

    @Override
    public boolean isValidParentID ( int hierarchyID, int IDToCheck ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.IS_VALID_PARENT_ID_QUERY, new ArrayList<>( Arrays.asList( IDToCheck, hierarchyID, IDToCheck, hierarchyID ) ) );
        return rs.next();
    }

    @Override
    public Category getCategoryByNameAndHierarchyID ( String name, int hierarchyID ) throws SQLException
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CATEGORY_BY_NAME_AND_HIERARCHY_ID_QUERY, new ArrayList<>( Arrays.asList( name, hierarchyID, name, hierarchyID ) ) );
        return rs.next() ? new Category( rs.getInt( 1 ), name, rs.getString( 3 ), rs.getString( 4 ), rs.getBoolean( 5 ), hierarchyID, rs.getInt( 7 ) ) : null;
    }

    @Override
    public List<Category> getAllLeaf () throws SQLException 
    {
        return getAll( Queries.GET_ALL_LEAF_QUERY );
    }

    @Override
    public List<Category> getAllRoot() throws SQLException 
    {
        return getAll ( Queries.GET_ALL_ROOT_QUERY );
    }

    @Override
    public List<Category> getAllSavedLeaf () throws SQLException 
    {
        return getAll( Queries.GET_ALL_SAVED_LEAF_QUERY );
    }

    @Override
    public List<Category> getAllNotSavedLeaf () throws SQLException 
    {
        return getAll( Queries.GET_ALL_NOT_SAVED_LEAF_QUERY );
    }

    @Override
    public List<Category> getAllSavedRoot () throws SQLException 
    {
        return getAll( Queries.GET_ALL_SAVED_ROOT );
    }

    @Override
    public List<Category> getAllNotSavedRoot () throws SQLException 
    {
        return getAll( Queries.GET_ALL_NOT_SAVED_ROOT );
    }

    @Override
    public List<Category> getCategoriesWithoutChild() throws SQLException 
    {
        return getAll( Queries.GET_CATEGORY_WITHOUT_CHILD_QUERY );
    }

    private List<Category> getAll ( String query ) throws SQLException
    {
        List<Category> toReturn = new ArrayList<Category>();
        ResultSet rs = Conn.exQuery( query );
        while ( rs.next() ) toReturn.add( getCategoryByID( rs.getInt( 1 ) ) );
        return toReturn;
    }

    @Override
    public List<Category> getParentCategories ( int hierarchyID ) throws SQLException 
    {
        List<Category> toReturn = new ArrayList<Category>();
        ResultSet rs = Conn.exQuery( Queries.GET_PARENT_CATEGORIES_QUERY, new ArrayList<>( Arrays.asList( hierarchyID, hierarchyID ) ) );
        while ( rs.next() ) toReturn.add( getCategoryByID( rs.getInt( 1 ) ) );
        return toReturn;
    }

    @Override
    public Category createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID, int configuratorID ) throws SQLException 
    {
        if ( isRoot )
        {
            ResultSet rs = Conn.exQuery( Queries.CREATE_CATEGORY_QUERY_1 );
            rs.next();
            hierarchyID = rs.getInt( 1 ) + 1;
        }

        Conn.queryUpdate( Queries.CREATE_CATEGORY_QUERY_2, new ArrayList<>( Arrays.asList( name, field, description, ( isRoot ? 1 : 0 ), hierarchyID, configuratorID ) ) );
        return getCategoryByNameAndHierarchyID( name, hierarchyID );
    }

    @Override
    public Category getRootByLeaf ( Category leaf ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_ROOT_BY_LEAF_QUERY, new ArrayList<>( Arrays.asList( leaf.getID(), leaf.getName(), leaf.getID(), leaf.getName(), leaf.getID(), leaf.getName(), leaf.getID(), leaf.getName() ) ) );
        rs.next();
        return getCategoryByID( rs.getInt( 1 ) );
    }

    @Override
    public int getNumberOfEqualsCategories ( Category category ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_NUMBER_OF_EQUALS_CATEGORIES_QUERY, new ArrayList<>( Arrays.asList( category.getName(), category.getName() ) ) );
        rs.next();
        return rs.getInt( 1 );
    }

    @Override
    public void saveTmpCategories () throws SQLException 
    {
        Conn.queryUpdate( Queries.SAVE_TMP_CATEGORIES_QUERY );
    }

    @Override
    public void deleteTmpCategories () throws SQLException 
    {
        Conn.queryUpdate( Queries.DELETE_TMP_CATEGORIES_QUERY );
    }

    @Override
    public Integer getMaxID () throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_MAX_ID_CATEGORY_QUERY );
        return rs.next() ? rs.getInt( 1 ) : null;
    }

    @Override
    public void setIDValueAutoIncrement ( int newValue ) throws SQLException
    {
        Conn.queryUpdate( Queries.SET_CATEGORY_ID_VALUE_AUTO_INCREMENT_QUERY, new ArrayList<>( Arrays.asList( newValue ) ) );
    }

    @Override
    public void setTmpIDValueAutoIncrement ( int newValue ) throws SQLException 
    {
        Conn.queryUpdate( Queries.SET_CATEGORY_TMP_ID_VALUE_AUTO_INCREMENT_QUERY, new ArrayList<>( Arrays.asList( newValue ) ) );
    }

}
