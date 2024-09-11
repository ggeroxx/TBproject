package controller.MVCController;

import java.sql.SQLException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Configurator;
import model.util.Constants;
import service.ConfiguratorService;
import service.SessionService;
import view.ConfiguratorMenuView;

public class ConfiguratorController {

    private ConfiguratorMenuView configuratorMenuView;
    private SessionService sessionService;
    private DistrictController districtController;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;
    private ProposalController proposalController;
    private ConfiguratorService configuratorService;

    public ConfiguratorController ( ConfiguratorMenuView configuratorMenuView, SessionService sessionService, DistrictController districtController, CategoryController categoryController, ConversionFactorsController conversionFactorsController, ProposalController proposalController, ConfiguratorService configuratorService )
    {
        this.configuratorMenuView = configuratorMenuView;
        this.sessionService = sessionService;
        this.districtController = districtController;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
        this.proposalController = proposalController;
        this.configuratorService = configuratorService;

        this.configuratorMenuView.getInsertNewDistrictButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                districtController.startInsertNewDistrictView(ConfiguratorController.this);
			}
		});

        this.configuratorMenuView.getInsertConversionFactorsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    conversionFactorsController.startInsertConversionFactorsView();
                } catch (SQLException e1) {
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
                } catch (SQLException e1) 
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
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});

        this.configuratorMenuView.getViewConversionFactorsOfCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    conversionFactorsController.startConversionFactorsOfCategoryView();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

		});

        this.configuratorMenuView.getInsertNewHierarchyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    categoryController.startInsertNewHierarchyView(ConfiguratorController.this);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

		});

        this.configuratorMenuView.getViewHierarchyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    categoryController.startHierarchyView();
                } catch (SQLException e1) {
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
                } catch (IllegalStateException e1) {
                    configuratorMenuView.getLblErrorSave().setForeground(Color.RED);
                    configuratorMenuView.setLblErrorSave(Constants.IMPOSSIBLE_SAVE_CF);
                    configuratorMenuView.viewMessageSave();
                }catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

		});

        this.configuratorMenuView.getViewProposalOfCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    proposalController.startProposalOfCategoryView();
                } catch (SQLException e1) {
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
                } catch (SQLException e1) {
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
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});
    }

    public void start () throws SQLException
    {
        configuratorMenuView.setUndecorated(true);
        configuratorMenuView.setVisible(true);
    }

    public void close() throws SQLException
    {
        sessionService.logout();
        conversionFactorsController.resetConversionFactors();
        configuratorMenuView.dispose();
    }

    public void setConfigurator ( Configurator configurator ) 
    {
        this.configuratorService.setConfigurator(configurator);
    }

    public void saveAll ( ) throws SQLException
    {
        this.configuratorService.saveAll();
    }

    public void changeCredentials ( String approvedUsername, String newPassword ) throws SQLException
    {
        this.configuratorService.changeCredentials(approvedUsername, newPassword);
    }

    public void createDistrict ( String districtName ) throws SQLException
    {
       this.configuratorService.createDistrict(districtName);
    }

    public void createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException
    {
        this.configuratorService.createCategory(name, field, description, isRoot, hierarchyID);
    }

    public Configurator getConfiguratorByUsername ( String username ) throws SQLException
    {
        return configuratorService.getConfiguratorByUsername( username );
    }

}
