import controller.GRASPController.CategoryGRASPController;
import controller.GRASPController.ConfiguratorGRASPController;
import controller.GRASPController.ConversionFactorsGRASPController;
import controller.GRASPController.DistrictGRASPController;
import controller.GRASPController.MunicipalityGRASPController;
import controller.GRASPController.ProposalGRASPController;
import controller.GRASPController.SessionGRASPController;
import controller.GRASPController.SubjectGRASPController;
import controller.GRASPController.UserGRASPController;
import controller.MVCController.CategoryController;
import controller.MVCController.ChangeCredentialsConfiguratorController;
import controller.MVCController.ConfiguratorController;
import controller.MVCController.ConversionFactorController;
import controller.MVCController.ConversionFactorsController;
import controller.MVCController.DistrictController;
import controller.MVCController.LoginController;
import controller.MVCController.MunicipalityController;
import controller.MVCController.ProposalController;
import controller.MVCController.RegistrationUserController;
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
import view.CategoryView;
import view.ConfiguratorMenuView;
import view.ConfiguratorView;
import view.ConversionFactorView;
import view.ConversionFactorsOfCategoryView;
import view.ConversionFactorsView;
import view.DistrictInfoView;
import view.DistrictView;
import view.HierarchyView;
import view.InsertConversionFactorsView;
import view.InsertDistrictView;
import view.InsertNewHierarchyView;
import view.LoginView;
import view.MunicipalityView;
import view.ProposalView;
import view.ChangeCredentialsConfiguratorView;
import view.RegistrationUserView;
import view.SubjectView;
import view.UserView;

public class Main {

    public static void main ( String[] args ) {

        DistrictView districtView = new DistrictView();
        MunicipalityView municipalityView = new MunicipalityView();
        ConfiguratorView configuratorView = new ConfiguratorView();
        CategoryView categoryView = new CategoryView();
        ConversionFactorView conversionFactorView = new ConversionFactorView();
        ConversionFactorsView conversionFactorsView = new ConversionFactorsView();
        ProposalView proposalView = new ProposalView();
        UserView userView = new UserView();
        SubjectView subjectView = new SubjectView();

        AccessRepository accessRepository = new JDBCAccessRepositoryl();
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
        SubjectService subjectService = new SubjectService(configuratorService, userService);

        SessionGRASPController sessionGRASPController = new SessionGRASPController(session); 
        UserGRASPController userGRASPController = new UserGRASPController(userService);
        ConfiguratorGRASPController configuratorGRASPController =new ConfiguratorGRASPController(configuratorService);
        ProposalGRASPController proposalGRASPController = new ProposalGRASPController(proposalService);
        MunicipalityGRASPController municipalityGRASPController = new MunicipalityGRASPController(municipalityService);
        DistrictGRASPController districtGRASPController = new DistrictGRASPController(districtService);
        ConversionFactorsGRASPController conversionFactorsGRASPController = new ConversionFactorsGRASPController(conversionFactorsService);
        CategoryGRASPController categoryGRASPController = new CategoryGRASPController(categoryService);
        SubjectGRASPController subjectGRASPController = new SubjectGRASPController(subjectService);

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

        SubjectController subjectController = new SubjectController( subjectView, subjectGRASPController );
        MunicipalityController municipalityController = new MunicipalityController(municipalityView, municipalityGRASPController);
        DistrictController districtController = new DistrictController(insertDistrictView, districtInfoView, municipalityController, districtService);
        CategoryController categoryController = new CategoryController(insertNewHierarchyView, hierarchyView, categoryService);
        ConversionFactorController conversionFactorController = new ConversionFactorController( conversionFactorView, categoryController );
        ConversionFactorsController conversionFactorsController = new ConversionFactorsController(allConversionFactorsView, insertConversionFactorsView, conversionFactorsOfCategoryView, conversionFactorController, categoryController, conversionFactorsService);
        ProposalController proposalController = new ProposalController(proposalView, categoryController, conversionFactorsController, proposalGRASPController);
        ConfiguratorController configuratorController = new ConfiguratorController(configuratorMenuView, sessionService, districtController, categoryController, conversionFactorsController, proposalController, configuratorService);
        
        UserController userController = new UserController(userView, subjectGRASPController, sessionGRASPController, categoryController, proposalController, userGRASPController, userService);
        ChangeCredentialsConfiguratorController changeCredentialsConfiguratorController = new ChangeCredentialsConfiguratorController(changeCredentialsConfiguratorView, configuratorController, subjectController);
        RegistrationUserController registrationUserController = new RegistrationUserController(registrationUserView, districtController, subjectController, userController);
        LoginController loginController = new LoginController(loginView, changeCredentialsConfiguratorController, registrationUserController, sessionService, accessRepository, districtController, configuratorController, userController);


        loginController.start();

    }

}