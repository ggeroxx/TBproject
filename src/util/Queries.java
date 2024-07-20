package util;

public class Queries {
    
    // ---------------------------------------- MunicipalityJDBCImpl ----------------------------------------

    public static final String SELECT_QUERY = "SELECT * " + 
                                              "FROM municipalities " + 
                                              "WHERE name = ?";

    // ---------------------------------------- DistrictJDBCImpl ----------------------------------------

    public static final String GET_DISTRICT_BY_NAME_QUERY = "SELECT id, idconfigurator " +
                                                            "FROM districts " +
                                                            "WHERE name = ? " +
                                                            "UNION " +
                                                            "SELECT id, idconfigurator " +
                                                            "FROM tmp_districts " +
                                                            "WHERE name = ?";

    public static final String GET_DISTRICT_BY_ID_QUERY = "SELECT name, idconfigurator " +
                                                          "FROM districts " +
                                                          "WHERE id = ? " +
                                                          "UNION " +
                                                          "SELECT name, idconfigurator " +
                                                          "FROM tmp_districts " +
                                                          "WHERE id = ?";

    public static final String CREATE_DISTRICT_QUERY = "INSERT INTO tmp_districts (name, idconfigurator) " + 
                                                       "VALUES (?, ?)";

    public static final String GET_ALL_DISTRICTS_QUERY = "SELECT id " +
                                                         "FROM districts " +
                                                         "UNION " +
                                                         "SELECT id " +
                                                         "FROM tmp_districts";

    public static final String GET_ALL_SAVED_DISTRICTS = "SELECT id FROM districts";

    public static final String GET_ALL_NOT_SAVED_DISTRICTS = "SELECT id FROM tmp_districts";

    public static final String SAVE_TMP_DISTRICTS_QUERY = "INSERT INTO districts (id, name, idconfigurator) " +
                                                          "SELECT id, name, idconfigurator " +
                                                          "FROM tmp_districts";

    public static final String DELETE_TMP_DISTRICTS_QUERY = "DELETE FROM tmp_districts";

    public static final String GET_MAX_ID_DISTRICT_QUERY = "SELECT MAX(id) + 1 " +
                                                           "AS max_id " +
                                                           "FROM ( " +
                                                                "SELECT id FROM districts " +
                                                                "UNION ALL " +
                                                                "SELECT id FROM tmp_districts " +
                                                           ") AS merged_table";

    public static final String SET_DISTRICT_TMP_ID_VALUE_AUTO_INCREMENT_QUERY = "ALTER TABLE tmp_districts AUTO_INCREMENT = ?";
    
    // ---------------------------------------- DistrictToMunicipalitiesJDBCImpl ----------------------------------------

    public static final String ADD_MUNICIPALITY_QUERY = "INSERT INTO tmp_districttomunicipalities (IDDistrict, IDMunicipality)" +
                                                        "VALUES (?, ?)";

    public static final String IS_PRESENT_MUNICIPALITY_IN_DISTRICT_QUERY = "SELECT * FROM tmp_districttomunicipalities " + 
                                                                           "WHERE iddistrict = ? AND idmunicipality = ?";

    public static final String SELECT_ALL_MUNICIPALITY_OF_DISTRICT = "SELECT municipalities.name " +
                                                                     "FROM districttomunicipalities " +
                                                                     "JOIN municipalities ON districttomunicipalities.idmunicipality = municipalities.id " +
                                                                     "WHERE districttomunicipalities.iddistrict = ? " +
                                                                     "UNION " +
                                                                     "SELECT municipalities.name " +
                                                                     "FROM tmp_districttomunicipalities " +
                                                                     "JOIN municipalities ON tmp_districttomunicipalities.idmunicipality = municipalities.id " +
                                                                     "WHERE tmp_districttomunicipalities.iddistrict = ?";
    
    public static final String SAVE_TMP_DISTRICT_TO_MUNICIPALITIES_QUERY = "INSERT INTO districttomunicipalities (iddistrict, idmunicipality) " +
                                                                           "SELECT iddistrict, idmunicipality " +
                                                                           "FROM tmp_districttomunicipalities";

    public static final String DELETE_TMP_DISTRICT_TO_MUNICIPALITIES_QUERY = "DELETE FROM tmp_districttomunicipalities";

    // ---------------------------------------- ConfiguratorJDBCImpl ----------------------------------------

    public static final String GET_CONFIGURATOR_BY_USERNAME_QUERY = "SELECT * " +
                                                                    "FROM configurators " +
                                                                    "WHERE username = ?";

    public static final String GET_CONFIGURATOR_BY_ID_QUERY = "SELECT * " +
                                                              "FROM configurators " +
                                                              "WHERE id = ?";

