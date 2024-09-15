package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import model.Category;
import model.Proposal;
import model.User;
import model.util.Constants;
import service.ControlPatternService;
import service.ProposalService;
import view.ProposalOfCategoryView;
import view.ProposalOfUserView;
import view.ProposeProposalView;
import view.RetireProposalView;

public class ProposalController {
    
    private ProposalOfCategoryView proposalOfCategoryView;
    private ProposalOfUserView proposalOfUserView;
    private RetireProposalView retireProposalView;
    private ProposeProposalView proposeProposalView;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;
    private ProposalService proposalService;
    private UserController userController;
    private HashMap<JRadioButton, Proposal> radioButtonObjectMapForRetireProposal = new HashMap<>();
    private HashMap<JRadioButton, Category> radioButtonObjectMapRequestedCategory = new HashMap<>();
    private HashMap<JRadioButton, Category> radioButtonObjectMapOfferedCategory = new HashMap<>();
    private Category requestCategory;
    private Category offerCategory;
    private int requestedHours;
    private int offeredHours;

    public ProposalController ( ProposalOfCategoryView proposalOfCategoryView, ProposalOfUserView proposalOfUserView, RetireProposalView retireProposalView, ProposeProposalView proposeProposalView, CategoryController categoryController, ConversionFactorsController conversionFactorsController, ProposalService proposalService)
    {
        this.proposalOfCategoryView = proposalOfCategoryView;
        this.proposalOfUserView = proposalOfUserView;
        this.retireProposalView = retireProposalView;
        this.proposeProposalView = proposeProposalView;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
        this.proposalService = proposalService;

        this.retireProposalView.getRetireProposalButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    if (proposalService.getAllOpenProposalByUser(userController.getUser()).size() > 0 && retireProposalView.getSelectedRadioButton() != null) 
                    {
                        userController.retireProposal(radioButtonObjectMapForRetireProposal.get(retireProposalView.getSelectedRadioButton()) );
                        closeRetireProposalView();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });

