package projectClass;

import util.*;
import java.sql.*;

public class ConversionFactor {

    private Category leaf_1;
    private Category leaf_2;
    private Double value;

    public ConversionFactor ( Category leaf_1, Category leaf_2, Double value ) throws SQLException
    {
        this.leaf_1 = leaf_1;
        this.leaf_2 = leaf_2;
        this.value = value;
    }

    public Category getLeaf_1() 
    {
        return this.leaf_1;
    }

    public Category getLeaf_2() 
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
    public boolean equals( Object obj ) 
    {
        return this.leaf_1.equals( ((ConversionFactor)obj).leaf_1 ) && this.leaf_2.equals( ((ConversionFactor)obj).leaf_2 );
    }

    @Override
    public String toString() 
    {
        StringBuffer toReturn = new StringBuffer();

        String COLOR = this.value == null ? Constants.RED : Constants.BOLD + Constants.GREEN;

        toReturn.append( this.leaf_1.getName() + Util.padRight( this.leaf_1.getName(), 50 ) + "-->\t\t" + this.leaf_2.getName() + Util.padRight( this.leaf_2.getName(), 50 ) + ": " + COLOR + this.value + Constants.RESET );

        return toReturn.toString();
    }

}