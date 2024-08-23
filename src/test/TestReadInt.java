package test;

import org.junit.*;
import org.mockito.Mockito;

import util.Constants;

import controller.MVCController.Controller;
import view.View;

import static org.junit.Assert.assertEquals;

import java.util.InputMismatchException;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestReadInt {

    private View mockView;
    private Controller controller;
    

    @Before
    public void setUp() {
        // Crea un mock della classe View
        mockView = Mockito.mock(View.class);
        controller = new Controller(mockView);
    }

    @Test
    public void testReadInt_ValidInput() {
        // Configura il comportamento del mock per il metodo enterInt
        when(mockView.enterInt(anyString())).thenReturn(5);

        Predicate<Integer> condition = i -> i < 1;

        Integer result = controller.readInt("Enter number: ", "Number is too low", condition);
        Integer expected = 5;

        assertEquals(expected, result);

        // Verifica che il metodo enterInt sia stato chiamato
        verify(mockView).enterInt("Enter number: ");
        //Verifica che il metodo print sia stato chiamato con il messaggio di errore
        verify(mockView, never()).print("Number is too low");
    }

    @Test
    public void testReadInt_InvalidInputMessage() 
    {
        // Configura il comportamento del mock per il metodo enterInt
        when(mockView.enterInt(anyString()))
            .thenReturn(0)  // Prima volta input non valido
            .thenReturn(5); // Seconda volta input valido

        Predicate<Integer> condition = i -> i < 1;

        Integer result = controller.readInt("Enter number: ", "Number is too low", condition);
        Integer expected = 5;

        assertEquals(expected, result);

        // Verifica che il metodo enterInt sia stato chiamato due volte
        verify(mockView, times(2)).enterInt("Enter number: ");
        // Verifica che il metodo print sia stato chiamato una volta con il messaggio di errore
        verify(mockView).print("Number is too low");
    }

    @Test
    public void testReadInt_ErrorMessage() 
    {
        // Configura il comportamento del mock per il metodo enterInt
        when(mockView.enterInt(anyString()))
            .thenThrow(new InputMismatchException()) // Prima volta genera un'eccezione
            .thenReturn(5); // Seconda volta input valido

        Predicate<Integer> condition = i -> i < 1;

        Integer result = controller.readInt("Enter number: ", "Number is too low", condition);
        Integer expected = 5;

        assertEquals(expected, result);

        // Verifica che il metodo enterInt sia stato chiamato due volte
        verify(mockView, times(2)).enterInt("Enter number: ");
        // Verifica che il metodo print sia stato chiamato con Constants.INVALID_OPTION dopo l'eccezione
        verify(mockView).print(Constants.INVALID_OPTION);
        // Verifica che il messaggio di errore personalizzato non venga stampato
        verify(mockView, never()).print("Number is too low");
    }
}