        this.proposeProposalView.getRequestCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    if ( radioButtonObjectMapRequestedCategory.get( proposeProposalView.getSelectedRadioButton( true ) ) != null )
                    {
                        requestCategory = radioButtonObjectMapRequestedCategory.get( proposeProposalView.getSelectedRadioButton( true ) );
                        offeredCategory();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });

        this.proposeProposalView.getOfferCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if ( radioButtonObjectMapOfferedCategory.get( proposeProposalView.getSelectedRadioButton( false ) ) != null )
                {
                    try 
                    {
                        offerCategory = radioButtonObjectMapOfferedCategory.get( proposeProposalView.getSelectedRadioButton( false ) );
                        requestedHours = Integer.parseInt ( proposeProposalView.getTextValue() ) ;
                        confirmOfferCategory();
                    } 
                    catch (SQLException e1) 
                    {
                        e1.printStackTrace();
                    }
                    catch (NumberFormatException e1) 
                    {
                        proposeProposalView.setLblErrorValue("Invalid Value");
                    }
                }
            }

        });

        this.proposeProposalView.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    Proposal newProposal = new Proposal( null, requestCategory, offerCategory, requestedHours, offeredHours, userController.getUser(), "open" );
                    userController.insertProposal( newProposal );
                    startProposalOfUserView(userController.getUser());
                    closeProposeProposalView();
                } 
                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }

        });

        this.proposeProposalView.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeProposeProposalView();
            }

        });

        this.proposalOfCategoryView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeProposalOfCategoryView();
			}
		});

        this.proposalOfUserView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeProposalOfUserView();
			}
		});

        this.retireProposalView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeRetireProposalView();
			}
		});

        this.proposeProposalView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                System.exit(0);
			}
		});
    }

    public void verifyProposal ( Proposal inserted, Proposal toVerify, List<Proposal> toCloses ) throws SQLException
    {
        this.proposalService.verifyProposal(inserted, toVerify, toCloses);
    }

    public void startProposalOfCategoryView() throws SQLException
    {
        proposalOfCategoryView.init();

        for ( Category toPrint : categoryController.getAllSavedLeaf() ) 
        {
            addMenuItemForProposalCategoryView( toPrint, " " + toPrint.getID() + ". " + ControlPatternService.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + ControlPatternService.padRight( toPrint.getName() , 50 ) + "  [ " + categoryController.getRootByLeaf( toPrint ).getName() + " ]  " );
        }
        for ( Category toPrint : categoryController.getAllNotSavedLeaf() )
        {
            addMenuItemForProposalCategoryView( toPrint, " " + toPrint.getID() + ". " + ControlPatternService.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + ControlPatternService.padRight( toPrint.getName() , 50 ) + "  [ " + categoryController.getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );
        } 
    }

    public void closeProposalOfCategoryView ()
    {
        proposalOfCategoryView.resetFields();
        proposalOfCategoryView.dispose();
    }

    public void startProposalOfUserView (User user) throws SQLException
    {
        proposalOfUserView.init();
        for ( Proposal proposal : proposalService.getAllProposalsByUser( user ) )
        {
            proposalOfUserView.addlblProposal( proposal.getID() + "." + ControlPatternService.padRight( proposal.getID() + ".", 5) + "requested:" + ControlPatternService.padRight( "requested:" ,10 ) + "[ " + proposal.getRequestedCategory().getName() + ControlPatternService.padRight( proposal.getRequestedCategory().getName(), 40 ) + ", " + proposal.getRequestedHours() + ControlPatternService.padRight(Integer.toString(proposal.getRequestedHours()), 3) + " hours ]\n" + ControlPatternService.padRight( "", 5 ) + "offered:" + ControlPatternService.padRight( "offered:" ,10 ) + "[ " + proposal.getOfferedCategory().getName() + ControlPatternService.padRight( proposal.getOfferedCategory().getName(), 40 ) + ", " + proposal.getOfferedHours() + " hours ] " + " : " +  proposal.getState() + "\n");     
        }

    }

    public void closeProposalOfUserView ()
    {
        proposalOfUserView.resetFields();
        proposalOfUserView.dispose();
    }

    public void startRetireProposalView ( UserController userController) throws SQLException
    {
        retireProposalView.init();
        this.userController = userController;
        for ( Proposal proposal : proposalService.getAllOpenProposalByUser( userController.getUser() ) )
        {
           addRadioButtonRetireProposal( proposal );     
        }
    }

    public void closeRetireProposalView ()
    {
        retireProposalView.resetFields();
        retireProposalView.dispose();
    }

    public void startProposeProposalView ( UserController userController ) throws SQLException
    {
        this.proposeProposalView.init();
        this.userController = userController;
        for ( Category toPrint : categoryController.getAllSavedLeaf() ) addRadioButton( toPrint, true );
    }

    public void closeProposeProposalView ()
    {
        this.proposeProposalView.resetFields();
        this.proposeProposalView.dispose();
    }

    public void offeredCategory () throws SQLException
    {
        this.proposalOfCategoryView.resetFields();
        this.proposeProposalView.removeAllRadioButtons(true);
        this.proposeProposalView.getRequestCategoryButton().setVisible(false);
        this.proposeProposalView.getOfferCategoryButton().setVisible(true);
        this.proposeProposalView.getTextFieldValue().setVisible(true);
        this.proposeProposalView.getLblValue().setVisible(true);
        for ( Category toPrint : categoryController.getAllSavedLeaf() ) 
            if ( !toPrint.equals( requestCategory ) && !toPrint.getName().equals( requestCategory.getName() ) )
                addRadioButton( toPrint , false );

    }

    public void confirmOfferCategory () throws SQLException
    {
        this.proposeProposalView.resetFields();
        this.proposeProposalView.removeAllRadioButtons(false);
        this.proposeProposalView.getLblValue().setVisible(false);
        this.proposeProposalView.getOfferCategoryButton().setVisible(false);
        this.proposeProposalView.getConfirmButton().setVisible(true);
        this.proposeProposalView.getCancelButton().setVisible(true);
        this.proposeProposalView.getTextFieldValue().setVisible(false);

        proposeProposalView.setLblErrorValue("");
        Category requestedCategory = requestCategory;
        Category offeredCategory = offerCategory;
        offeredHours = (int) Math.round( conversionFactorsController.getConversionFactorValue(requestedCategory, offeredCategory) * requestedHours );
        proposeProposalView.addLblCategory("requested:" + ControlPatternService.padRight( "requested:" ,15 ) + "[ " + requestedCategory.getName() + ControlPatternService.padRight( requestedCategory.getName(), 50 ) + ", " + requestedHours+ " hours ]" );
        proposeProposalView.addLblCategory("offered:" + ControlPatternService.padRight( "offered:" ,15 ) + "[ " + offeredCategory.getName() + ControlPatternService.padRight( offeredCategory.getName(), 50 ) + ", " + offeredHours + " hours ] ");
    }

    private void addRadioButton( Category category, boolean requestedCategory ) throws SQLException 
    {
        
        JRadioButton radioButton = proposeProposalView.addRadioButton(  " " + category.getID() + ". " + ControlPatternService.padRight( Integer.toString( category.getID() ) , 3 ) + category.getName() + ControlPatternService.padRight( category.getName() , 50 ) + "  [ " + categoryController.getRootByLeaf( category ).getName() + " ]  ", requestedCategory );
        if(requestedCategory) radioButtonObjectMapRequestedCategory.put( radioButton, category );
        else radioButtonObjectMapOfferedCategory.put( radioButton, category );
    }

    public void addMenuItemForProposalCategoryView( Category category, String info)
    {
        JMenuItem categoryItem = proposalOfCategoryView.addMenuItem(info);
        categoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                proposalOfCategoryView.getPanel().removeAll();
                proposalOfCategoryView.setLblProposal(category.getName());
                try {
                    for ( Proposal proposal : proposalService.getAllProposalsByLeaf( categoryController.getCategoryByID( category.getID() ) ) )
                    {
                        proposalOfCategoryView.addlblProposal( proposal.getID() + "." + ControlPatternService.padRight( proposal.getID() + ".", 5) + "requested:" + ControlPatternService.padRight( "requested:" ,15 ) + "[ " + proposal.getRequestedCategory().getName() + ControlPatternService.padRight( proposal.getRequestedCategory().getName(), 50 ) + ", " + proposal.getRequestedHours() + ControlPatternService.padRight(Integer.toString(proposal.getRequestedHours()), 3) + " hours ]\n" + ControlPatternService.padRight( "", 5 ) + "offered:" + ControlPatternService.padRight( "offered:" ,15 ) + "[ " + proposal.getOfferedCategory().getName() + ControlPatternService.padRight( proposal.getOfferedCategory().getName(), 50 ) + ", " + proposal.getOfferedHours() + " hours ] ");     
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                
            }
        });
        
    }

    private void addRadioButtonRetireProposal( Proposal proposal ) throws SQLException 
    { 
        JRadioButton radioButton = retireProposalView.addRadioButton( proposal.getID() + "." + ControlPatternService.padRight( proposal.getID() + ".", 5) + "requested:" + ControlPatternService.padRight( "requested:" ,10 ) + "[ " + proposal.getRequestedCategory().getName() + ControlPatternService.padRight( proposal.getRequestedCategory().getName(), 40 ) + ", " + proposal.getRequestedHours() + ControlPatternService.padRight(Integer.toString(proposal.getRequestedHours()), 3) + " hours ]\n" + ControlPatternService.padRight( "", 5 ) + "offered:" + ControlPatternService.padRight( "offered:" ,10 ) + "[ " + proposal.getOfferedCategory().getName() + ControlPatternService.padRight( proposal.getOfferedCategory().getName(), 40 ) + ", " + proposal.getOfferedHours() + " hours ] " + " : " +  proposal.getState() + "\n" );
        radioButtonObjectMapForRetireProposal.put( radioButton, proposal );
    }

}
