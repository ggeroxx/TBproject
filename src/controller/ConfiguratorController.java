package controller;

import java.sql.*;
import java.util.*;
import model.*;
import util.*;
import view.*;

public class ConfiguratorController extends SubjectController {

    private ConfiguratorView configuratorView;
    private Configurator configurator;
    private Session session;
    private ConfiguratorJDBC configuratorJDBC;
    private DistrictController districtController;
    private CategoryController categoryController;
    private ConversionFactorController conversionFactorController;
    private ProposalController proposalController;

    public ConfiguratorController ( ConfiguratorView configuratorView, Session session, ConfiguratorJDBC configuratorJDBC, DistrictController districtController, CategoryController categoryController, ConversionFactorController conversionFactorController, ProposalController proposalController )
    {
        super( configuratorView );
        this.configuratorView = configuratorView;
        this.session = session;
        this.configuratorJDBC = configuratorJDBC;
        this.districtController = districtController;
        this.categoryController = categoryController;
        this.conversionFactorController = conversionFactorController;
        this.proposalController = proposalController;
    }

    public void setConfigurator ( Configurator configurator ) 
    {
        this.configurator = configurator;
    }

    public ConfiguratorJDBC getConfiguratorJDBC () 
    {
        return this.configuratorJDBC;
    }

    public void start () throws SQLException
    {
        int choice = 0;

        do
        {
            try
            {
                super.clearConsole( Constants.TIME_SWITCH_MENU );
                choice = configuratorView.viewConfiguratorMenu();

                switch ( choice ) 
                {
                    case 1:
                            districtController.enterDistrict( this.configurator );
                        break;

                    case 2:
                            categoryController.enterHierarchy( this.configurator );
                        break;

                    case 3:
                            conversionFactorController.enterConversionFactors();
                        break;

                    case 4:
                            this.saveAll();
                        break;

                    case 5:
                            districtController.viewDistrict();
                        break;

                    case 6:
                            categoryController.viewHierarchy();
                        break;

                    case 7:
                            conversionFactorController.listAllConversionFactors();
                        break;

                    case 8:
                            conversionFactorController.listConversionFactorsOfLeaf();
                        break;

                    case 9:
                            proposalController.listProposalsOfLeaf();
                        break;

                    case 10:
                            session.logout();
                            conversionFactorController.resetConversionFactors();
                            configuratorView.println( Constants.LOG_OUT );
                            super.clearConsole( Constants.TIME_LOGOUT );
                        break;

                    default:
                            configuratorView.print( Constants.INVALID_OPTION );
                            super.clearConsole( Constants.TIME_ERROR_MESSAGE);
                        break;
                }

            }
            catch ( InputMismatchException e )
            {
                configuratorView.print( Constants.INVALID_OPTION );
            }
            catch ( Exception e )
            {
                configuratorView.print( Constants.GENERIC_EXCEPTION_MESSAGE );
                e.printStackTrace();
            }
        } while ( choice != 10 );
    }

    public void saveAll () throws SQLException
    {
        if ( !conversionFactorController.getConversionFactors().isComplete() )
        {
            configuratorView.println( Constants.IMPOSSIBLE_SAVE_CF );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }
        categoryController.saveCategories();
        districtController.saveDistricts();
        conversionFactorController.saveConversionFactors();
        configuratorView.println( Constants.SAVE_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }

}
