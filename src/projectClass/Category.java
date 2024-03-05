package projectClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Conn;

public class Category {
    
    private int ID;
    private String name;
    private String field;
    private String description;
    private boolean root;
    private int hierarchyID;
    private int IDConfigurator;

    public Category ( String name, String field, String description, boolean root, int hierarchyID, int IDConfigurator ) throws SQLException
    {
        this.name = name;
        this.field = field;
        this.description = description;
        this.root = root;
        this.hierarchyID = hierarchyID;
        this.IDConfigurator = IDConfigurator;
        this.ID = takeID();
    }

    public int getHierarchyID() 
    {
        return hierarchyID;
    }

    private int takeID () throws SQLException
    {
        String query = "SELECT id FROM categories WHERE name = ? AND hierarchyID = ? " +
                       "UNION " +
                       "SELECT id FROM tmp_categories WHERE name = ? AND hierarchyID = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );
        parameters.add( Integer.toString( this.hierarchyID ) );
        parameters.add( this.name );
        parameters.add( Integer.toString( this.hierarchyID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getInt( 1 );
    }

    public void createRelationship ( int parentID, String fieldType ) throws SQLException
    {
        String query = "INSERT INTO tmp_relationshipsBetweenCategories (parentid, childid, fieldtype) VALUES (?, ?, ?)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( parentID ) );
        parameters.add( Integer.toString( this.ID ) );
        parameters.add( fieldType );

        Conn.queryUpdate( query, parameters );
    }

    public static boolean isPresentRootCategory ( String nameToCheck ) throws SQLException
    {
        String query = "SELECT name FROM categories WHERE name = ? AND root = ? " +
                       "UNION " +
                       "SELECT name FROM tmp_categories WHERE name = ? AND root = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( nameToCheck );
        parameters.add( String.valueOf( 1 ) );
        parameters.add( nameToCheck );
        parameters.add( String.valueOf( 1 ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    public static boolean isPresentRootCategory ( int IDToCheck ) throws SQLException
    {
        String query = "SELECT name FROM categories WHERE id = ? AND root = ? " +
                       "UNION " +
                       "SELECT name FROM tmp_categories WHERE id = ? AND root = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( IDToCheck ) );
        parameters.add( String.valueOf( 1 ) );
        parameters.add( Integer.toString( IDToCheck ) );
        parameters.add( String.valueOf( 1 ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    public static boolean isPresentInternalCategory ( String nameToCheck, int hierarchyID ) throws SQLException
    {
        String query = "SELECT name FROM categories WHERE name = ? AND hierarchyID = ? " +
                       "UNION " +
                       "SELECT name FROM tmp_categories WHERE name = ? AND hierarchyID = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( nameToCheck );
        parameters.add( Integer.toString( hierarchyID ) );
        parameters.add( nameToCheck );
        parameters.add( Integer.toString( hierarchyID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    public static String printCategoriesList ( int hierarchyID ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        String query = "SELECT id, name FROM categories WHERE hierarchyID = ? AND field IS NOT NULL AND description IS NOT NULL AND id NOT IN (SELECT MAX(id) FROM categories) " +
                       "UNION " +
                       "SELECT id, name FROM tmp_categories WHERE hierarchyID = ? AND field IS NOT NULL AND description IS NOT NULL AND id NOT IN (SELECT MAX(id) FROM tmp_categories)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( hierarchyID ) );
        parameters.add( Integer.toString( hierarchyID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        while ( rs.next() )
        {
            toReturn.append( "  " + rs.getInt( 1 ) + ". " + rs.getString( 2 ) + "\n" );
        }

        return toReturn.toString();
    }

    public static String printAllRoot () throws SQLException
    {
        String query = "SELECT name, hierarchyid FROM categories WHERE root = ?" +
                       "UNION " +
                       "SELECT name, hierarchyid FROM tmp_categories WHERE root = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( String.valueOf( 1 ) );
        parameters.add( String.valueOf( 1 ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        StringBuffer toReturn = new StringBuffer();
        while ( rs.next() ) toReturn.append( " " + rs.getString( 2 ) + ". " + rs.getString( 1 ) + "\n" );

        return toReturn.toString();
    }

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

}
