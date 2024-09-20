package Request;

import java.io.Serializable;

public class SomeRequestUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private String name;
    private String password;
    private int districtID;
    private String email;

    public SomeRequestUser(String action, String name, String password, int districtID, String email) {
        this.action = action;
        this.name = name;
        this.password = password;
        this.districtID = districtID;
        this.email = email;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName()
    {
        return this.name;
    }

    public String getPassword()
    {
        return this.password;
    }

    public int getDistrictID()
    {
        return this.districtID;
    }

    public String getEmail ()
    {
        return this.email;
    }


    @Override
    public String toString() {
        return "SomeRequestUser{" +
                "action='" + action + '\'' +
                ", name='" + name + '\'' +
                ", districtID='" + districtID + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
