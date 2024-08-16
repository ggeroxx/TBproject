package controller.MVCController;

import java.sql.SQLException;

import model.*;
import util.*;
import view.*;

public class ConversionFactorController extends Controller {

    private ConversionFactorView conversionFactorView;
    private CategoryController categoryController;

    public ConversionFactorController ( ConversionFactorView conversionFactorView, CategoryController categoryController ) 
    {
        super( conversionFactorView );
        this.conversionFactorView = conversionFactorView;
        this.categoryController = categoryController;
    }

    public void print ( ConversionFactor cf ) throws SQLException
    {
        String COLOR = cf.getValue() == null ? Constants.RED : Constants.BOLD + Constants.GREEN;

        String rootLeaf1 = "";
        if ( categoryController.getCategoryRepository().getNumberOfEqualsCategories( cf.getLeaf_1() ) > 1 )
            rootLeaf1 = "  [ " + categoryController.getCategoryRepository().getRootByLeaf( cf.getLeaf_1() ).getName() + " ]  ";

        String rootLeaf2 = "";
        if ( categoryController.getCategoryRepository().getNumberOfEqualsCategories( cf.getLeaf_2() ) > 1 )
            rootLeaf2 = "  [ " + categoryController.getCategoryRepository().getRootByLeaf( cf.getLeaf_2() ).getName() + " ]  ";

        conversionFactorView.println( 
                cf.getLeaf_1().getName() + rootLeaf1 + super.padRight( cf.getLeaf_1().getName() + rootLeaf1, 70 ) + "-->\t\t" + 
                cf.getLeaf_2().getName() + rootLeaf2 + super.padRight( cf.getLeaf_2().getName() + rootLeaf2, 70 ) + ": " + 
                COLOR + String.format( java.util.Locale.US, "%.4f", cf.getValue() ) + Constants.RESET
            );
    }

}