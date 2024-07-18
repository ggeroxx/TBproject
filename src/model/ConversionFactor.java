package model;

public class ConversionFactor {

    private Category leaf_1;
    private Category leaf_2;
    private Double value;

    public ConversionFactor ( Category leaf_1, Category leaf_2, Double value )
    {
        this.leaf_1 = leaf_1;
        this.leaf_2 = leaf_2;
        this.value = value;
    }

    public Category getLeaf_1 () 
    {
        return this.leaf_1;
    }

    public Category getLeaf_2 () 
    {
        return this.leaf_2;
    }

    public Double getValue ()
    {
        return this.value;
    }

    public void setValue ( Double value ) 
    {
        this.value = value;
    }

    @Override
    public boolean equals ( Object obj )
    {
        ConversionFactor cf = ( ConversionFactor ) obj;
        
        return this.leaf_1.getHierarchyID() == cf.getLeaf_1().getHierarchyID() 
                && this.leaf_2.getHierarchyID() == cf.getLeaf_2().getHierarchyID() 
                && this.leaf_1.getName().equals( cf.leaf_1.getName() ) 
                && this.leaf_2.getName().equals( cf.leaf_2.getName() );
    }

}