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
        parameters.add( Boolean.toString( true ) );
        parameters.add( nameToCheck );
        parameters.add( Boolean.toString( true ) );

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

        String query = "SELECT id, name FROM categories WHERE hierarchyID = ? LIMIT (SELECT COUNT(*) FROM categories) " +
                       "UNION " +
                       "SELECT id, name FROM tmp_categories WHERE hierarchyID = ? LIMIT (SELECT COUNT(*) FROM tmp_categories)";

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

}
