package util;

import java.sql.*;
import java.util.*;
import java.util.function.*;

public class Util {

    private static PrintService printService = new PrintService();

    public static void clearConsole ( int millis ) throws Exception
    {
        Thread.sleep( millis );
        if ( System.getProperty("os.name").contains("Windows") )
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            new ProcessBuilder("clear").inheritIO().start().waitFor();
    }

    public static <T> T insertWithCheck ( String textToPrint, String errorMessage, Predicate<T> condition, Scanner scanner ) throws SQLException, Exception
    {
        T toReturn;

        do
        {
            printService.print( textToPrint );
            toReturn = (T) scanner.nextLine();
            if ( condition.test( (T) toReturn ) ) printService.println( errorMessage );
        } while ( condition.test( (T) toReturn ) );

        return toReturn;
    }

    public static String padRight( String str, int maxLenght )
    {
        String toReturn = "";

        for ( int i = str.length(); i < maxLenght; i++) toReturn += " ";

        return toReturn;
    }

}
