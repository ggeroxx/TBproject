import view.*;
import controller.GRASPController.CategoryGRASPController;
import controller.GRASPController.ConfiguratorGRASPController;
import controller.GRASPController.ConversionFactorsGRASPController;
import controller.GRASPController.DistrictGRASPController;
import controller.GRASPController.MunicipalityGRASPController;
import controller.GRASPController.ProposalGRASPController;
import controller.GRASPController.SessionGRASPController;
import controller.GRASPController.SubjectGRASPController;
import controller.GRASPController.UserGRASPController;
import controller.MVCController.*;
import model.*;
import service.CategoryService;
import service.ConfiguratorService;
import service.ConversionFactorsService;
import service.DistrictService;
import service.MunicipalityService;
import service.ProposalService;
import service.SubjectService;
import service.UserService;

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

        AuthenticationService_PureFabrication authenticationService = new AuthenticationService_PureFabrication( configuratorRepository, userRepository, accessRepository );
        TemporaryOperationsManager_PureFabrication tempOpsManager = new TemporaryOperationsManager_PureFabrication( districtRepository, categoryRepository, districtToMunicipalitiesRepository, relationshipsBetweenCategoriesRepository );
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