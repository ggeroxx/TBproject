package Client.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;

import Client.ClientClasse;
import Client.util.Constants;
import Client.view.ProposalOfCategoryView;
import Client.view.ProposalOfUserView;
import Client.view.ProposeProposalView;
import Client.view.RetireProposalView;
import Request.SomeRequestProposal;

public class ProposalController {
    
    private ProposalOfCategoryView proposalOfCategoryView;
    private ProposalOfUserView proposalOfUserView;
    private RetireProposalView retireProposalView;
    private ProposeProposalView proposeProposalView;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;
    private UserController userController;
    private SessionController sessionController;
    private ControlPatternController controlPatternController;
    private HashMap<JRadioButton, Integer> radioButtonObjectMapForRetireProposal = new HashMap<>();
    private HashMap<JRadioButton, Integer> radioButtonObjectMapRequestedCategory = new HashMap<>();
    private HashMap<JRadioButton, Integer> radioButtonObjectMapOfferedCategory = new HashMap<>();
    private int requestCategoryID;
    private int offerCategoryID;
    private int requestedHours;
    private int offeredHours;

    private ClientClasse client;
    private SomeRequestProposal requestProposal;

    public ProposalController ( ProposalOfCategoryView proposalOfCategoryView, ProposalOfUserView proposalOfUserView, RetireProposalView retireProposalView, ProposeProposalView proposeProposalView, CategoryController categoryController, ConversionFactorsController conversionFactorsController, SessionController sessionController, ControlPatternController controlPatternController, ClientClasse client)
    {
        this.proposalOfCategoryView = proposalOfCategoryView;
        this.proposalOfUserView = proposalOfUserView;
        this.retireProposalView = retireProposalView;
        this.proposeProposalView = proposeProposalView;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
        this.sessionController = sessionController;
        this.controlPatternController = controlPatternController;
        this.client = client;

        this.retireProposalView.getRetireProposalButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    if (getNumberOfAllOpenProposalByUserUsername(userController.getUserUsername()) > 0 && retireProposalView.getSelectedRadioButton() != null) 
                    {
                        requestProposal = new SomeRequestProposal("RETIRE_PROPOSAL", radioButtonObjectMapForRetireProposal.get(retireProposalView.getSelectedRadioButton()), 0, 0, 0, 0, null, null);
                        client.sendRequest(requestProposal);
                        client.receiveResponse();
                        //userController.retireProposal(radioButtonObjectMapForRetireProposal.get(retireProposalView.getSelectedRadioButton()) );
                        closeRetireProposalView();
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

        this.proposeProposalView.getRequestCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    if ( radioButtonObjectMapRequestedCategory.get( proposeProposalView.getSelectedRadioButton( true ) ) != null )
                    {
                        requestCategoryID = radioButtonObjectMapRequestedCategory.get( proposeProposalView.getSelectedRadioButton( true ) );
                        offeredCategory();
                    }
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

        this.proposeProposalView.getOfferCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if ( radioButtonObjectMapOfferedCategory.get( proposeProposalView.getSelectedRadioButton( false ) ) != null )
                {
                    try 
                    {
                        offerCategoryID = radioButtonObjectMapOfferedCategory.get( proposeProposalView.getSelectedRadioButton( false ) );
                        requestedHours = Integer.parseInt ( proposeProposalView.getTextValue() ) ;
                        confirmOfferCategory();
                    } 
                    catch (NumberFormatException e1) 
                    {
                        proposeProposalView.setLblErrorValue("Invalid Value");
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
            }

        });

        this.proposeProposalView.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    requestProposal = new SomeRequestProposal("INSERT_PROPOSAL", offerCategoryID, requestCategoryID, offerCategoryID, requestedHours, offerCategoryID, userController.getUserUsername(), "open");
                    client.sendRequest(requestProposal);
                    client.receiveResponse();
                    /*Proposal newProposal = new Proposal( null, requestCategory, offerCategory, requestedHours, offeredHours, userController.getUser(), "open" );
                    userController.insertProposal( newProposal );*/
                    startProposalOfUserView(userController.getUserUsername());
                    closeProposeProposalView();
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
                try 
                {
                    sessionController.logout();
                    client.close();
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
			}
		});

        this.proposalOfUserView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeProposalOfUserView();
                try 
                {
                    client.close();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
			}
		});

        this.retireProposalView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeRetireProposalView();
                try 
                {
                    client.close();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
			}
		});

        this.proposeProposalView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                try
                {
                    client.close();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
			}
		});

        this.proposalOfCategoryView.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeProposalOfCategoryView();
            }
		});

        this.proposalOfUserView.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeProposalOfUserView();
            }
		});

        this.retireProposalView.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeRetireProposalView();
            }
		});

        this.proposeProposalView.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeProposeProposalView();
            }
		});
    }

    public void startProposalOfCategoryView() throws ClassNotFoundException, IOException
    {
        proposalOfCategoryView.init();

        for ( int toPrint : categoryController.getAllSavedLeafID() ) 
        {
            addMenuItemForProposalCategoryView( toPrint, " " + toPrint + ". " + controlPatternController.padRight( Integer.toString( toPrint ) , 3 ) + categoryController.getCategoryNameByID(toPrint) + controlPatternController.padRight( categoryController.getCategoryNameByID(toPrint) , 50 ) + "  [ " + categoryController.getRootNameByLeaf( toPrint ) + " ]  " );
        }
        for ( int toPrint : categoryController.getAllNotSavedLeafID() )
        {
            addMenuItemForProposalCategoryView( toPrint, " " + toPrint + ". " + controlPatternController.padRight( Integer.toString( toPrint) , 3 ) + categoryController.getCategoryNameByID(toPrint) + controlPatternController.padRight( categoryController.getCategoryNameByID(toPrint) , 50 ) + "  [ " + categoryController.getRootNameByLeaf( toPrint ) + " ]  " + Constants.NOT_SAVED );
        } 
    }

    public void closeProposalOfCategoryView ()
    {
        proposalOfCategoryView.resetFields();
        proposalOfCategoryView.dispose();
    }

    public void startProposalOfUserView (String userUsername) throws ClassNotFoundException, IOException
    {
        proposalOfUserView.init();

        for ( int proposal : getAllProposalsIDByUserUsername( userUsername ) )
        {
            proposalOfUserView.addlblProposal( proposal + "." + controlPatternController.padRight( proposal + ".", 5) + "requested:" + controlPatternController.padRight( "requested:" ,10 ) + "[ " + getRequestedCategoryNameByProposaID(proposal) + controlPatternController.padRight( getRequestedCategoryNameByProposaID(proposal), 40 ) + ", " + getRequestedHoursByProposaID(proposal) + controlPatternController.padRight(Integer.toString(getRequestedHoursByProposaID(proposal)), 3) + " hours ]\n" + controlPatternController.padRight( "", 5 ) + "offered:" + controlPatternController.padRight( "offered:" ,10 ) + "[ " + getOfferedCategoryNameByProposaID(proposal) + controlPatternController.padRight( getOfferedCategoryNameByProposaID(proposal), 40 ) + ", " + getOfferedHoursByProposaID(proposal) + " hours ] " + " : " +  getStateByProposaID(proposal)+ "\n");     
        }

    }

    public void closeProposalOfUserView ()
    {
        proposalOfUserView.resetFields();
        proposalOfUserView.dispose();
    }

    public void startRetireProposalView ( String userUsername, UserController userController) throws ClassNotFoundException, IOException
    {
        this.userController = userController;
        retireProposalView.init();
        for ( int proposal : getAllOpenProposalIDByUserUsername( userUsername) )
        {
           addRadioButtonRetireProposal( proposal );     
        }
    }

    public void closeRetireProposalView ()
    {
        retireProposalView.resetFields();
        retireProposalView.dispose();
    }

    public void startProposeProposalView ( UserController userController ) throws ClassNotFoundException, IOException
    {
        this.proposeProposalView.init();
        this.userController = userController;
        for ( int toPrint : categoryController.getAllSavedLeafID() ) addRadioButton( toPrint, true );
    }

    public void closeProposeProposalView ()
    {
        this.proposeProposalView.resetFields();
        this.proposeProposalView.dispose();
    }

    public void offeredCategory () throws ClassNotFoundException, IOException
    {
        this.proposalOfCategoryView.resetFields();
        this.proposeProposalView.removeAllRadioButtons(true);
        this.proposeProposalView.getRequestCategoryButton().setVisible(false);
        this.proposeProposalView.getOfferCategoryButton().setVisible(true);
        this.proposeProposalView.getTextFieldValue().setVisible(true);
        this.proposeProposalView.getLblValue().setVisible(true);
        for ( int toPrint : categoryController.getAllSavedLeafID() ) 
            if ( !(toPrint ==  requestCategoryID )  && !(categoryController.getCategoryNameByID(toPrint).equals( categoryController.getCategoryNameByID(requestCategoryID) )) )
                addRadioButton( toPrint , false );

    }

    public void confirmOfferCategory () throws ClassNotFoundException, IOException
    {
        this.proposeProposalView.resetFields();
        this.proposeProposalView.getBackButton().setVisible(false);
        this.proposeProposalView.removeAllRadioButtons(false);
        this.proposeProposalView.getLblValue().setVisible(false);
        this.proposeProposalView.getOfferCategoryButton().setVisible(false);
        this.proposeProposalView.getConfirmButton().setVisible(true);
        this.proposeProposalView.getCancelButton().setVisible(true);
        this.proposeProposalView.getTextFieldValue().setVisible(false);

        proposeProposalView.setLblErrorValue("");
        int requestedCategoryID = requestCategoryID;
        int offeredCategoryID = offerCategoryID;
        offeredHours = (int) Math.round( conversionFactorsController.getConversionFactorValue(requestedCategoryID, offeredCategoryID) * requestedHours );
        proposeProposalView.addLblCategory("requested:" + controlPatternController.padRight( "requested:" ,15 ) + "[ " + categoryController.getCategoryNameByID(requestedCategoryID) + controlPatternController.padRight( categoryController.getCategoryNameByID(requestedCategoryID), 50 ) + ", " + requestedHours+ " hours ]" );
        proposeProposalView.addLblCategory("offered:" + controlPatternController.padRight( "offered:" ,15 ) + "[ " + categoryController.getCategoryNameByID(offeredCategoryID) + controlPatternController.padRight( categoryController.getCategoryNameByID(offeredCategoryID), 50 ) + ", " + offeredHours + " hours ] ");
    }

    private void addRadioButton( int categoryID, boolean requestedCategory ) throws ClassNotFoundException, IOException 
    {
        
        JRadioButton radioButton = proposeProposalView.addRadioButton(  " " + categoryID + ". " + controlPatternController.padRight( Integer.toString( categoryID ) , 3 ) + categoryController.getCategoryNameByID(categoryID) + controlPatternController.padRight( categoryController.getCategoryNameByID(categoryID) , 50 ) + "  [ " + categoryController.getRootNameByLeaf( categoryID ) + " ]  ", requestedCategory );
        if(requestedCategory) radioButtonObjectMapRequestedCategory.put( radioButton, categoryID );
        else radioButtonObjectMapOfferedCategory.put( radioButton, categoryID );
    }

    public void addMenuItemForProposalCategoryView( int categoryID, String info)
    {
        JMenuItem categoryItem = proposalOfCategoryView.addMenuItem(info);
        categoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                proposalOfCategoryView.getPanel().removeAll();
                try {
                    proposalOfCategoryView.setLblProposal(categoryController.getCategoryNameByID(categoryID));
                    for ( int proposal : getAllProposalsIDByLeafID( categoryID ) )
                    {
                        proposalOfCategoryView.addlblProposal( proposal + "." + controlPatternController.padRight( proposal + ".", 5) + "requested:" + controlPatternController.padRight( "requested:" ,15 ) + "[ " + getRequestedCategoryNameByProposaID(proposal) + controlPatternController.padRight( getRequestedCategoryNameByProposaID(proposal), 50 ) + ", " + getRequestedHoursByProposaID(proposal) + controlPatternController.padRight(Integer.toString(getRequestedHoursByProposaID(proposal)), 3) + " hours ]\n" + controlPatternController.padRight( "", 5 ) + "offered:" + controlPatternController.padRight( "offered:" ,15 ) + "[ " + getOfferedCategoryNameByProposaID(proposal) + controlPatternController.padRight( getOfferedCategoryNameByProposaID(proposal), 50 ) + ", " + getOfferedHoursByProposaID(proposal) + " hours ] ");     
                    }
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

    private void addRadioButtonRetireProposal( int proposal ) throws ClassNotFoundException, IOException 
    { 
        JRadioButton radioButton = retireProposalView.addRadioButton( proposal + "." + controlPatternController.padRight( proposal + ".", 5) + "requested:" + controlPatternController.padRight( "requested:" ,10 ) + "[ " + getRequestedCategoryNameByProposaID(proposal) + controlPatternController.padRight( getRequestedCategoryNameByProposaID(proposal), 40 ) + ", " + getRequestedHoursByProposaID(proposal) + controlPatternController.padRight(Integer.toString(getRequestedHoursByProposaID(proposal)), 3) + " hours ]\n" + controlPatternController.padRight( "", 5 ) + "offered:" + controlPatternController.padRight( "offered:" ,10 ) + "[ " + getOfferedCategoryNameByProposaID(proposal) + controlPatternController.padRight( getOfferedCategoryNameByProposaID(proposal), 40 ) + ", " + getOfferedHoursByProposaID(proposal) + " hours ] " + " : " +  getStateByProposaID(proposal) + "\n" );
        radioButtonObjectMapForRetireProposal.put( radioButton, proposal );
    }

    public int getNumberOfAllOpenProposalByUserUsername (String userUsername) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_NUMBER_OF_ALL_OPEN_PROPOSAL_BY_USER_USERNAME", 0, 0, 0, 0, 0, userUsername, null);
        client.sendRequest(requestProposal);
        return (int) client.receiveResponse();
    }

    /*public void verifyProposal ( Proposal inserted, Proposal toVerify, List<Proposal> toCloses ) throws SQLException
    {
        this.proposalService.verifyProposal(inserted, toVerify, toCloses);
    }*/

    public int[] getAllProposalsIDByUserUsername(String userUsername) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_ALL_PROPOSALS_ID_BY_USER_USERNAME", 0, 0, 0, 0, 0, userUsername, null);
        client.sendRequest(requestProposal);
        return (int[]) client.receiveResponse();
    }

    public String getRequestedCategoryNameByProposaID (int proposalID) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_REQUEST_CATEGORY_NAME_BY_PROPOSAL_ID", proposalID, 0, 0, 0, 0, null, null);
        client.sendRequest(requestProposal);
        return (String) client.receiveResponse();
    }

    public String getOfferedCategoryNameByProposaID (int proposalID) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_OFFER_CATEGORY_NAME_BY_PROPOSAL_ID", proposalID, 0, 0, 0, 0, null, null);
        client.sendRequest(requestProposal);
        return (String) client.receiveResponse();
    }

    public int getRequestedHoursByProposaID (int proposalID) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_REQUEST_HOURS_BY_PROPOSAL_ID", proposalID, 0, 0, 0, 0, null, null);
        client.sendRequest(requestProposal);
        return (int) client.receiveResponse();
    }

    public int getOfferedHoursByProposaID (int proposalID) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_OFFER_HOURS_BY_PROPOSAL_ID", proposalID, 0, 0, 0, 0, null, null);
        client.sendRequest(requestProposal);
        return (int) client.receiveResponse();
    }

    public String getStateByProposaID (int proposalID) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_STATE_BY_PROPOSAL_ID", proposalID, 0, 0, 0, 0, null, null);
        client.sendRequest(requestProposal);
        return (String) client.receiveResponse();
    }

    public int[] getAllOpenProposalIDByUserUsername ( String userUsername) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_ALL_OPEN_PROPOSALS_BY_USER_USERNAME", 0, 0, 0, 0, 0, userUsername, null);
        client.sendRequest(requestProposal);
        return (int[]) client.receiveResponse();
    }

    public int[] getAllProposalsIDByLeafID ( int leafID) throws ClassNotFoundException, IOException
    {
        requestProposal = new SomeRequestProposal("GET_ALL_PROPOSALS_ID_BY_LEAF_ID", leafID, 0, 0, 0, 0, null, null);
        client.sendRequest(requestProposal);
        return (int[]) client.receiveResponse();
    }


}
