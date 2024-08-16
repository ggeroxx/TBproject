package model;

import java.sql.*;

public class Session {
    
    private Boolean status;
    private Character subject;
    private ConfiguratorRepository configuratorRepository = new JDBCConfiguratorRepository();
    private UserRepository userRepository = new JDBCUserRepository();
    private DistrictRepository districtRepository = new JDBCDistrictRepository();
    private DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository = new JDBCDistrictToMunicipalitiesRepository();
    private CategoryRepository categoryRepository = new JDBCCategoryRepository();
    private RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository = new JDBCRelationshipsBetweenCategoriesRepository();
    private AccessRepository accessRepository = new JDBCAccessRepositoryl();
    private AuthenticationService_PureFabrication authenticationService = new AuthenticationService_PureFabrication( configuratorRepository, userRepository, accessRepository );
    private TemporaryOperationsManager_PureFabrication tempOpsManager = new TemporaryOperationsManager_PureFabrication( districtRepository, categoryRepository, districtToMunicipalitiesRepository, relationshipsBetweenCategoriesRepository );

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
        Configurator conf = configuratorRepository.getConfiguratorByUsername( usernameToCheck );
        User user = userRepository.getUserByUsername( usernameToCheck );

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

                    districtRepository.setTmpIDValueAutoIncrement( districtRepository.getMaxID());
                    categoryRepository.setTmpIDValueAutoIncrement( categoryRepository.getMaxID() );

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
            districtToMunicipalitiesRepository.deleteTmpDistrictToMunicipalities();
            districtRepository.deleteTmpDistricts();
            relationshipsBetweenCategoriesRepository.deleteTmpRelationshipsBetweenCategories();
            categoryRepository.deleteTmpCategories();

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
