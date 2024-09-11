package controller.MVCController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import controller.GRASPController.ProposalGRASPController;
import model.Category;
import model.Proposal;
import model.User;
import model.util.Constants;
import repository.ProposalRepository;
import view.ProposalView;

public class ProposalController extends Controller {
    
    private ProposalView proposalView;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;
    private ProposalGRASPController controllerGRASP;

    public ProposalController ( ProposalView proposalView, CategoryController categoryController, ConversionFactorsController conversionFactorsController, ProposalGRASPController controllerGRASP)
    {
        super( proposalView );
        this.proposalView = proposalView;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
        this.controllerGRASP = controllerGRASP;
    }

    public ProposalRepository getProposalRepository () 
    {
        return this.controllerGRASP.getProposalRepository();
    }

    public void listProposals ( List<Proposal> proposals )
    {
        for( Proposal proposal : proposals )
        {
            String COLOR = proposal.getState().equals( "open" ) ? Constants.GREEN : proposal.getState().equals( "close" ) ? Constants.RED : Constants.YELLOW;
            //this.printProposal( proposal );
            proposalView.printState( proposal, COLOR );
        }
    }

    public void listProposalsWithID ( List<Proposal> proposals )
    {
        for( Proposal proposal : proposals )
        {
            String COLOR = proposal.getState().equals( "open" ) ? Constants.GREEN : proposal.getState().equals( "close" ) ? Constants.RED : Constants.YELLOW;
            proposalView.println( proposal.getID() + "." + super.padRight( proposal.getID() + ".", 5) + "requested:" + super.padRight( "requested:" ,15 ) + "[ " + proposal.getRequestedCategory().getName() + super.padRight( proposal.getRequestedCategory().getName(), 50 ) + ", " + proposal.getRequestedHours() + " hours ]" );
            proposalView.print( super.padRight( "", 5 ) + "offered:" + super.padRight( "offered:" ,15 ) + "[ " + proposal.getOfferedCategory().getName() + super.padRight( proposal.getOfferedCategory().getName(), 50 ) + ", " + proposal.getOfferedHours() + " hours ] " );
            proposalView.printState( proposal, COLOR );
        }
    }

    public void verifyProposal ( Proposal inserted, Proposal toVerify, List<Proposal> toCloses ) throws SQLException
    {
        this.controllerGRASP.verifyProposal(inserted, toVerify, toCloses);
    }

    private int enterProposalID ( List<Proposal> openProposalsByUser, List<Integer> IDs )
    {
        return super.readInt( Constants.ENTER_PROPOSAL_ID, Constants.NOT_EXIST_MESSAGE, ( input ) -> !IDs.contains( (Integer) input ) );
    }

    public void retireProposal ( UserController userController ) throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        proposalView.print( Constants.PROPOSAL_LIST );

        List<Proposal> openProposalsByUser = this.getProposalRepository().getAllOpenProposalByUser( userController.getUser() );

        if ( openProposalsByUser.isEmpty() )
        {
            proposalView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        List<Integer> IDs = new ArrayList<Integer>();
        for( Proposal toAdd : openProposalsByUser ) IDs.add( toAdd.getID() );

        this.listProposalsWithID( openProposalsByUser );
        int proposalID = this.enterProposalID( openProposalsByUser, IDs );

        userController.retireProposal( openProposalsByUser.get( IDs.lastIndexOf( proposalID ) ) );
        proposalView.println( Constants.OPERATION_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }

    public void listProposalsByUser ( User user ) throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        proposalView.print( Constants.PROPOSAL_LIST );

        List<Proposal> proposalsByUser = getProposalRepository().getAllProposalsByUser( user );

        this.listProposals( proposalsByUser );

        proposalView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    /*public void listProposalsOfLeaf () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        proposalView.print( Constants.LEAF_CATEGORY_LIST );

        if ( categoryController.getCategoryRepository().getAllLeaf().isEmpty() )
        {
            proposalView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        categoryController.listAllLeafs();
        int leafID = categoryController.enterLeafID();
        if ( leafID == 0 ) return;

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        proposalView.print( Constants.PROPOSAL_LIST );
        this.listProposals( getProposalRepository().getAllProposalsByLeaf( categoryController.getCategoryRepository().getCategoryByID( leafID ) ) );

        proposalView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public void proposeProposal ( UserController userController ) throws SQLException
    {
        if ( categoryController.getCategoryRepository().getAllLeaf().isEmpty() || categoryController.getCategoryRepository().getAllLeaf().size() == 1 )
        {
            super.clearConsole( Constants.TIME_SWITCH_MENU );
            proposalView.println( Constants.PROPOSE_PROPOSAL_SCREEN + Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        super.clearConsole( Constants.TIME_SWITCH_MENU );

        Category requestedCategory = categoryController.enterRequestedCategory();

        super.clearConsole( Constants.TIME_SWITCH_MENU );

        Category offeredCategory = categoryController.enterOfferedCategory( requestedCategory );

        super.clearConsole( Constants.TIME_SWITCH_MENU );

        int requestedHours = categoryController.enterRequestedHours( requestedCategory );
        
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        proposalView.print( Constants.PROPOSE_PROPOSAL_SCREEN );

        int offeredHours = (int) Math.round( conversionFactorsController.getConversionFactorValue(requestedCategory, offeredCategory) * requestedHours );
        Proposal newProposal = new Proposal( null, requestedCategory, offeredCategory, requestedHours, offeredHours, userController.getUser(), "open" );
        this.printProposalWithCyanHours( newProposal );

        String saveOrNot = super.readString( Constants.CONFIRM_PROPOSAL, Constants.NOT_EXIST_MESSAGE, ( input ) -> !input.equals( Constants.NO_MESSAGE ) && !input.equals( Constants.YES_MESSAGE ) );

        if ( saveOrNot.equals( Constants.YES_MESSAGE ) )
        {
            proposalView.print( Constants.PROPOSAL_SAVED );
            super.clearConsole( Constants.TIME_MESSAGE );
            userController.insertProposal( newProposal );
        }
        else
        {
            proposalView.print( Constants.PROPOSAL_NOT_SAVED );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
        }

        this.verifyProposal( newProposal, null, new ArrayList<Proposal>() );
    }

    public void printProposal ( Proposal proposal )
    {
        proposalView.println( "requested:" + super.padRight( "requested:" ,15 ) + "[ " + proposal.getRequestedCategory().getName() + super.padRight( proposal.getRequestedCategory().getName(), 50 ) + ", " + proposal.getRequestedHours() + " hours ]" );
        proposalView.print( "offered:" + super.padRight( "offered:" ,15 ) + "[ " + proposal.getOfferedCategory().getName() + super.padRight( proposal.getOfferedCategory().getName(), 50 ) + ", " + proposal.getOfferedHours() + " hours ] " );
    }

    public void printProposalWithCyanHours ( Proposal proposal )
    {
        proposalView.println( "requested:" + super.padRight( "requested:" ,15 ) + "[ " + proposal.getRequestedCategory().getName() + super.padRight( proposal.getRequestedCategory().getName(), 50 ) + ", " + proposal.getRequestedHours() + " hours ]" );
        proposalView.print( "offered:" + super.padRight( "offered:" ,15 ) + "[ " + proposal.getOfferedCategory().getName() + super.padRight( proposal.getOfferedCategory().getName(), 50 ) + ", " + Constants.CYAN + proposal.getOfferedHours() + " hours " + Constants.RESET + "] " );
    }*/

}
