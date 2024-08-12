package model;

import java.sql.*;

public class TemporaryOperationsManager_PureFabrication {
    private DistrictJDBC districtJDBC;
    private CategoryJDBC categoryJDBC;
    private DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC;
    private RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC;

    public TemporaryOperationsManager_PureFabrication ( DistrictJDBC districtJDBC, CategoryJDBC categoryJDBC, DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC, RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC ) 
    {
        this.districtJDBC = districtJDBC;
        this.categoryJDBC = categoryJDBC;
        this.districtToMunicipalitiesJDBC = districtToMunicipalitiesJDBC;
        this.relationshipsBetweenCategoriesJDBC = relationshipsBetweenCategoriesJDBC;
    }

    public void prepareTemporaryData() throws SQLException 
    {
        districtJDBC.setTmpIDValueAutoIncrement( districtJDBC.getMaxID() );
        categoryJDBC.setTmpIDValueAutoIncrement( categoryJDBC.getMaxID() );
    }

    public void clearTemporaryData() throws SQLException 
    {
        districtToMunicipalitiesJDBC.deleteTmpDistrictToMunicipalities();
        districtJDBC.deleteTmpDistricts();
        relationshipsBetweenCategoriesJDBC.deleteTmpRelationshipsBetweenCategories();
        categoryJDBC.deleteTmpCategories();
    }
}
