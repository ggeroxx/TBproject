package controller.MVCController;

import java.sql.*;
import java.util.*;

import controller.GRASPController.*;
import model.*;
import util.*;
import view.*;

public class ConfiguratorController extends SubjectController {

    private ConfiguratorView configuratorView;
    private SessionGRASPController sessionGRASPController;
    private DistrictController districtController;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;
    private ProposalController proposalController;
    private ConfiguratorGRASPController controllerGRASP;

    public ConfiguratorController ( ConfiguratorView configuratorView, SubjectGRASPController subjectGRASPController, SessionGRASPController sessionGRASPController, DistrictController districtController, CategoryController categoryController, ConversionFactorsController conversionFactorsController, ProposalController proposalController, ConfiguratorGRASPController controllerGRASP )
    {
        super( configuratorView, subjectGRASPController);
        this.configuratorView = configuratorView;
        this.sessionGRASPController = sessionGRASPController;
        this.districtController = districtController;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
        this.proposalController = proposalController;
        this.controllerGRASP = controllerGRASP;
    }

    public void setConfigurator ( Configurator configurator ) 
    {
        this.controllerGRASP.setConfigurator(configurator);
    }

    public ConfiguratorRepository getconfiguratorRepository () 
    {
        return this.controllerGRASP.getconfiguratorRepository();
    }

    public void start () throws SQLException
    {
        int choice = 0;

        super.forcedClosure( this.sessionGRASPController.getSession() );

        do
        {
            try
            {
                super.clearConsole( Constants.TIME_SWITCH_MENU );
                choice = configuratorView.viewConfiguratorMenu();

                switch ( choice ) 
                {
                    case 1:
                            districtController.enterDistrict( this );
                        break;

                    case 2:
                            categoryController.enterHierarchy( this );
                        break;

                    case 3:
                            conversionFactorsController.enterConversionFactors();
                        break;

                    case 4:

                            try
                            {
                                controllerGRASP.saveAll();
                            }
                            catch( IllegalStateException e )
                            {
                                configuratorView.println( Constants.IMPOSSIBLE_SAVE_CF );
                                super.clearConsole( Constants.TIME_ERROR_MESSAGE );
                                break;
                            }

                            configuratorView.println( Constants.SAVE_COMPLETED );
                            super.clearConsole( Constants.TIME_MESSAGE );
                        break;

                    case 5:
                            districtController.viewDistrict();
                        break;

                    case 6:
                            categoryController.viewHierarchy();
                        break;

                    case 7:
                            conversionFactorsController.listAllConversionFactors();
                        break;

                    case 8:
                            conversionFactorsController.listConversionFactorsOfLeaf();
                        break;

                    case 9:
                            proposalController.listProposalsOfLeaf();
                        break;

                    case 10:
                            this.sessionGRASPController.logout();
                            conversionFactorsController.resetConversionFactors();
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

    public void changeCredentials ( String approvedUsername, String newPassword ) throws SQLException
    {
        this.controllerGRASP.changeCredentials(approvedUsername, newPassword);
    }

    public void createDistrict ( String districtName ) throws SQLException
    {
       this.controllerGRASP.createDistrict(districtName);
    }

    public void createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException
    {
        this.controllerGRASP.createCategory(name, field, description, isRoot, hierarchyID);
    }

}
