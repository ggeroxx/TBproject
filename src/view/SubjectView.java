package view;

import util.*;

public class SubjectView extends View {
    
    public String enterPassword ()
    {
        return new String( super.console.readPassword( Constants.ENTER_PASSWORD ) );
    }

    public String enterNewPassword ()
    {
        return new String( super.console.readPassword( Constants.ENTER_NEW_PASSWORD ) );
    }

}
