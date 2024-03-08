package util;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.function.Predicate;

public class Util {

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
            System.out.print( textToPrint );
            toReturn = (T) scanner.nextLine();
            if ( condition.test( (T) toReturn ) ) System.out.println( errorMessage );
        } while ( condition.test( (T) toReturn ) );

        return toReturn;
    }

}
