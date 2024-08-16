package controller.GRASPController;

import java.sql.SQLException;
import java.util.*;
import model.*;


public class ProposalGRASPController {
    
    private ProposalRepository proposalRepository;

    public ProposalGRASPController ( ProposalRepository proposalRepository )
    {
        this.proposalRepository = proposalRepository;
    }

    public ProposalRepository getproposalRepository () 
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

}
