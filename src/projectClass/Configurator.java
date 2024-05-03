package projectClass;

import util.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class Configurator {
    
    private int ID;
    private String username;
    private String password;
    private boolean firstAccess;
    private ConfiguratorJDBC configuratorJDBC;
    private DistrictJDBC districtJDBC;
    private CategoryJDBC categoryJDBC;

    public Configurator ( int ID, String username, String password, boolean firstAccess )
    {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.firstAccess = firstAccess;
        this.configuratorJDBC = new ConfiguratorJDBCImpl();
        this.districtJDBC = new DistrictJDBCImpl();
        this.categoryJDBC = new CategoryJDBCImpl();
    }

    public boolean getFirstAccess()
    {
        return this.firstAccess;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void changeCredentials ( String approvedUsername, String newPassword ) throws SQLException
    {
        configuratorJDBC.changeCredentials( this.username, approvedUsername, BCrypt.hashpw( newPassword, BCrypt.gensalt() ) );

        this.username = approvedUsername;
        this.password = newPassword;
        this.firstAccess = false;
    }

    public District createDistrict ( String districtName ) throws SQLException
    {
        if ( Controls.isPresentDistrict( districtName ) ) return null;
        return districtJDBC.createDistrict( districtName , this.ID );
    }

    public Category createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException
    {
        return categoryJDBC.createCategory( name, field, description, isRoot, hierarchyID, this.ID );
    }

    public void saveAll ( ConversionFactors toSave ) throws SQLException
    {
        Save.saveAll( toSave );
    }

}
