package service;

import java.sql.SQLException;
import model.*;

public class UserService{
    
    private UserRepository userRepository;
    private ProposalService proposalService;

    public UserService ( UserRepository userRepository, ProposalService proposalService )
    {
        this.userRepository = userRepository;
        this.proposalService = proposalService;
    }

    public UserRepository getuserRepository ()
    {
        return this.userRepository;
    }

    public void insertProposal ( Proposal toInsert ) throws SQLException
    {
        this.proposalService.getProposalRepository().insertProposal( toInsert.getRequestedCategory(), toInsert.getOfferedCategory(), toInsert.getRequestedHours(), toInsert.getOfferedHours(), toInsert.getUser(), toInsert.getState() );
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        this.proposalService.getProposalRepository().retireProposal( toRetire );
    }

}