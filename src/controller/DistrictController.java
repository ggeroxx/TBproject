package controller;

import java.sql.*;
import model.*;
import util.*;
import view.*;

public class DistrictController extends Controller {
    
    private DistrictView districtView;
    private DistrictJDBC districtJDBC;
    private District district;
    private DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC;
    private MunicipalityController municipalityController;

    public DistrictController ( DistrictView districtView, DistrictJDBC districtJDBC, DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC, MunicipalityController municipalityController )
    {
        super( districtView );
        this.districtView = districtView;
        this.districtJDBC = districtJDBC;
        this.districtToMunicipalitiesJDBC = districtToMunicipalitiesJDBC;
        this.municipalityController = municipalityController;
    }

    public DistrictJDBC getDistrictJDBC ()
    {
        return this.districtJDBC;
    }

    public void setDistrict ( District district )
    {
        this.district = district;
    }

    public void listAllWithMunicipalities () throws SQLException, Exception
    {
        for ( District toPrint : districtJDBC.getAllDistricts() )
        {
            districtView.printDistrict( toPrint );
            municipalityController.listAll( toPrint );
            districtView.print( "\n" );
        }
    }

    public void listAll () throws SQLException
    {
        for ( District toPrint : districtJDBC.getAllSavedDistricts() ) districtView.println( " " + toPrint.getID() + ". " + toPrint.getName() );
        for ( District toPrint : districtJDBC.getAllNotSavedDistricts() ) districtView.println( " " + toPrint.getID() + ". " + toPrint.getName() + Constants.NOT_SAVED );
    }

    public int chooseDistrict ()
    {
        return super.readIntWithExit( Constants.ENTER_DISTRICT_OR_EXIT, Constants.NOT_EXIST_MESSAGE, ( input ) -> {
            try 
            {
                return districtJDBC.getDistrictByID( (Integer) input ) == null;
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

        if ( districtJDBC.getAllDistricts().isEmpty() )
        {
            districtView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        this.listAll();
        int districtID = this.chooseDistrict();
        if ( districtID == 0 ) return;

        District tmp = districtJDBC.getDistrictByID( districtID );
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

        if ( districtJDBC.getDistrictByName( districtName ) != null )
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

            Municipality municipalityToAdd = municipalityController.getMunicipalityJDBC().getMunicipalityByName( municipalityName );

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
        districtJDBC.saveTmpDistricts();

        districtToMunicipalitiesJDBC.saveTmpDistrictToMunicipalities();

        districtToMunicipalitiesJDBC.deleteTmpDistrictToMunicipalities();

        districtJDBC.deleteTmpDistricts();
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return districtToMunicipalitiesJDBC.isPresentMunicipalityInDistrict( this.district.getID(), municipalityToCheck.getID() );
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        districtToMunicipalitiesJDBC.addMunicipality( this.district.getID(), municipalityToAdd.getID() );
    }

}
