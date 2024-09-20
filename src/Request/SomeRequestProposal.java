package Request;


import java.io.Serializable;

public class SomeRequestProposal implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private int id;
    private int requestCategoryID;
    private int offerCategoryID;
    private int  requestHours;
    private int offerHours;
    private String user;
    private String state;

    public SomeRequestProposal(String action, int id, int requestCategoryID, int offerCategoryID, int requestedHours, int offerHours, String user, String state) 
    {
        this.action = action;
        this.id = id;
        this.requestCategoryID = requestCategoryID;
        this.offerCategoryID = offerCategoryID;
        this.requestHours = requestedHours;
        this.offerHours = offerHours;
        this.user = user;
        this.state = state;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getID()
    {
        return this.id;
    }

    public int getRequestCategoryID() {
        return requestCategoryID;
    }

    public int getOfferCategoryID() {
        return offerCategoryID;
    }

    public int getRequestHours() {
        return requestHours;
    }

    public int getOfferHours() {
        return offerHours;
    }

    public String getUser() {
        return user;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "SomeRequestProposal{" +
                "action='" + action + '\'' +
                '}';
    }
}
