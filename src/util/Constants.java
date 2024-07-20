package util;

public class Constants {

    // ---------------------------------------- text formatting ----------------------------------------

    public static final String BLACK = "\u001B[30m";

    public static final String RED = "\u001B[31m";

    public static final String GREEN = "\u001B[32m";

    public static final String YELLOW = "\u001B[33m";

    public static final String BLUE = "\u001B[34m";

    public static final String MAGENTA = "\u001B[35m";

    public static final String CYAN = "\u001B[36m";

    public static final String WHITE = "\u001B[37m";

    public static final String GREY = "\u001B[38;2;94;94;94m";

    public static final String PURPLE = "\u001B[38;2;143;0;255m";

    public static final String BOLD = "\u001B[1m";

    public static final String ITALIC = "\033[1m\033[3m";

    public static final String UNDERLINE = "\u001B[4m";

    public static final String RESET = "\u001B[0m";

    // ---------------------------------------- conversion factors constants ----------------------------------------

    public static final double MIN_VALUES_CONVERSION_FACTOR = 0.5;

    public static final double MAX_VALUES_CONVERSION_FACTOR = 2.0;

    public static final double NEUTRAL_VALUE = 1.0;

    // ---------------------------------------- time constants ----------------------------------------

    public static final int TIME_ZERO = 0;

    public static final int TIME_SWITCH_MENU = 100;

    public static final int TIME_ERROR_MESSAGE = 2000;

    public static final int TIME_MESSAGE = 1500;

    public static final int TIME_LOGOUT = 1000;

    // ---------------------------------------- MainView ----------------------------------------
    
    public static final String MAIN_MENU = "\n" +
                                            "─────────────────────────────\n" + 
                                            YELLOW + BOLD + "\t  TIME BANK\n" + RESET +
                                            "─────────────────────────────\n" +
                                            " 1.  login\n" +
                                            " 2.  sign in\n\n" +
                                            " 3.  exit\n" +
                                            "─────────────────────────────\n" +
                                            "Enter yout choice [1-3] --> ";

    // ---------------------------------------- MainController ----------------------------------------

    public static final String LOGIN_SCREEN = RED + BOLD + "\n\nLOGIN\n\n" + RESET;

    public static final String REGISTRATION_SCREEN = RED + BOLD + "\n\nREGISTRATION\n\n" + RESET;

    public static final String ENTER_USERNAME = "enter username: ";

    public static final String BYE_BYE_MESSAGE = YELLOW + "\nBye bye ...\n\n" + RESET;

    public static final String LOGIN_ERROR = RED + "\n --- " + BOLD + "ATTENTION" + RESET + RED + ", invalid username a/o password! Try again! ---" + RESET;

    public static final String DENIED_ACCESS = RED + BOLD + "\n --- DENIED ACCESS, %s is working... --- \n" + RESET;

    // ---------------------------------------- SubjectView ----------------------------------------

    public static final String ENTER_PASSWORD = "enter password: ";

    public static final String ENTER_NEW_PASSWORD = "Enter new password (alphanumeric, min 8 max 25 characters): ";

    // ---------------------------------------- SubjectController ----------------------------------------

    public static final String ENTER_NEW_USERNAME = "Enter new username (min 3 max 20 characters): ";

    public static final String ENTER_MAIL = "Enter mail: ";

    public static final String USERNAME_NOT_AVAILABLE = RED + "\n --- username NOT available ! --- \n" + RESET;

    public static final String ERROR_PATTERN_USERNAME = RED + "\n --- " + BOLD + "ATTENTION" + RESET + RED + ", parameters not respected! min 3 max 20 characters --- \n" + RESET;

    public static final String ERROR_PATTERN_PASSWORD = RED + "\n --- " + BOLD + "ATTENTION" + RESET + RED + ", parameters not respected! min 8 max 25 characters, at least one digit and one character required --- \n" + RESET;

    public static final String ERROR_PATTERN_MAIL = RED + "\n --- " + BOLD + "ATTENTION" + RESET + RED + ", parameters not respected! --- \n\n" + RESET;

    // ---------------------------------------- DistrictController ----------------------------------------

    public static final String ENTER_DISTRICT_OR_EXIT = "\nEnter district ID (enter '0' to exit) --> ";

    public static final String DISTRICTS_LIST = PURPLE + BOLD + "\n\nDISTRICTS LIST\n\n" + RESET;

