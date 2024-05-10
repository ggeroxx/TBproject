package util;

import java.sql.*;
import projectClass.*;

public class Controls {
    
    private static ConfiguratorJDBC configuratorJDBC = new ConfiguratorJDBCImpl();
    private static MunicipalityJDBC municipalityJDBC = new MunicipalityJDBCImpl();
    private static DistrictJDBC districtJDBC = new DistrictJDBCImpl();
    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
    private static UserJDBC userJDBC = new UserJDBCImpl();
    private static RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();

    public static boolean checkPattern ( String strToCheck, int minLength, int maxLength )
    {
        if ( strToCheck.isBlank() ) return true;
        return strToCheck.length() <= minLength || strToCheck.length() >= maxLength;
    }

    public static boolean checkPatternUsername ( String strToCheck, int minLength, int maxLength )
    {
        char[] invalidChars = { ' ', '\t', '\n' };

        for ( char character : invalidChars ) if ( strToCheck.contains( Character.toString( character ) ) ) return true;

        return checkPattern( strToCheck, minLength, maxLength );
    }

    public static boolean checkPatternPassword ( String strToCheck, int minLength, int maxLength ) 
    {
        char[] invalidChars = { ' ', '\t', '\n' };

        int contDigit = 0, contChar = 0;

        if ( strToCheck.length() <= minLength || strToCheck.length() >= maxLength ) return false;

        for ( char character : invalidChars ) if ( strToCheck.contains( Character.toString( character ) ) ) return false;

        for ( char character : strToCheck.toCharArray() ) if( Character.isDigit(character) ) contDigit++;
        for ( char character : strToCheck.toCharArray() ) if( Character.isLetter(character) ) contChar++;

        return (contDigit == 0 || contChar == 0) ? false : true;
    }

    public static boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        Configurator configurator = configuratorJDBC.getConfiguratorByUsername( usernameToCheck );
        User user = userJDBC.getUserByUsername( usernameToCheck );
        return configurator != null || user != null;
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

    public static boolean checkPatternMail ( String mailToCheck, int minLength, int maxLength )
    {
        char[] invalidChars = { ' ', '!', '"', '#', '$', '%', '&', '\'', '*', '+', '/', '=', '?', '^', '`', '{', '|', '}', '~' };

        if ( mailToCheck.length() <= minLength || mailToCheck.length() >= maxLength ) return false;

        int contAt = 0;
        for ( char character : mailToCheck.toCharArray() ) if( character == '@' ) contAt++;

        if ( contAt != 1 ) return false;
        
        String name = mailToCheck.substring( 0, mailToCheck.indexOf( '@' ) );
        for ( char character : invalidChars ) if ( name.contains( Character.toString( character ) ) ) return false;

        String domain = mailToCheck.substring( mailToCheck.indexOf( '@' ), mailToCheck.length() );
        return domain.contains( Character.toString( '.' ) );
    }

    public static boolean existValueOfField ( String field, Category parent ) throws SQLException
    {
        return relationshipsBetweenCategoriesJDBC.getChildCategoryByFieldAndParentID( field, parent ) != null;
    }

}
