package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Random;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import model.*;

public class TestConfigurator extends TestScheme 
{

    @Test
    public void testCreateDistrict() throws SQLException
    {
        Configurator conf = configuratorRepository.getOneConfiguratorForTest();
        configuratorController.setConfigurator( conf );

        String districtName = "TestDistrict";
        configuratorController.createDistrict( districtName );

        District dist = districtRepository.getDistrictByName( districtName );
        assertEquals( districtName, dist.getName() );

        districtRepository.deleteTmpDistricts();
    }

    @Test
    public void testCreateCategory() throws SQLException
    {
        Configurator conf = configuratorRepository.getOneConfiguratorForTest();
        configuratorController.setConfigurator( conf );

        String name = "TestCategory";
        String field = "TestField";
        String description = "TestDescription";
        Boolean isRoot = false;
        int hierarchyID = categoryRepository.getAllRoot().get( 0 ).getID();

        configuratorController.createCategory( name, field, description, isRoot, hierarchyID );

        Category cat = categoryRepository.getCategoryByNameAndHierarchyID( name, hierarchyID );
        assertEquals( name, cat.getName() );
        assertEquals( hierarchyID, cat.getHierarchyID() );

        categoryRepository.deleteTmpCategories();
    }

    @Test
    public void testCreateHierarchy() throws SQLException
    {
        Configurator conf = configuratorRepository.getOneConfiguratorForTest();
        configuratorController.setConfigurator( conf );

        String nameRoot = "TestCategoryRoot";
        String field = "TestField";
        String description = null;
        Boolean isRoot = true;

        configuratorController.createCategory( nameRoot, field, description, isRoot, null );
        Category root = categoryRepository.getRootCategoryByName( nameRoot );

        String name = "TestCategoryChild";
        field = null;
        description = "TestDescription";
        isRoot = false;
        int hierarchyID = root.getID();

        configuratorController.createCategory( name, field, description, isRoot, hierarchyID );
        Category leaf = categoryRepository.getCategoryByNameAndHierarchyID( name, hierarchyID );

        relationshipsBetweenCategoriesRepository.createRelationship( root.getID(), leaf.getID(), "test" );
        assertEquals( leaf.getHierarchyID(), root.getID() );

        Category child = relationshipsBetweenCategoriesRepository.getChildCategoryByFieldAndParentID( "test", root );
        assertEquals( child, leaf );

        relationshipsBetweenCategoriesRepository.deleteTmpRelationshipsBetweenCategories();
        categoryRepository.deleteTmpCategories();
    }

    @Test
    public void testChangeCredentials() throws SQLException
    {
        Configurator conf = configuratorRepository.getNewConfiguratorForTest();
        configuratorController.setConfigurator( conf );

        String newUsername = "TestUsername";
        String newPassword = "TestPassword";
        
        configuratorController.changeCredentials( newUsername, newPassword );
        conf = configuratorRepository.getConfiguratorByID( conf.getID() );
        configuratorController.setConfigurator( conf );

        assertEquals( newUsername, conf.getUsername() );
        assertTrue( BCrypt.checkpw( newPassword, conf.getPassword() ) );

        configuratorRepository.deleteNewConfiguratorForTest( conf.getUsername() );
    }

    @Test
    public void testConversionFactors() throws SQLException
    {
        Configurator conf = configuratorRepository.getOneConfiguratorForTest();
        configuratorController.setConfigurator( conf );

        String nameRoot = "TestCategoryRoot";
        String field = "TestField";
        String description = null;
        Boolean isRoot = true;

        configuratorController.createCategory( nameRoot, field, description, isRoot, null );
        Category root = categoryRepository.getRootCategoryByName( nameRoot );

        String name = "TestCategoryChild";
        field = null;
        description = "TestDescription";
        isRoot = false;
        int hierarchyID = root.getID();

        configuratorController.createCategory( name, field, description, isRoot, hierarchyID );
        Category leaf = categoryRepository.getCategoryByNameAndHierarchyID( name, hierarchyID );

        relationshipsBetweenCategoriesRepository.createRelationship( root.getID(), leaf.getID(), "test" );

        Category leafFromAnotherRoot = categoryRepository.getAllLeaf().get( 0 );

        conversionFactorsController.populate();
        ConversionFactors cfs = conversionFactorsController.getConversionFactors();

        int index = cfs.getIndex( leaf, leafFromAnotherRoot );

        double[] range = conversionFactorsController.calculateRange( index );

        double min = range[0];
        double max = range[1];

        assertTrue(min >= 0.5 && max <= 2.0);
      
        Random random = new Random();
        double randomValue = min + (max - min) * random.nextDouble();

        conversionFactorsController.calculate(index, randomValue);

        assertTrue(conversionFactorsController.getConversionFactors().getList().entrySet().stream().allMatch( entry -> ( ( entry.getValue().getValue() >= 0.5 ) && ( entry.getValue().getValue() <= 2.00 ) ) ) );
        assertTrue( conversionFactorsController.isComplete() );

        relationshipsBetweenCategoriesRepository.deleteTmpRelationshipsBetweenCategories();
        categoryRepository.deleteTmpCategories();
    }

    @Test public void testAddMunicipality() throws SQLException
    {
        Configurator conf = configuratorRepository.getOneConfiguratorForTest();
        configuratorController.setConfigurator( conf );

        String districtName = "TestDistrict";
        configuratorController.createDistrict( districtName );

        District dist = districtRepository.getDistrictByName( districtName );

        Municipality mun = municipalityRepository.getMunicipalityByName( "Orzinuovi" );

        districtController.addMunicipality( mun );

        assertTrue( districtToMunicipalitiesRepository.isPresentMunicipalityInDistrict( dist.getID(), mun.getID() ) );

        districtToMunicipalitiesRepository.deleteTmpDistrictToMunicipalities();
        districtRepository.deleteTmpDistricts();
    }

}