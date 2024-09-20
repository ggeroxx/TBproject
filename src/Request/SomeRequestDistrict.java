package Request;

import java.io.Serializable;

public class SomeRequestDistrict implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private String data;

    public SomeRequestDistrict(String action, String data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SomeRequestDistrict{" +
                "action='" + action + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
