package controller.GRASPController;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Category;
import repository.CategoryRepository;
import repository.RelationshipsBetweenCategoriesRepository;
import service.CategoryService;
import service.strategy.BuildHierarchyStrategy;
import service.strategy.InfoStrategy;

public class CategoryGRASPController{
    
    private CategoryService categoryService;

    public CategoryGRASPController (CategoryService categoryService )
    {
        this.categoryService = categoryService;
    }

    public void setCategory ( Category category ) 
    {
        this.categoryService.setCategory(category);
    }

    public Category getCategory () 
    {
        return this.categoryService.getCategory();
    }

    public CategoryRepository getCategoryRepository ()
    {
        return this.categoryService.getCategoryRepository();
    }
    public RelationshipsBetweenCategoriesRepository getRelationshipsBetweenCategoriesRepository () 
    {
        return this.categoryService.getRelationshipsBetweenCategoriesRepository();
    }

    public String buildHierarchy ( int IDToPrint, StringBuffer toReturn, StringBuffer spaces ) throws SQLException
    {
        ArrayList<Object> params = new ArrayList<>();
        params.add(IDToPrint);
        params.add(toReturn);
        params.add(spaces);

        this.categoryService.setStrategy(new BuildHierarchyStrategy());
        return this.categoryService.execute(params);
    }

    public String info ( Category category ) throws SQLException
    {
        ArrayList<Object> params = new ArrayList<>();
        params.add( category );

        this.categoryService.setStrategy(new InfoStrategy());
        return this.categoryService.execute(params);
    }

    public boolean existValueOfField ( String field, Category parent ) throws SQLException
    {
        return this.categoryService.existValueOfField(field, parent);
    }

    public void saveCategories () throws SQLException
    {
        this.categoryService.saveCategories();
    }

    public void createRelationship ( int parentID, String fieldType ) throws SQLException
    {
        this.categoryService.createRelationship(parentID, fieldType);
    }

    public boolean isPresentInternalCategory ( Category root, String nameToCheck ) throws SQLException
    {
        return this.categoryService.isPresentInternalCategory(root, nameToCheck);
    }

    public boolean isValidParentID ( Category root, int IDToCheck ) throws SQLException
    {
        return this.categoryService.isValidParentID(root, IDToCheck);
    }

}