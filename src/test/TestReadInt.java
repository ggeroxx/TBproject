package test;

import org.junit.*;
import org.mockito.Mockito;

import util.Constants;

import controller.MVCController.Controller;
import view.View;

import static org.junit.Assert.*;

import java.util.InputMismatchException;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestReadInt {

    private View mockView;
    private Controller controller;
    

    @Before
    public void setUp() 
    {
        mockView = Mockito.mock(View.class);
        controller = new Controller(mockView);
    }

    @Test
    public void testReadInt_ValidInput() 
    {
        when( mockView.enterInt( anyString() ) ).thenReturn( 5 );

        Predicate<Integer> condition = i -> i < 1;

        Integer result = controller.readInt( "Enter number: ", "Number is too low", condition );
        Integer expected = 5;

        assertEquals( expected, result );

        verify( mockView ).enterInt( "Enter number: " );
        verify (mockView, never() ).print( "Number is too low" );
    }

    @Test
    public void testReadInt_InvalidInputMessage() 
    {
        when( mockView.enterInt( anyString() ) ).thenReturn( 0 ).thenReturn( 5 );

        Predicate<Integer> condition = i -> i < 1;

        Integer result = controller.readInt( "Enter number: ", "Number is too low", condition );
        Integer expected = 5;

        assertEquals( expected, result );

        verify( mockView, times( 2 )).enterInt( "Enter number: " );
        verify( mockView ).print( "Number is too low" );
    }

    @Test
    public void testReadInt_ErrorMessage() 
    {
        when( mockView.enterInt( anyString() ) ).thenThrow( new InputMismatchException() ).thenReturn( 5 );

        Predicate<Integer> condition = i -> i < 1;

        Integer result = controller.readInt( "Enter number: ", "Number is too low", condition );
        Integer expected = 5;

        assertEquals( expected, result );

        verify( mockView, times( 2 )).enterInt( "Enter number: " );
        verify( mockView ).print( Constants.INVALID_OPTION );
        verify( mockView, never() ).print( "Number is too low" );
    }
}
