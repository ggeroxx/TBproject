package model;

import java.sql.*;

public class TemporaryOperationsManager_PureFabrication {
    private DistrictRepository districtRepository;
    private CategoryRepository categoryRepository;
    private DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository;
    private RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository;

    public TemporaryOperationsManager_PureFabrication ( DistrictRepository districtRepository, CategoryRepository categoryRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository, RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository ) 
    {
        this.districtRepository = districtRepository;
        this.categoryRepository = categoryRepository;
        this.districtToMunicipalitiesRepository = districtToMunicipalitiesRepository;
        this.relationshipsBetweenCategoriesRepository = relationshipsBetweenCategoriesRepository;
    }

    public void prepareTemporaryData() throws SQLException 
    {
        districtRepository.setTmpIDValueAutoIncrement( districtRepository.getMaxID() );
        categoryRepository.setTmpIDValueAutoIncrement( categoryRepository.getMaxID() );
    }

    public void clearTemporaryData() throws SQLException 
    {
        districtToMunicipalitiesRepository.deleteTmpDistrictToMunicipalities();
        districtRepository.deleteTmpDistricts();
        relationshipsBetweenCategoriesRepository.deleteTmpRelationshipsBetweenCategories();
        categoryRepository.deleteTmpCategories();
    }
}
