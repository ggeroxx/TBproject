package controller.ClientServer;

import java.util.HashMap;
import java.util.Map;

public class ConversionFactorsDTO 
{
    private Map<Integer, ConversionFactorDTO> conversionFactors;

    public ConversionFactorsDTO() 
    {
        this.conversionFactors = new HashMap<>();
    }

    public void addConversionFactorDTO(int id, ConversionFactorDTO factorDTO) 
    {
        this.conversionFactors.put(id, factorDTO);
    }

    public Map<Integer, ConversionFactorDTO> getConversionFactors() 
    {
        return conversionFactors;
    }
}

