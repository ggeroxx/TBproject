package controller.ClientServer;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
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
import repository.JDBCRepository.JDBCAccessRepositoryl;
import repository.JDBCRepository.JDBCCategoryRepository;
import repository.JDBCRepository.JDBCConfiguratorRepository;
import repository.JDBCRepository.JDBCConversionFactorsRepository;
import repository.JDBCRepository.JDBCDistrictRepository;
import repository.JDBCRepository.JDBCDistrictToMunicipalitiesRepository;
import repository.JDBCRepository.JDBCMunicipalityRepository;
import repository.JDBCRepository.JDBCProposalRepository;
import repository.JDBCRepository.JDBCRelationshipsBetweenCategoriesRepository;
import repository.JDBCRepository.JDBCUserRepository;
import service.ConnectionService;
import service.pure_fabrication.Authentication;
import service.pure_fabrication.AuthenticationService;
import service.pure_fabrication.TemporaryOperations;
import service.pure_fabrication.TemporaryOperationsManager;


public class Server 
{
    ServerSocket serverSocket;
    AccessRepository accessRepository;
    ConfiguratorRepository configuratorRepository;
    UserRepository userRepository;
    DistrictRepository districtRepository;
    MunicipalityRepository municipalityRepository;
    DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository;
    CategoryRepository categoryRepository;
    RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository;
    ProposalRepository proposalRepository;
    ConversionFactorsRepository conversionFactorsRepository;
    Authentication authenticationService;
    TemporaryOperations tempOpsManager;

    public Server(int port) throws IOException, SQLException 
    {
        serverSocket = new ServerSocket(port);
        accessRepository = new JDBCAccessRepositoryl();
        configuratorRepository = new JDBCConfiguratorRepository();
        userRepository = new JDBCUserRepository();
        districtRepository = new JDBCDistrictRepository();
        municipalityRepository = new JDBCMunicipalityRepository();
        districtToMunicipalitiesRepository = new JDBCDistrictToMunicipalitiesRepository();
        categoryRepository = new JDBCCategoryRepository();
        relationshipsBetweenCategoriesRepository = new JDBCRelationshipsBetweenCategoriesRepository();
        proposalRepository = new JDBCProposalRepository();
        conversionFactorsRepository = new JDBCConversionFactorsRepository();
        authenticationService = new AuthenticationService( configuratorRepository, userRepository, accessRepository );
        tempOpsManager = new TemporaryOperationsManager( districtRepository, categoryRepository, districtToMunicipalitiesRepository, relationshipsBetweenCategoriesRepository );
        
        ConnectionService.openConnection();
        System.out.println("Server started on port " + port);
    }

    public void start() throws IOException 
    {
        System.out.println("Waiting for clients...");

        while (true) 
        {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());
            new Thread(new ClientHandler(clientSocket, accessRepository, configuratorRepository, userRepository, districtRepository, municipalityRepository, districtToMunicipalitiesRepository, categoryRepository, relationshipsBetweenCategoriesRepository, proposalRepository, conversionFactorsRepository, authenticationService, tempOpsManager)).start();
        }
    }

    public static void main(String[] args) throws SQLException 
    {
        try 
        {
            Server server = new Server(12345);
            server.start();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}




