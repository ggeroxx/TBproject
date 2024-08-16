package controller.GRASPController;

import java.sql.*;
import model.*;
import util.*;


public class CategoryGRASPController{
    
    private CategoryRepository categoryRepository;
    private Category category;
    private RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository;

    public CategoryGRASPController (CategoryRepository categoryRepository, RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository )
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

    public CategoryRepository getCategoryRepository ()
    {
        return this.categoryRepository;
    }

    public String buildHierarchy ( int IDToPrint, StringBuffer toReturn, StringBuffer spaces ) throws SQLException
    {
        Category notLeaf = getCategoryRepository().getCategoryByID( IDToPrint );

        if ( notLeaf.isRoot() ) toReturn.append( notLeaf.getID() + ". " + notLeaf.getName() + "\n\n" );
        else if ( getCategoryRepository().getCategoriesWithoutChild().contains( notLeaf ) ) toReturn.append( spaces.toString() + Constants.RED + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + Constants.NO_CHILD + Constants.RESET + "\n\n" );
        else toReturn.append( spaces.toString() + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + "\n\n" );
        
        for ( Integer IDLeaf : relationshipsBetweenCategoriesRepository.getChildIDsFromParentID( IDToPrint ) ) buildHierarchy( IDLeaf, toReturn, spaces.append( "\t" ) );

        if ( spaces.length() > 1 ) spaces.setLength( spaces.length() - 1 );
        else spaces.setLength( 0 );

        return toReturn.toString();
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