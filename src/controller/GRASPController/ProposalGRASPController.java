package controller.GRASPController;

import java.sql.SQLException;
import java.util.List;
import model.Proposal;
import repository.ProposalRepository;
import service.ProposalService;

public class ProposalGRASPController {
    
    private ProposalService proposalService;

    public ProposalGRASPController ( ProposalService proposalService )
    {
        this.proposalService = proposalService;
    }

    public ProposalRepository getProposalRepository () 
    {
        return this.proposalService.getProposalRepository();
    }

    public void verifyProposal ( Proposal inserted, Proposal toVerify, List<Proposal> toCloses ) throws SQLException
    {
        this.proposalService.verifyProposal(inserted, toVerify, toCloses);
    }

}
