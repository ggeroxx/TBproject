package controller.MVCController;

import java.sql.SQLException;
import model.ConversionFactor;
import service.ControlPatternService;

public class ConversionFactorController {

    private CategoryController categoryController;

    public ConversionFactorController (CategoryController categoryController ) 
    {
        this.categoryController = categoryController;
    }

    public String infoConversionFactor ( ConversionFactor cf ) throws SQLException
    {

        String rootLeaf1 = "";
        if ( categoryController.getCategoryRepository().getNumberOfEqualsCategories( cf.getLeaf_1() ) > 1 )
            rootLeaf1 = "  [ " + categoryController.getCategoryRepository().getRootByLeaf( cf.getLeaf_1() ).getName() + " ]  ";

        String rootLeaf2 = "";
        if ( categoryController.getCategoryRepository().getNumberOfEqualsCategories( cf.getLeaf_2() ) > 1 )
            rootLeaf2 = "  [ " + categoryController.getCategoryRepository().getRootByLeaf( cf.getLeaf_2() ).getName() + " ]  ";

        return ( 
                cf.getLeaf_1().getName() + rootLeaf1 + ControlPatternService.padRight( cf.getLeaf_1().getName() + rootLeaf1, 70 ) + "\t\t" + 
                cf.getLeaf_2().getName() + rootLeaf2 + ControlPatternService.padRight( cf.getLeaf_2().getName() + rootLeaf2, 70 ) + ":  " + 
                String.format( java.util.Locale.US, "%.4f", cf.getValue() ) 
            );
    }

}