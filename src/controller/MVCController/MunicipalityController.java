package controller.MVCController;

import java.sql.SQLException;
import model.District;
import model.Municipality;
import repository.DistrictToMunicipalitiesRepository;
import service.MunicipalityService;

public class MunicipalityController  {
    
    private MunicipalityService municipalityService;

    public MunicipalityController ( MunicipalityService municipalityService)
    {
        this.municipalityService = municipalityService;    
    }

    public DistrictToMunicipalitiesRepository getDistrictToMunicipalitiesRepository ()
    {
        return this.municipalityService.getDistrictToMunicipalitiesRepository();
    }

    public String getAllMunicipalityFromDistrict ( District district ) throws SQLException
    {
        return this.municipalityService.getAllMunicipalityFromDistrict(district);
    }

    public boolean existaMunicipalityName (String name) throws SQLException
    {
        if( getMunicipalityByName( name ) == null) return false;
        return true;
    }

    public Municipality getMunicipalityByName ( String municipalityName ) throws SQLException
    {
        return municipalityService.getMunicipalityByName( municipalityName );
    }

}
