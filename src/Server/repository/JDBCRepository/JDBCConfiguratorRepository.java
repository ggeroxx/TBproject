package Server.repository.JDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.mindrot.jbcrypt.BCrypt;

import Server.model.Configurator;
import Server.model.util.Conn;
import Server.model.util.Queries;
import Server.repository.ConfiguratorRepository;

public class JDBCConfiguratorRepository implements ConfiguratorRepository {

    @Override
    public Configurator getConfiguratorByUsername ( String username ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CONFIGURATOR_BY_USERNAME_QUERY, new ArrayList<>( Arrays.asList( username ) ) );
        return rs.next() ? new Configurator( rs.getInt( 1 ) , username, rs.getString( 3 ), rs.getBoolean( 4 ) ) : null;
    }

    @Override
    public Configurator getConfiguratorByID ( int ID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CONFIGURATOR_BY_ID_QUERY, new ArrayList<>( Arrays.asList( ID ) ) );
        return rs.next() ? new Configurator( ID , rs.getString( 2 ), rs.getString( 3 ), rs.getBoolean( 4 ) ) : null;
    }

    @Override
    public void changeCredentials( String oldUsername, String approvedUsername, String hashedPassword ) throws SQLException 
    {
        Conn.queryUpdate( Queries.CHANGE_CREDENTIALS_QUERY, new ArrayList<>( Arrays.asList( approvedUsername, hashedPassword, oldUsername ) ) );
    }

    @Override
    public Configurator getOneConfiguratorForTest () throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_ONE_CONFIGURATOR, new ArrayList<>( ));
        return rs.next() ? new Configurator( rs.getInt(1) , rs.getString( 2 ), rs.getString( 3 ), rs.getBoolean( 4 ) ) : null;
    }

    @Override
    public Configurator getNewConfiguratorForTest() throws SQLException
    {
        String passwordTest = BCrypt.hashpw("PasswordTest", BCrypt.gensalt());
        Conn.queryUpdate( Queries.INSERT_NEW_CONFIGURATOR, new ArrayList<>( Arrays.asList( "NameTest", passwordTest, 0 ) ) );
        return getConfiguratorByUsername("NameTest");
    }

    @Override
    public void deleteNewConfiguratorForTest ( String userName ) throws SQLException
    {
        Conn.queryUpdate( Queries.DELETE_NEW_CONFIGURATOR, new ArrayList<>( Arrays.asList( userName ) ) );
    }
}