package service;

import java.sql.SQLException;
import model.Proposal;
import model.User;
import repository.UserRepository;

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

    public void insertUser ( User newUser ) throws SQLException
    {
        userRepository.insertUser(newUser);
    }

    public User getUserByUsername ( String username ) throws SQLException
    {
        return userRepository.getUserByUsername(username);
    }

}