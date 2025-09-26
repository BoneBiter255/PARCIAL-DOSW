package eci.edu.dosw.parcial.util.validator;

import eci.edu.dosw.parcial.util.ValidadorTarjeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardValidatorTest {

    private ValidadorTarjeta validadorTarjeta;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        validadorTarjeta = new ValidadorTarjeta();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testValidar() {
        boolean resultado = validadorTarjeta.validar();
        
        assertTrue(resultado, "El validador de tarjeta siempre debe retornar true");
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Validando tarjeta...", output, "Debe mostrar el mensaje de validacion correctamente");
    }

    @Test
    void testValidarMultiplesLlamadas() {
        boolean resultado1 = validadorTarjeta.validar();
        boolean resultado2 = validadorTarjeta.validar();
        boolean resultado3 = validadorTarjeta.validar();
        
        assertTrue(resultado1, "Primera validacion debe ser true");
        assertTrue(resultado2, "Segunda validacion debe ser true");
        assertTrue(resultado3, "Tercera validacion debe ser true");
        
        String output = outputStreamCaptor.toString();
        int count = output.split("Validando tarjeta...", -1).length - 1;
        assertEquals(3, count, "Debe mostrar el mensaje 3 veces");
    }

    @Test
    void testValidarConsistencia() {
        ValidadorTarjeta otroValidador = new ValidadorTarjeta();
        
        boolean resultado1 = validadorTarjeta.validar();
        boolean resultado2 = otroValidador.validar();
        
        assertEquals(resultado1, resultado2, "Diferentes instancias deben comportarse igual");
    }
}