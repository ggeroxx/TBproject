package controller;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

import model.*;
import util.*;

public class ConfiguratorGRASPController {

    private Configurator configurator;
    private ConfiguratorJDBC configuratorJDBC;
    private DistrictController districtController;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;

    public ConfiguratorGRASPController( Configurator configurator, ConfiguratorJDBC configuratorJDBC, DistrictController districtController, CategoryController categoryController, ConversionFactorsController conversionFactorsController ) 
    {
        this.configurator = configurator;
        this.configuratorJDBC = configuratorJDBC;
        this.districtController = districtController;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
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
        configuratorJDBC.changeCredentials( configurator.getUsername(), approvedUsername, BCrypt.hashpw( newPassword, BCrypt.gensalt() ) );
        configurator.setUsername( approvedUsername );
        configurator.setPassword( newPassword );
        configurator.setFirstAccess(false);
    }

    public void createDistrict( String districtName ) throws SQLException 
    {
        districtController.setDistrict(districtController.getDistrictJDBC().createDistrict(districtName, configurator.getID()));
    }

    public void createCategory( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException 
    {
        categoryController.setCategory( categoryController.getCategoryJDBC().createCategory(name, field, description, isRoot, hierarchyID, configurator.getID()) );
    }
}
