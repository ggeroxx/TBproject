package projectClass;
import util.*;
import java.sql.*;
import java.util.ArrayList;

public class District {
    
    private int ID;
    private String name;
    private int IDConfigurator;
    private DistrictDAO districtDAO;

    public District ( int ID, String name, int IDConfigurator ) throws SQLException
    {
        this.ID = ID;
        this.name = name;
        this.IDConfigurator = IDConfigurator;
        this.districtDAO = new DistrictDAOImpl();
    }

    public String getName() 
    {
        return name;
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return districtDAO.isPresentMunicipalityInDistrict( this.ID, municipalityToCheck.getID() );
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        districtDAO.addMunicipality( this.ID, municipalityToAdd.getID() );
    }

    public String printAllMunicipalities () throws SQLException
    {
        String query = "SELECT municipalities.name " +
                       "FROM districttomunicipalities " +
                       "JOIN municipalities ON districttomunicipalities.idmunicipality = municipalities.id " + 
                       "WHERE districttomunicipalities.iddistrict = ?" +
                       "UNION " +
                       "SELECT municipalities.name " +
                       "FROM tmp_districttomunicipalities " +
                       "JOIN municipalities ON tmp_districttomunicipalities.idmunicipality = municipalities.id " + 
                       "WHERE tmp_districttomunicipalities.iddistrict = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID ) );
        parameters.add( Integer.toString( this.ID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        Municipality tmp;
        StringBuffer toReturn = new StringBuffer();
        while ( rs.next() )
        {
            //tmp = new Municipality( rs.getString( 1 ) );
            tmp = new MunicipalityDAOImpl().getMunicipalityByName( rs.getString( 1 ) );
            toReturn.append( tmp.toString() );
        }

        return toReturn.toString();
    }

    @Override
    public String toString() 
    {
        StringBuffer toReturn = new StringBuffer();

        toReturn.append( " " + this.ID + ". " + this.name);

        return toReturn.toString();
    }

}