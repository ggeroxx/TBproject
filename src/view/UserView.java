package view;

import model.util.Constants;

public class UserView extends SubjectView {
    
    public int viewUserMenu () throws Exception 
    {
        return super.enterInt( Constants.USER_MENU );
    }

}
