package Server.repository;

import java.sql.SQLException;
import java.util.List;

import Server.model.Category;
import Server.model.Proposal;
import Server.model.User;

public interface ProposalRepository {

    void insertProposal ( Category requestedCategory, Category offeredCategory, int requestedHours, int offeredHours, User user, String state ) throws SQLException;

    List<Proposal> getAllOpenProposalByUser ( User user ) throws SQLException;

    void retireProposal ( Proposal proposal ) throws SQLException;

    List<Proposal> getAllProposalsByUser ( User user ) throws SQLException;

    List<Proposal> getAllProposalsByLeaf ( Category category ) throws SQLException;

    List<Proposal> getAllCompatibleProposals ( Proposal proposal ) throws SQLException;

    void closeProposal ( Proposal proposal ) throws SQLException;

    void deleteProposalByUser ( int userID ) throws SQLException;

    Proposal getProposalById (int id) throws SQLException;
}
