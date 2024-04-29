package projectClass;

import java.sql.*;
import java.util.*;

public interface RelationshipsBetweenCategoriesJDBC {
    
    void createRelationship ( int parentID, int childID, String fieldType ) throws SQLException;

    List<String> getFieldValuesFromParentID ( int parentID ) throws SQLException;

    String getFieldValueFromChildID ( int childID ) throws SQLException;

    List<Integer> getChildIDsFromParentID ( int parentID ) throws SQLException;

    void saveTmpRelationshipsBetweenCategories () throws SQLException;

    void deleteTmpRelationshipsBetweenCategories () throws SQLException;

    void dropTmpRelationshipsBetweenCategoriesTable () throws SQLException;

    void createTmpTable () throws SQLException;

}
