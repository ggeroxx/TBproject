package view;

import model.Municipality;

public class MunicipalityView extends View {
    
    public void printMunicipality ( Municipality toPrint )
    {
        super.println( "  " + toPrint.getCAP() + " " + toPrint.getProvince() + " " + toPrint.getName() );
    }

}
