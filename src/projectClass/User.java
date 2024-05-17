package projectClass;

import java.sql.SQLException;

public class User {
    
    private Integer ID;
    private String username;
    private String password;
    private int districtID;
    private String mail;
    private ProposalJDBC proposalJDBC = new ProposalJDBCImpl();

    public User ( Integer ID, String username, String password, int districtID, String mail )
    {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.districtID = districtID;
        this.mail = mail;
    }

    public int getID() 
    {
        return this.ID;
    }

    public String getUsername() 
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public int getDistrictID() 
    {
        return this.districtID;
    }

    public String getMail() 
    {
        return this.mail;
    }

    public void insertProposal ( Proposal toInsert ) throws SQLException
    {
        proposalJDBC.insertProposal( toInsert.getRequestedCategory(), toInsert.getOfferedCategory(), toInsert.getRequestedHours(), toInsert.getOfferedHours(), toInsert.getUser(), toInsert.getState() );
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        proposalJDBC.retireProposal( toRetire );
    }
}
