
import java.io.IOException;

import controller.ClientServer.Client;
import controller.MVCController.CategoryController;
import controller.MVCController.ChangeCredentialsConfiguratorController;
import controller.MVCController.ConfiguratorController;
import controller.MVCController.ControlPatternController;
import controller.MVCController.ConversionFactorController;
import controller.MVCController.ConversionFactorsController;
import controller.MVCController.DistrictController;
import controller.MVCController.LoginController;
import controller.MVCController.MunicipalityController;
import controller.MVCController.ProposalController;
import controller.MVCController.RegistrationUserController;
import controller.MVCController.SessionController;
import controller.MVCController.SubjectController;
import controller.MVCController.UserController;
import repository.AccessRepository;
import repository.CategoryRepository;
import repository.ConfiguratorRepository;
import repository.ConversionFactorsRepository;
import repository.DistrictRepository;
import repository.DistrictToMunicipalitiesRepository;
import repository.MunicipalityRepository;
import repository.ProposalRepository;
import repository.RelationshipsBetweenCategoriesRepository;
import repository.UserRepository;
import repository.JDBCRepository.JDBCAccessRepositoryl;
import repository.JDBCRepository.JDBCCategoryRepository;
import repository.JDBCRepository.JDBCConfiguratorRepository;
import repository.JDBCRepository.JDBCConversionFactorsRepository;
import repository.JDBCRepository.JDBCDistrictRepository;
import repository.JDBCRepository.JDBCDistrictToMunicipalitiesRepository;
import repository.JDBCRepository.JDBCMunicipalityRepository;
import repository.JDBCRepository.JDBCProposalRepository;
import repository.JDBCRepository.JDBCRelationshipsBetweenCategoriesRepository;
import repository.JDBCRepository.JDBCUserRepository;
import service.CategoryService;
import service.ConfiguratorService;
import service.ConversionFactorsService;
import service.DistrictService;
import service.MunicipalityService;
import service.ProposalService;
import service.Session;
import service.SessionService;
import service.SubjectService;
import service.UserService;
import service.pure_fabrication.AuthenticationService;
import service.pure_fabrication.TemporaryOperationsManager;
import service.pure_fabrication.Authentication;
import service.pure_fabrication.TemporaryOperations;
import view.AllConversionFactorsView;
import view.ConfiguratorMenuView;
import view.ConversionFactorsOfCategoryView;
import view.DistrictInfoView;
import view.HierarchyView;
import view.InsertConversionFactorsView;
import view.InsertDistrictView;
import view.InsertNewHierarchyView;
import view.LoginView;
import view.NavigateHierarchyView;
import view.ProposalOfCategoryView;
import view.ProposalOfUserView;
import view.ProposeProposalView;
import view.ChangeCredentialsConfiguratorView;
import view.RegistrationUserView;
import view.RetireProposalView;
import view.UserMenuView;

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

        Client client;
        try 
        {
            client = new Client("localhost", 12345);

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