    public static final String CHANGE_CREDENTIALS_QUERY = "UPDATE configurators " +
                                                          "SET username = ?, password = ?, firstAccess = 0 " +
                                                          "WHERE username = ?";

    // ---------------------------------------- CategoryJDBCImpl ----------------------------------------

    public static final String GET_CATEGORY_BY_ID_QUERY = "SELECT * FROM categories " +
                                                          "WHERE id = ? " +
                                                          "UNION " +
                                                          "SELECT * " +
                                                          "FROM tmp_categories " +
                                                          "WHERE id = ?";

    public static final String GET_ROOT_CATEGORY_BY_ID_QUERY = "SELECT * " +
                                                               "FROM categories " +
                                                               "WHERE id = ? AND root = ? " +
                                                               "UNION " +
                                                               "SELECT * " +
                                                               "FROM tmp_categories " +
                                                               "WHERE id = ? AND root = ?";

    public static final String GET_ROOT_CATEGORY_BY_NAME_QUERY = "SELECT * " +
                                                                 "FROM categories " +
                                                                 "WHERE name = ? AND root = ? " +
                                                                 "UNION " +
                                                                 "SELECT * " +
                                                                 "FROM tmp_categories " + 
                                                                 "WHERE name = ? AND root = ?";

    public static final String IS_PRESENT_INTERNAL_CATEGORY_QUERY = "SELECT name " +
                                                                    "FROM categories " +
                                                                    "WHERE name = ? AND hierarchyID = ? " +
                                                                    "UNION " +
                                                                    "SELECT name " +
                                                                    "FROM tmp_categories " +
                                                                    "WHERE name = ? AND hierarchyID = ?";

    public static final String IS_VALID_PARENT_ID_QUERY = "SELECT name " +
                                                          "FROM categories " +
                                                          "WHERE id = ? AND hierarchyID = ? AND field IS NOT NULL  AND id != LAST_INSERT_ID() " +
                                                          "UNION " +
                                                          "SELECT name " +
                                                          "FROM tmp_categories " +
                                                          "WHERE id = ? AND hierarchyID = ? AND field IS NOT NULL  AND id != LAST_INSERT_ID() ";

    public static final String GET_CATEGORY_BY_NAME_AND_HIERARCHY_ID_QUERY = "SELECT * " +
                                                                             "FROM categories " +
                                                                             "WHERE name = ? AND hierarchyID = ? " +
                                                                             "UNION " +
                                                                             "SELECT * " +
                                                                             "FROM tmp_categories " +
                                                                             "WHERE name = ? AND hierarchyID = ?";

    public static final String GET_ALL_LEAF_QUERY = "SELECT * " +
                                                    "FROM categories " +
                                                    "WHERE field IS NULL " +
                                                    "UNION " +
                                                    "SELECT * " +
                                                    "FROM tmp_categories " +
                                                    "WHERE field IS NULL";

    public static final String GET_ALL_ROOT_QUERY = "SELECT * " +
                                                    "FROM categories " +
                                                    "WHERE root = 1 " +
                                                    "UNION " +
                                                    "SELECT * " +
                                                    "FROM tmp_categories " +
                                                    "WHERE root = 1";

    public static final String GET_ALL_SAVED_LEAF_QUERY = "SELECT id " +
                                                          "FROM categories " +
                                                          "WHERE field IS NULL";

    public static final String GET_ALL_NOT_SAVED_LEAF_QUERY = "SELECT id " +
                                                              "FROM tmp_categories " +
                                                              "WHERE field IS NULL";

    public static final String GET_ALL_SAVED_ROOT = "SELECT id " +
                                                    "FROM categories " +
                                                    "WHERE root = 1";

    public static final String GET_ALL_NOT_SAVED_ROOT = "SELECT id " +
                                                        "FROM tmp_categories " +
                                                        "WHERE root = 1";

    public static final String GET_PARENT_CATEGORIES_QUERY = "SELECT * " +
                                                             "FROM categories " +
                                                             "WHERE hierarchyID = ? AND field IS NOT NULL AND id NOT IN (SELECT MAX(id) " +
                                                             "FROM categories ) " +
                                                             "UNION " +
                                                             "SELECT * " +
                                                             "FROM tmp_categories " +
                                                             "WHERE hierarchyID = ? AND field IS NOT NULL AND id NOT IN (SELECT MAX(id) " +
                                                             "FROM tmp_categories )";