    public static final String ADDED_SUCCESFULL_MESSAGE = GREEN + "\n --- Added succesfull ✓ --- \n" + RESET;

    public static final String DISTRICT_NAME_ALREADY_PRESENT = RED + "\n --- District name already present --- \n" + RESET;

    public static final String MUNICIPALITY_NAME_ALREADY_PRESENT = RED + "\n --- Municipality already present ---" + RESET;

    public static final String ENTER_DISTRICT_NAME = "\nenter district name: ";

    // ---------------------------------------- UserView ----------------------------------------

    public static final String USER_MENU = "\n" +
                                           "─────────────────────────────\n" +
                                           BLUE + BOLD + "\t   OPTIONS\n" + RESET +
                                           "─────────────────────────────\n" +
                                           " 1.  navigate hierarchies\n" +
                                           " 2.  propose proposal\n" +
                                           " 3.  retire proposal\n" +
                                           " 4.  view all proposals\n\n" +
                                           " 5.  logout\n" +
                                           "─────────────────────────────\n" +
                                           "Enter your choice [1-5] --> ";

    // ---------------------------------------- CategoryController ----------------------------------------

    public static final String CATEGORY_INFO = PURPLE + BOLD + "\n\nCATEGORY INFO\n\n" + RESET;

    public static final String ENTER_REQUESTED_CATEGORY_ID = "Enter ID of requested category --> ";

    public static final String ENTER_OFFERED_CATEGORY_ID = "Enter ID of offered category --> ";

    public static final String ENTER_REQUESTED_HOURS = "\nEnter number of requested hours of ";

    public static final String ENTER_VALUE_OF_FIELD_MESSAGE = "Enter " + YELLOW + "value of field" + RESET + " ('<' to go back) --> ";

    public static final String NOT_EXIST_FIELD_MESSAGE = RED + BOLD + " --- NOT exist field ! --- \n" + RESET;

    public static final String ERROR_HOUR = RED + BOLD + "\n --- not integer ot not greater than zero --- \n" + RESET;

    public static final String HIERARCHY_SCREEN = CYAN + BOLD + "\n\nHIERARCHY INSERTION\n\n" + RESET;

    public static final String ENTER_CATEGORY_ID = "\nEnter category ID --> ";

    public static final String ENTER_CATEGORY_ID_WITH_EXIT = "\nEnter category ID (enter '0' to exit) --> ";

    public static final String ENTER_CATEGORY_NAME = "enter category name: ";

    public static final String ENTER_FIELD = "\nenter field: ";

    public static final String ENTER_DESCRIPTION = "\nenter description (enter to skip): ";

    public static final String LEAF_CATEGORY_MESSAGE = "\nleaf category? (y/n) --> ";

    public static final String ENTER_DAD_MESSAGE = "\n enter parent ID --> ";

    public static final String ENTER_FIELD_TYPE = "\nenter field type: ";

    public static final String CATEGORY_LIST = " categories:\n\n";

    public static final String LINE = GREY + "────────────────────\n" + RESET;

    public static final String ERROR_PATTERN_FIELD = RED + "\n --- " + BOLD + "ATTENTION" + RESET + RED + ", parameters not respected! min 1 max 25 characters --- \n" + RESET;

    public static final String ERROR_FIELD_VALUE = RED + "\n --- " + BOLD + "ATTENTION" + RESET + RED + ", parameters not respected or value already present ! --- \n" + RESET;

    public static final String ERROR_PATTERN_DESCRIPTION = RED + "\n --- " + BOLD + "ATTENTION" + RESET + RED + ", parameters not respected! min 0 max 100 characters --- \n" + RESET;

    public static final String ROOT_CATEGORY_ALREADY_PRESENT = RED + "\n --- Root category already present --- \n" + RESET;

    public static final String INTERNAL_CATEGORY_ALREADY_PRESENT = RED + "\n --- Category already present --- \n" + RESET;

    public static final String NO_CHILD = " (no child) ";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String VALUE_OF_DOMAIN = "value of domain";

    public static final String FIELD = "field";

    public static final String HIERARCHIES_LIST = PURPLE + BOLD + "\n\nHIERARCHIES LIST\n\n" + RESET;

    public static final String ENTER_HIERARCHY_ID = "\nEnter hierarchy ID (enter '0' to exit) --> ";
    
