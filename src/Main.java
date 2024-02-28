import java.util.Scanner;
//import org.mindrot.jbcrypt.BCrypt;
import projectClass.*;
import util.Conn;
import java.io.Console;

public class Main 
{
    public static void main(String[] args) throws Exception 
    {
        //System.out.println( BCrypt.hashpw( "password1", BCrypt.gensalt() ) );
        //System.out.println( BCrypt.hashpw( "password2", BCrypt.gensalt() ) );
        //System.out.println( BCrypt.hashpw( "password3", BCrypt.gensalt() ) );

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

                        System.out.print("enter username: ");
                        String username = scanner.nextLine();
                        
                        char[] passwordChars = console.readPassword( "enter password: " );
                        String password = new String( passwordChars );

                        Configurator conf = new Configurator( username, password );

                        if ( !conf.login() )
                        {   
                            System.out.println("\n --- ATTENTION, invalid username a/o password! Try again! ---");
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
                                System.out.print("Enter new username (min 3 max 20 characters): ");
                                newUsername = scanner.nextLine();
                                next = conf.attendedUsername( newUsername );
                                if ( next ) System.out.println("\n --- username NOT available ! --- \n");
                                otherNext = conf.checkPatternUsername( newUsername );
                                if ( !otherNext ) System.out.print( "\n --- ATTENTION, parameters not respected! min 3 max 20 characters --- \n" );
                            } while ( next || !otherNext );
                            do
                            {   
                                passwordChars = console.readPassword( "Enter new password (alphanumeric, min 8 max 25 characters): " );
                                newPassword = new String( passwordChars );
                                otherNext = conf.checkPatternPassword( newPassword );
                                if ( !otherNext ) System.out.println( "\n --- ATTENTION, parameters not respected! min 8 max 25 characters, at least one digit and one character required --- \n" );
                            } while ( !otherNext );
                            
                            conf.changeCredentials( newUsername, newPassword );
                        }
                        clearConsole(1000);
                        configuratorOptions( scanner, conf );
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

    public static void configuratorOptions ( Scanner scanner, Configurator conf )
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
                        System.out.print("\nenter district name: ");
                        String districName = scanner.nextLine();

                        District newDistrict = conf.createDistrict( districName );
                        if ( newDistrict == null )
                        {
                            System.out.println( "\n --- District name already present --- \n" );
                            clearConsole(2000);
                            break;
                        } 
                        newDistrict.setID();

                        String municipalityName, continueInsert = "n";
                        do
                        {
                            System.out.print("\nEnter municipality: ");
                            municipalityName = scanner.nextLine();
                            if ( !newDistrict.existMunicipality( municipalityName )) 
                            {
                                System.out.println( "\n --- NOT exist ! --- " );
                                continue;
                            }
                            if ( newDistrict.attendedMunicipalityDistrict( municipalityName ) )
                            {
                                System.out.println( "\n --- Municipality already present ---" );
                                continue;
                            }

                            newDistrict.insertMunicipality( municipalityName );
                            System.out.println( "\n --- Added succesfull ✓ --- \n" );

                            System.out.print( "end: (y/n) --> " );
                            continueInsert = scanner.nextLine();
                        } while ( !continueInsert.equals("y") );

                        System.out.println( "\n --- Operation completed ✓ --- \n" );
                        clearConsole(2000);
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