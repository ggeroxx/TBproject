package controller.GRASPController;

import java.sql.SQLException;
import java.util.List;
import model.District;
import model.Municipality;
import repository.DistrictRepository;
import service.DistrictService;

public class DistrictGRASPController {
    
    private DistrictService districtService;

    public DistrictGRASPController ( DistrictService districtService )
    {
        this.districtService = districtService;
    }

    public DistrictRepository getDistrictRepository ()
    {
        return this.districtService.getDistrictRepository();
    }

    public void setDistrict ( District district )
    {
        this.districtService.setDistrict(district);
    }

    public void saveDistricts () throws SQLException
    {
        this.districtService.saveDistricts();
    }

    public List<String> allDistrictName () throws SQLException
    {
        return this.districtService.allDistrictName();
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return this.districtService.isPresentMunicipalityInDistrict(municipalityToCheck);
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        this.districtService.addMunicipality(municipalityToAdd);
    }

}
