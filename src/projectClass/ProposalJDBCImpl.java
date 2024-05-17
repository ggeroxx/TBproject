package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class ProposalJDBCImpl implements ProposalJDBC {
    
    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
    private static UserJDBC userJDBC = new UserJDBCImpl();

    @Override
    public void insertProposal ( Category requestedCategory, Category offeredCategory, int requestedHours, int offeredHours, User user, String state ) throws SQLException
    {
        Conn.queryUpdate( Queries.INSERT_PROPOSAL_QUERY, new ArrayList<>( Arrays.asList( requestedCategory.getID(), offeredCategory.getID(), requestedHours, offeredHours, user.getID(), state ) ) );   
    }

    @Override
    public List<Proposal> getAllOpenProposalByUser ( User user ) throws SQLException 
    {
        return getAllByUser( Queries.GET_ALL_OPEN_PROPOSALS_BY_USER_QUERY, user );
    }

    @Override
    public List<Proposal> getAllProposalsByUser ( User user ) throws SQLException 
    {
        return getAllByUser( Queries.GET_ALL_PROPOSALS_BY_USER_QUERY, user );
    }

    private List<Proposal> getAllByUser ( String query, User user ) throws SQLException
    {
        List<Proposal> toReturn = new ArrayList<Proposal>();
        ResultSet rs = Conn.exQuery( query, new ArrayList<>( Arrays.asList( user.getID() ) ) );
        while( rs.next() ) toReturn.add( new Proposal( rs.getInt( 1 ), categoryJDBC.getCategoryByID( rs.getInt( 2 ) ), categoryJDBC.getCategoryByID( rs.getInt( 3 ) ), rs.getInt( 4 ), rs.getInt( 5 ), userJDBC.getUserByID( rs.getInt( 6 ) ), rs.getString( 7 ) ) );
        return toReturn;
    }

    @Override
    public List<Proposal> getAllProposalsByLeaf ( Category leaf ) throws SQLException 
    {
        List<Proposal> toReturn = new ArrayList<Proposal>();
        ResultSet rs = Conn.exQuery( Queries.GET_ALL_PROPOSALS_BY_LEAF_QUERY, new ArrayList<>( Arrays.asList( leaf.getID(), leaf.getID() ) ) );
        while( rs.next() ) toReturn.add( new Proposal( rs.getInt( 1 ), categoryJDBC.getCategoryByID( rs.getInt( 2 ) ), categoryJDBC.getCategoryByID( rs.getInt( 3 ) ), rs.getInt( 4 ), rs.getInt( 5 ), userJDBC.getUserByID( rs.getInt( 6 ) ), rs.getString( 7 ) ) );
        return toReturn;
    }

    @Override
    public void retireProposal ( Proposal proposal ) throws SQLException 
    {
        Conn.queryUpdate( Queries.RETIRE_PROPOSAL_QUERY, new ArrayList<>( Arrays.asList( "retire", proposal.getID() ) ) );   
    }

}
