package controller.GRASPController;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

import controller.MVCController.*;
import model.*;
import util.*;

public class ConfiguratorGRASPController {

    private Configurator configurator;
    private ConfiguratorRepository configuratorRepository;
    private DistrictController districtController;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;

    public ConfiguratorGRASPController( ConfiguratorRepository configuratorRepository, DistrictController districtController, CategoryController categoryController, ConversionFactorsController conversionFactorsController ) 
    {
        this.configuratorRepository = configuratorRepository;
        this.districtController = districtController;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
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
        if ( !conversionFactorsController.isComplete() ) 
        {
            throw new IllegalStateException( Constants.IMPOSSIBLE_SAVE_CF );
        }

        categoryController.saveCategories();
        districtController.saveDistricts();
        conversionFactorsController.saveConversionFactors();
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
        districtController.setDistrict(districtController.getdistrictRepository().createDistrict(districtName, configurator.getID()));
    }

    public void createCategory( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException 
    {
        categoryController.setCategory( categoryController.getCategoryRepository().createCategory(name, field, description, isRoot, hierarchyID, configurator.getID()) );
    }
}
