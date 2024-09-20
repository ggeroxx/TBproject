package Server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import Server.repository.AccessRepository;
import Server.repository.CategoryRepository;
import Server.repository.ConfiguratorRepository;
import Server.repository.ConversionFactorsRepository;
import Server.repository.DistrictRepository;
import Server.repository.DistrictToMunicipalitiesRepository;
import Server.repository.MunicipalityRepository;
import Server.repository.ProposalRepository;
import Server.repository.RelationshipsBetweenCategoriesRepository;
import Server.repository.UserRepository;
import Server.repository.JDBCRepository.JDBCAccessRepositoryl;
import Server.repository.JDBCRepository.JDBCCategoryRepository;
import Server.repository.JDBCRepository.JDBCConfiguratorRepository;
import Server.repository.JDBCRepository.JDBCConversionFactorsRepository;
import Server.repository.JDBCRepository.JDBCDistrictRepository;
import Server.repository.JDBCRepository.JDBCDistrictToMunicipalitiesRepository;
import Server.repository.JDBCRepository.JDBCMunicipalityRepository;
import Server.repository.JDBCRepository.JDBCProposalRepository;
import Server.repository.JDBCRepository.JDBCRelationshipsBetweenCategoriesRepository;
import Server.repository.JDBCRepository.JDBCUserRepository;
import Server.service.ConnectionService;
import Server.service.pure_fabrication.Authentication;
import Server.service.pure_fabrication.AuthenticationService;
import Server.service.pure_fabrication.TemporaryOperations;
import Server.service.pure_fabrication.TemporaryOperationsManager;


public class ServerClasse 
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

    public ServerClasse(int port) throws IOException, SQLException 
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
            ServerClasse server = new ServerClasse(12345);
            server.start();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}




