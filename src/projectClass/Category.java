package projectClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import util.*;

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

    public Category ( int ID ) throws SQLException
    {
        String query = "SELECT * FROM categories WHERE id = ? UNION SELECT * FROM tmp_categories WHERE id = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( ID ) );
        parameters.add( Integer.toString( ID ) );

        ResultSet rs = Conn.exQuery( query, parameters );
        rs.next();

        this.ID = ID;
        this.name = rs.getString( 2 );
        this.field = rs.getString( 3 );
        this.description = rs.getString( 4 );
        this.root = rs.getBoolean( 5 );
        this.hierarchyID = rs.getInt( 6 );
        this.IDConfigurator = rs.getInt( 7 );
    }

    public int getID() 
    {
        return ID;
    }

    public String getName() 
    {
        return name;
    }

    public String getField() 
    {
        return field;
    }

    public String getDescription() 
    {
        return description;
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

    public boolean isPresentInternalCategory ( String nameToCheck ) throws SQLException
    {
        String query = "SELECT name FROM categories WHERE name = ? AND hierarchyID = ? " +
                       "UNION " +
                       "SELECT name FROM tmp_categories WHERE name = ? AND hierarchyID = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( nameToCheck );
        parameters.add( Integer.toString( this.hierarchyID ) );
        parameters.add( nameToCheck );
        parameters.add( Integer.toString( this.hierarchyID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    public static Category getRootByLeaf ( Category leaf ) throws SQLException
    {
        String query = "SELECT id FROM categories WHERE id = ( SELECT hierarchyid FROM categories WHERE id = ? AND name = ? UNION SELECT hierarchyid FROM tmp_categories WHERE id = ? AND name = ?) " + 
                       "UNION " + 
                       "SELECT id FROM tmp_categories WHERE id = ( SELECT hierarchyid FROM categories WHERE id = ? AND name = ? UNION SELECT hierarchyid FROM tmp_categories WHERE id = ? AND name = ?)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( leaf.getID() ) );
        parameters.add( leaf.getName() );
        parameters.add( Integer.toString( leaf.getID() ) );
        parameters.add( leaf.getName() );
        parameters.add( Integer.toString( leaf.getID() ) );
        parameters.add( leaf.getName() );
        parameters.add( Integer.toString( leaf.getID() ) );
        parameters.add( leaf.getName() );

        ResultSet rs = Conn.exQuery( query, parameters );
        rs.next();

        return new Category ( rs.getInt( 1 ) );
    }

    public boolean isValidParentID ( int IDToCheck ) throws SQLException
    {
        String query = "SELECT name FROM categories WHERE id = ? AND hierarchyID = ? AND field IS NOT NULL AND id != LAST_INSERT_ID() " +
                       "UNION " +
                       "SELECT name FROM tmp_categories WHERE id = ? AND hierarchyID = ? AND field IS NOT NULL AND id != LAST_INSERT_ID()";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( IDToCheck ) );
        parameters.add( Integer.toString( this.hierarchyID ) );
        parameters.add( Integer.toString( IDToCheck ) );
        parameters.add( Integer.toString( this.hierarchyID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    @Override
    public boolean equals ( Object obj ) 
    {
        return this.ID == ((Category)obj).ID;
    }

}
