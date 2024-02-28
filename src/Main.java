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
        String scelta;

        Conn.openConnection();

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
                                "Inserisci la scelta (1/2) --> "
                            );
            scelta = scanner.nextLine();

            switch(scelta) 
            {
                case "1":
                        System.out.print("\nInserisci username: ");
                        String username = scanner.nextLine();
                        
                        char[] passwordChars = console.readPassword( "Inserisci password: " );
                        String password = new String( passwordChars );

                        Configurator conf = new Configurator( username, password );

                        if ( !conf.login() )
                        {
                            System.out.println("\n --- ATTENZIONE, username e/o password errati! Riprova! ---");
                            break;
                        }

                        if ( conf.firstAccess() )
                        {
                            String newUsername, newPassword;
                            boolean next = false, otherNext = false;
                            do
                            {
                                System.out.println();
                                System.out.print("Inserisci un nuovo username (min 3 max 20 caratteri): ");
                                newUsername = scanner.nextLine();
                                next = conf.attendedUsername( newUsername );
                                if ( next ) System.out.println("\n --- username NON disponibile ! --- \n");
                                otherNext = conf.checkPatternUsername( newUsername );
                                if ( !otherNext ) System.out.println( "\n --- ATTENZIONE, parametri non rispettati! Minimo 3 e massimo 20 caratteri --- \n" );
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
                    break;
                case "2":
                        System.out.println("\nArrivederci...\n\n");
                    break;
                default:
                        System.out.println("\n --- Opzione non valida ! ---");
                    break;
            }
        } while ( !scelta.equals("2") );
        
        Conn.closeConnection();
        scanner.close();
    }
}