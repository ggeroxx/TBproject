package util;

import java.sql.*;
import projectClass.*;

public class Controls {
    
    private static ConfiguratorJDBC configuratorJDBC = new ConfiguratorJDBCImpl();
    private static MunicipalityJDBC municipalityJDBC = new MunicipalityJDBCImpl();
    private static DistrictJDBC districtJDBC = new DistrictJDBCImpl();
    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();

    public static boolean checkPattern ( String strToCheck, int minLength, int maxLength )
    {
        return strToCheck.length() <= minLength || strToCheck.length() >= maxLength;
    }

    public static boolean checkPatternPassword ( String strToCheck, int minLength, int maxLength ) 
    {
        int contDigit = 0, contChar = 0;

        if ( strToCheck.length() <= minLength || strToCheck.length() >= maxLength ) return false;

        for ( char character : strToCheck.toCharArray() )
            if( Character.isDigit(character) ) contDigit++;
        for ( char character : strToCheck.toCharArray() )
            if( Character.isLetter(character) ) contChar++;

        return (contDigit == 0 || contChar == 0) ? false : true;
    }

    public static boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        Configurator configurator = configuratorJDBC.getConfiguratorByUsername( usernameToCheck );
        return configurator != null;
    } 

    public static boolean existMunicipality ( String nameToCheck ) throws SQLException
    {
        Municipality municipality = municipalityJDBC.getMunicipalityByName( nameToCheck );
        return municipality != null;
    } 

    public static <T> boolean isPresentDistrict ( T toCheck ) throws SQLException
    {
        District district = null;

        if ( toCheck instanceof Integer ) district = districtJDBC.getDistrictByID( ( Integer ) toCheck );
        if ( toCheck instanceof String ) district = districtJDBC.getDistrictByName( ( String ) toCheck );

        return district != null;
    }

    public static <T> boolean isPresentRootCategory ( T toCheck ) throws SQLException
    {
        Category root = null;

        if ( toCheck instanceof Integer ) root = categoryJDBC.getRootCategoryByID( ( Integer ) toCheck );
        if ( toCheck instanceof String ) root = categoryJDBC.getRootCategoryByName( ( String ) toCheck );

        return root != null;
    }

    public static boolean isInt ( String toCheck )
    {
        for ( char c : toCheck.toCharArray() )
            if ( !Character.isDigit( c ) )
                return false;
        
        return true;
    }

    public static boolean isDouble ( String toCheck )
    {
        String str = toCheck;

        if ( toCheck.charAt( 0 ) == '.' ) return false;

        if ( toCheck.indexOf( '.' ) != -1 )
            str = toCheck.substring( 0, toCheck.indexOf( '.' ) ) + toCheck.substring( toCheck.indexOf( '.' ) + 1 );
            
        return isInt( str );
    }

}