    public static final String CREATE_CATEGORY_QUERY_1 = "SELECT MAX(id) " +
                                                         "AS max_id " +
                                                         "FROM ( " +
                                                            "SELECT id " +
                                                            "FROM categories " +
                                                            "UNION " +
                                                            "SELECT id " +
                                                            "FROM tmp_categories " +
                                                         ") " +
                                                         "AS combinedTables";

    public static final String CREATE_CATEGORY_QUERY_2 = "INSERT INTO tmp_categories (name, field, description, root, hierarchyid, idconfigurator) " +
                                                         "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String GET_ROOT_BY_LEAF_QUERY = "SELECT id " +
                                                        "FROM categories " +
                                                        "WHERE id = ( " +
                                                            "SELECT hierarchyid " +
                                                            "FROM categories " +
                                                            "WHERE id = ? AND name = ? " +
                                                            "UNION " +
                                                            "SELECT hierarchyid " +
                                                            "FROM tmp_categories " +
                                                            "WHERE id = ? AND name = ? " +
                                                        ") " +
                                                        "UNION " +
                                                        "SELECT id " +
                                                        "FROM tmp_categories " +
                                                        "WHERE id = ( "+
                                                            "SELECT hierarchyid " +
                                                            "FROM categories " +
                                                            "WHERE id = ? AND name = ? " +
                                                            "UNION " +
                                                            "SELECT hierarchyid " +
                                                            "FROM tmp_categories " +
                                                            "WHERE id = ? AND name = ? " +
                                                        ")";

    public static final String GET_NUMBER_OF_EQUALS_CATEGORIES_QUERY = "SELECT ( " +
                                                                            "SELECT COUNT(*) " +
                                                                            "FROM categories " +
                                                                            "WHERE name = ? " +
                                                                       ") " +
                                                                       "+ ( " +
                                                                            "SELECT COUNT(*) " +
                                                                            "FROM tmp_categories " +
                                                                            "WHERE name = ? " +
                                                                        ")";
    
    public static final String SAVE_TMP_CATEGORIES_QUERY = "INSERT INTO categories (id, name, field, description ,hierarchyid, idconfigurator, root) " +
                                                           "SELECT id, name, field, description, hierarchyid, idconfigurator, root " +
                                                           "FROM tmp_categories";

    public static final String DELETE_TMP_CATEGORIES_QUERY = "DELETE FROM tmp_categories";
    
    public static final String GET_MAX_ID_CATEGORY_QUERY = "SELECT MAX(id) + 1 " +
                                                           "AS max_id " +
                                                           "FROM ( " +
                                                                "SELECT id FROM categories " +
                                                                "UNION ALL " +
                                                                "SELECT id FROM tmp_categories " +
                                                           ") AS merged_table";

    public static final String SET_CATEGORY_TMP_ID_VALUE_AUTO_INCREMENT_QUERY = "ALTER TABLE tmp_categories AUTO_INCREMENT = ?";

    public static final String GET_CATEGORY_WITHOUT_CHILD_QUERY = "SELECT id " +
                                                                  "FROM tmp_categories " +
                                                                  "WHERE field IS NOT NULL " +
                                                                  "AND id NOT IN (" + Queries.GET_PARENTID_QUERY + ")";

    // ---------------------------------------- RelationshipsBetweenCategoriesJDBCImpl ----------------------------------------

    public static final String CREATE_RELATIONSHIP_QUERY = "INSERT INTO tmp_relationshipsBetweenCategories (parentid, childid, fieldtype) " +
                                                           "VALUES (?, ?, ?)";

    public static final String GET_FIELD_VALUES_FROM_PARENT_ID_QUERY = "SELECT fieldtype " +
                                                                       "FROM relationshipsbetweencategories " +
                                                                       "WHERE parentid = ? " +
                                                                       "UNION " +
                                                                       "SELECT fieldtype " +
                                                                       "FROM tmp_relationshipsbetweencategories " +
                                                                       "WHERE parentid = ?";

    public static final String GET_FIELD_VALUE_FROM_CHILD_ID_QUERY = "SELECT fieldtype " +
                                                                     "FROM relationshipsbetweencategories " +
                                                                     "WHERE childid = ? " +
                                                                     "UNION " +
                                                                     "SELECT fieldtype " +
                                                                     "FROM tmp_relationshipsbetweencategories " +
                                                                     "WHERE childid = ?";

    public static final String GET_CHILD_IDS_FROM_PARENT_ID_QUERY = "SELECT childid " +
                                                                    "FROM relationshipsBetweenCategories " +
                                                                    "WHERE parentid = ? " +
                                                                    "UNION " +
                                                                    "SELECT childid " +
                                                                    "FROM tmp_relationshipsBetweenCategories " +
                                                                    "WHERE parentid = ?";

