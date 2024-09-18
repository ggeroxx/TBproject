package controller.MVCController;

import java.io.IOException;
import controller.ClientServer.ConversionFactorDTO;

public class ConversionFactorController {

    private CategoryController categoryController;
    private ControlPatternController controlPatternController;

    public ConversionFactorController (CategoryController categoryController, ControlPatternController controlPatternController ) 
    {
        this.categoryController = categoryController;
        this.controlPatternController = controlPatternController;
    }

    public String infoConversionFactor ( ConversionFactorDTO cf ) throws ClassNotFoundException, IOException
    {

        String rootLeaf1 = "";
        if ( categoryController.getNumberOfEqualsCategories( cf.getLeaf1ID() ) > 1 )
            rootLeaf1 = "  [ " + categoryController.getRootNameByLeaf(cf.getLeaf1ID())+ " ]  ";

        String rootLeaf2 = "";
        if ( categoryController.getNumberOfEqualsCategories( cf.getLeaf2ID() ) > 1 )
            rootLeaf2 = "  [ " + categoryController.getRootNameByLeaf(cf.getLeaf2ID())+ " ]  ";

        return ( 
                cf.getLeaf1Name() + rootLeaf1 + controlPatternController.padRight( cf.getLeaf1Name() + rootLeaf1, 70 ) + "\t\t" + 
                cf.getLeaf2Name() + rootLeaf2 + controlPatternController.padRight( cf.getLeaf2Name() + rootLeaf2, 70 ) + ":  " + 
                String.format( java.util.Locale.US, "%.4f", cf.getValue() ) 
            );
    }

}