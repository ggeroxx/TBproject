package service;

import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import model.Configurator;
import model.util.Constants;
import repository.ConfiguratorRepository;

public class ConfiguratorService{

    private Configurator configurator;
    private ConfiguratorRepository configuratorRepository;
    private DistrictService districtService;
    private CategoryService categoryService;
    private ConversionFactorsService conversionFactorsService;

    public ConfiguratorService ( ConfiguratorRepository configuratorRepository, DistrictService districtService, CategoryService categoryService, ConversionFactorsService conversionFactorsService) 
    {
        this.configuratorRepository = configuratorRepository;
        this.districtService = districtService;
        this.categoryService = categoryService;
        this.conversionFactorsService = conversionFactorsService;
    }
    public void setConfigurator ( Configurator configurator ) 
    {
        this.configurator = configurator;
    }

    public ConfiguratorRepository getconfiguratorRepository () 
    {
        return this.configuratorRepository;
    }

    public void saveAll() throws SQLException 
    {
        if ( !conversionFactorsService.isComplete() ) 
        {
            throw new IllegalStateException( Constants.IMPOSSIBLE_SAVE_CF );
        }

        categoryService.saveCategories();
        districtService.saveDistricts();
        conversionFactorsService.saveConversionFactors();
    }  

    public void changeCredentials( String approvedUsername, String newPassword ) throws SQLException 
    {
        configuratorRepository.changeCredentials( configurator.getUsername(), approvedUsername, BCrypt.hashpw( newPassword, BCrypt.gensalt() ) );
        configurator.setUsername( approvedUsername );
        configurator.setPassword( newPassword );
        configurator.setFirstAccess(false);
    }

    public void createDistrict( String districtName ) throws SQLException 
    {
        districtService.setDistrict(districtService.getDistrictRepository().createDistrict(districtName, configurator.getID()));
    }

    public void createCategory( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException 
    {
        categoryService.setCategory( categoryService.getCategoryRepository().createCategory(name, field, description, isRoot, hierarchyID, configurator.getID()) );
    }

    public Configurator getConfiguratorByUsername( String username ) throws SQLException
    {
        return configuratorRepository.getConfiguratorByUsername(username);
    }
}
