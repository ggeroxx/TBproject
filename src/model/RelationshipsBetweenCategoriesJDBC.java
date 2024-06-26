package model;

import java.sql.*;
import java.util.*;

public interface RelationshipsBetweenCategoriesJDBC {
    
    void createRelationship ( int parentID, int childID, String fieldType ) throws SQLException;

    List<String> getFieldValuesFromParentID ( int parentID ) throws SQLException;

    String getFieldValueFromChildID ( int childID ) throws SQLException;

    List<Integer> getChildIDsFromParentID ( int parentID ) throws SQLException;

    void saveTmpRelationshipsBetweenCategories () throws SQLException;

    void deleteTmpRelationshipsBetweenCategories () throws SQLException;
    
    Category getChildCategoryByFieldAndParentID ( String field, Category parent ) throws SQLException;

}
