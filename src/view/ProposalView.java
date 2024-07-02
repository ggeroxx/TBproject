package view;

import model.*;
import util.*;

public class ProposalView extends View {
    
    public void printState ( Proposal proposal, String COLOR )
    {
        super.println( " : " + COLOR + Constants.BOLD + proposal.getState() + Constants.RESET + "\n" );
    }

}
