package controller.MVCController;

import java.io.IOException;
import controller.ClientServer.Client;
import controller.ClientServer.SomeRequestMunicipality;

public class MunicipalityController  {
    
    private Client client;
    private SomeRequestMunicipality requestMunicipality;

    public MunicipalityController (Client client)
    {
        this.client = client;   
    }

    /*public DistrictToMunicipalitiesRepository getDistrictToMunicipalitiesRepository ()
    {
        return this.municipalityService.getDistrictToMunicipalitiesRepository();
    }

    public Municipality getMunicipalityByName ( String municipalityName ) throws SQLException
    {
        return municipalityService.getMunicipalityByName( municipalityName );
    }*/

    public String getAllMunicipalityFromDistrict ( String districtName ) throws IOException, ClassNotFoundException
    {
        requestMunicipality = new SomeRequestMunicipality("GET_ALL_MUNICIPALITY_FROM_DISTRICT", districtName);
        client.sendRequest(requestMunicipality);
        return (String) client.receiveResponse();
    }

    public boolean existaMunicipalityName (String name) throws IOException, ClassNotFoundException
    {
        requestMunicipality = new SomeRequestMunicipality("EXIST_MUNICIPALITY", name);
        client.sendRequest(requestMunicipality);
        return (boolean) client.receiveResponse();
        /*if( getMunicipalityByName( name ) == null) return false;
        return true;*/
    }

    public void addMunicipality ( String municipalityNameToAdd ) throws IOException, ClassNotFoundException
    {
        requestMunicipality = new SomeRequestMunicipality("ADD_MUNICIPALITY", municipalityNameToAdd );
        client.sendRequest(requestMunicipality);
        client.receiveResponse();
        //this.districtService.addMunicipality( municipalityToAdd );
    }

    public boolean isPresentMunicipalityInDistrict ( String municipalityNameToCheck ) throws ClassNotFoundException, IOException
    {
        requestMunicipality = new SomeRequestMunicipality("IS_PRESENT_MUNICIPALITY_IN_DISTRICT", municipalityNameToCheck);
        client.sendRequest(requestMunicipality);
        return (boolean) client.receiveResponse();
        //return this.districtService.isPresentMunicipalityInDistrict( municipalityToCheck );
    }

}
