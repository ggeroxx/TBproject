package controller;

import java.util.function.*;
import util.*;
import view.*;

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

}
