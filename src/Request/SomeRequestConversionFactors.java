package Request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import Request.DTO.ConversionFactorDTO;

public class SomeRequestConversionFactors implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private int index;
    private Double value;
    private boolean check;
    private HashMap<Integer, ConversionFactorDTO> copyCFs;
    private List<String> equations;
    private int anotherIndex;


    public SomeRequestConversionFactors(String action, int index, Double value, boolean check, HashMap<Integer, ConversionFactorDTO> copyCFs, List<String> equations, int anotherIndex) 
    {
        this.action = action;
        this.index = index;
        this.value = value;
        this.check = check;
        this.copyCFs = copyCFs;
        this.equations = equations;
        this.anotherIndex = anotherIndex;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getIndex()
    {
        return this.index;
    }

    public Double getValue()
    {
        return this.value;
    }

    public boolean getCheck()
    {
        return this.check;
    }

    public  HashMap<Integer, ConversionFactorDTO> getCopyCfs()
    {
        return this.copyCFs;
    }

    public List<String> getEquations()
    {
        return this.equations;
    }

    public int getAnotherIndex()
    {
        return this.anotherIndex;
    }

    @Override
    public String toString() {
        return "SomeRequestConversionFactors{" +
                "action='" + this.action + '\'' +
                ", index='" + this.index + '\'' +
                ", value='" + this.value + '\'' +
                '}';
    }
    
}
