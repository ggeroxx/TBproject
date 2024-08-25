package controller.GRASPController;

import java.sql.SQLException;
import model.Configurator;
import repository.ConfiguratorRepository;
import service.ConfiguratorService;

public class ConfiguratorGRASPController {

    private ConfiguratorService configuratorService;

    public ConfiguratorGRASPController( ConfiguratorService configuratorService ) 
    {
        this.configuratorService = configuratorService;
    }
    public void setConfigurator ( Configurator configurator ) 
    {
        this.configuratorService.setConfigurator(configurator);
    }

    public ConfiguratorRepository getconfiguratorRepository () 
    {
        return this.configuratorService.getconfiguratorRepository();
    }

    public void saveAll() throws SQLException 
    {
        this.configuratorService.saveAll();
    }  

    public void changeCredentials( String approvedUsername, String newPassword ) throws SQLException 
    {
        this.configuratorService.changeCredentials(approvedUsername, newPassword);
    }

    public void createDistrict( String districtName ) throws SQLException 
    {
        this.configuratorService.createDistrict(districtName);
    }

    public void createCategory( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException 
    {
        this.configuratorService.createCategory(name, field, description, isRoot, hierarchyID);
    }
}
