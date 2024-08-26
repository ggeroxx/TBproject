package repository.JDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import model.Configurator;
import model.util.Conn;
import model.util.Queries;
import repository.AccessRepository;
import repository.ConfiguratorRepository;

public class JDBCAccessRepositoryl implements AccessRepository {
    
    private ConfiguratorRepository configuratorRepository = new JDBCConfiguratorRepository();

    @Override
    public Configurator getPermission() throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_PERMISSION_QUERY );
        return rs.next() ? configuratorRepository.getConfiguratorByID( rs.getInt( 1 ) ) : null;
    }

    @Override
    public void denyPermission ( Configurator configurator ) throws SQLException 
    {
        Conn.queryUpdate( Queries.DENY_PERMISSION_QUERY, new ArrayList<>( Arrays.asList( configurator.getID() ) ) );
    }

    @Override
    public void allowPermission () throws SQLException 
    {
        Conn.queryUpdate( Queries.ALLOW_PERMISSION_QUERY );
    }

}
