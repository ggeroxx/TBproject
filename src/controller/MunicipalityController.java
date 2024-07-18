package controller;

import java.sql.*;
import model.*;
import util.*;
import view.*;

public class MunicipalityController extends Controller {
    
    private MunicipalityView municipalityView;
    private MunicipalityJDBC municipalityJDBC;
    private DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC;

    public MunicipalityController ( MunicipalityView municipalityView, MunicipalityJDBC municipalityJDBC, DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC )
    {
        super( municipalityView );
        this.municipalityView = municipalityView;
        this.municipalityJDBC = municipalityJDBC;
        this.districtToMunicipalitiesJDBC = districtToMunicipalitiesJDBC;
    }

    public MunicipalityJDBC getMunicipalityJDBC ()
    {
        return this.municipalityJDBC;
    }

    public DistrictToMunicipalitiesJDBC getDistrictToMunicipalitiesJDBC ()
    {
        return this.districtToMunicipalitiesJDBC;
    }

    public void listAll ( District district ) throws SQLException
    {
        for ( Municipality toPrint : districtToMunicipalitiesJDBC.selectAllMunicipalityOfDistrict( district ) ) municipalityView.printMunicipality( toPrint );
    }

    public String enterName () throws SQLException
    {
        return super.readString( Constants.ENTER_MUNICIPALITY, Constants.NOT_EXIST_MESSAGE, ( str ) -> {
            try 
            {
                return municipalityJDBC.getMunicipalityByName( str ) == null;
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }

}
