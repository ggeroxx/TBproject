package controller.ClientServer;

import java.util.List;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.Category;
import model.ConversionFactor;
import model.ConversionFactors;
import model.Municipality;
import model.Proposal;
import model.User;
import repository.AccessRepository;
import repository.CategoryRepository;
import repository.ConfiguratorRepository;
import repository.ConversionFactorsRepository;
import repository.DistrictRepository;
import repository.DistrictToMunicipalitiesRepository;
import repository.MunicipalityRepository;
import repository.ProposalRepository;
import repository.RelationshipsBetweenCategoriesRepository;
import repository.UserRepository;
import service.CategoryService;
import service.ConfiguratorService;
import service.ControlPatternService;
import service.ConversionFactorsService;
import service.DistrictService;
import service.MunicipalityService;
import service.ProposalService;
import service.Session;
import service.SessionService;
import service.SubjectService;
import service.UserService;
import service.pure_fabrication.Authentication;
import service.pure_fabrication.TemporaryOperations;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    Session session;

    AccessRepository accessRepository;
    SessionService sessionService;
    ProposalService proposalService;
    MunicipalityService municipalityService;
    DistrictService districtService;
    CategoryService categoryService;
    ConversionFactorsService conversionFactorsService;
    UserService userService;
    ConfiguratorService configuratorService;
    SubjectService subjectService;

    /*SubjectController subjectController;
    MunicipalityController municipalityController;
    DistrictController districtController;
    CategoryController categoryController;
    ConversionFactorController conversionFactorController;
    ConversionFactorsController conversionFactorsController;
    ProposalController proposalController;
    ConfiguratorController configuratorController;*/

    public ClientHandler(Socket socket, AccessRepository accessRepository, ConfiguratorRepository configuratorRepository, UserRepository userRepository, DistrictRepository districtRepository, MunicipalityRepository municipalityRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository, CategoryRepository categoryRepository, RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository, ProposalRepository proposalRepository,  ConversionFactorsRepository conversionFactorsRepository, Authentication authenticationService, TemporaryOperations tempOpsManager ) throws IOException 
    {
        this.clientSocket = socket;
        this.accessRepository = accessRepository;
        this.session = new Session(authenticationService, tempOpsManager);
        this.sessionService = new SessionService(session);
        this.proposalService = new ProposalService(proposalRepository);
        this.municipalityService = new MunicipalityService(municipalityRepository, districtToMunicipalitiesRepository);
        this.districtService = new DistrictService(districtRepository, districtToMunicipalitiesRepository);
        this.categoryService = new CategoryService(categoryRepository, relationshipsBetweenCategoriesRepository);
        this.conversionFactorsService = new ConversionFactorsService(conversionFactorsRepository, categoryService);
        this.userService = new UserService(userRepository, proposalService);
        this.configuratorService = new ConfiguratorService(configuratorRepository, districtService, categoryService, conversionFactorsService);
        this.subjectService = new SubjectService(configuratorService, userService);

        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try
        {
            while (true) 
            {
                try 
                {
                    System.out.println("Waiting for client request...");
                    Object request = in.readObject();
                    System.out.println("Request received: " + request);

                    // Process request and generate response
                    Object response = processRequest(request);
                    System.out.println("Response generated: " + response);

                    out.writeObject(response);
                    out.flush();
                    System.out.println("Response sent to client.");
                } 
                catch (EOFException e) 
                {
                    System.out.println("Client disconnected.");
                    break; // Exit the loop if client disconnects
                }
            }
            
        } catch (IOException | ClassNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            try 
            {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    private Object processRequest(Object request) throws IOException, SQLException 
    {

        if (request instanceof SomeRequestSession) {
            SomeRequestSession someRequest = (SomeRequestSession) request;
            String action = someRequest.getAction();
            String username = someRequest.getUsername();
            String password = someRequest.getPassword();

            // Example handling based on action
            switch (action) {

                case "LOGIN":
                {
                    sessionService.login(username, password);
                    return new SomeResponse("Login...", true);
                }

                case "GET_STATUS":
                {
                    return sessionService.getStatus();
                }

                case "GET_SUBJECT":
                {
                    return sessionService.getSubject();
                }

                case "GET_USERNAME_PERMISSION":
                {
                    return accessRepository.getPermission().getUsername();
                }

                case "LOGOUT":
                {
                    sessionService.logout();
                    return new SomeResponse("Logout...", true);
                }

                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestSubject) {
            SomeRequestSubject someRequest = (SomeRequestSubject) request;
            String action = someRequest.getAction();
            String name = someRequest.getName();

            // Example handling based on action
            switch (action) {
                case "IS_PRESENT_USERNAME":
                {
                    return subjectService.isPresentUsername(name);
                }

                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestUser) {
            SomeRequestUser someRequest = (SomeRequestUser) request;
            String action = someRequest.getAction();
            String userName = someRequest.getName();
            String password = someRequest.getPassword();
            int districtID = someRequest.getDistrictID();
            String email = someRequest.getEmail();

            // Example handling based on action
            switch (action) {
                case "SET_USER":
                {
                    userService.setUser(userService.getUserByUsername(userName));
                }
                case "INSERT_USER":
                {
                    userService.insertUser(new User(null, userName, password, districtID, email));
                    new SomeResponse("User added",true);
                }
                case "GET_USER_NAME":
                {
                    return userService.getUser().getUsername();
                }


                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestConfigurator) {
            SomeRequestConfigurator someRequest = (SomeRequestConfigurator) request;
            String action = someRequest.getAction();
            String username = someRequest.getUsername();
            String newPassword  = someRequest.getNewPassword();

            // Example handling based on action
            switch (action) {
                case "SAVE_ALL":
                {
                    configuratorService.saveAll();
                    return new SomeResponse("All saved", true);
                }
                case "CHANGE_CREDENTIALS":
                {
                    configuratorService.changeCredentials(username, newPassword);;
                    return new SomeResponse("credentials changed ", true);
                }

                case "GET_CONFIGURATOR_BY_USERNAME":
                {
                    return configuratorService.getConfiguratorByUsername( username );
                }

                case "GET_FIRST_ACCESS":
                {
                    return configuratorService.getConfigurator().getFirstAccess();
                }

                case "SET_CONFIGURATOR":
                {
                    configuratorService.setConfigurator(configuratorService.getConfiguratorByUsername(username));
                }

                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestDistrict) {
            SomeRequestDistrict someRequest = (SomeRequestDistrict) request;
            String action = someRequest.getAction();
            String districtName = someRequest.getData();

            // Example handling based on action
            switch (action) {
                case "CREATE_DISTRICT":
                {
                    configuratorService.createDistrict(districtName);
                    return new SomeResponse("District created successfully", true);
                }
                case "ALREADY_DISTRICT_PRESENT":
                {
                    return (districtService.getDistrictByName( districtName ) != null) ? true : false;
                }

                case "GET_ALL_DISTRICT_NAME":
                {
                    return districtService.allDistrictName();
                }

                case "GET_DISTRICT_ID_BY_NAME":
                {
                    return  districtService.getDistrictByName(districtName).getID();
                }

                case "SAVE_DISTRICT":
                {
                    districtService.saveDistricts();
                    return new SomeResponse("Districts saved",true);
                }
                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestMunicipality) {
            SomeRequestMunicipality someRequest = (SomeRequestMunicipality) request;
            String action = someRequest.getAction();
            String municipalityName = someRequest.getMunicipalityName();

            // Example handling based on action
            switch (action) {
                case "ADD_MUNICIPALITY":
                {
                    districtService.addMunicipality(municipalityService.getMunicipalityByName(municipalityName));
                    return new SomeResponse("Municipality added successfully", true);
                }
                case "EXIST_MUNICIPALITY":
                {
                    if ( municipalityService.getMunicipalityByName( municipalityName ) == null) return false;
                    else return true;
                }
                case "IS_PRESENT_MUNICIPALITY_IN_DISTRICT":
                {
                    Municipality municipalityToAdd = municipalityService.getMunicipalityByName( municipalityName );
                    return districtService.isPresentMunicipalityInDistrict( municipalityToAdd );
                }
                case "GET_ALL_MUNICIPALITY_FROM_DISTRICT":
                {
                    return municipalityService.getAllMunicipalityFromDistrict(districtService.getDistrictByName(municipalityName));
                }
                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestCategory) {
            SomeRequestCategory someRequest = (SomeRequestCategory) request;
            String action = someRequest.getAction();
            int id = someRequest.getID();
            String categoryName = someRequest.getCategoryName();
            String field = someRequest.getField();
            String description = someRequest.getDescription();
            boolean isRoot = someRequest.getIsRoot();
            int hierarchyID = someRequest.getHierarchyID();

            // Example handling based on action
            switch (action) {
                case "CREATE_CATEGORY":
                {
                    configuratorService.createCategory(categoryName, field, description, isRoot, hierarchyID);
                    return new SomeResponse("Category created", true);
                }
                case "INSERT_ROOT":
                {
                    configuratorService.createCategory(categoryName, field, null, true, null);
                    return new SomeResponse("Root insert successfully", true);
                }
                case "EXIST_VALUE_OF_FIELD":
                {
                    return categoryService.existValueOfField(field,categoryService.getCategoryByID(id));
                }
                case "SAVE_DISTRICTS":
                {
                    categoryService.saveCategories();
                    return new SomeResponse("Categories saved", true);
                }
                case "CREATE_RELATIONSHIP":
                {
                    categoryService.createRelationship(id, field);
                }
                case "GET_ALL_SAVED_LEAF_ID":
                {
                    List<Category> categories = categoryService.getAllSavedLeaf();
                    int[] categoriesID = new int[categories.size()];
                    for(int i = 0; i < categories.size(); i++)
                    {
                        categoriesID[i] = categories.get(i).getID();
                    }
                    return categoriesID;
                }
                case "GET_ALL_NOT_SAVED_LEAF_ID":
                {
                    List<Category> categories = categoryService.getAllNotSavedLeaf();
                    int[] categoriesID = new int[categories.size()];
                    for(int i = 0; i < categories.size(); i++)
                    {
                        categoriesID[i] = categories.get(i).getID();
                    }
                    return categoriesID;
                }
                case "GET_NUMBER_OF_EQUALS_CATEGORY":
                {
                    return categoryService.getNumberOfEqualsCategories(categoryService.getCategoryByID(id));
                }
                case "GET_ROOT_NAME_BY_LEAF":
                {
                    return categoryService.getRootByLeaf(categoryService.getCategoryByID(id)).getName();
                }
                case "IS_PRESENT_INTERNAL_CATEGORY":
                {
                    Category root = categoryService.getCategoryByID(id);
                    return categoryService.isPresentInternalCategory(root, categoryName);
                }
                case "IS_VALID_PARENT_ID":
                {
                    Category root = categoryService.getCategoryByID(hierarchyID);
                    return categoryService.isValidParentID(root, id);
                }
                case "GET_ROOT_CATEGORY_ID_BY_NAME":
                {
                    return categoryService.getRootCategoryByName(categoryName).getID();
                }
                case "SET_CATEGORY":
                {
                    categoryService.setCategory(categoryService.getCategoryByID(id));
                }
                case "GET_CATEGORY_ID":
                {
                    return categoryService.getCategory();
                }
                case "GET_NUMBER_OF_CATEGORIES_WITHOUT_CHILD":
                {
                    return categoryService.getCategoryRepository().getCategoriesWithoutChild().size();
                }
                case "GET_PARENT_CATEGORIES_ID":
                {
                    List<Category> categories = categoryService.getCategoryRepository().getParentCategories(id);
                    int[] categoriesID = new int[categories.size()];
                    for(int i = 0; i < categories.size(); i++)
                    {
                        categoriesID[i] = categories.get(i).getID();
                    }
                    return categoriesID;

                }
                case "GET_CATEGORY_NAME_BY_ID":
                {
                    return categoryService.getCategoryByID(id).getName();
                }
                case "BUILD_HIERARCHY":
                {
                    return categoryService.buildHierarchy( id, new StringBuffer(), new StringBuffer() );
                }
                case "INFO":
                {
                    return categoryService.info( categoryService.getCategoryByID(id) );
                }
                case "GET_FIELD_VALUES_FROM_PARENT_ID":
                {
                    return categoryService.getFieldValuesFromParentID(id);
                }
                case "GET_ALL_CATEGORIES_ID_FROM_ROOT_ID":
                {
                    return categoryService.getAllCategoriesFromRoot(categoryService.getCategoryByID(id));
                }
                case "GET_ALL_ROOT_ID":
                {
                    List<Category> categories = categoryService.getAllRoot();
                    int[] categoriesID = new int[categories.size()];
                    for(int i = 0; i < categories.size(); i++)
                    {
                        categoriesID[i] = categories.get(i).getID();
                    }
                    return categoriesID;
                }
                case "GET_CHILD_CATEGORY_ID_BY_FIELD_AND_PARENT_ID":
                {
                    return categoryService.getChildCategoryByFieldAndParentID(field, categoryService.getCategoryByID(id));
                }
                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestConversionFactors) {
            SomeRequestConversionFactors someRequest = (SomeRequestConversionFactors) request;
            String action = someRequest.getAction();
            int index = someRequest.getIndex();
            Double value = someRequest.getValue();
            boolean check = someRequest.getCheck();
            HashMap<Integer, ConversionFactorDTO> copyCFs = someRequest.getCopyCfs();
            List<String> equations = someRequest.getEquations();
            int anotherIndex = someRequest.getAnotherIndex();

            // Example handling based on action
            switch (action) {
                case "IS_COMPLETE":
                {
                    return conversionFactorsService.isComplete();
                }
                case "POPULATE":
                {
                    conversionFactorsService.populate();
                }
                case "GET_CONVERSION_FACTORS":
                {
                    ConversionFactors conversionFactors = conversionFactorsService.getConversionFactors();
                    ConversionFactorsDTO conversionFactorsDTO = new ConversionFactorsDTO();

                    for ( Entry< Integer, ConversionFactor > entry : conversionFactors.getList().entrySet())
                    {
                        ConversionFactor factor = entry.getValue();
                        ConversionFactorDTO factorDTO = new ConversionFactorDTO(entry.getKey(), factor.getLeaf_1().getID(), factor.getLeaf_1().getName(), factor.getLeaf_1().getHierarchyID(),factor.getLeaf_2().getID(), factor.getLeaf_2().getName(), factor.getLeaf_2().getHierarchyID(), factor.getValue());
                        conversionFactorsDTO.addConversionFactorDTO(entry.getKey(), factorDTO);
                    }

                    return conversionFactorsDTO;
                }
                case "RESET_CONVERSION_FACTORS":
                {
                    conversionFactorsService.resetConversionFactors();
                    return new SomeResponse("Conversion Factors resetted",true);
                }
                case "SAVE_CONVERSION_FACTORS":
                {
                    conversionFactorsService.saveConversionFactors();
                    return new SomeResponse("Conversion Factors saved",true);
                }
                case "CALCULATE":
                {
                    conversionFactorsService.calculate(index, value);
                }
                case "CALCULATE_EQ":
                {
                    HashMap<Integer, ConversionFactor> copyConversionFactorsMap = new HashMap<>();
                    for (Map.Entry<Integer, ConversionFactorDTO> entry : copyCFs.entrySet()) 
                    {
                        Integer id = entry.getKey();
                        ConversionFactorDTO dto = entry.getValue();

                        // Usa il metodo di conversione per trasformare il DTO in un oggetto ConversionFactor
                        ConversionFactor conversionFactor = new ConversionFactor(categoryService.getCategoryByID(dto.getLeaf1ID()), categoryService.getCategoryByID(dto.getLeaf2ID()), dto.getValue());

                        // Aggiungi il ConversionFactor alla nuova mappa
                        copyConversionFactorsMap.put(id, conversionFactor);
                    }
                    conversionFactorsService.calculateEq(index, value, check, copyConversionFactorsMap, equations);
                }
                case "CALCULAT_RANGE":
                {
                    return conversionFactorsService.calculateRange(index);
                }
                case "GET_CONVERSION_FACTOR_VALUE":
                {
                    return conversionFactorsService.getConversionFactorValue( categoryService.getCategoryByID(index), categoryService.getCategoryByID(anotherIndex) );
                }
                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestPattern) {
            SomeRequestPattern someRequest = (SomeRequestPattern) request;
            String action = someRequest.getAction();
            String name = someRequest.getName();
            int min = someRequest.getMin();
            int max = someRequest.getMax();
            boolean isPresent = someRequest.getIsPresent();

            // Example handling based on action
            switch (action) {
                case "CHECK_PATTERN_NAME":
                {
                    return ControlPatternService.checkPattern( name, min, max ) ? false : true;
                }
                case "GET_MSG_ERROR_NEW_USERNAME":
                {
                    return ControlPatternService.messageErrorNewUsername( name, isPresent);
                }
                case "GET_MSG_ERROR_NEW_PASSWORD":
                {
                    return ControlPatternService.messageErrorNewPassword( name );
                }
                case "GET_MSG_ERROR_EMAIL":
                {
                    return ControlPatternService.messaggeErrorNewEmail( name );
                }
                case "PADRIGHT":
                {
                    return ControlPatternService.padRight(name, max);
                }
                case "CHECK_PATTERN":
                {
                    return ControlPatternService.checkPattern(name, min, max);
                }
                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (request instanceof SomeRequestProposal) {
            SomeRequestProposal someRequest = (SomeRequestProposal) request;
            String action = someRequest.getAction();
            int id = someRequest.getID();
            int requestCategoryID = someRequest.getOfferCategoryID();
            int offerCategoryID = someRequest.getOfferCategoryID();
            int requestHours = someRequest.getOfferHours();
            int offerHours = someRequest.getOfferHours();
            String user = someRequest.getUser();
            String state = someRequest.getState();
            // Example handling based on action
            switch (action) {
                case "INSERT_PROPOSAL":
                {
                    Proposal newProposal = new Proposal(null, categoryService.getCategoryByID(requestCategoryID), categoryService.getCategoryByID(offerCategoryID), requestHours, offerHours, userService.getUserByUsername(user), state) ;
                    userService.insertProposal( newProposal );
                }
                case "GET_ALL_PROPOSALS_ID_BY_USER_USERNAME":
                {
                    List<Proposal> proposals = proposalService.getAllProposalsByUser( userService.getUserByUsername(user) );
                    int[] proposalsID = new int[proposals.size()];
                    for(int i = 0; i < proposals.size(); i++)
                    {
                        proposalsID[i] = proposals.get(i).getID();
                    }
                    return proposalsID;
                }
                case "GET_REQUEST_CATEGORY_NAME_BY_PROPOSAL_ID":
                {
                    return proposalService.getProposalRepository().getProposalById(id).getRequestedCategory().getName();
                }
                case "GET_OFFER_CATEGORY_NAME_BY_PROPOSAL_ID":
                {
                    return proposalService.getProposalRepository().getProposalById(id).getOfferedCategory().getName();
                }
                case "GET_REQUEST_HOURS_BY_PROPOSAL_ID":
                {
                    return proposalService.getProposalRepository().getProposalById(id).getRequestedHours();
                }
                case "GET_OFFER_HOURS_BY_PROPOSAL_ID":
                {
                    return proposalService.getProposalRepository().getProposalById(id).getOfferedHours();
                }
                case "GET_STATE_BY_PROPOSAL_ID":
                {
                    return proposalService.getProposalRepository().getProposalById(id).getState();
                }
                case "GET_ALL_OPEN_PROPOSALS_BY_USER_USERNAME":
                {
                    List<Proposal> proposals = proposalService.getAllOpenProposalByUser( userService.getUserByUsername(user) );
                    int[] proposalsID = new int[proposals.size()];
                    for(int i = 0; i < proposals.size(); i++)
                    {
                        proposalsID[i] = proposals.get(i).getID();
                    }
                    return proposalsID;
                }
                case "RETIRE_PROPOSAL":
                {
                    userService.retireProposal(proposalService.getProposalRepository().getProposalById(id));
                }
                case "GET_NUMBER_OF_ALL_OPEN_PROPOSAL_BY_USER_USERNAME":
                {
                    return proposalService.getAllOpenProposalByUser(userService.getUserByUsername(user)).size();
                }
                case "GET_ALL_PROPOSALS_ID_BY_LEAF_ID":
                {
                    List<Proposal> proposals = proposalService.getAllProposalsByLeaf( categoryService.getCategoryByID(id) );
                    int[] proposalsID = new int[proposals.size()];
                    for(int i = 0; i < proposals.size(); i++)
                    {
                        proposalsID[i] = proposals.get(i).getID();
                    }
                    return proposalsID;
                }
                default:
                    return new SomeResponse("Unknown action",false);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return new SomeResponse("Invalid request type",false);
    }
}
