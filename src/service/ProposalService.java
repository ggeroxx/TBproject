package service;

import java.sql.SQLException;
import java.util.List;

import model.Category;
import model.Proposal;
import model.User;
import repository.ProposalRepository;

public class ProposalService {
    
    private ProposalRepository proposalRepository;

    public ProposalService ( ProposalRepository proposalRepository )
    {
        this.proposalRepository = proposalRepository;
    }

    public ProposalRepository getProposalRepository () 
    {
        return this.proposalRepository;
    }

    public void verifyProposal ( Proposal inserted, Proposal toVerify, List<Proposal> toCloses ) throws SQLException
    {
        List<Proposal> compatibleProposals = proposalRepository.getAllCompatibleProposals( toVerify != null ? toVerify : inserted );

        if ( compatibleProposals.isEmpty() ) return;

        if ( toVerify != null && inserted.getOfferedCategory().getID() == toVerify.getOfferedCategory().getID() && inserted.getRequestedCategory().getID() == toVerify.getRequestedCategory().getID() )
        {
            closeProposals( toCloses );
            return;
        }

        toCloses.add( compatibleProposals.get( 0 ) );
        verifyProposal( inserted, compatibleProposals.get( 0 ), toCloses );
    }

    private void closeProposals ( List<Proposal> toCloses ) throws SQLException
    {
        for ( Proposal toClose : toCloses )
            proposalRepository.closeProposal( toClose );
    }

    public List<Proposal> getAllProposalsByLeaf (Category category) throws SQLException
    {
        return proposalRepository.getAllProposalsByLeaf(category);
    }

    public List<Proposal> getAllProposalsByUser (User user) throws SQLException
    {
        return proposalRepository.getAllProposalsByUser(user);
    }

    public List<Proposal> getAllOpenProposalByUser (User user) throws SQLException
    {
        return proposalRepository.getAllOpenProposalByUser(user);
    }

}
