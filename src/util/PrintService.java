package util;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import projectClass.*;

public class PrintService {

    private DistrictJDBC districtJDBC;
    private DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC;
    private CategoryJDBC categoryJDBC;
    private RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC;

    public PrintService () 
    {
        this.districtJDBC = new DistrictJDBCImpl();
        this.districtToMunicipalitiesJDBC = new DistrictToMunicipalitiesJDBCImpl();
        this.categoryJDBC = new CategoryJDBCImpl();
        this.relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();
    }

    public void print ( String toPrint )
    {
        System.out.print( toPrint );
    }

    public void println ( String toPrint )
    {
        System.out.println( toPrint );
    }

    public void printCategoriesList ( int hierarchyID ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Category toPrint : categoryJDBC.getParentCategories( hierarchyID ) ) toReturn.append( "  " + Constants.YELLOW + toPrint.getID() + ". " + Constants.RESET + toPrint.getName() + "\n" );

        this.println( toReturn.toString() );
    }

    public void printAllRoots () throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Category toPrint : categoryJDBC.getAllSavedRoot() ) toReturn.append( " " + toPrint.getID() + ". " + toPrint.getName() + "\n" );
        for ( Category toPrint : categoryJDBC.getAllNotSavedRoot() ) toReturn.append( " " + toPrint.getID() + ". " + toPrint.getName() + Constants.NOT_SAVED );

        this.println( toReturn.toString() );
    }

    public void printAllLeafCategories () throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Category toPrint : categoryJDBC.getAllSavedLeaf() )
            toReturn.append( " " + toPrint.getID() + ". " + Util.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + Util.padRight( toPrint.getName() , 50 ) + "  [ " + categoryJDBC.getRootByLeaf( toPrint ).getName() + " ]  " + "\n" );

        for ( Category toPrint : categoryJDBC.getAllNotSavedLeaf() )
            toReturn.append( " " + toPrint.getID() + ". " + Util.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + Util.padRight( toPrint.getName() , 50 ) + "  [ " + categoryJDBC.getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );

        this.println( toReturn.toString() );
    }

    public void printLeafCategoriesWithout ( Category toRemoved ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Category toPrint : categoryJDBC.getAllSavedLeaf() )
            if ( !toPrint.equals( toRemoved ) && !toPrint.getName().equals( toRemoved.getName() ) )
                toReturn.append( " " + toPrint.getID() + ". " + Util.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + Util.padRight( toPrint.getName() , 50 ) + "  [ " + categoryJDBC.getRootByLeaf( toPrint ).getName() + " ]  " + "\n" );

        for ( Category toPrint : categoryJDBC.getAllNotSavedLeaf() )
            if ( !toPrint.equals( toRemoved ) && !toPrint.getName().equals( toRemoved.getName() ) )
                toReturn.append( " " + toPrint.getID() + ". " + Util.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + Util.padRight( toPrint.getName() , 50 ) + "  [ " + categoryJDBC.getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );

        this.println( toReturn.toString() );
    }

    public void printAllDistricts () throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        for ( District toPrint : districtJDBC.getAllSavedDistricts() )
            toReturn.append( " " + toPrint.getID() + ". " + toPrint.getName() + "\n" );

        for ( District toPrint : districtJDBC.getAllNotSavedDistricts() )
            toReturn.append( " " + toPrint.getID() + ". " + toPrint.getName() + Constants.NOT_SAVED );

        this.println( toReturn.toString() );
    }

    public void printAllMunicipalitiesOfDistrict ( District district ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Municipality toPrint : districtToMunicipalitiesJDBC.selectAllMunicipalityOfDistrict( district ) ) toReturn.append( "  " + toPrint.getCAP() + " " + toPrint.getProvince() + " " + toPrint.getName() + "\n" );

        this.println( toReturn.toString() );
    }  

    public void printHierarchy ( int IDToPrint ) throws SQLException
    {
        this.println( printHierarchy( IDToPrint, new StringBuffer(), new StringBuffer() ) );
    }

    private String printHierarchy ( int IDToPrint, StringBuffer toReturn, StringBuffer spaces ) throws SQLException
    {
        Category notLeaf = categoryJDBC.getCategoryByID( IDToPrint );

        if ( notLeaf.isRoot() ) toReturn.append( notLeaf.getID() + ". " + notLeaf.getName() + "\n\n" );
        else if ( categoryJDBC.getCategoriesWithoutChild().contains( notLeaf ) ) toReturn.append( spaces.toString() + Constants.RED + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + Constants.NO_CHILD + Constants.RESET + "\n\n" );
        else toReturn.append( spaces.toString() + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + "\n\n" );
        
        for ( Integer IDLeaf : relationshipsBetweenCategoriesJDBC.getChildIDsFromParentID( IDToPrint ) ) printHierarchy( IDLeaf, toReturn, spaces.append( "\t" ) );

        if ( spaces.length() > 1 ) spaces.setLength( spaces.length() - 1 );
        else spaces.setLength( 0 );

        return toReturn.toString();
    } 

    public void printInfoCategory ( Category toPrint ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();    

        toReturn.append( Constants.NAME + ":" + Util.padRight( Constants.NAME + ":", 20 ) + Constants.BOLD + toPrint.getName() + Constants.RESET + "\n" );
        if ( !toPrint.isRoot() ) toReturn.append( Constants.DESCRIPTION + ":" + Util.padRight( Constants.DESCRIPTION + ":", 20 ) + toPrint.getDescription() + "\n" );

        if ( toPrint.getField() == null ) toReturn.append( Constants.VALUE_OF_DOMAIN + ":" + Util.padRight( Constants.VALUE_OF_DOMAIN + ":", 20 ) + relationshipsBetweenCategoriesJDBC.getFieldValueFromChildID( toPrint.getID() ) + "\n" );
        else 
        {
            toReturn.append( Constants.FIELD + ":" + Util.padRight( Constants.FIELD + ":", 20 ) + toPrint.getField() + " = { " );
            for ( String fieldValue : relationshipsBetweenCategoriesJDBC.getFieldValuesFromParentID( toPrint.getID() ) ) toReturn.append( Constants.YELLOW + fieldValue + Constants.RESET + ", " );
            toReturn.deleteCharAt( toReturn.length() - 2 );
            toReturn.append( "}\n" );
        }

        this.println( toReturn.toString() );
    }

    public void printConversionFactors ( ConversionFactors conversionFactors ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();
        String COLOR, rootLeaf1, rootLeaf2;

        for ( Entry<Integer, ConversionFactor> entry : conversionFactors.getList().entrySet() )
        {
            rootLeaf1 = ( categoryJDBC.getNumberOfEqualsCategories( entry.getValue().getLeaf_1() ) > 1 ) ? ( "  [ " + categoryJDBC.getRootByLeaf( entry.getValue().getLeaf_1() ).getName() + " ]  " ) : "";
            rootLeaf2 = ( categoryJDBC.getNumberOfEqualsCategories( entry.getValue().getLeaf_2() ) > 1 ) ? ( "  [ " + categoryJDBC.getRootByLeaf( entry.getValue().getLeaf_2() ).getName() + " ]  " ) : "";

            COLOR = entry.getValue().getValue() == null ? Constants.RED : Constants.BOLD + Constants.GREEN;

            toReturn.append( " " + entry.getKey() + ". " + Util.padRight( Integer.toString( entry.getKey() ), 5 ) + entry.getValue().getLeaf_1().getName() + rootLeaf1 + Util.padRight( entry.getValue().getLeaf_1().getName() + rootLeaf1, 70 ) + "-->\t\t" + entry.getValue().getLeaf_2().getName() + rootLeaf2 + Util.padRight( entry.getValue().getLeaf_2().getName() + rootLeaf2, 70 ) + ": " + COLOR + entry.getValue().getValue() + Constants.RESET + "\n" );
        }

        this.println( toReturn.toString() );
    } 

    public void printConversionFactorsByLeaf ( int IDLeafCategory, ConversionFactors conversionFactors )
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Entry<Integer, ConversionFactor> entry : conversionFactors.getList().entrySet() )
            if ( entry.getValue().getLeaf_1().getID() == IDLeafCategory || entry.getValue().getLeaf_2().getID() == IDLeafCategory )
                toReturn.append( "  " + entry.getValue().toString() + "\n" );
        
        this.println( toReturn.toString() );
    } 

    public void printProposal ( Proposal toPrint )
    {
        StringBuffer toReturn = new StringBuffer();

        toReturn.append( "requested:" + Util.padRight( "requested:" ,15 ) + "[ " + toPrint.getRequestedCategory().getName() + Util.padRight( toPrint.getRequestedCategory().getName(), 50 ) + ", " + toPrint.getRequestedHours() + " hours ]" );
        toReturn.append( "\n" );
        toReturn.append( "offered:" + Util.padRight( "offered:" ,15 ) + "[ " + toPrint.getOfferedCategory().getName() + Util.padRight( toPrint.getOfferedCategory().getName(), 50 ) + ", " + Constants.CYAN + toPrint.getOfferedHours() + " hours" + Constants.RESET +" ]" );

        this.print( toReturn.toString() + "\n" );
    }

    public void printProposals ( List<Proposal> toPrints )
    {
        StringBuffer toReturn = new StringBuffer();

        for( Proposal toPrint : toPrints )
        {
            String color = toPrint.getState().equals( "open" ) ? Constants.GREEN : toPrint.getState().equals( "close" ) ? Constants.RED : Constants.YELLOW;
            toReturn.append( " " + toPrint.getID() + ". " + Constants.RESET + "requested:" + Util.padRight( "requested:" ,15 ) + "[ " + toPrint.getRequestedCategory().getName() + Util.padRight( toPrint.getRequestedCategory().getName(), 50 ) + ", " + toPrint.getRequestedHours() + " hours ]\n" );
            toReturn.append( Util.padRight( "", ( " " + toPrint.getID() + ". " ).length() ) + "offered:" + Util.padRight( "offered:" ,15 ) + "[ " + toPrint.getOfferedCategory().getName() + Util.padRight( toPrint.getOfferedCategory().getName(), 50 ) + ", " + toPrint.getOfferedHours() + " hours ] : " + color + Constants.BOLD + toPrint.getState() + Constants.RESET + "\n\n" );
        }
        
        this.print( toReturn.toString() );
    }

}