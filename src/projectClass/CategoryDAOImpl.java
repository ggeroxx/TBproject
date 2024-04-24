package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class CategoryDAOImpl implements CategoryDAO {
    
    @Override
    public Category getCategoryByID( int ID ) throws SQLException 
    {
        String query = "SELECT * FROM categories WHERE id = ? " + 
                       "UNION " +
                       "SELECT * FROM tmp_categories WHERE id = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( ID ) );
        parameters.add( Integer.toString( ID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        if ( rs.next() ) return new Category( ID, rs.getString( "name" ), rs.getString( "field" ), rs.getString( "description" ), rs.getBoolean( "root" ), rs.getInt( "hierarchyid" ), rs.getInt( "idconfigurator" ) );
        else return null;
    }    

}
