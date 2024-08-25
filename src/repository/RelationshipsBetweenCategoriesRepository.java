package repository;

import java.sql.SQLException;
import java.util.List;
import model.Category;

public interface RelationshipsBetweenCategoriesRepository {
    
    void createRelationship ( int parentID, int childID, String fieldType ) throws SQLException;

    List<String> getFieldValuesFromParentID ( int parentID ) throws SQLException;

    String getFieldValueFromChildID ( int childID ) throws SQLException;

    List<Integer> getChildIDsFromParentID ( int parentID ) throws SQLException;

    void saveTmpRelationshipsBetweenCategories () throws SQLException;

    void deleteTmpRelationshipsBetweenCategories () throws SQLException;
    
    Category getChildCategoryByFieldAndParentID ( String field, Category parent ) throws SQLException;

}
