package test;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import controller.MVCController.*;
import model.District;
import model.DistrictRepository;
import model.JDBCDistrictRepository;

public class TestConfigurator {
   // Esempio di Test di Integrazione per la creazione di un distretto

   
   @Test
   public void testCreateDistrict_Integration() throws SQLException {
       // Arrange
       // Configura il database di test e crea una nuova istanza di ConfiguratorController
   
       // Act
       configuratorController.createDistrict(("New District"));
   
       // Assert
       // Recupera i distretti dal database e verifica che il nuovo distretto sia presente
       DistrictRepository districtRepository = new JDBCDistrictRepository();
       List<District> districts = districtRepository.getAllDistricts(); // Supponendo che questo metodo esista
       boolean districtExists = districts.stream().anyMatch(d -> d.getName().equals("New District"));
       
       assertTrue(districtExists); // Verifica che il distretto sia stato creato
   }
    
}


