package controller.MVCController;

import controller.ClientServer.Client;
import controller.ClientServer.SomeRequestConfigurator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import model.util.Constants;
import view.ConfiguratorMenuView;

public class ConfiguratorController {

    private ConfiguratorMenuView configuratorMenuView;
    private SessionController sessionController;
    private DistrictController districtController;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;
    private ProposalController proposalController;

    private Client client;
    private SomeRequestConfigurator requestConfigurator;


    public ConfiguratorController ( ConfiguratorMenuView configuratorMenuView, SessionController sessionController, DistrictController districtController, CategoryController categoryController, ConversionFactorsController conversionFactorsController, ProposalController proposalController, Client client )
    {
        this.configuratorMenuView = configuratorMenuView;
        this.sessionController = sessionController;
        this.districtController = districtController;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
        this.proposalController = proposalController;
        this.client = client;

        this.configuratorMenuView.getInsertNewDistrictButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                districtController.startInsertNewDistrictView();
			}
		});

        this.configuratorMenuView.getInsertConversionFactorsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    conversionFactorsController.startInsertConversionFactorsView();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
			}
		});

        this.configuratorMenuView.getViewDistrictButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    districtController.startDistrictInfoView();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
			}
		});

        this.configuratorMenuView.getViewAllConversionFactorsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    conversionFactorsController.startAllConversionFactorsView();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
			}
		});

        this.configuratorMenuView.getViewConversionFactorsOfCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try 
                {
                    conversionFactorsController.startConversionFactorsOfCategoryView();
                }  
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
            }

		});

        this.configuratorMenuView.getInsertNewHierarchyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                categoryController.startInsertNewHierarchyView(ConfiguratorController.this);
            }

		});

        this.configuratorMenuView.getViewHierarchyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    categoryController.startHierarchyView();
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
            }

		});

        this.configuratorMenuView.getSaveAllButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    saveAll();
                    configuratorMenuView.getLblErrorSave().setForeground(Color.GREEN);
                    configuratorMenuView.setLblErrorSave(Constants.SAVE_COMPLETED);
                    configuratorMenuView.viewMessageSave();
                } 
                catch (IllegalStateException e1) {
                    configuratorMenuView.getLblErrorSave().setForeground(Color.RED);
                    configuratorMenuView.setLblErrorSave(Constants.IMPOSSIBLE_SAVE_CF);
                    configuratorMenuView.viewMessageSave();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                }
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
            }

		});

        this.configuratorMenuView.getViewProposalOfCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    proposalController.startProposalOfCategoryView();
                }  
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
            }

		});

        this.configuratorMenuView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try 
                {
                    close();
                    System.exit(0);
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
			}
		});

        this.configuratorMenuView.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    close();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
			}
		});
    }

    public void start () 
    {
        configuratorMenuView.setUndecorated(true);
        configuratorMenuView.setVisible(true);
    }

    public void close() throws IOException, ClassNotFoundException
    {
        sessionController.logout();
        conversionFactorsController.resetConversionFactors();
        configuratorMenuView.dispose();
    }

    /*public Configurator getConfigurator()
    {
        return this.configuratorService.getConfigurator();
    }*/

    public void setConfigurator ( String username ) throws IOException, ClassNotFoundException 
    {
        requestConfigurator = new SomeRequestConfigurator("SET_CONFIGURATOR", username, null);
        client.sendRequest(requestConfigurator);
        client.receiveResponse();
        //this.configuratorService.setConfigurator(configurator);
    }

    public void saveAll ( ) throws IOException, ClassNotFoundException
    {
        requestConfigurator = new SomeRequestConfigurator("SAVE_ALL", null, null);
        client.sendRequest(requestConfigurator);
        client.receiveResponse();
        //this.configuratorService.saveAll();
    }

    public void changeCredentials ( String approvedUsername, String newPassword ) throws IOException, ClassNotFoundException
    {
        requestConfigurator = new SomeRequestConfigurator("CHANGE_CREDENTIALS", approvedUsername, newPassword);
        client.sendRequest(requestConfigurator);
        client.receiveResponse();
        //this.configuratorService.changeCredentials(approvedUsername, newPassword);
    }

    public boolean getFirstAccess () throws IOException, ClassNotFoundException
    {
        requestConfigurator = new SomeRequestConfigurator("GET_FIRST_ACCESS", null, null);
        client.sendRequest(requestConfigurator);
        return (boolean) client.receiveResponse();
    }

    /*public void createDistrict ( String districtName ) throws SQLException
    {
       this.configuratorService.createDistrict(districtName);
    }

    public void createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException
    {
        this.configuratorService.createCategory(name, field, description, isRoot, hierarchyID);
    }*/

    /*public Configurator getConfiguratorByUsername ( String username ) throws IOException, ClassNotFoundException
    {
        requestConfigurator = new SomeRequestConfigurator("GET_CONFIGURATOR_BY_USERNAME", username, null);
        client.sendRequest(requestConfigurator);
        return (Configurator) client.receiveResponse();
        
    }*/

}
