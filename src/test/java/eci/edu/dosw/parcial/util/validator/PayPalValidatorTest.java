package eci.edu.dosw.parcial.util.validator;

import eci.edu.dosw.parcial.util.ValidadorPayPal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PayPalValidatorTest {

    private ValidadorPayPal validadorPayPal;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        validadorPayPal = new ValidadorPayPal();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testValidar() {
        boolean resultado = validadorPayPal.validar();
        
        assertTrue(resultado, "El validador de PayPal siempre debe retornar true");
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Validando PayPal...", output, "Debe mostrar el mensaje de validacion correctamente");
    }

    @Test
    void testValidarMultiplesLlamadas() {
        boolean resultado1 = validadorPayPal.validar();
        boolean resultado2 = validadorPayPal.validar();
        
        assertTrue(resultado1, "Primera validacion debe ser true");
        assertTrue(resultado2, "Segunda validacion debe ser true");
        
        String output = outputStreamCaptor.toString();
        int count = output.split("Validando PayPal...", -1).length - 1;
        assertEquals(2, count, "Debe mostrar el mensaje 2 veces");
    }

    @Test
    void testValidarConsistencia() {
        ValidadorPayPal otroValidador = new ValidadorPayPal();
        
        boolean resultado1 = validadorPayPal.validar();
        boolean resultado2 = otroValidador.validar();
        
        assertEquals(resultado1, resultado2, "Diferentes instancias deben comportarse igual");
    }
}