package controller.ClientServer;

import java.io.Serializable;

public class SomeRequestSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private String username;
    private String password;

    public SomeRequestSession(String action, String username, String password) {
        this.action = action;
        this.username = username;
        this.password = password;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername ()
    {
        return this.username;
    }

    public String getPassword ()
    {
        return this.password;
    }

    @Override
    public String toString() {
        return "SomeRequestSession{" +
                "action='" + action + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
