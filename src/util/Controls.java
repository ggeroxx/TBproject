package util;

public class Controls {

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

}
