package view;

import model.util.Constants;

public class MainView extends View {

    public int viewMainMenu () throws Exception 
    {
        return super.enterInt( Constants.MAIN_MENU );
    }

}
