package service;

import java.sql.SQLException;
import model.Category;
import repository.CategoryRepository;
import repository.RelationshipsBetweenCategoriesRepository;
import service.strategy.Strategy;

public class CategoryService{
    
    private CategoryRepository categoryRepository;
    private Category category;
    private RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository;

    private Strategy strategy;

    public CategoryService (CategoryRepository categoryRepository, RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository )
    {
        this.categoryRepository = categoryRepository;
        this.relationshipsBetweenCategoriesRepository = relationshipsBetweenCategoriesRepository;

    }

    public void setCategory ( Category category ) 
    {
        this.category = category;
    }

    public Category getCategory () 
    {
        return this.category;
    }

    public void setStrategy( Strategy strategy ) 
    {
        this.strategy = strategy;
    }

    public RelationshipsBetweenCategoriesRepository getRelationshipsBetweenCategoriesRepository () 
    {
        return this.relationshipsBetweenCategoriesRepository;
    }

    public CategoryRepository getCategoryRepository ()
    {
        return this.categoryRepository;
    }

    public String execute ( Category category, int IDToPrint, StringBuffer toReturn, StringBuffer spaces ) throws SQLException
    {
        return strategy.execute(this, category, IDToPrint, toReturn, spaces);
    }

    public String padRight ( String str, int maxLenght )
    {
        String toReturn = "";

        for ( int i = str.length(); i < maxLenght; i++) toReturn += " ";

        return toReturn;
    } 

    public boolean existValueOfField ( String field, Category parent ) throws SQLException
    {
        return relationshipsBetweenCategoriesRepository.getChildCategoryByFieldAndParentID( field, parent ) != null;
    }

    public void saveCategories () throws SQLException
    {
        categoryRepository.saveTmpCategories();

        relationshipsBetweenCategoriesRepository.saveTmpRelationshipsBetweenCategories();     

        relationshipsBetweenCategoriesRepository.deleteTmpRelationshipsBetweenCategories();

        categoryRepository.deleteTmpCategories();
    }

    public void createRelationship ( int parentID, String fieldType ) throws SQLException
    {
        relationshipsBetweenCategoriesRepository.createRelationship( parentID, this.category.getID(), fieldType );
    }

    public boolean isPresentInternalCategory ( Category root, String nameToCheck ) throws SQLException
    {
        return categoryRepository.isPresentInternalCategory( root.getHierarchyID(), nameToCheck );
    }

    public boolean isValidParentID ( Category root, int IDToCheck ) throws SQLException
    {
        return categoryRepository.isValidParentID( root.getHierarchyID(), IDToCheck );
    }

}