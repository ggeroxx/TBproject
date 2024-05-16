package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class ProposalJDBCImpl implements ProposalJDBC {
    
    @Override
    public void insertProposal ( Category requestedCategory, Category offeredCategory, int requestedHours, int offeredHours, User user, String state ) throws SQLException
    {
        Conn.queryUpdate( Queries.INSERT_PROPOSAL_QUERY, new ArrayList<>( Arrays.asList( requestedCategory.getID(), offeredCategory.getID(), requestedHours, offeredHours, user.getID(), state ) ) );   
    }

}
