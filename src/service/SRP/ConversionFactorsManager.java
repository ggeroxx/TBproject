
package service.SRP;

import java.sql.SQLException;
import model.Category;
import model.ConversionFactor;
import model.ConversionFactors;
import repository.ConversionFactorsRepository;
import service.CategoryService;

public class ConversionFactorsManager{
    
    private ConversionFactors conversionFactors;


    public ConversionFactorsManager ()
    {
        this.conversionFactors = new ConversionFactors();
    }

    public ConversionFactors getConversionFactors ()
    {
        return this.conversionFactors;
    }

    public void resetConversionFactors ()
    {
        this.conversionFactors = new ConversionFactors();
    }

    public void populate (ConversionFactorsRepository conversionFactorsRepository, CategoryService categoryService) throws SQLException
    {
        for ( ConversionFactor toAdd : conversionFactorsRepository.getAll() )
            if ( !conversionFactors.contains( toAdd ) && !toAdd.getLeaf_1().getName().equals( toAdd.getLeaf_2().getName() ) )
                conversionFactors.put( toAdd );
        
        for ( Category fixed : categoryService.getCategoryRepository().getAllLeaf() ) 
        {
            for ( Category mobile : categoryService.getCategoryRepository().getAllLeaf() ) 
            {
                if ( fixed.getID() != mobile.getID() )
                {  
                    ConversionFactor toAdd = new ConversionFactor( categoryService.getCategoryRepository().getCategoryByID( fixed.getID() ), categoryService.getCategoryRepository().getCategoryByID( mobile.getID() ), null );
                    if ( !conversionFactors.contains( toAdd ) && !toAdd.getLeaf_1().getName().equals( toAdd.getLeaf_2().getName() ) ) conversionFactors.put( toAdd );
                }
            }
        }
    }

}
