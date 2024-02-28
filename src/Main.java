import java.util.Scanner;
//import org.mindrot.jbcrypt.BCrypt;
import projectClass.Configurator;
import util.Conn;
import java.io.Console;

public class Main 
{
    public static void main(String[] args) throws Exception 
    {
        /*System.out.println( BCrypt.hashpw( "pass1", BCrypt.gensalt() ) );
        System.out.println( BCrypt.hashpw( "pass2", BCrypt.gensalt() ) );
        System.out.println( BCrypt.hashpw( "pass3", BCrypt.gensalt() ) );*/

        Scanner scanner = new Scanner( System.in );
        Console console = System.console();
        String choice;

        Conn.openConnection();

        clearConsole(0);
        do 
        {
            System.out.print(   
                                "\n" +
                                "--------------\n" +
                                "TIME BANK\n" +
                                "--------------\n" +
                                " 1.  login\n" +
                                " 2.  exit\n" +
                                "--------------\n" +
                                "Enter yout choice (1/2) --> "
                            );
            choice = scanner.nextLine();

            switch ( choice ) 
            {
                case "1":
                        clearConsole(100);
                        System.out.print(
                                            "\n" +
                                            "-----------\n" +
                                            "LOGIN\n" +
                                            "-----------\n"
                                        );

                        System.out.print("inserisci username: ");
                        String username = scanner.nextLine();
                        
                        char[] passwordChars = console.readPassword( "Inserisci password: " );
                        String password = new String( passwordChars );

                        Configurator conf = new Configurator( username, password );

                        if ( !conf.login() )
                        {   
                            System.out.println("\n --- ATTENZIONE, username e/o password errati! Riprova! ---");
                            clearConsole(2500);
                            break;
                        }

                        if ( conf.firstAccess() )
                        {
                            String newUsername, newPassword;
                            boolean next = false, otherNext = false;

                            clearConsole(200);
                            System.out.print(
                                                "\n" +
                                                "--------------\n" +
                                                "REGISTRATION\n" +
                                                "--------------\n"
                                            );

                            do
                            {
                                System.out.print("Inserisci un nuovo username (min 3 max 20 caratteri): ");
                                newUsername = scanner.nextLine();
                                next = conf.attendedUsername( newUsername );
                                if ( next ) System.out.println("\n --- username NON disponibile ! --- \n");
                                otherNext = conf.checkPatternUsername( newUsername );
                                if ( !otherNext ) System.out.print( "\n --- ATTENZIONE, parametri non rispettati! Minimo 3 e massimo 20 caratteri --- \n" );
                            } while ( next || !otherNext );
                            do
                            {   
                                passwordChars = console.readPassword( "Inserisci una nuova password (alfanumerica, min 8 max 25 caratteri): " );
                                newPassword = new String( passwordChars );
                                otherNext = conf.checkPatternPassword( newPassword );
                                if ( !otherNext ) System.out.println( "\n --- ATTENZIONE, parametri non rispettati! Minimo 8 e massimo 25 caratteri, almeno una cifra e un carattere richiesti --- \n" );
                            } while ( !otherNext );
                            
                            conf.changeCredentials( newUsername, newPassword );
                        }
                        clearConsole(1000);
                        configuratorOtions( scanner );
                    break;
                case "2":
                        System.out.println("\nBye bye ...\n\n");
                    break;
                default:
                        System.out.println("\n --- Invalid option ! ---");
                        clearConsole(2000);
                    break;
            }
        } while ( !choice.equals("2") );
        
        Conn.closeConnection();
        scanner.close();
    }

    public static void clearConsole ( int millis ) 
    {
        try 
        {
            Thread.sleep(millis);
            if ( System.getProperty("os.name").contains("Windows") )
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public static void configuratorOtions ( Scanner scanner )
    {
        String choice;
        
        do
        {
            System.out.print(
                                "\n" +
                                "--------------\n" +
                                "OPTIONS\n" +
                                "--------------\n" +
                                " 1.  insert new district\n" +
                                " 2.  view district\n" + 
                                " 9.  logout\n" +
                                "--------------\n" +
                                "Enter your choice (1/9) --> "
                            );
            choice = scanner.nextLine();

            switch ( choice )
            {
                case "1":
                    break;
                case "2":
                    break;
                case "9":
                        clearConsole(1000);
                    break;
                default:
                        System.out.println("\n --- Invalid option ! ---");
                        clearConsole(2000);
                    break;
            }
        } while ( !choice.equals("9") );
    }
}