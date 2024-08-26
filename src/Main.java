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
import controller.MVCController.ConfiguratorController;
import controller.MVCController.ConversionFactorController;
import controller.MVCController.ConversionFactorsController;
import controller.MVCController.DistrictController;
import controller.MVCController.MainController;
import controller.MVCController.MunicipalityController;
import controller.MVCController.ProposalController;
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
import service.SubjectService;
import service.UserService;
import service.pure_fabrication.AuthenticationService;
import service.pure_fabrication.TemporaryOperationsManager;
import service.pure_fabrication.Authentication;
import service.pure_fabrication.TemporaryOperations;
import view.CategoryView;
import view.ConfiguratorView;
import view.ConversionFactorView;
import view.ConversionFactorsView;
import view.DistrictView;
import view.MainView;
import view.MunicipalityView;
import view.ProposalView;
import view.SubjectView;
import view.UserView;

public class Main {

    public static void main ( String[] args ) {

        MainView mainView = new MainView();
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

        SubjectController subjectController = new SubjectController( subjectView, subjectGRASPController );
        MunicipalityController municipalityController = new MunicipalityController(municipalityView, municipalityGRASPController);
        DistrictController districtController = new DistrictController(districtView, municipalityController, districtGRASPController);
        CategoryController categoryController = new CategoryController(categoryView, categoryGRASPController);
        ConversionFactorController conversionFactorController = new ConversionFactorController( conversionFactorView, categoryController );
        ConversionFactorsController conversionFactorsController = new ConversionFactorsController(conversionFactorsView, conversionFactorController, categoryController, conversionFactorsGRASPController);
        ProposalController proposalController = new ProposalController(proposalView, categoryController, conversionFactorsController, proposalGRASPController);
        UserController userController = new UserController(userView, subjectGRASPController, sessionGRASPController, categoryController, proposalController, userGRASPController);
        ConfiguratorController configuratorController = new ConfiguratorController(configuratorView, subjectGRASPController, sessionGRASPController, districtController, categoryController, conversionFactorsController, proposalController, configuratorGRASPController);
        MainController MainController = new MainController( mainView, session, accessRepository, districtController, subjectController, configuratorController, userController );

        MainController.start();

    }

}