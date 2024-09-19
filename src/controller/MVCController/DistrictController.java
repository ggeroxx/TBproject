package controller.MVCController;

import java.util.List;
import controller.ClientServer.Client;
import controller.ClientServer.SomeRequestDistrict;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import model.util.Constants;
import view.DistrictInfoView;
import view.InsertDistrictView;

public class DistrictController {
    
    private InsertDistrictView insertDistrictView;
    private DistrictInfoView districtInfoView;
    private MunicipalityController municipalityController;
    private ControlPatternController controlPatternController;
    private SessionController sessionController;
    
    private Client client;
    private SomeRequestDistrict requestDistrict; 
    
    public DistrictController ( InsertDistrictView insertDistrictView, DistrictInfoView districtInfoView, MunicipalityController municipalityController, ControlPatternController controlPatternController, SessionController sessionController, Client client)
    {
        this.insertDistrictView = insertDistrictView;
        this.districtInfoView = districtInfoView;
        this.municipalityController = municipalityController;
        this.controlPatternController = controlPatternController;
        this.sessionController = sessionController;
    
        this.client = client;

        this.insertDistrictView.getAddDistrictAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    String districtName = insertDistrictView.getTextNameDistrict();
                    if( controlPatternController.checkCorrectName( districtName ) )
                    {   

                        if( districtAlreadyPresent(districtName) /*districtService.getDistrictByName( districtName ) != null*/) 
                        {
                            insertDistrictView.setLblErrorNameDistrict( Constants.DISTRICT_NAME_ALREADY_PRESENT );
                        }
                        else
                        {
                            createDistrict(districtName);
                            //configuratorController.createDistrict( districtName );
                            insertDistrictView.blockAddDistrictAnEnableAddMunicipality(districtName);
                        }
                        
                    }
                    else
                    {
                        insertDistrictView.setLblErrorNameDistrict(Constants.ERROR_PATTERN_NAME);
                    }
                } 
                catch (IOException e1)
                {
                    e1.printStackTrace();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                }
                
			}
		});

        this.insertDistrictView.getAddmunicipalityButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {

                    if(municipalityController.existaMunicipalityName(insertDistrictView.getTextMunicipality()))
                    {
                        
                        if ( municipalityController.isPresentMunicipalityInDistrict(insertDistrictView.getTextMunicipality()))
                        {
                            insertDistrictView.setLblErrorMunicipality( Constants.MUNICIPALITY_NAME_ALREADY_PRESENT );
                        }
                        else
                        {
                            municipalityController.addMunicipality(insertDistrictView.getTextMunicipality());
                            //addMunicipality( municipalityToAdd );
                            insertDistrictView.addedSuccesfullMunicipality(Constants.ADDED_SUCCESFULL_MESSAGE, municipalityController.getAllMunicipalityFromDistrict(insertDistrictView.getTextNameDistrict())/*getAllMunicipalityFromDistrict(districtService.getDistrictByName(insertDistrictView.getTextNameDistrict()))*/ );
                        }
                    }
                    else
                    {
                        insertDistrictView.setLblErrorMunicipality(Constants.NOT_EXIST_MESSAGE);
                    }
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }

                
		});

        this.districtInfoView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
				closeDistrictInfoView();
                try 
                {
                    sessionController.logout();
                    client.close();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                }    
                System.exit(0);
			}
		});

        this.districtInfoView.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeDistrictInfoView();
            }
		});

        this.insertDistrictView.getEndButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeInserNewDistrictView();
            }
		});

        this.insertDistrictView.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeInserNewDistrictView();
            }
		});

        this.insertDistrictView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeInserNewDistrictView();
                try 
                {
                    sessionController.logout();
                    client.close();
                }
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
			}
		});
    }

    public void startInsertNewDistrictView( )
    {
        //this.configuratorController = configuratorController;
        this.insertDistrictView.setUndecorated(true);
        this.insertDistrictView.setVisible(true);
    }

    public void closeInserNewDistrictView ()
    {
        insertDistrictView.resetFields();
		insertDistrictView.dispose();
    }

    public void startDistrictInfoView() throws ClassNotFoundException, IOException
    {
        this.districtInfoView.setUndecorated(true);
        this.districtInfoView.setVisible(true);
        this.districtInfoView.getmenuBar().removeAll();

        for (String name : allDistrictName()) 
        {
            districtInfoView.createDistrictButton(name, municipalityController.getAllMunicipalityFromDistrict(name) );
        }
    }

    public void closeDistrictInfoView()
    {
        districtInfoView.dispose();
    }

    /*public void setDistrict ( District district )
    {
        //this.districtService.setDistrict(district);
    }*/

    public List<String> allDistrictName () throws IOException, ClassNotFoundException 
    {
        requestDistrict = new SomeRequestDistrict("GET_ALL_DISTRICT_NAME", null);
        client.sendRequest(requestDistrict);
        return (List<String>) client.receiveResponse();
        //return this.districtService.allDistrictName();
    }

    public void saveDistricts () throws IOException, ClassNotFoundException
    {
        requestDistrict = new SomeRequestDistrict("SAVE_DISTRICT", null);
        client.sendRequest(requestDistrict);
        client.receiveResponse();
        //this.districtService.saveDistricts();
    }

    /*public boolean isPresentMunicipalityInDistrict ( String municipalityNameToCheck ) throws ClassNotFoundException, IOException
    {
        requestMunicipality = new SomeRequestMunicipality("IS_PRESENT_MUNICIPALITY_IN_DISTRICT", municipalityNameToCheck);
        client.sendRequest(requestMunicipality);
        return (boolean) client.receiveResponse();
        //return this.districtService.isPresentMunicipalityInDistrict( municipalityToCheck );
    }*/

    /*public void addMunicipality ( String municipalityNameToAdd ) throws IOException, ClassNotFoundException
    {
        requestMunicipality = new SomeRequestMunicipality("ADD_MUNICIPALITY", municipalityNameToAdd );
        client.sendRequest(requestMunicipality);
        response = (SomeResponse) client.receiveResponse();
        //this.districtService.addMunicipality( municipalityToAdd );
    }*/

    public boolean districtAlreadyPresent( String districtName) throws IOException, ClassNotFoundException
    {
        requestDistrict = new SomeRequestDistrict("ALREADY_DISTRICT_PRESENT", districtName);
        client.sendRequest(requestDistrict);
        return (boolean) client.receiveResponse();
    }

    public void createDistrict( String districtName) throws ClassNotFoundException, IOException
    {
        requestDistrict = new SomeRequestDistrict("CREATE_DISTRICT", districtName);
        client.sendRequest(requestDistrict);
        client.receiveResponse();
    }

    public int getDistrictIDByName( String districtName) throws ClassNotFoundException, IOException
    {
        requestDistrict = new SomeRequestDistrict("GET_DISTRICT_ID_BY_NAME", districtName);
        client.sendRequest(requestDistrict);
        return (int) client.receiveResponse();
    }



}
