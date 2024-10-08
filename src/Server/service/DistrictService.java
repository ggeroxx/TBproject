package Server.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Server.model.District;
import Server.model.Municipality;
import Server.repository.DistrictRepository;
import Server.repository.DistrictToMunicipalitiesRepository;

public class DistrictService {
    
    private DistrictRepository districtRepository;
    private District district;
    private DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository;

    public DistrictService ( DistrictRepository districtRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository )
    {
        this.districtRepository = districtRepository;
        this.districtToMunicipalitiesRepository = districtToMunicipalitiesRepository;
    }

    public DistrictRepository getDistrictRepository ()
    {
        return this.districtRepository;
    }

    public District getDistrict()
    {
        return this.district;
    }

    public void setDistrict ( District district )
    {
        this.district = district;
    }

    public void saveDistricts () throws SQLException
    {
        districtRepository.saveTmpDistricts();

        districtToMunicipalitiesRepository.saveTmpDistrictToMunicipalities();

        districtToMunicipalitiesRepository.deleteTmpDistrictToMunicipalities();

        districtRepository.deleteTmpDistricts();
    }

    public List<String> allDistrictName () throws SQLException
    {
        List<String> districtNames = new ArrayList<>();

        for ( District district : districtRepository.getAllDistricts() )
        {
            districtNames.add( district.getName() );
        }
        return districtNames;
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return districtToMunicipalitiesRepository.isPresentMunicipalityInDistrict( this.district.getID(), municipalityToCheck.getID() );
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        districtToMunicipalitiesRepository.addMunicipality( this.district.getID(), municipalityToAdd.getID() );
    }

    public District getDistrictByName( String name ) throws SQLException
    {
        return districtRepository.getDistrictByName( name );
    }

}
