package controller.MVCController;

import java.sql.*;

import controller.GRASPController.*;
import model.*;
import util.*;
import view.*;

public class DistrictController extends Controller {
    
    private DistrictView districtView;
    private MunicipalityController municipalityController;
    private DistrictGRASPController businessController;

    public DistrictController ( DistrictView districtView, DistrictRepository districtRepository, DistrictToMunicipalitiesRepository districtToMunicipalitiesRepository, MunicipalityController municipalityController )
    {
        super( districtView );
        this.districtView = districtView;
        this.municipalityController = municipalityController;
        this.businessController = new DistrictGRASPController(districtRepository, districtToMunicipalitiesRepository);
    }

    public DistrictRepository getdistrictRepository ()
    {
        return this.businessController.getdistrictRepository();
    }

    public void setDistrict ( District district )
    {
        this.businessController.setDistrict(district);
    }

    public void listAllWithMunicipalities () throws SQLException, Exception
    {
        for ( District toPrint : getdistrictRepository().getAllDistricts() )
        {
            districtView.printDistrict( toPrint );
            municipalityController.listAll( toPrint );
            districtView.print( "\n" );
        }
    }

    public void listAll () throws SQLException
    {
        for ( District toPrint : getdistrictRepository().getAllSavedDistricts() ) districtView.println( " " + toPrint.getID() + ". " + toPrint.getName() );
        for ( District toPrint : getdistrictRepository().getAllNotSavedDistricts() ) districtView.println( " " + toPrint.getID() + ". " + toPrint.getName() + Constants.NOT_SAVED );
    }

    public int chooseDistrict ()
    {
        return super.readIntWithExit( Constants.ENTER_DISTRICT_OR_EXIT, Constants.NOT_EXIST_MESSAGE, ( input ) -> {
            try 
            {
                return getdistrictRepository().getDistrictByID( (Integer) input ) == null;
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }

    public String enterName ()
    {
        return super.readString( Constants.ENTER_DISTRICT_NAME, Constants.ERROR_PATTERN_NAME, ( str ) -> Controls.checkPattern( str, 0, 50 ) );
    }

    public void viewDistrict () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        districtView.print( Constants.DISTRICTS_LIST );

        if ( getdistrictRepository().getAllDistricts().isEmpty() )
        {
            districtView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        this.listAll();
        int districtID = this.chooseDistrict();
        if ( districtID == 0 ) return;

        District tmp = getdistrictRepository().getDistrictByID( districtID );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        districtView.println( "\n" + tmp.getName() + ":\n" );
        municipalityController.listAll( tmp );

        districtView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public void enterDistrict ( ConfiguratorController configuratorController ) throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        String districtName = this.enterName();

        if ( getdistrictRepository().getDistrictByName( districtName ) != null )
        {
            districtView.println( Constants.DISTRICT_NAME_ALREADY_PRESENT );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        } 
        configuratorController.createDistrict( districtName );

        String municipalityName, continueInsert = Constants.NO_MESSAGE;
        do
        {
            municipalityName = municipalityController.enterName();

            Municipality municipalityToAdd = municipalityController.getmunicipalityRepository().getMunicipalityByName( municipalityName );

            if ( this.isPresentMunicipalityInDistrict( municipalityToAdd ) )
            {
                districtView.println( Constants.MUNICIPALITY_NAME_ALREADY_PRESENT );
                continue;
            }
            this.addMunicipality( municipalityToAdd );
            districtView.print( Constants.ADDED_SUCCESFULL_MESSAGE );

            continueInsert = this.enterYesOrNo( Constants.END_ADD_MESSAGE );
            
        } while ( !continueInsert.equals(Constants.YES_MESSAGE) );

        districtView.println( Constants.OPERATION_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }

    public String enterYesOrNo ( String msg )
    {
        return super.readString( msg, Constants.INVALID_OPTION, ( str ) -> !str.equals( Constants.NO_MESSAGE ) && !str.equals( Constants.YES_MESSAGE ) );
    }

    public void saveDistricts () throws SQLException
    {
        this.businessController.saveDistricts();
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return this.businessController.isPresentMunicipalityInDistrict( municipalityToCheck );
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        this.businessController.addMunicipality( municipalityToAdd );
    }

}
