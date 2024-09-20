package Request.DTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConversionFactorsDTO implements Serializable
{
    private static final long serialVersionUID = 1L;
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

