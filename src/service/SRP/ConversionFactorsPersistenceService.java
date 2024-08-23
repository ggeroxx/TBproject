package service.SRP;

import java.sql.SQLException;

import model.*;

public class ConversionFactorsPersistenceService {
    
    private ConversionFactorsRepository conversionFactorsRepository;


    public ConversionFactorsPersistenceService  ( ConversionFactorsRepository conversionFactorsRepository)
    {
        this.conversionFactorsRepository = conversionFactorsRepository;
    }

    public ConversionFactorsRepository getConversionFactorsRepository ()
    {
        return this.conversionFactorsRepository;
    }

    
    public void saveConversionFactors ( ConversionFactors conversionFactors ) throws SQLException
    {
        conversionFactorsRepository.saveAll( conversionFactors );
    }

}
