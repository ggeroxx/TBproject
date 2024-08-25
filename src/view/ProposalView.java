package view;

import model.Proposal;
import model.util.Constants;

public class ProposalView extends View {
    
    public void printState ( Proposal proposal, String COLOR )
    {
        super.println( " : " + COLOR + Constants.BOLD + proposal.getState() + Constants.RESET + "\n" );
    }

}
