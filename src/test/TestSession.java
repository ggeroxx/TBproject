package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.sql.SQLException;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import model.AccessRepository;
import model.AuthenticationService_PureFabrication;
import model.Configurator;
import model.Session;
import model.User;

public class TestSession extends TestScheme{
    
    @Test
    public void testLoginLogoutConfigurator() throws SQLException
    {
        AccessRepository accRep = mock( AccessRepository.class );
        AuthenticationService_PureFabrication authenticationService = new AuthenticationService_PureFabrication( configuratorRepository, userRepository, accRep );

        Session session = new Session( authenticationService, tempOpsManager );
        
        Configurator conf = configuratorRepository.getNewConfiguratorForTest();

        when( accRep.getPermission() ).thenReturn( null );
        doNothing().when( accRep ).denyPermission( conf ); 

        session.login( conf.getUsername(), "PasswordTest" );

        assertTrue( session.getStatus() );
        Character configuratorCharacter = 'c';
        assertEquals( session.getSubject(), configuratorCharacter );

        session.logout();
        assertFalse( session.getStatus() );

        configuratorRepository.deleteNewConfiguratorForTest( conf.getUsername() );
    }

    @Test
    public void testLoginLogoutUser() throws SQLException
    {
        User user = new User( 0 , "TestUsername", BCrypt.hashpw( "TestPassword", BCrypt.gensalt() ), districtRepository.getOneDistrictForTest().getID(), "test@test.test" );
        userRepository.insertUser( user );
        session.login( user.getUsername(), "TestPassword" );

        assertTrue( session.getStatus() );
        Character userCharacter = 'u';
        assertEquals( session.getSubject(), userCharacter );

        session.logout();
        assertFalse( session.getStatus() );

        userRepository.deleteNewUserForTest( user.getUsername() );
    }
}
