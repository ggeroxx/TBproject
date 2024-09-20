package Request;

import java.io.Serializable;

public class SomeRequestSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private String name;

    public SomeRequestSubject ( String action, String name) {
        this.action = action;
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return this.name;
    }


    @Override
    public String toString() {
        return "SomeRequestPattern{" +
                "action='" + action + '\'' +
                ", name ='" + name + '\'' +
                '}';
    }
    
}
