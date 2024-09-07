package controller.MVCController;

import java.sql.SQLException;
import controller.GRASPController.MunicipalityGRASPController;
import model.District;
import model.util.Constants;
import repository.DistrictToMunicipalitiesRepository;
import repository.MunicipalityRepository;
import view.MunicipalityView;

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

    public String getAllMunicipalityFromDistrict ( District district ) throws SQLException
    {
        return this.controllerGRASP.getAllMunicipalityFromDistrict(district);
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

    public boolean existaMunicipalityName (String name) throws SQLException
    {
        if(getMunicipalityRepository().getMunicipalityByName( name ) == null) return false;
        return true;
    }

}
