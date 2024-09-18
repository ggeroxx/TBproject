package controller.ClientServer;

public class ConversionFactorDTO 
{
    private int id;
    private int leaf1ID;
    private String leaf1Name;
    private int leaf1HierarchyID;
    private int leaf2ID;
    private String leaf2Name;
    private int leaf2HierarchyID;
    private Double value;

    public ConversionFactorDTO(int id, int leaf1ID, String leaf1Name, int leaf1HierarchyID, int leaf2ID, String leaf2Name, int leaf2HierarchyID, Double value) 
    {
        this.id = id;
        this.leaf1ID = leaf1ID;
        this.leaf1Name = leaf1Name;
        this.leaf1HierarchyID = leaf1HierarchyID;
        this.leaf2ID = leaf2ID;
        this.leaf2Name = leaf2Name;
        this.leaf2HierarchyID = leaf2HierarchyID;
        this.value = value;
    }

    public int getId() 
    {
        return id;
    }

    public int getLeaf1ID() 
    {
        return leaf1ID;
    }

    public String getLeaf1Name() 
    {
        return leaf1Name;
    }

    public int getLeaf1HierarchyID() 
    {
        return leaf1HierarchyID;
    }

    public int getLeaf2ID() 
    {
        return leaf2ID;
    }

    public String getLeaf2Name() 
    {
        return leaf2Name;
    }

    public int getLeaf2HierarchyID() 
    {
        return leaf2HierarchyID;
    }

    public Double getValue() 
    {
        return value;
    }

     @Override
    public boolean equals ( Object obj )
    {
        ConversionFactorDTO cf = ( ConversionFactorDTO ) obj;
        
        return this.leaf1HierarchyID == cf.getLeaf1HierarchyID()
                && this.leaf2HierarchyID == cf.getLeaf2HierarchyID()
                && this.leaf1Name.equals( cf.leaf1Name) 
                && this.leaf2Name.equals( cf.leaf2Name );
    }
}
