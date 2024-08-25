package service;

import repository.DistrictToMunicipalitiesRepository;
import repository.MunicipalityRepository;

public class MunicipalityService{
    
    private MunicipalityRepository municipalityRepository;
    private DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository;

    public MunicipalityService ( MunicipalityRepository municipalityRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository )
    {
        this.municipalityRepository = municipalityRepository;
        this.districtToMunicipalitiesRepository = districtToMunicipalitiesRepository;
    }

    public MunicipalityRepository getMunicipalityRepository ()
    {
        return this.municipalityRepository;
    }

    public DistrictToMunicipalitiesRepository getDistrictToMunicipalitiesRepository ()
    {
        return this.districtToMunicipalitiesRepository;
    }

}