package Server.service;

import java.sql.SQLException;

import Server.model.District;
import Server.model.Municipality;
import Server.repository.DistrictToMunicipalitiesRepository;
import Server.repository.MunicipalityRepository;

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

    public String getAllMunicipalityFromDistrict ( District district ) throws SQLException
    {
        StringBuffer municipalities = new StringBuffer();
        for ( Municipality toPrint : getDistrictToMunicipalitiesRepository().selectAllMunicipalityOfDistrict( district ) ) municipalities.append("  " + toPrint.getCAP() + " " + toPrint.getProvince() + " " + toPrint.getName()  + "\n");
        return municipalities.toString();
    }

    public Municipality getMunicipalityByName ( String municipalityName) throws SQLException
    {
        return municipalityRepository.getMunicipalityByName(municipalityName);
    }

}