    public static final String GET_PARENTID_QUERY = "SELECT parentID "+
                                                    "FROM tmp_relationshipsbetweencategories";

    public static final String SAVE_TMP_RELATIONSHIPS_BETWEEN_CATEGORIES_QUERY = "INSERT INTO relationshipsbetweencategories (parentid, childid, fieldtype) " +
                                                                                 "SELECT parentid, childid, fieldtype " +
                                                                                 "FROM tmp_relationshipsbetweencategories";

    public static final String DELETE_TMP_RELATIONSHIPS_BETWEEN_CATEGORIES_QUERY = "DELETE FROM tmp_relationshipsbetweencategories";

    public static final String GET_CHILD_CATEGORY_BY_FIELD_QUERY = "SELECT childid " +
                                                                   "FROM relationshipsbetweencategories " +
                                                                   "WHERE fieldtype = ? AND parentid = ? " +
                                                                   "UNION " +
                                                                   "SELECT childid " +
                                                                   "FROM tmp_relationshipsbetweencategories " +
                                                                   "WHERE fieldtype = ? AND parentid = ?";

    // ---------------------------------------- ConversioFactorsJDBCImpl ----------------------------------------

    public static final String GET_ALL_CONVERION_FACTORS_QUERY = "SELECT * " +
                                                                 "FROM conversionfactors";

    public static final String SAVE_CONVERSION_FACTORS_QUERY = "INSERT IGNORE INTO conversionfactors (ID_leaf_1, ID_leaf_2, value) " +
                                                               "VALUES (?, ? ,?)";

    public static final String GET_CONVERSION_FACTOR_QUERY = "SELECT * " +
                                                             "FROM conversionfactors " +
                                                             "WHERE id_leaf_1 = ? AND id_leaf_2 = ?";

    // ---------------------------------------- UserJDBCImpl ----------------------------------------

    public static final String GET_USER_BY_USERNAME_QUERY = "SELECT * " +
                                                            "FROM users " +
                                                            "WHERE username = ?";

    public static final String GET_USER_BY_ID_QUERY = "SELECT * " +
                                                      "FROM users " +
                                                      "WHERE id = ?";

    public static final String INSERT_USER_QUERY = "INSERT INTO users ( username, password, districtid, mail ) " +
                                                   "VALUES ( ?, ?, ?, ? )";

    // ---------------------------------------- AccessJDBCImpl ----------------------------------------

    public static final String GET_PERMISSION_QUERY = "SELECT configuratorid " +
                                                      "FROM access " +
                                                      "WHERE permit = 0";

    public static final String DENY_PERMISSION_QUERY = "UPDATE access " +
                                                       "SET permit = 0, configuratorid = ? " +
                                                       "WHERE id = 1";

    public static final String ALLOW_PERMISSION_QUERY = "UPDATE access " +
                                                        "SET permit = 1 " +
                                                        "WHERE id = 1";

    // ---------------------------------------- ProposalJDBCImpl ----------------------------------------

    public static final String INSERT_PROPOSAL_QUERY = "INSERT INTO proposals ( requestedcategoryid, offeredcategoryid, requestedhours, offeredhours, userid, state ) " + 
                                                       "VALUES ( ?, ?, ?, ?, ?, ? )";

    public static final String GET_ALL_OPEN_PROPOSALS_BY_USER_QUERY = "SELECT * " +
                                                                      "FROM proposals " +
                                                                      "WHERE userid = ? AND state = 'open'";

    public static final String GET_ALL_PROPOSALS_BY_USER_QUERY = "SELECT * " +
                                                                 "FROM proposals " +
                                                                 "WHERE userid = ?";
                                                                
    public static final String RETIRE_PROPOSAL_QUERY = "UPDATE proposals " +
                                                       "SET state = ? " +
                                                       "WHERE id = ?";

    public static final String GET_ALL_PROPOSALS_BY_LEAF_QUERY = "SELECT * " +
                                                                 "FROM proposals " +
                                                                 "WHERE requestedcategoryid = ? OR offeredcategoryid = ?";

    public static final String GET_ALL_COMPATIBLE_PROPOSALS_QUERY = "SELECT * " +
                                                                    "FROM proposals " +
                                                                    "WHERE requestedcategoryid = ? AND requestedhours = ? AND userID <> ? AND ( SELECT districtid FROM users WHERE id = userid ) = ( SELECT districtid FROM users WHERE id = ? ) AND state = 'open'";

    public static final String CLOSE_PROPOSAL_QUERY = "UPDATE proposals " +
                                                      "SET state = ? " +
                                                      "WHERE id = ?";
                                                                    
}