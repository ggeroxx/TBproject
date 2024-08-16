import view.*;
import controller.MVCController.*;
import model.*;

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

        Session session = new Session();
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

        SubjectController subjectController = new SubjectController( subjectView );
        MunicipalityController municipalityController = new MunicipalityController( municipalityView, municipalityRepository, districtToMunicipalitiesRepository );
        DistrictController districtController = new DistrictController( districtView, districtRepository, districtToMunicipalitiesRepository, municipalityController );
        CategoryController categoryController = new CategoryController( categoryView, categoryRepository, relationshipsBetweenCategoriesRepository );
        ConversionFactorController conversionFactorController = new ConversionFactorController( conversionFactorView, categoryController );
        ConversionFactorsController conversionFactorsController = new ConversionFactorsController( conversionFactorsView, conversionFactorsRepository, conversionFactorController, categoryController );
        ProposalController proposalController = new ProposalController( proposalView, proposalRepository, categoryController, conversionFactorsController );
        UserController userController = new UserController( userView, session, userRepository, categoryController, proposalController );
        ConfiguratorController configuratorController = new ConfiguratorController( configuratorView, session, configuratorRepository, districtController, categoryController, conversionFactorsController, proposalController );
        MainController MainController = new MainController( mainView, session, accessRepository, districtController, subjectController, configuratorController, userController );

        MainController.start();

    }

}