package Server.test;

import java.io.Console;
import java.util.Locale;
import java.util.Scanner;

public class View {
    
    private Scanner scanner = new Scanner( System.in );
    private Console console = System.console();

    public Console getConsole ()
    {
        return this.console;
    }

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

    public Integer enterInt ( String msg )
    {
        scanner = new Scanner( System.in );

        print( msg );
        Integer integer = scanner.nextInt();

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
