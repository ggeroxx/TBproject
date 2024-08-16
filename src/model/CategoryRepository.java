package model;

import java.sql.*;
import java.util.*;

public interface CategoryRepository {
    
    Category getCategoryByID ( int ID ) throws SQLException;

    Category getRootCategoryByID ( int ID ) throws SQLException;

    Category getRootCategoryByName ( String name ) throws SQLException;

    boolean isPresentInternalCategory ( int hierarchyID, String nameToCheck ) throws SQLException;

    boolean isValidParentID ( int hierarchyID, int IDToCheck ) throws SQLException;

    Category getCategoryByNameAndHierarchyID ( String name, int hierarchyID ) throws SQLException;

    List<Category> getAllLeaf () throws SQLException;

    List<Category> getAllRoot () throws SQLException;

    List<Category> getAllSavedLeaf () throws SQLException;

    List<Category> getAllNotSavedLeaf () throws SQLException;

    List<Category> getAllSavedRoot () throws SQLException;

    List<Category> getAllNotSavedRoot () throws SQLException;

    List<Category> getParentCategories ( int hierarchyID ) throws SQLException;

    List<Category> getCategoriesWithoutChild () throws SQLException;

    Category createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID, int configuratorID ) throws SQLException;

    Category getRootByLeaf ( Category leaf ) throws SQLException;

    int getNumberOfEqualsCategories ( Category category ) throws SQLException;

    void saveTmpCategories () throws SQLException;

    void deleteTmpCategories () throws SQLException;

    Integer getMaxID () throws SQLException;

    void setTmpIDValueAutoIncrement ( int newValue ) throws SQLException;

}
