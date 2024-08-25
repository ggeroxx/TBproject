package view;

import model.util.Constants;

public class ConfiguratorView extends SubjectView {
    
    public int viewConfiguratorMenu () throws Exception 
    {
        return super.enterInt( Constants.CONFIGURATOR_MENU );
    }

}
