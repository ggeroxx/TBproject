package projectClass;

import java.sql.SQLException;

public class Proposal {
    
    private Integer ID;
    private Category requestedCategory;
    private Category offeredCategory;
    private int requestedHours;
    private int offeredHours;
    private User user;
    private String state;
    private static ProposalJDBC proposalJDBC = new ProposalJDBCImpl();

    public Proposal ( Integer ID, Category requestedCategory, Category offeredCategory, int requestedHours, int offeredHours, User user, String state )
    {
        this.ID = ID;
        this.requestedCategory = requestedCategory;
        this.offeredCategory = offeredCategory;
        this.requestedHours = requestedHours;
        this.offeredHours = offeredHours;
        this.user = user;
        this.state = state;
    }

    public Category getRequestedCategory() 
    {
        return this.requestedCategory;
    }

    public Category getOfferedCategory() 
    {
        return this.offeredCategory;
    }

    public int getRequestedHours() 
    {
        return this.requestedHours;
    }

    public int getOfferedHours() 
    {
        return this.offeredHours;
    }

    public void save () throws SQLException
    {
        proposalJDBC.insertProposal( this.requestedCategory, this.offeredCategory, this.requestedHours, this.offeredHours, this.user, this.state );
    }

}
