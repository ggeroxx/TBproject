import view.*;
import controller.*;
import model.*;

public class Main {

    public static void main ( String[] args ) {

        MainView mainView = new MainView();
        DistrictView districtView = new DistrictView();
        MunicipalityView municipalityView = new MunicipalityView();
        ConfiguratorView configuratorView = new ConfiguratorView();
        CategoryView categoryView = new CategoryView();
        ConversionFactorView conversionFactorView = new ConversionFactorView();
        ProposalView proposalView = new ProposalView();
        UserView userView = new UserView();
        SubjectView subjectView = new SubjectView();

        Session session = new Session();
        AccessJDBC accessJDBC = new AccessJDBDImpl();
        ConfiguratorJDBC configuratorJDBC = new ConfiguratorJDBCImpl();
        UserJDBC userJDBC = new UserJDBCImpl();
        DistrictJDBC districtJDBC = new DistrictJDBCImpl();
        MunicipalityJDBC municipalityJDBC = new MunicipalityJDBCImpl();
        DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC = new DistrictToMunicipalitiesJDBCImpl();
        CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
        RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();
        ProposalJDBC proposalJDBC = new ProposalJDBCImpl();
        ConversionFactorsJDBC conversionFactorsJDBC = new ConversionFactorsJDBCImpl();

        SubjectController subjectController = new SubjectController( subjectView );
        MunicipalityController municipalityController = new MunicipalityController( municipalityView, municipalityJDBC, districtToMunicipalitiesJDBC );
        DistrictController districtController = new DistrictController( districtView, districtJDBC, districtToMunicipalitiesJDBC, municipalityController );
        CategoryController categoryController = new CategoryController( categoryView, categoryJDBC, relationshipsBetweenCategoriesJDBC );
        ConversionFactorController conversionFactorController = new ConversionFactorController( conversionFactorView, conversionFactorsJDBC, categoryController );
        ProposalController proposalController = new ProposalController( proposalView, proposalJDBC, categoryController, conversionFactorController );
        UserController userController = new UserController( userView, session, userJDBC, categoryController, proposalController );
        ConfiguratorController configuratorController = new ConfiguratorController( configuratorView, session, configuratorJDBC, districtController, categoryController, conversionFactorController, proposalController );
        MainController MainController = new MainController( mainView, session, accessJDBC, districtController, subjectController, configuratorController, userController );

        MainController.start();

    }

}