package controller.MVCController;

import java.sql.*;

import controller.GRASPController.*;
import model.*;
import util.*;
import view.*;

public class MunicipalityController extends Controller {
    
    private MunicipalityView municipalityView;
    private MunicipalityGRASPController businessController;

    public MunicipalityController ( MunicipalityView municipalityView, MunicipalityRepository municipalityRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository )
    {
        super( municipalityView );
        this.municipalityView = municipalityView;
        this.businessController = new MunicipalityGRASPController( municipalityRepository, districtToMunicipalitiesRepository);
    }

    public MunicipalityRepository getmunicipalityRepository ()
    {
        return this.businessController.getmunicipalityRepository();
    }

    public DistrictToMunicipalitiesRepository getdistrictToMunicipalitiesRepository ()
    {
        return this.businessController.getdistrictToMunicipalitiesRepository();
    }

    public void listAll ( District district ) throws SQLException
    {
        for ( Municipality toPrint : getdistrictToMunicipalitiesRepository().selectAllMunicipalityOfDistrict( district ) ) municipalityView.printMunicipality( toPrint );
    }

    public String enterName () throws SQLException
    {
        return super.readString( Constants.ENTER_MUNICIPALITY, Constants.NOT_EXIST_MESSAGE, ( str ) -> {
            try 
            {
                return getmunicipalityRepository().getMunicipalityByName( str ) == null;
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }

}
