package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import model.*;

public class TestUser extends TestScheme {

    @Test
    public void testSignInUser() throws SQLException
    {
        User user = new User( 0 , "TestUsername", BCrypt.hashpw( "TestPassword", BCrypt.gensalt() ), districtRepository.getOneDistrictForTest().getID(), "test@test.test" );
        userRepository.insertUser( user );

        User userCreated = userRepository.getUserByUsername( "TestUsername" );

        assertEquals( user.getUsername(), userCreated.getUsername() );
        assertTrue( BCrypt.checkpw( "TestPassword", userCreated.getPassword() ) );

        userRepository.deleteNewUserForTest( user.getUsername() );
    }

    @Test
    public void testProposeProposal () throws SQLException
    {
        User user = new User( 0 , "TestUsername", BCrypt.hashpw("TestPassword", BCrypt.gensalt()), districtRepository.getOneDistrictForTest().getID(), "test@test.test");
        userRepository.insertUser( user );

        User userCreated = userRepository.getUserByUsername( "TestUsername" );

        userController.setUser( userCreated );

        Category leaf1 = categoryRepository.getAllLeaf().get( 1 );
        Category leaf2 = categoryRepository.getAllLeaf().get( 2 );

        Proposal prop = new Proposal( null, leaf1, leaf2, 2, 3, userCreated, "open" );

        userController.insertProposal( prop );

        assertTrue(proposalRepository.getAllOpenProposalByUser( userCreated ).size() == 1 );

        proposalRepository.deleteProposalByUser( userCreated.getID() );
        userRepository.deleteNewUserForTest( userCreated.getUsername() );
    }

    @Test
    public void testRetireProposal () throws SQLException
    {
        User user = new User( 0 , "TestUsername", BCrypt.hashpw( "TestPassword", BCrypt.gensalt() ), districtRepository.getOneDistrictForTest().getID(), "test@test.test" );
        userRepository.insertUser( user );

        User userCreated = userRepository.getUserByUsername( "TestUsername" );

        userController.setUser( userCreated );

        Category leaf1 = categoryRepository.getAllLeaf().get( 1 );
        Category leaf2 = categoryRepository.getAllLeaf().get( 2 );

        Proposal prop = new Proposal( null, leaf1, leaf2, 2, 3, userCreated, "open" );

        userController.insertProposal( prop );

        Proposal propCreated = proposalRepository.getAllOpenProposalByUser( userCreated ).get( 0 );

        assertEquals( propCreated.getState(), "open" );
        
        userController.retireProposal( propCreated );

        propCreated = proposalRepository.getAllProposalsByUser( userCreated ).get( 0 );

        assertEquals( propCreated.getState(), "retire" );

        proposalRepository.deleteProposalByUser( userCreated.getID() );
        userRepository.deleteNewUserForTest( userCreated.getUsername() );
    }


    
}
