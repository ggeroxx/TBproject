package controller.MVCController;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.District;
import model.Municipality;
import model.util.Constants;
import repository.DistrictRepository;
import service.ControlPatternService;
import service.DistrictService;
import view.DistrictInfoView;

import view.InsertDistrictView;

public class DistrictController {
    
    private InsertDistrictView insertDistrictView;
    private DistrictInfoView districtInfoView;
    private MunicipalityController municipalityController;
    private ConfiguratorController configuratorController;
    private DistrictService districtService;

    public DistrictController ( InsertDistrictView insertDistrictView, DistrictInfoView districtInfoView, MunicipalityController municipalityController, DistrictService districtService)
    {
        this.municipalityController = municipalityController;
        this.districtService = districtService;

        this.insertDistrictView = insertDistrictView;
        this.districtInfoView = districtInfoView;

        this.insertDistrictView.getAddDistrictAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    String districtName = insertDistrictView.getTextNameDistrict();
                    if( checkCorrectName( districtName ) )
                    {   
                        if( getDistrictRepository().getDistrictByName( districtName ) != null ) 
                        {
                            insertDistrictView.setLblErrorNameDistrict( Constants.DISTRICT_NAME_ALREADY_PRESENT );
                        }
                        else
                        {
                            configuratorController.createDistrict( districtName );
                            insertDistrictView.blockAddDistrictAnEnableAddMunicipality(districtName);
                        }
                        
                    }
                    else
                    {
                        insertDistrictView.setLblErrorNameDistrict(Constants.ERROR_PATTERN_NAME);
                    }
                } 
                catch (SQLException e1) 
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
                        Municipality municipalityToAdd = municipalityController.getMunicipalityRepository().getMunicipalityByName( insertDistrictView.getTextMunicipality() );
                        if ( isPresentMunicipalityInDistrict( municipalityToAdd ) )
                        {
                            insertDistrictView.setLblErrorMunicipality( Constants.MUNICIPALITY_NAME_ALREADY_PRESENT );
                        }
                        else
                        {
                            addMunicipality( municipalityToAdd );
                            insertDistrictView.addedSuccesfullMunicipality(Constants.ADDED_SUCCESFULL_MESSAGE, getAllMunicipalityFromDistrict(districtService.getDistrictByName(insertDistrictView.getTextNameDistrict())) );
                        }
                    }
                    else
                    {
                        insertDistrictView.setLblErrorMunicipality(Constants.NOT_EXIST_MESSAGE);
                    }
                } catch (SQLException e1) 
                {
                    e1.printStackTrace();
                }
            }

                
		});

        this.districtInfoView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
				closeDistrictInfoView();
			}
		});


        this.insertDistrictView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeInserNewDistrictView();
			}
		});
    }

    public void startInsertNewDistrictView( ConfiguratorController configuratorController )
    {
        this.configuratorController = configuratorController;
        this.insertDistrictView.setUndecorated(true);
        this.insertDistrictView.setVisible(true);
    }

    public void closeInserNewDistrictView ()
    {
        insertDistrictView.resetFields();
		insertDistrictView.dispose();
    }

    public void startDistrictInfoView() throws SQLException
    {
        this.districtInfoView.setUndecorated(true);
        this.districtInfoView.setVisible(true);
        this.districtInfoView.getmenuBar().removeAll();

        for (String name : allDistrictName()) 
        {
            districtInfoView.createDistrictButton(name, getAllMunicipalityFromDistrict( districtService.getDistrictByName( name ) ) );
        }
    }

    public void closeDistrictInfoView()
    {
        districtInfoView.dispose();
    }

    public DistrictRepository getDistrictRepository ()
    {
        return this.districtService.getDistrictRepository();
    }

    public void setDistrict ( District district )
    {
        this.districtService.setDistrict(district);
    }

    public List<String> allDistrictName () throws SQLException 
    {
        return this.districtService.allDistrictName();
    }

    public String getAllMunicipalityFromDistrict(District district) throws SQLException
    {
        return municipalityController.getAllMunicipalityFromDistrict( district );
    }

    public boolean checkCorrectName( String name)
    {
        return ControlPatternService.checkPattern( name, 0, 50 ) ? false : true;
    }

    public void saveDistricts () throws SQLException
    {
        this.districtService.saveDistricts();
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return this.districtService.isPresentMunicipalityInDistrict( municipalityToCheck );
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        this.districtService.addMunicipality( municipalityToAdd );
    }

    public District getDistrictByName( String name) throws SQLException
    {
        return districtService.getDistrictByName( name );
    }


}
