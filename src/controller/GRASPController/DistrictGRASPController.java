package controller.GRASPController;

import java.sql.*;
import model.*;

public class DistrictGRASPController {
    
    private DistrictRepository districtRepository;
    private District district;
    private DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository;

    public DistrictGRASPController ( DistrictRepository districtRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository )
    {
        this.districtRepository = districtRepository;
        this.districtToMunicipalitiesRepository = districtToMunicipalitiesRepository;
    }

    public DistrictRepository getdistrictRepository ()
    {
        return this.districtRepository;
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

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return districtToMunicipalitiesRepository.isPresentMunicipalityInDistrict( this.district.getID(), municipalityToCheck.getID() );
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        districtToMunicipalitiesRepository.addMunicipality( this.district.getID(), municipalityToAdd.getID() );
    }

}
