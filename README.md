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