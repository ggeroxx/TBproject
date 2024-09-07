package controller.GRASPController;

import java.sql.SQLException;
import model.District;
import repository.DistrictToMunicipalitiesRepository;
import repository.MunicipalityRepository;
import service.MunicipalityService;

public class MunicipalityGRASPController{
    
    private MunicipalityService municipalityService;

    public MunicipalityGRASPController ( MunicipalityService municipalityService )
    {
        this.municipalityService = municipalityService;
    }

    public MunicipalityRepository getMunicipalityRepository ()
    {
        return this.municipalityService.getMunicipalityRepository();
    }

    public DistrictToMunicipalitiesRepository getDistrictToMunicipalitiesRepository ()
    {
        return this.municipalityService.getDistrictToMunicipalitiesRepository();
    }

    public String getAllMunicipalityFromDistrict ( District district ) throws SQLException
    {
        return this.municipalityService.getAllMunicipalityFromDistrict(district);
    }

}
