package service;
import java.sql.SQLException;

import model.util.Constants;
import model.util.Controls;

public class ControlPatternService {

    public static boolean checkPattern ( String strToCheck, int minLength, int maxLength )
    {
        return Controls.checkPattern(strToCheck, minLength, maxLength);
    }

    public static boolean checkPatternUsername ( String strToCheck, int minLength, int maxLength )
    {
        return Controls.checkPatternUsername(strToCheck, minLength, maxLength);
    }

    public static boolean checkPatternPassword ( String strToCheck, int minLength, int maxLength ) 
    {
        return Controls.checkPatternPassword(strToCheck, minLength, maxLength);
    }

    public static boolean checkPatternMail ( String mailToCheck, int minLength, int maxLength )
    {
        return Controls.checkPatternMail(mailToCheck, minLength, maxLength);
    }

    public static String padRight ( String str, int maxLenght )
    {
        return Controls.padRight(str, maxLenght);
    }

    public static String messageErrorNewUsername( String newUsername, boolean isPresentUsername ) throws SQLException
    {
        boolean checkUsername = false;
        
        checkUsername = isPresentUsername;
        if ( checkUsername ) 
        {
        	return ( Constants.USERNAME_NOT_AVAILABLE );
        }
        
        checkUsername = checkPatternUsername( newUsername, 2, 21 );
        if ( checkUsername ) 
    	{
    		return ( Constants.ERROR_PATTERN_USERNAME );
    	}
   
        return null;
    } 

    public static String messageErrorNewPassword ( String newPassword ) throws SQLException
    {
        boolean checkPassword;

        checkPassword = checkPatternPassword( newPassword, 7, 26 );
        if ( !checkPassword ) 
        {
        	return( Constants.ERROR_PATTERN_PASSWORD );
        }

        return null;
    }

    public static String messaggeErrorNewEmail( String newEmail )
    {
        boolean checkEmail;
        
        checkEmail = checkPatternMail( newEmail, 4, 51 );
        if ( !checkEmail ) 
        {
        	return( Constants.ERROR_PATTERN_MAIL );
        }

        return null;
    }

}
