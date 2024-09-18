package repository.JDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Category;
import model.Proposal;
import model.User;
import model.util.Conn;
import model.util.Queries;
import repository.CategoryRepository;
import repository.ProposalRepository;
import repository.UserRepository;

public class JDBCProposalRepository implements ProposalRepository {
    
    private static CategoryRepository categoryRepository = new JDBCCategoryRepository();
    private static UserRepository userRepository = new JDBCUserRepository();

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
        while( rs.next() ) toReturn.add( new Proposal( rs.getInt( 1 ), categoryRepository.getCategoryByID( rs.getInt( 2 ) ), categoryRepository.getCategoryByID( rs.getInt( 3 ) ), rs.getInt( 4 ), rs.getInt( 5 ), userRepository.getUserByID( rs.getInt( 6 ) ), rs.getString( 7 ) ) );
        return toReturn;
    }

    @Override
    public List<Proposal> getAllProposalsByLeaf ( Category leaf ) throws SQLException 
    {
        List<Proposal> toReturn = new ArrayList<Proposal>();
        ResultSet rs = Conn.exQuery( Queries.GET_ALL_PROPOSALS_BY_LEAF_QUERY, new ArrayList<>( Arrays.asList( leaf.getID(), leaf.getID() ) ) );
        while( rs.next() ) toReturn.add( new Proposal( rs.getInt( 1 ), categoryRepository.getCategoryByID( rs.getInt( 2 ) ), categoryRepository.getCategoryByID( rs.getInt( 3 ) ), rs.getInt( 4 ), rs.getInt( 5 ), userRepository.getUserByID( rs.getInt( 6 ) ), rs.getString( 7 ) ) );
        return toReturn;
    }

    @Override
    public void retireProposal ( Proposal proposal ) throws SQLException 
    {
        Conn.queryUpdate( Queries.RETIRE_PROPOSAL_QUERY, new ArrayList<>( Arrays.asList( "retire", proposal.getID() ) ) );   
    }

    @Override
    public List<Proposal> getAllCompatibleProposals ( Proposal proposal ) throws SQLException 
    {
        List<Proposal> toReturn = new ArrayList<Proposal>();
        ResultSet rs = Conn.exQuery( Queries.GET_ALL_COMPATIBLE_PROPOSALS_QUERY, new ArrayList<>( Arrays.asList( proposal.getOfferedCategory().getID(), proposal.getOfferedHours(), proposal.getUser().getID(), proposal.getUser().getID() ) ) );
        while( rs.next() ) toReturn.add( new Proposal( rs.getInt( 1 ), categoryRepository.getCategoryByID( rs.getInt( 2 ) ), categoryRepository.getCategoryByID( rs.getInt( 3 ) ), rs.getInt( 4 ), rs.getInt( 5 ), userRepository.getUserByID( rs.getInt( 6 ) ), rs.getString( 7 ) ) );
        return toReturn;
    }

    @Override
    public void closeProposal ( Proposal proposal ) throws SQLException 
    {
        Conn.queryUpdate( Queries.CLOSE_PROPOSAL_QUERY, new ArrayList<>( Arrays.asList( "close", proposal.getID() ) ) );
    }

    @Override
    public void deleteProposalByUser( int userID ) throws SQLException 
    {
        Conn.queryUpdate( Queries.DELETE_PROPOSAL_BY_USER, new ArrayList<>( Arrays.asList( userID ) ) );  
    }

    @Override
    public Proposal getProposalById (int id) throws SQLException
    {
        ResultSet rs = Conn.exQuery( Queries.GET_PROPOSAL_BY_ID, new ArrayList<>( Arrays.asList( id ) ) );
        return new Proposal( rs.getInt( 1 ), categoryRepository.getCategoryByID( rs.getInt( 2 ) ), categoryRepository.getCategoryByID( rs.getInt( 3 ) ), rs.getInt( 4 ), rs.getInt( 5 ), userRepository.getUserByID( rs.getInt( 6 ) ), rs.getString( 7 ) ) ;
    }

}
