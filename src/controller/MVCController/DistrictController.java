package controller.MVCController;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controller.GRASPController.DistrictGRASPController;
import model.District;
import model.Municipality;
import model.util.Conn;
import model.util.Constants;
import model.util.Controls;
import repository.DistrictRepository;
import view.DistrictInfoView;
import view.DistrictView;
import view.InsertDistrictView;

public class DistrictController extends Controller {
    
    private InsertDistrictView insertDistrictView;
    private DistrictInfoView districtInfoView;
    private DistrictView districtView;
    private MunicipalityController municipalityController;
    private DistrictGRASPController controllerGRASP;
    private ConfiguratorController configuratorController;

    public DistrictController ( InsertDistrictView insertDistrictView, DistrictInfoView districtInfoView, DistrictView districtView,MunicipalityController municipalityController, DistrictGRASPController controllerGRASP )
    {
        super( districtView );
        this.districtView = districtView;
        this.municipalityController = municipalityController;
        this.controllerGRASP = controllerGRASP;

        this.insertDistrictView = insertDistrictView;
        this.districtInfoView = districtInfoView;

        this.insertDistrictView.getAddDistrictAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String districtName = insertDistrictView.getTextNameDistrict();
                if( checkCorrectName( districtName ) )
                {   
                    try 
                    {
                        if( getDistrictRepository().getDistrictByName( districtName ) != null ) 
                        {
                            insertDistrictView.setLblErrorNameDistrict( Constants.DISTRICT_NAME_ALREADY_PRESENT );
                        }
                        else
                        {
                            configuratorController.createDistrict( districtName );
                            insertDistrictView.setLblErrorNameDistrict("");
                            insertDistrictView.setTextNameDistrict(districtName);
                            insertDistrictView.getTextFiledNameDistrict().setEnabled(false);
                            insertDistrictView.getAddDistrictAddButton().setVisible(false);
                            insertDistrictView.getlblMunicipality().setVisible(true);
                            insertDistrictView.getTextFiledMunicipality().setVisible(true);
                            insertDistrictView.getlblErrorMunicipality().setVisible(true);
                            insertDistrictView.getAddmunicipalityButton().setVisible(true);
                            insertDistrictView.revalidate();
                            insertDistrictView.repaint();
                        }
                    } 
                    catch (SQLException e1) 
                    {
                        e1.printStackTrace();
                    }
                }
                else
                {
                    insertDistrictView.setLblErrorNameDistrict(Constants.ERROR_PATTERN_NAME);
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
                            insertDistrictView.setLblErrorMunicipality( Constants.ADDED_SUCCESFULL_MESSAGE );
                            insertDistrictView.setTextMunicipalities(null);
                            insertDistrictView.setTextMunicipalities(getAllMunicipalityFromDistrict(getDistrictRepository().getDistrictByName(insertDistrictView.getTextNameDistrict())));
                            insertDistrictView.getScrollPane().setVisible(true);
                            insertDistrictView.getTxtMunicipalities().setVisible(true);   
                            insertDistrictView.getlblErrorMunicipality().setForeground(Color.GREEN);

                            Timer timer = new Timer(2000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {
                                    insertDistrictView.setLblErrorMunicipality("");
                                    insertDistrictView.getlblErrorMunicipality().setForeground(Color.RED); 
                                    insertDistrictView.setTxtMunicipality("");
                                }
                            });
                            timer.setRepeats(false); 
                            timer.start();
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
				districtInfoView.dispose();
			}
		});


        this.insertDistrictView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                resetFields();
				insertDistrictView.dispose();
			}
		});
    }

    public void startInsertNewDistrictView(ConfiguratorController configuratorController)
    {
        this.configuratorController = configuratorController;
        this.insertDistrictView.setUndecorated(true);
        this.insertDistrictView.setVisible(true);
    }

    public void startDistrictInfoView() throws SQLException
    {
        this.districtInfoView.setUndecorated(true);
        this.districtInfoView.setVisible(true);

        for (String name : allDistrictName()) 
        {
            createDistrictButton(name);
        }
    }

    public DistrictRepository getDistrictRepository ()
    {
        return this.controllerGRASP.getDistrictRepository();
    }

    public void setDistrict ( District district )
    {
        this.controllerGRASP.setDistrict(district);
    }

    public List<String> allDistrictName () throws SQLException 
    {
        return this.controllerGRASP.allDistrictName();
    }

    public String getAllMunicipalityFromDistrict(District district) throws SQLException
    {
        return municipalityController.getAllMunicipalityFromDistrict( district );
    }

    public void listAll () throws SQLException
    {
        for ( District toPrint : getDistrictRepository().getAllSavedDistricts() ) districtView.println( " " + toPrint.getID() + ". " + toPrint.getName() );
        for ( District toPrint : getDistrictRepository().getAllNotSavedDistricts() ) districtView.println( " " + toPrint.getID() + ". " + toPrint.getName() + Constants.NOT_SAVED );
    }

    public int chooseDistrict ()
    {
        return super.readIntWithExit( Constants.ENTER_DISTRICT_OR_EXIT, Constants.NOT_EXIST_MESSAGE, ( input ) -> {
            try 
            {
                return getDistrictRepository().getDistrictByID( (Integer) input ) == null;
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }

    public boolean checkCorrectName( String name)
    {
        return Controls.checkPattern( name, 0, 50 ) ? false : true;
    }

    public void viewDistrict () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        districtView.print( Constants.DISTRICTS_LIST );

        if ( getDistrictRepository().getAllDistricts().isEmpty() )
        {
            districtView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        this.listAll();
        int districtID = this.chooseDistrict();
        if ( districtID == 0 ) return;

        District tmp = getDistrictRepository().getDistrictByID( districtID );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        districtView.println( "\n" + tmp.getName() + ":\n" );
        //municipalityController.listAll( tmp );

        districtView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public void saveDistricts () throws SQLException
    {
        this.controllerGRASP.saveDistricts();
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return this.controllerGRASP.isPresentMunicipalityInDistrict( municipalityToCheck );
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        this.controllerGRASP.addMunicipality( municipalityToAdd );
    }

    public void resetFields()
    {
        insertDistrictView.setTextNameDistrict("");
        insertDistrictView.getTextFiledNameDistrict().setEnabled(true);
        insertDistrictView.getAddDistrictAddButton().setVisible(true);
        insertDistrictView.getScrollPane().setVisible(false);
        insertDistrictView.getTxtMunicipalities().setVisible(false);
        insertDistrictView.getlblMunicipality().setVisible(false);
        insertDistrictView.getTextFiledMunicipality().setVisible(false);
        insertDistrictView.getlblErrorMunicipality().setVisible(false);
        insertDistrictView.getAddmunicipalityButton().setVisible(false);
        insertDistrictView.setTextMunicipalities("");
        insertDistrictView.setTxtMunicipality("");
        insertDistrictView.setLblErrorMunicipality("");
        insertDistrictView.setLblErrorNameDistrict("");
        insertDistrictView.revalidate();
        insertDistrictView.repaint();
    }

     public void createDistrictButton(String districtName) {
        JButton button = new JButton(districtName);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    districtInfoView.setLblDistrictName(districtName);
                    districtInfoView.setTextAreaMuniciplaities(getAllMunicipalityFromDistrict(getDistrictRepository().getDistrictByName(districtName)));
                } catch (SQLException e1) 
                {
                    e1.printStackTrace();
                }
            }
        });

        districtInfoView.addButton(button);
        districtInfoView.getmenuBar().add(button);
        button.doClick();
    }

}
