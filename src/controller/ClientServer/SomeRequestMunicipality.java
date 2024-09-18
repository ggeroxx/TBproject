package controller.ClientServer;

import java.io.Serializable;

public class SomeRequestMunicipality implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private String municipalityName;


    public SomeRequestMunicipality(String action, String municipalityName) {
        this.action = action;
        this.municipalityName = municipalityName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMunicipalityName() {
        return this.municipalityName;
    }


    @Override
    public String toString() {
        return "SomeRequestMunicipality{" +
                "action='" + action + '\'' +
                ", municipalityName='" + municipalityName + '\'' +
                '}';
    }
    
}
