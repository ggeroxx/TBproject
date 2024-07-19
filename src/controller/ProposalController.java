package controller;

import java.sql.SQLException;
import java.util.*;
import model.*;
import util.*;
import view.*;

public class ProposalController extends Controller {
    
    private ProposalView proposalView;
    private ProposalJDBC proposalJDBC;
    private CategoryController categoryController;
    private ConversionFactorsController conversionFactorsController;

    public ProposalController ( ProposalView proposalView, ProposalJDBC proposalJDBC, CategoryController categoryController, ConversionFactorsController conversionFactorsController )
    {
        super( proposalView );
        this.proposalView = proposalView;
        this.proposalJDBC = proposalJDBC;
        this.categoryController = categoryController;
        this.conversionFactorsController = conversionFactorsController;
    }

    public ProposalJDBC getProposalJDBC () 
    {
        return this.proposalJDBC;
    }

    public void listProposals ( List<Proposal> proposals )
    {
        for( Proposal proposal : proposals )
        {
            String COLOR = proposal.getState().equals( "open" ) ? Constants.GREEN : proposal.getState().equals( "close" ) ? Constants.RED : Constants.YELLOW;
            this.printProposal( proposal );
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
        List<Proposal> compatibleProposals = proposalJDBC.getAllCompatibleProposals( toVerify != null ? toVerify : inserted );

        if ( compatibleProposals.isEmpty() ) return;

        if ( toVerify != null && inserted.getOfferedCategory().getID() == toVerify.getOfferedCategory().getID() && inserted.getRequestedCategory().getID() == toVerify.getRequestedCategory().getID() )
        {
            closeProposals( toCloses );
            return;
        }

        toCloses.add( compatibleProposals.get( 0 ) );
        verifyProposal( inserted, compatibleProposals.get( 0 ), toCloses );
    }

    private void closeProposals ( List<Proposal> toCloses ) throws SQLException
    {
        for ( Proposal toClose : toCloses )
            proposalJDBC.closeProposal( toClose );
    }

    private int enterProposalID ( List<Proposal> openProposalsByUser, List<Integer> IDs )
    {
        return super.readInt( Constants.ENTER_PROPOSAL_ID, Constants.NOT_EXIST_MESSAGE, ( input ) -> !IDs.contains( (Integer) input ) );
    }

    public void retireProposal ( UserController userController ) throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        proposalView.print( Constants.PROPOSAL_LIST );

        List<Proposal> openProposalsByUser = this.proposalJDBC.getAllOpenProposalByUser( userController.getUser() );

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

        List<Proposal> proposalsByUser = this.proposalJDBC.getAllProposalsByUser( user );

        this.listProposals( proposalsByUser );

        proposalView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public void listProposalsOfLeaf () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        proposalView.print( Constants.LEAF_CATEGORY_LIST );

        if ( categoryController.getCategoryJDBC().getAllLeaf().isEmpty() )
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
        this.listProposals( proposalJDBC.getAllProposalsByLeaf( categoryController.getCategoryJDBC().getCategoryByID( leafID ) ) );

        proposalView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public void proposeProposal ( UserController userController ) throws SQLException
    {
        if ( categoryController.getCategoryJDBC().getAllLeaf().isEmpty() || categoryController.getCategoryJDBC().getAllLeaf().size() == 1 )
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

        int offeredHours = (int) Math.round( conversionFactorsController.getConversionFactorsJDBC().getConversionFactor( requestedCategory, offeredCategory ).getValue() * requestedHours );
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
    }

}
