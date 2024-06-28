package view;

import model.*;
import util.*;

public class DistrictView extends View {

    public void printDistrict ( District toPrint )
    {
        super.print( Constants.BOLD + Constants.ITALIC + toPrint.getID() + ". " + toPrint.getName() + ":\n" + Constants.RESET );
    }    

}
