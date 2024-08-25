package view;

import model.util.Constants;

public class SubjectView extends View {
    
    public String enterPassword ()
    {
        return new String( super.getConsole().readPassword( Constants.ENTER_PASSWORD ) );
    }

    public String enterNewPassword ()
    {
        return new String( super.getConsole().readPassword( Constants.ENTER_NEW_PASSWORD ) );
    }

}
