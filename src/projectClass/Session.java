package projectClass;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
import util.*;

public class Session {
    
    private Boolean status;
    private Character subject; 
    private ConfiguratorJDBC configuratorJDBC = new ConfiguratorJDBCImpl();
    private UserJDBC userJDBC = new UserJDBCImpl();
    private DistrictJDBC districtJDBC = new DistrictJDBCImpl();
    private DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC = new DistrictToMunicipalitiesJDBCImpl();
    private CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
    private RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();
    private AccessJDBC accessJDBC = new AccessJDBDImpl();
    private PrintService printService = new PrintService();

    public Boolean getStatus() 
    {
        return status;
    }

    public void login ( String usernameToCheck, String passwordToCheck ) throws SQLException
    {
        Configurator conf = configuratorJDBC.getConfiguratorByUsername( usernameToCheck );
        User user = userJDBC.getUserByUsername( usernameToCheck );

        subject = ( conf == null && user == null ) ? null : ( conf != null ? 'c' : 'u' );

        if ( subject == null )
        {
            this.status = false;
            return;
        }
        else
        {
            if ( subject == 'c' )
            {
                if ( !BCrypt.checkpw( passwordToCheck, conf.getPassword() ) )
                {
                    this.status = false;
                    return;
                }
                else
                {
                    if ( accessJDBC.getPermission() != null )
                    {
                        printService.print( "accesso negato: " + accessJDBC.getPermission().getUsername() );
                        this.status = false;
                        return;
                    }
                    else
                    {
                        Integer max_id;

                        max_id = districtJDBC.getMaxID();
                        if( max_id == null ) districtJDBC.setIDValueAutoIncrement( 1 );
                        districtJDBC.setTmpIDValueAutoIncrement( max_id );

                        max_id = categoryJDBC.getMaxID();
                        if( max_id == null ) categoryJDBC.setIDValueAutoIncrement( 1 );
                        categoryJDBC.setTmpIDValueAutoIncrement( max_id );
                    }
                }
            }
            else
            {
                if ( !BCrypt.checkpw( passwordToCheck, user.getPassword() ) )
                {
                    this.status = false;
                    return;
                }
            }
        }
        this.status = true;
    }

    public void logout () throws SQLException
    {
        this.status = false;

        districtToMunicipalitiesJDBC.deleteTmpDistrictToMunicipalities();
        districtJDBC.deleteTmpDistricts();
        relationshipsBetweenCategoriesJDBC.deleteTmpRelationshipsBetweenCategories();
        categoryJDBC.deleteTmpCategories();
    }

}
