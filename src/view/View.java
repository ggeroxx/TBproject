package view;

import java.io.*;
import java.util.*;

public class View {
    
    public Scanner scanner = new Scanner( System.in );
    public Console console = System.console();

    public void print ( String msg )
    {
        System.out.print( msg );
    }

    public void println ( String msg )
    {
        System.out.println( msg );
    }

    public String enterString ( String msg )
    {
        scanner = new Scanner( System.in );

        print( msg );
        String string = scanner.nextLine();

        return string;
    }

    public int enterInt ( String msg )
    {
        scanner = new Scanner( System.in );

        print( msg );
        int integer = scanner.nextInt();

        return integer;
    }

    public Double enterDouble ( String msg )
    {
        scanner = new Scanner( System.in );
        scanner.useLocale( Locale.US );

        print( msg );
        Double dbl = scanner.nextDouble();

        return dbl;
    }

}
