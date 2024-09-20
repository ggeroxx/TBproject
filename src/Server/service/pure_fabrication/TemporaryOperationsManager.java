package Server.service.pure_fabrication;

import java.sql.SQLException;

import Server.repository.CategoryRepository;
import Server.repository.DistrictRepository;
import Server.repository.DistrictToMunicipalitiesRepository;
import Server.repository.RelationshipsBetweenCategoriesRepository;

public class TemporaryOperationsManager implements TemporaryOperations {
    private DistrictRepository districtRepository;
    private CategoryRepository categoryRepository;
    private DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository;
    private RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository;

    public TemporaryOperationsManager ( DistrictRepository districtRepository, CategoryRepository categoryRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository, RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository ) 
    {
        this.districtRepository = districtRepository;
        this.categoryRepository = categoryRepository;
        this.districtToMunicipalitiesRepository = districtToMunicipalitiesRepository;
        this.relationshipsBetweenCategoriesRepository = relationshipsBetweenCategoriesRepository;
    }

    @Override
    public void prepareTemporaryData() throws SQLException 
    {
        districtRepository.setTmpIDValueAutoIncrement( districtRepository.getMaxID() );
        categoryRepository.setTmpIDValueAutoIncrement( categoryRepository.getMaxID() );
    }

    @Override
    public void clearTemporaryData() throws SQLException 
    {
        districtToMunicipalitiesRepository.deleteTmpDistrictToMunicipalities();
        districtRepository.deleteTmpDistricts();
        relationshipsBetweenCategoriesRepository.deleteTmpRelationshipsBetweenCategories();
        categoryRepository.deleteTmpCategories();
    }
}
