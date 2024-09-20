package Server.model;

public class Category {
    
    private int ID;
    private String name;
    private String field;
    private String description;
    private boolean root;
    private int hierarchyID;
    private int IDConfigurator;

    public Category ( int ID, String name, String field, String description, boolean root, int hierarchyID, int IDConfigurator )
    {
        this.ID = ID;
        this.name = name;
        this.field = field;
        this.description = description;
        this.root = root;
        this.hierarchyID = hierarchyID;
        this.IDConfigurator = IDConfigurator;
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

    public int getIDConfigurator ()
    {
        return this.IDConfigurator;
    }

    public boolean isRoot ()
    {
        return this.root;
    }

    public boolean isLeaf ()
    {
        return this.field == null;
    }

    @Override
    public boolean equals ( Object obj ) 
    {
        return this.ID == ( ( Category )obj ).ID;
    }

}