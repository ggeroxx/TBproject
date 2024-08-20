package service;

import java.sql.*;
import model.*;
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

    /*public void setBuildHierarchyStrategy( Strategy buildHierarchyStrategy ) 
    {
        this.buildHierarchyStrategy = buildHierarchyStrategy;
    }

    public void setInfoStrategy( Strategy infoStrategy ) 
    {
        this.infoStrategy = infoStrategy;
    }*/

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

    /*public String buildHierarchy ( int IDToPrint, StringBuffer toReturn, StringBuffer spaces ) throws SQLException
    {
        //return strategy.execute(this, IDToPrint, toReturn, spaces);

        Category notLeaf = getCategoryRepository().getCategoryByID( IDToPrint );

        if ( notLeaf.isRoot() ) toReturn.append( notLeaf.getID() + ". " + notLeaf.getName() + "\n\n" );
        else if ( getCategoryRepository().getCategoriesWithoutChild().contains( notLeaf ) ) toReturn.append( spaces.toString() + Constants.RED + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + Constants.NO_CHILD + Constants.RESET + "\n\n" );
        else toReturn.append( spaces.toString() + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + "\n\n" );
        
        for ( Integer IDLeaf : relationshipsBetweenCategoriesRepository.getChildIDsFromParentID( IDToPrint ) ) buildHierarchy( IDLeaf, toReturn, spaces.append( "\t" ) );

        if ( spaces.length() > 1 ) spaces.setLength( spaces.length() - 1 );
        else spaces.setLength( 0 );

        return toReturn.toString();


    }*/

    public String padRight ( String str, int maxLenght )
    {
        String toReturn = "";

        for ( int i = str.length(); i < maxLenght; i++) toReturn += " ";

        return toReturn;
    } 

    /*public String info ( Category category ) throws SQLException
    {
        //return strategy.execute(this, category);

        StringBuffer sb = new StringBuffer();    

        sb.append( Constants.NAME + ":" + padRight( Constants.NAME + ":", 20 ) + Constants.BOLD + category.getName() + Constants.RESET + "\n" );
        if ( !category.isRoot() ) sb.append( Constants.DESCRIPTION + ":" + padRight( Constants.DESCRIPTION + ":", 20 ) + category.getDescription() + "\n" );

        if ( category.getField() == null ) sb.append( Constants.VALUE_OF_DOMAIN + ":" + padRight( Constants.VALUE_OF_DOMAIN + ":", 20 ) + relationshipsBetweenCategoriesRepository.getFieldValueFromChildID( category.getID() ) + "\n" );
        else 
        {
            sb.append( Constants.FIELD + ":" + padRight( Constants.FIELD + ":", 20 ) + category.getField() + " = { " );
            for ( String fieldValue : relationshipsBetweenCategoriesRepository.getFieldValuesFromParentID( category.getID() ) ) sb.append( Constants.YELLOW + fieldValue + Constants.RESET + ", " );
            sb.deleteCharAt( sb.length() - 2 );
            sb.append( "}\n" );
        }

        return sb.toString();
    }*/

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