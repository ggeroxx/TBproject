package controller.MVCController;

import java.sql.*;

import controller.GRASPController.*;
import model.*;
import util.*;
import view.*;

public class MunicipalityController extends Controller {
    
    private MunicipalityView municipalityView;
    private MunicipalityGRASPController controllerGRASP;

    public MunicipalityController ( MunicipalityView municipalityView, MunicipalityGRASPController controllerGRASP)
    {
        super( municipalityView );
        this.municipalityView = municipalityView;
        this.controllerGRASP = controllerGRASP;    
    }

    public MunicipalityRepository getMunicipalityRepository ()
    {
        return this.controllerGRASP.getMunicipalityRepository();
    }

    public DistrictToMunicipalitiesRepository getDistrictToMunicipalitiesRepository ()
    {
        return this.controllerGRASP.getDistrictToMunicipalitiesRepository();
    }

    public void listAll ( District district ) throws SQLException
    {
        for ( Municipality toPrint : getDistrictToMunicipalitiesRepository().selectAllMunicipalityOfDistrict( district ) ) municipalityView.printMunicipality( toPrint );
    }

    public String enterName () throws SQLException
    {
        return super.readString( Constants.ENTER_MUNICIPALITY, Constants.NOT_EXIST_MESSAGE, ( str ) -> {
            try 
            {
                return getMunicipalityRepository().getMunicipalityByName( str ) == null;
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }

}