    // ---------------------------------------- ProposalController ----------------------------------------

    public static final String CONFIRM_PROPOSAL = "\n\nconfirm? (y/n) --> ";

    public static final String PROPOSAL_SAVED = GREEN + "\n --- " + BOLD + "Proposal saved ✓" + RESET + GREEN + " --- \n" + RESET;

    public static final String PROPOSAL_NOT_SAVED = RED + "\n --- " + BOLD + "Proposal NOT saved !!!" + RESET + RED + " --- \n" + RESET;

    public static final String ENTER_PROPOSAL_ID = "\nEnter proposal ID to " + RED + BOLD + "retire" + RESET + " --> ";

    public static final String PROPOSAL_LIST = BOLD + PURPLE + "\n\nPROPOSAL LIST\n\n" + RESET;

    // ---------------------------------------- ConfiguratorView ----------------------------------------

    public static final String CONFIGURATOR_MENU = "\n" +
                                                   "───────────────────────────────────────────\n" +
                                                   BLUE + BOLD + "\t\t  OPTIONS\n" + RESET +
                                                   "───────────────────────────────────────────\n" +
                                                   " 1.  insert new district\n" +
                                                   " 2.  insert new hierarchy\n" +
                                                   " 3.  insert conversion factors\n" +
                                                   " 4.  save all data\n" +
                                                   " 5.  view district\n" + 
                                                   " 6.  view hierarchy\n" +
                                                   " 7.  view all conversion factors\n" +
                                                   " 8.  view conversion factors of a category\n" +
                                                   " 9.  view proposals of a category\n\n" +
                                                   " 10.  logout\n" +
                                                   "───────────────────────────────────────────\n" +
                                                   "Enter your choice [1-10] --> ";

    // ---------------------------------------- MunicipalityController ----------------------------------------

    public static final String ENTER_MUNICIPALITY = "\nEnter municipality: ";

    // ---------------------------------------- ConversionFactorsController ----------------------------------------

    public static final String ENTER_CHOICE_PAIR = "\nEnter your choice: ";

    public static final String ENTER_VALUE_CONVERSION_FACTOR = "Enter value of conversion factor ";

    public static final String OUT_OF_RANGE_ERROR = RED + "\n --- Invalid or out of range !!! --- \n\n" + RESET;

    public static final String CONVERSION_FACTORS_LIST = PURPLE + BOLD + "\n\nCONVERSION FACTORS LIST\n\n" + RESET;

    // ---------------------------------------- ConfiguratorController ----------------------------------------

    public static final String SAVE_COMPLETED = GREEN + "\n --- saved COMPLETED ✓ ---\n" + RESET;

    public static final String IMPOSSIBLE_SAVE_CF = RED + BOLD + "\n --- ATTENTION, not all conversion factors have been set !!! --- " + RESET;

    // ---------------------------------------- multiple uses constants ----------------------------------------

    public static final String ENTER_TO_EXIT = "\nPress enter to exit ↵  ";

    public static final String YES_MESSAGE = "y";

    public static final String NO_MESSAGE = "n";

    public static final String OPERATION_COMPLETED = GREEN + BOLD + "\n --- Operation completed ✓ --- \n" + RESET;

    public static final String LOG_OUT = BLUE + "\nLog out ...\n\n" + RESET;

    public static final String INVALID_OPTION = RED + "\n --- Invalid option ! --- \n" + RESET;

    public static final String NOT_EXIST_MESSAGE = RED + "\n --- NOT exist ! --- \n" + RESET;

    public static final String GENERIC_EXCEPTION_MESSAGE = "GENERIC EXCEPTION";

    public static final String PROPOSE_PROPOSAL_SCREEN = CYAN + BOLD + "\n\nPROPOSAL\n\n" + RESET;

    public static final String LEAF_CATEGORY_LIST = PURPLE + BOLD + "\n\nLEAF CATEGORY LIST\n\n" + RESET;

    public static final String END_ADD_MESSAGE = "\nend: (y/n) --> ";

    public static final String ERROR_PATTERN_NAME = RED + "\n --- " + BOLD + "ATTENTION" + RESET + RED + ", parameters not respected! min 1 max 50 characters --- \n" + RESET;

    public static final String NOT_SAVED = "  -->  (not saved)";

}