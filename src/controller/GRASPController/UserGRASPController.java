package controller.GRASPController;

import java.sql.SQLException;

import controller.MVCController.*;
import model.*;

public class UserGRASPController {
    
    private User user;
    private UserRepository userRepository;
    private ProposalController proposalController;

    public UserGRASPController ( UserRepository userRepository, ProposalController proposalController )
    {
        this.userRepository = userRepository;
        this.proposalController = proposalController;
    }

    public User getUser ()
    {
        return this.user;
    }

    public void setUser ( User user )
    {
        this.user = user;
    }

    public UserRepository getuserRepository ()
    {
        return this.userRepository;
    }

    public void insertProposal ( Proposal toInsert ) throws SQLException
    {
        proposalController.getproposalRepository().insertProposal( toInsert.getRequestedCategory(), toInsert.getOfferedCategory(), toInsert.getRequestedHours(), toInsert.getOfferedHours(), toInsert.getUser(), toInsert.getState() );
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        proposalController.getproposalRepository().retireProposal( toRetire );
    }

}