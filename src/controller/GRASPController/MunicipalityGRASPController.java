package controller.GRASPController;

import model.*;

public class MunicipalityGRASPController{
    
    private MunicipalityRepository municipalityRepository;
    private DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository;

    public MunicipalityGRASPController ( MunicipalityRepository municipalityRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository )
    {
        this.municipalityRepository = municipalityRepository;
        this.districtToMunicipalitiesRepository = districtToMunicipalitiesRepository;
    }

    public MunicipalityRepository getmunicipalityRepository ()
    {
        return this.municipalityRepository;
    }

    public DistrictToMunicipalitiesRepository getdistrictToMunicipalitiesRepository ()
    {
        return this.districtToMunicipalitiesRepository;
    }

}
