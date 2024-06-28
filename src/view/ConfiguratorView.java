package view;

import util.*;

public class ConfiguratorView extends SubjectView {
    
    public int viewConfiguratorMenu () throws Exception 
    {
        return super.enterInt( Constants.CONFIGURATOR_MENU );
    }

}
