package service;

import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Category;
import repository.CategoryRepository;
import repository.RelationshipsBetweenCategoriesRepository;
import service.strategy.BuildHierarchyStrategy;
import service.strategy.InfoStrategy;
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

    public String execute ( ArrayList <Object> params) throws SQLException
    {
        return strategy.execute(this, params);
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

    public String buildHierarchy ( int IDToPrint, StringBuffer toReturn, StringBuffer spaces ) throws SQLException
    {
        ArrayList<Object> params = new ArrayList<>();
        params.add(IDToPrint);
        params.add(toReturn);
        params.add(spaces);

        setStrategy(new BuildHierarchyStrategy());
        return execute(params);
    }

    public String info ( Category category ) throws SQLException
    {
        ArrayList<Object> params = new ArrayList<>();
        params.add( category );

        setStrategy(new InfoStrategy());
        return execute(params);
    }

    public List<Category> getAllRoot() throws SQLException
    {
        return categoryRepository.getAllRoot();
    }

    public List<Category> getAllCategoriesFromRoot( Category root) throws SQLException
    {
        return categoryRepository.getAllCategoriesFromRoot(root);
    }

    public List <Category> getAllSavedLeaf () throws SQLException
    {
        return categoryRepository.getAllSavedLeaf();
    }
    
    public List <Category> getAllNotSavedLeaf () throws SQLException
    {
        return categoryRepository.getAllNotSavedLeaf();
    }

    public Category getCategoryByID ( int categoryID ) throws SQLException
    {
        return categoryRepository.getCategoryByID ( categoryID);
    }

    public Category getRootByLeaf ( Category category ) throws SQLException
    {
        return categoryRepository.getRootByLeaf(category);
    }

    public List<String> getFieldValuesFromParentID ( int parentID ) throws SQLException
    {
        return relationshipsBetweenCategoriesRepository.getFieldValuesFromParentID(parentID);
    }

    public Category getChildCategoryByFieldAndParentID ( String field, Category parent ) throws SQLException
    {
        return relationshipsBetweenCategoriesRepository.getChildCategoryByFieldAndParentID(field, parent);
    }

    public Category getRootCategoryByName (String name) throws SQLException
    {
        return categoryRepository.getRootCategoryByName(name);
    }

    public int getNumberOfEqualsCategories( Category category) throws SQLException
    {
        return categoryRepository.getNumberOfEqualsCategories(category);
    }

}
