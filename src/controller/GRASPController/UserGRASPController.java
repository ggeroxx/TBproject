package controller.GRASPController;

import java.sql.SQLException;
import model.Proposal;
import model.User;
import model.UserRepository;
import service.UserService;

public class UserGRASPController {
    
    private User user;
    private UserService userService;

    public UserGRASPController ( UserService userService )
    {
        this.userService = userService;
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
        return this.userService.getuserRepository();
    }

    public void insertProposal ( Proposal toInsert ) throws SQLException
    {
        userService.insertProposal(toInsert);    
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        userService.retireProposal(toRetire);
    }

}