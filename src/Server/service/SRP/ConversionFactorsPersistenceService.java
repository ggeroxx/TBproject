package Server.service.SRP;

import java.sql.SQLException;

import Server.model.Category;
import Server.model.ConversionFactors;
import Server.repository.ConversionFactorsRepository;

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

    public Double getConversionFactorValue ( Category requested, Category offered ) throws SQLException
    {
        return conversionFactorsRepository.getConversionFactor( requested, offered ).getValue();
    }

}
