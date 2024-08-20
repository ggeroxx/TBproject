package controller.GRASPController;

import model.*;
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

}
