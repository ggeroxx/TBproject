package Request;

import java.io.Serializable;

public class SomeRequestPattern implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private String name;
    private int min;
    private int max;
    private boolean isPresent;


    public SomeRequestPattern ( String action, String name, int min, int max, boolean isPresent ) {
        this.action = action;
        this.name = name;
        this.min = min;
        this.max = max;
        this.isPresent = isPresent;
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

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public boolean getIsPresent ()
    {
        return this.isPresent;
    }


    @Override
    public String toString() {
        return "SomeRequestPattern{" +
                "action='" + action + '\'' +
                ", name ='" + name + '\'' +
                ", min ='" + min + '\'' +
                ", max ='" + max + '\'' +
                ", isPresent ='" + isPresent + '\'' +
                '}';
    }
    
}
