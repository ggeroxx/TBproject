package controller.ClientServer;

import java.io.Serializable;

public class SomeRequestConfigurator implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private String username;
    private String newPassword;

    public SomeRequestConfigurator(String action, String username, String newPassword) {
        this.action = action;
        this.username = username;
        this.newPassword = newPassword;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return this.username;
    }

    public String getNewPassword() {
        return this.newPassword;
    }


    @Override
    public String toString() {
        return "SomeRequestConfigurator{" +
                "action='" + this.action + '\'' +
                ", username='" + this.username + '\'' +
                '}';
    }
    
}
