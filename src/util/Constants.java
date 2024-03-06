package util;

public class Constants {
    
    public static final String MAIN_MENU = "\n" +
                                            "--------------\n" +
                                            "TIME BANK\n" +
                                            "--------------\n" +
                                            " 1.  login\n" +
                                            " 2.  exit\n" +
                                            "--------------\n" +
                                            "Enter yout choice (1/2) --> ";

    public static final String LOGIN_SCREEN = "\n" +
                                              "-----------\n" +
                                              "LOGIN\n" +
                                              "-----------\n";

    public static final String REGISTRATION_SCREEN = "\n" +
                                                     "--------------\n" +
                                                     "REGISTRATION\n" +
                                                     "--------------\n";

    public static final String CONFIGURATOR_MENU = "\n" +
                                                   "----------------------\n" +
                                                   "\tOPTIONS\n" +
                                                   "----------------------\n" +
                                                   " 1.  insert new district\n" +
                                                   " 2.  insert new hierarchy\n" +
                                                   " 5.  save all data\n" +
                                                   " 6.  view district\n" + 
                                                   " 7.  view hierarchy\n" +
                                                   " 9.  logout\n" +
                                                   "----------------------\n" +
                                                   "Enter your choice [1-9] --> ";

    public static final String DISTRICT_LIST = "\n" +
                                               "---------------\n" + 
                                               "DISTRICT LIST\n" + 
                                               "---------------\n";

    public static final String HIERARCHY_LIST = "\n" +
                                                "---------------\n" + 
                                                "HIERARCHY LIST\n" + 
                                                "---------------\n";

    public static final String HIERARCHY_SCREEN = "\n" +
                                                   "--------------------\n" +
                                                   "HIERARCHY INSERTION\n" +
                                                   "--------------------\n";

    public static final String ENTER_USERNAME = "enter username: ";
    public static final String ENTER_NEW_USERNAME = "Enter new username (min 3 max 20 characters): ";
    public static final String ENTER_PASSWORD = "enter password: ";
    public static final String ENTER_NEW_PASSWORD = "Enter new password (alphanumeric, min 8 max 25 characters): ";
    public static final String ENTER_DISTRICT_NAME = "\nenter district name: ";
    public static final String ENTER_MUNICIPALITY = "\nEnter municipality (' for accent): ";
    public static final String END_ADD_MESSAGE = "end: (y/n) --> ";
    public static final String ENTER_TO_EXIT = "Press enter to exit ↵  ";
    public static final String ENTER_DISTRICT_ID = "Enter district ID --> ";
    public static final String ENTER_HIERARCHY_ID = "Enter hierarchy ID --> ";
    public static final String ENTER_CATEGORY_NAME = "\nenter category name: ";
    public static final String ENTER_FIELD = "enter field: ";
    public static final String ENTER_DESCRIPTION = "enter description (enter to skip): ";
    public static final String LEAF_CATEGORY_MESSAGE = "leaf category? (y/n) --> ";
    public static final String ENTER_DAD_MESSAGE = " - enter dad --> ";
    public static final String ENTER_FIELD_TYPE = "enter field type: ";

    public static final String BYE_BYE_MESSAGE = "\nBye bye ...\n\n";
    public static final String ADDED_SUCCESFULL_MESSAGE = "\n --- Added succesfull ✓ --- \n";
    public static final String OPERATION_COMPLETED = "\n --- Operation completed ✓ --- \n";
    public static final String NOT_SAVED_STRING = "  -->  (not saved)\n";
    public static final String SAVE_COMPLETED = "\n --- saved COMPLETED ✓ ---\n";
    public static final String LOG_OUT = "\nLog out ...\n\n";
    public static final String CATEGORY_LIST = " - categories:\n";
    public static final String LINE = "--------------------";

    public static final String LOGIN_ERROR = "\n --- ATTENTION, invalid username a/o password! Try again! ---";
    public static final String USERNAME_NOT_AVAILABLE = "\n --- username NOT available ! --- \n";
    public static final String ERROR_PATTERN_USERNAME = "\n --- ATTENTION, parameters not respected! min 3 max 20 characters --- \n";
    public static final String ERROR_PATTERN_PASSWORD = "\n --- ATTENTION, parameters not respected! min 8 max 25 characters, at least one digit and one character required --- \n";
    public static final String ERROR_PATTERN_NAME = "\n --- ATTENTION, parameters not respected! min 1 max 50 characters --- \n";
    public static final String ERROR_PATTERN_FIELD = "\n --- ATTENTION, parameters not respected! min 1 max 25 characters --- \n";
    public static final String ERROR_PATTERN_DESCRIPTION = "\n --- ATTENTION, parameters not respected! min 1 max 100 characters --- \n";
    public static final String INVALID_OPTION = "\n --- Invalid option ! --- \n";
    public static final String DISTRICT_NAME_ALREADY_PRESENT = "\n --- District name already present --- \n";
    public static final String MUNICIPALITY_NAME_ALREADY_PRESENT = "\n --- Municipality already present ---";
    public static final String NOT_EXIST_MESSAGE = "\n --- NOT exist ! --- ";
    public static final String ROOT_CATEGORY_ALREADY_PRESENT = "\n --- Root category already present --- \n";
    public static final String INTERNAL_CATEGORY_ALREADY_PRESENT = "\n --- Category already present --- \n";

    public static final String SQL_EXCEPTION_MESSAGE = "Database error: ";
    public static final String GENERIC_EXCEPTION_MESSAGE = "Generic error:";

    public static final int TIME_ZERO = 0;
    public static final int TIME_SWITCH_MENU = 100;
    public static final int TIME_ERROR_MESSAGE = 2000;
    public static final int TIME_MESSAGE = 1500;
    public static final int TIME_LOGOUT = 1000;

}
