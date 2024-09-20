package Client;

import java.io.IOException;

import Client.MVCController.CategoryController;
import Client.MVCController.ChangeCredentialsConfiguratorController;
import Client.MVCController.ConfiguratorController;
import Client.MVCController.ControlPatternController;
import Client.MVCController.ConversionFactorController;
import Client.MVCController.ConversionFactorsController;
import Client.MVCController.DistrictController;
import Client.MVCController.LoginController;
import Client.MVCController.MunicipalityController;
import Client.MVCController.ProposalController;
import Client.MVCController.RegistrationUserController;
import Client.MVCController.SessionController;
import Client.MVCController.SubjectController;
import Client.MVCController.UserController;
import Client.view.AllConversionFactorsView;
import Client.view.ChangeCredentialsConfiguratorView;
import Client.view.ConfiguratorMenuView;
import Client.view.ConversionFactorsOfCategoryView;
import Client.view.DistrictInfoView;
import Client.view.HierarchyView;
import Client.view.InsertConversionFactorsView;
import Client.view.InsertDistrictView;
import Client.view.InsertNewHierarchyView;
import Client.view.LoginView;
import Client.view.NavigateHierarchyView;
import Client.view.ProposalOfCategoryView;
import Client.view.ProposalOfUserView;
import Client.view.ProposeProposalView;
import Client.view.RegistrationUserView;
import Client.view.RetireProposalView;
import Client.view.UserMenuView;

public class Main {

    public static void main ( String[] args ) {

        /*DistrictView districtView = new DistrictView();
        MunicipalityView municipalityView = new MunicipalityView();
        ConfiguratorView configuratorView = new ConfiguratorView();
        CategoryView categoryView = new CategoryView();
        ConversionFactorView conversionFactorView = new ConversionFactorView();
        ConversionFactorsView conversionFactorsView = new ConversionFactorsView();
        ProposalView proposalView = new ProposalView();
        UserView userView = new UserView();
        SubjectView subjectView = new SubjectView();*/

        /*AccessRepository accessRepository = new JDBCAccessRepositoryl();
        ConfiguratorRepository configuratorRepository = new JDBCConfiguratorRepository();
        UserRepository userRepository = new JDBCUserRepository();
        DistrictRepository districtRepository = new JDBCDistrictRepository();
        MunicipalityRepository municipalityRepository = new JDBCMunicipalityRepository();
        DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository = new JDBCDistrictToMunicipalitiesRepository();
        CategoryRepository categoryRepository = new JDBCCategoryRepository();
        RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository = new JDBCRelationshipsBetweenCategoriesRepository();
        ProposalRepository proposalRepository = new JDBCProposalRepository();
        ConversionFactorsRepository conversionFactorsRepository = new JDBCConversionFactorsRepository();

        Authentication authenticationService = new AuthenticationService( configuratorRepository, userRepository, accessRepository );
        TemporaryOperations tempOpsManager = new TemporaryOperationsManager( districtRepository, categoryRepository, districtToMunicipalitiesRepository, relationshipsBetweenCategoriesRepository );
        Session session = new Session(authenticationService, tempOpsManager);

        SessionService sessionService = new SessionService(session);
        ProposalService proposalService = new ProposalService(proposalRepository);
        MunicipalityService municipalityService = new MunicipalityService(municipalityRepository, districtToMunicipalitiesRepository);
        DistrictService districtService = new DistrictService(districtRepository, districtToMunicipalitiesRepository);
        CategoryService categoryService = new CategoryService(categoryRepository, relationshipsBetweenCategoriesRepository );
        ConversionFactorsService conversionFactorsService = new ConversionFactorsService(conversionFactorsRepository, categoryService);
        UserService userService = new UserService(userRepository, proposalService);
        ConfiguratorService configuratorService = new ConfiguratorService(configuratorRepository, districtService, categoryService, conversionFactorsService);
        SubjectService subjectService = new SubjectService(configuratorService, userService);*/

        LoginView loginView = new LoginView ();
        ChangeCredentialsConfiguratorView changeCredentialsConfiguratorView = new ChangeCredentialsConfiguratorView();
        RegistrationUserView registrationUserView = new RegistrationUserView();
        ConfiguratorMenuView configuratorMenuView = new ConfiguratorMenuView();
        InsertDistrictView insertDistrictView = new InsertDistrictView();
        DistrictInfoView districtInfoView = new DistrictInfoView();
        InsertConversionFactorsView insertConversionFactorsView = new InsertConversionFactorsView();
        AllConversionFactorsView allConversionFactorsView = new AllConversionFactorsView();
        ConversionFactorsOfCategoryView conversionFactorsOfCategoryView = new ConversionFactorsOfCategoryView();
        InsertNewHierarchyView insertNewHierarchyView = new InsertNewHierarchyView();
        HierarchyView hierarchyView = new HierarchyView();
        ProposalOfCategoryView proposalOfCategoryView = new ProposalOfCategoryView();
        UserMenuView userMenuView = new UserMenuView();
        ProposalOfUserView proposalOfUserView = new ProposalOfUserView();
        RetireProposalView retireProposalView = new RetireProposalView();
        ProposeProposalView proposeProposalView = new ProposeProposalView();
        NavigateHierarchyView navigateHierarchyView = new NavigateHierarchyView();

        ClientClasse client;
        try 
        {
            client = new ClientClasse("localhost", 12345);

            SessionController sessionController = new SessionController(client);
            ControlPatternController controlPatternController = new ControlPatternController(client);
            SubjectController subjectController = new SubjectController(client);
            MunicipalityController municipalityController = new MunicipalityController(client);
            DistrictController districtController = new DistrictController(insertDistrictView, districtInfoView, municipalityController, controlPatternController, sessionController, client);
            CategoryController categoryController = new CategoryController(insertNewHierarchyView, hierarchyView, navigateHierarchyView, sessionController, controlPatternController, client);
            ConversionFactorController conversionFactorController = new ConversionFactorController(categoryController, controlPatternController);
            ConversionFactorsController conversionFactorsController = new ConversionFactorsController(allConversionFactorsView, insertConversionFactorsView, conversionFactorsOfCategoryView, conversionFactorController, categoryController, controlPatternController, sessionController, client);
            ProposalController proposalController = new ProposalController(proposalOfCategoryView, proposalOfUserView, retireProposalView, proposeProposalView, categoryController, conversionFactorsController, sessionController, controlPatternController, client);
            ConfiguratorController configuratorController = new ConfiguratorController(configuratorMenuView, sessionController, districtController, categoryController, conversionFactorsController, proposalController, client);
            
            UserController userController = new UserController(userMenuView, sessionController, categoryController, proposalController, client);
            ChangeCredentialsConfiguratorController changeCredentialsConfiguratorController = new ChangeCredentialsConfiguratorController(changeCredentialsConfiguratorView, configuratorController, subjectController, sessionController, client);
            RegistrationUserController registrationUserController = new RegistrationUserController(registrationUserView, districtController, municipalityController, subjectController, userController, controlPatternController);
            LoginController loginController = new LoginController(loginView, changeCredentialsConfiguratorController, registrationUserController, districtController, configuratorController, userController, sessionController);
        
            loginController.start();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

}