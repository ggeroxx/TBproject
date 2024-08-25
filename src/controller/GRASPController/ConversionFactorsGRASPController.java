package controller.GRASPController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import model.ConversionFactor;
import model.ConversionFactors;
import repository.ConversionFactorsRepository;
import service.ConversionFactorsService;

public class ConversionFactorsGRASPController{
    
    private ConversionFactorsService conversionFactorsService;

    public ConversionFactorsGRASPController ( ConversionFactorsService conversionFactorsService )
    {
        this.conversionFactorsService = conversionFactorsService;
    }

    public ConversionFactorsRepository getConversionFactorsRepository () 
    {
        return this.conversionFactorsService.getConversionFactorsRepository();
    }

    public ConversionFactors getConversionFactors ()
    {
        return this.conversionFactorsService.getConversionFactors();
    }

    public void resetConversionFactors ()
    {
        this.conversionFactorsService.resetConversionFactors();
    }
    
    public void saveConversionFactors () throws SQLException
    {
        this.conversionFactorsService.saveConversionFactors();
    }

    public void populate () throws SQLException
    {
        this.conversionFactorsService.populate();
    }

    public void calculate ( int index, Double value ) throws SQLException
    {
        this.conversionFactorsService.calculate(index, value);
    }

    public void calculateEq ( int index, Double value, boolean check, HashMap<Integer, ConversionFactor> copyCFs, List<String> equations )
    {
        this.conversionFactorsService.calculateEq(index, value, check, copyCFs, equations);
    }

    public double[] calculateRange ( int index )
    {
        return this.conversionFactorsService.calculateRange(index);
    }

    public boolean isComplete () throws SQLException
    {
        return this.conversionFactorsService.isComplete();
    }

}
