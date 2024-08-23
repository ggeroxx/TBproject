package service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import model.*;
import service.SRP.*;

public class ConversionFactorsService {

    private CategoryService categoryService;
    private ConversionFactorsManager conversionFactorsManager;
    private ConversionFactorsCalculator conversionFactorsCalculator;
    private ConversionFactorsPersistenceService conversionFactorsPersistenceService;

    public ConversionFactorsService(ConversionFactorsRepository conversionFactorsRepository, CategoryService categoryService) 
    {
        this.categoryService = categoryService;
        this.conversionFactorsManager = new ConversionFactorsManager();
        this.conversionFactorsCalculator = new ConversionFactorsCalculator(conversionFactorsManager.getConversionFactors());
        this.conversionFactorsPersistenceService = new ConversionFactorsPersistenceService(conversionFactorsRepository);
    }

    public ConversionFactorsRepository getConversionFactorsRepository () 
    {
        return this.conversionFactorsPersistenceService.getConversionFactorsRepository();
    }

    public ConversionFactors getConversionFactors ()
    {
        return this.conversionFactorsManager.getConversionFactors();
    }

    public void resetConversionFactors() 
    {
        conversionFactorsManager.resetConversionFactors();
    }

    public void populate() throws SQLException 
    {
        conversionFactorsManager.populate (conversionFactorsPersistenceService.getConversionFactorsRepository(), categoryService );
    }

    public void calculate( int index, Double value ) throws SQLException 
    {
        conversionFactorsCalculator.calculate( index, value );
    }

    public void calculateEq( int index, Double value, boolean check, HashMap<Integer, ConversionFactor> copyCFs, List<String> equations ) 
    {
        conversionFactorsCalculator.calculateEq(index, value, check, copyCFs, equations);
    }

    public double[] calculateRange( int index ) 
    {
        return conversionFactorsCalculator.calculateRange( index );
    }

    public void saveConversionFactors() throws SQLException 
    {
        conversionFactorsPersistenceService.saveConversionFactors( conversionFactorsManager.getConversionFactors() );
    }

    public boolean isComplete() throws SQLException 
    {
        populate();
        return conversionFactorsManager.getConversionFactors().getList().entrySet().stream().allMatch(entry -> entry.getValue().getValue() != null);
    }
}
