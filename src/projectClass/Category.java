package projectClass;

import java.sql.SQLException;

public class Category implements Cloneable {
    
    private int ID;
    private String name;
    private String field;
    private String description;
    private boolean root;
    private int hierarchyID;
    private int IDConfigurator;
    private CategoryJDBC categoryJDBC;
    private RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC;

    public Category ( int ID, String name, String field, String description, boolean root, int hierarchyID, int IDConfigurator )
    {
        this.ID = ID;
        this.name = name;
        this.field = field;
        this.description = description;
        this.root = root;
        this.hierarchyID = hierarchyID;
        this.IDConfigurator = IDConfigurator;
        this.categoryJDBC = new CategoryJDBCImpl();
        this.relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();
    }

    public int getID() 
    {
        return this.ID;
    }

    public String getName() 
    {
        return this.name;
    }

    public String getField() 
    {
        return this.field;
    }

    public String getDescription() 
    {
        return this.description;
    }

    public int getHierarchyID() 
    {
        return this.hierarchyID;
    }

    public boolean isRoot ()
    {
        return this.root;
    }

    public boolean isLeaf ()
    {
        return this.field == null;
    }

    public void createRelationship ( int parentID, String fieldType ) throws SQLException
    {
        relationshipsBetweenCategoriesJDBC.createRelationship( parentID, this.ID, fieldType );
    }

    public boolean isPresentInternalCategory ( String nameToCheck ) throws SQLException
    {
        return categoryJDBC.isPresentInternalCategory( this.hierarchyID, nameToCheck );
    }

    public boolean isValidParentID ( int IDToCheck ) throws SQLException
    {
        return categoryJDBC.isValidParentID( this.hierarchyID, IDToCheck );
    }

    @Override
    protected Object clone() throws CloneNotSupportedException 
    {
        return new Category( this.ID, this.name, this.field, this.description, this.root, this.hierarchyID, this.IDConfigurator );
    }

    @Override
    public boolean equals ( Object obj ) 
    {
        return this.ID == ( ( Category ) obj ).ID;
    }

}