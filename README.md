## TBproject
static StringBuffer toReturn = new StringBuffer();
    static StringBuffer spaces = new StringBuffer();

    public static String printHierarchy ( int IDToPrint ) throws SQLException
    {
        String query;
        ResultSet rs;
        ArrayList<String> parameters;

        query = "SELECT name, root FROM categories WHERE id = ? " +
                "UNION " +
                "SELECT name, root FROM tmp_categories WHERE id = ?";

        parameters = new ArrayList<String>();
        parameters.add( Integer.toString( IDToPrint ) );
        parameters.add( Integer.toString( IDToPrint ) );

        rs = Conn.exQuery( query, parameters );
        rs.next();
        if ( rs.getBoolean( 2 ) ) toReturn.append( rs.getString( 1 ) + "\n" );
        else toReturn.append( spaces.toString() + "└──" + rs.getString( 1 ) + "\n" );

        query = "SELECT childid FROM relationshipsBetweenCategories WHERE parentid = ? " +
                "UNION " +
                "SELECT childid FROM tmp_relationshipsBetweenCategories WHERE parentid = ?";

        parameters = new ArrayList<String>();
        parameters.add( Integer.toString( IDToPrint ) );
        parameters.add( Integer.toString( IDToPrint ) );

        rs = Conn.exQuery( query, parameters );
        
        while ( rs.next() ) {
            spaces.append("   ");
            printHierarchy( rs.getInt( 1 ) );
        }
        spaces = new StringBuffer();

        return toReturn.toString();
    } 


        public static void openConnection () throws SQLException
    {
        conn = DriverManager.getConnection(url, user, pass);
        creationTmpTableDistict();
        creationTmpTableCategory();
    }

    public static void creationTmpTableDistict () throws SQLException
    {
        String query;
        ResultSet rs;

        query = "CREATE TABLE tmp_districts (" +
                            "ID int NOT NULL AUTO_INCREMENT," +
                            "name varchar(50) NOT NULL," +
                            "IDConfigurator int DEFAULT NULL," +
                            "PRIMARY KEY (ID)," +
                            "KEY IDConfigurator (IDConfigurator)," +
                            "CONSTRAINT fk_tmp_districts FOREIGN KEY (IDConfigurator) REFERENCES configurators (ID)" + 
                       ")";
        Conn.queryUpdate( query );

        query = "SELECT MAX(id) + 1 AS max_id FROM districts";
        rs = Conn.exQuery( query );
        rs.next();
        if( rs.getString( 1 ) == null )
        {
            query = "ALTER TABLE districts AUTO_INCREMENT = " + Integer.toString( rs.getInt(1) );
            Conn.queryUpdate( query );
        }
        query = "ALTER TABLE tmp_districts AUTO_INCREMENT = " + Integer.toString( rs.getInt(1) );
        Conn.queryUpdate( query );

        query = "CREATE TABLE tmp_districtToMunicipalities (" +
                    "ID int NOT NULL AUTO_INCREMENT," +
                    "IDDistrict int DEFAULT NULL," +
                    "IDMunicipality int DEFAULT NULL," +
                    "PRIMARY KEY (ID)," +
                    "KEY IDDistrict (IDDistrict), " +
                    "KEY IDMunicipality (IDMunicipality)," +
                    "CONSTRAINT fk1_tmp_districttomunicipalities FOREIGN KEY (IDDistrict) REFERENCES tmp_districts (ID)," +
                    "CONSTRAINT fk2_tmp_districttomunicipalities FOREIGN KEY (IDMunicipality) REFERENCES municipalities (ID)" +
                ")";
        Conn.queryUpdate( query );

        
    }

    public static void creationTmpTableCategory () throws SQLException
    {
        String query;
        ResultSet rs;
        query = "CREATE TABLE tmp_categories(" +
                    "ID int NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL," +
                    "field VARCHAR(25)," +
                    "description VARCHAR(100)," +
                    "root BOOLEAN NOT NULL," +
                    "hierarchyID int NOT NULL," +
                    "IDConfigurator int NOT NULL," +
                    "CONSTRAINT fk_tmp_categories FOREIGN KEY (IDConfigurator) REFERENCES configurators(ID)" +
                ")"; 
        Conn.queryUpdate( query );

        query = "SELECT MAX(id) + 1 AS max_id FROM categories";
        rs = Conn.exQuery( query );
        rs.next();
        if(rs.getString(1) == null)
        {
            query = "ALTER TABLE categories AUTO_INCREMENT = " + Integer.toString( rs.getInt(1) );
            Conn.queryUpdate( query );
        }
        query = "ALTER TABLE tmp_categories AUTO_INCREMENT = " + Integer.toString( rs.getInt(1) );
        Conn.queryUpdate( query );

        query = "CREATE TABLE tmp_relationshipsBetweenCategories(" +  
                    "parentID int NOT NULL," +
                    "childID int NOT NULL," +
                    "fieldType VARCHAR(25) NOT NULL," +
                    "PRIMARY KEY (parentID, childID)," +
                    "CONSTRAINT fk1_tmp_relationshipsBetweenCategories FOREIGN KEY (parentID) REFERENCES tmp_categories(ID)," +
                    "CONSTRAINT fk2_tmp_relationshipsBetweenCategories FOREIGN KEY (childID) REFERENCES tmp_categories(ID)" + 
                ")";
        Conn.queryUpdate( query );
    }


        public static void saveAll () throws SQLException
    {
        String query = "INSERT INTO categories (id, name, field, description,hierarchyid, idconfigurator,root) " +
                       "SELECT id, name, field, description, hierarchyid,idconfigurator,root " +
                       "FROM tmp_categories";
        
        Conn.queryUpdate( query );

        query = "INSERT INTO relationshipsbetweencategories (parentid, childid, fieldtype) " +
                "SELECT parentid, childid, fieldtype " +
                "FROM tmp_relationshipsbetweencategories";

        Conn.queryUpdate( query );

        query = "DELETE FROM tmp_relationshipsbetweencategories";
        

        Conn.queryUpdate(query);

        query = "DELETE FROM tmp_categories";

        Conn.queryUpdate(query);
    }