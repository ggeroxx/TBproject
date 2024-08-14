package model;

import java.sql.*;

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
    private AuthenticationService_PureFabrication authenticationService = new AuthenticationService_PureFabrication( configuratorJDBC, userJDBC, accessJDBC );
    private TemporaryOperationsManager_PureFabrication tempOpsManager = new TemporaryOperationsManager_PureFabrication( districtJDBC, categoryJDBC, districtToMunicipalitiesJDBC, relationshipsBetweenCategoriesJDBC );

    public Boolean getStatus () 
    {
        return this.status;
    }

    public void setStatus (Boolean val) 
    {
        this.status = val;
    }

    public Character getSubject () 
    {
        return this.subject;
    }

    /*public void login ( String usernameToCheck, String passwordToCheck ) throws SQLException 
    {
        Configurator conf = configuratorJDBC.getConfiguratorByUsername( usernameToCheck );
        User user = userJDBC.getUserByUsername( usernameToCheck );

        subject = ( conf == null && user == null ) ? null : ( conf != null ? 'c' : 'u' );

        if ( subject == null )
        {
            this.status = false;
            return;
        }
        else if ( subject == 'c' )
        {
            if ( !BCrypt.checkpw( passwordToCheck, conf.getPassword() ) )
            {
                this.status = false;
                this.subject = null;
                return;
            }
            else
            {
                if ( accessJDBC.getPermission() != null )
                {
                    this.status = false;
                    return;
                }
                else
                {
                    this.status = true;

                    districtJDBC.setTmpIDValueAutoIncrement( districtJDBC.getMaxID());
                    categoryJDBC.setTmpIDValueAutoIncrement( categoryJDBC.getMaxID() );

                    accessJDBC.denyPermission( conf );
                }
            }
        }
        else if ( subject == 'u' )
        {
            if ( !BCrypt.checkpw( passwordToCheck, user.getPassword() ) )
            {
                this.status = false;
                this.subject = null;
                return;
            }
            else 
            {
                this.status = true;
            }
        }
    }*/

    public void login( String usernameToCheck, String passwordToCheck ) throws SQLException 
    {
        this.subject = authenticationService.authenticate (usernameToCheck, passwordToCheck, this );
        if (this.status && this.subject == 'c') 
        {
            tempOpsManager.prepareTemporaryData();
        }
    }

    /*public void logout () throws SQLException 
    {
        this.status = false;

        if ( this.subject != null && this.subject == 'c' )
        {
            districtToMunicipalitiesJDBC.deleteTmpDistrictToMunicipalities();
            districtJDBC.deleteTmpDistricts();
            relationshipsBetweenCategoriesJDBC.deleteTmpRelationshipsBetweenCategories();
            categoryJDBC.deleteTmpCategories();

            accessJDBC.allowPermission();
        }

        this.subject = null;
    }*/

    public void logout() throws SQLException {
        this.status = false;

        if (this.subject != null && this.subject == 'c') 
        {
            tempOpsManager.clearTemporaryData();
            authenticationService.getAccessJDBC().allowPermission();
        }

        this.subject = null;
    }

}
