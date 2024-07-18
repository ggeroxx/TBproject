package model;

import java.util.*;
import java.util.Map.*;

public class ConversionFactors {
    
    private Integer index;
    private HashMap<Integer, ConversionFactor> list;

    public ConversionFactors () 
    {
        this.index = 0;
        this.list = new HashMap<>();
    }

    public HashMap<Integer, ConversionFactor> getList() 
    {
        return this.list;
    }

    public void put ( ConversionFactor toPut )
    {
        this.list.put( index++, toPut );
    }

    public boolean contains ( ConversionFactor toFind )
    {
        return this.list.containsValue( toFind );
    }

    public Integer getIndex ( Category leaf1, Category leaf2 )
    {
        for ( Entry<Integer, ConversionFactor> entry : this.list.entrySet() )
            if ( entry.getValue().getLeaf_1().equals( leaf1 ) && entry.getValue().getLeaf_2().equals( leaf2 ) ) return entry.getKey();

        return null;
    }

}
