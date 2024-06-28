package model;

public class Proposal {
    
    private Integer ID;
    private Category requestedCategory;
    private Category offeredCategory;
    private int requestedHours;
    private int offeredHours;
    private User user;
    private String state;

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

    public Integer getID() 
    {
        return this.ID;
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

    public User getUser() 
    {
        return this.user;
    }

    public String getState() 
    {
        return this.state;
    }

}
