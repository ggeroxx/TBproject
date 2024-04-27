package util;

import java.sql.*;
import projectClass.*;

public class Save {

    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
    private static RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();
    private static DistrictJDBC districtJDBC = new DistrictJDBCImpl();
    private static DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC = new DistrictToMunicipalitiesJDBCImpl();
    private static ConversionFactorsJDBC conversionFactorsJDBC = new ConversionFactorsJDBCImpl();
    
    public static void saveCategories () throws SQLException
    {
        categoryJDBC.saveTmpCategories();

        relationshipsBetweenCategoriesJDBC.saveTmpRelationshipsBetweenCategories();     

        relationshipsBetweenCategoriesJDBC.deleteTmpRelationshipsBetweenCategories();

        categoryJDBC.deleteTmpCategories();
    }

    public static void saveDistricts () throws SQLException
    {
        districtJDBC.saveTmpDistricts();

        districtToMunicipalitiesJDBC.saveTmpDistrictToMunicipalities();

        districtToMunicipalitiesJDBC.deleteTmpDistrictToMunicipalities();

        districtJDBC.deleteTmpDistricts();
    }

    public static void saveConversionFactors ( ConversionFactors toSave ) throws SQLException
    {
        conversionFactorsJDBC.saveAll( toSave );

        toSave = new ConversionFactors();
    }

    public static void saveAll ( ConversionFactors toSave ) throws SQLException
    {
        saveDistricts();
        saveCategories();
        saveConversionFactors( toSave );
    }

}
