package Server.test;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.function.Predicate;
import Server.model.util.Constants;
import Server.service.Session;

public class Controller {
    
    private View view;

    public Controller ( View view )
    {
        this.view = view;
    }

    public <T> T readString ( String msg, String error, Predicate<T> condition )
    {
        T string;

        do
        {
            string = (T) view.enterString( msg );
            if ( condition.test( (T) string ) ) view.print( error );
        } while ( condition.test( (T) string ) );

        return string;
    }

    public <T> Integer readIntWithExit ( String msg, String error, Predicate<T> condition )
    {
        Integer ID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                ID = view.enterInt( msg );
                if ( ID == 0 ) return ID;
                if ( condition.test( (T) ID ) ) view.print( error );
            }
            catch ( InputMismatchException e )
            {
                view.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || condition.test( (T) ID ) );

        return ID;
    }

    public <T> Integer readInt ( String msg, String error, Predicate<T> condition )
    {
        Integer ID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                ID = view.enterInt( msg );
                if ( condition.test( (T) ID ) ) view.print( error );
            }
            catch ( InputMismatchException e )
            {
                view.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || condition.test( (T) ID ) );

        return ID;
    }

    public void clearConsole ( int millis )
    {
        try
        {
            Thread.sleep( millis );

            if ( System.getProperty("os.name").contains("Windows") ) new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
        catch ( Exception e )
        {
            view.println( Constants.GENERIC_EXCEPTION_MESSAGE );
            e.printStackTrace();
        }
        
    }

    public String padRight ( String str, int maxLenght )
    {
        String toReturn = "";

        for ( int i = str.length(); i < maxLenght; i++) toReturn += " ";

        return toReturn;
    }

    public void forcedClosure ( Session session )
    {
        Runtime.getRuntime().addShutdownHook( new Thread ( () -> {
            try 
            {
                if ( session != null ) session.logout();
                view.print( "\n" );
            } 
            catch ( SQLException e ) 
            {
                e.printStackTrace();
            }
        } ) );
    }

}