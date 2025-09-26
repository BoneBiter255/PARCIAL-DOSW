package eci.edu.dosw.parcial.util.validator;

import eci.edu.dosw.parcial.util.ValidadorCrypto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CryptoValidatorTest {

    private ValidadorCrypto validadorCrypto;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        validadorCrypto = new ValidadorCrypto();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testValidar() {
        boolean resultado = validadorCrypto.validar();
        
        assertTrue(resultado, "El validador de crypto siempre debe retornar true");
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Validando Crypto...", output, "Debe mostrar el mensaje de validacion correctamente");
    }

    @Test
    void testValidarMultiplesLlamadas() {
        boolean resultado1 = validadorCrypto.validar();
        boolean resultado2 = validadorCrypto.validar();
        boolean resultado3 = validadorCrypto.validar();
        boolean resultado4 = validadorCrypto.validar();
        
        assertTrue(resultado1, "Primera validacion debe ser true");
        assertTrue(resultado2, "Segunda validacion debe ser true");
        assertTrue(resultado3, "Tercera validacion debe ser true");
        assertTrue(resultado4, "Cuarta validacion debe ser true");
        
        String output = outputStreamCaptor.toString();
        int count = output.split("Validando Crypto...", -1).length - 1;
        assertEquals(4, count, "Debe mostrar el mensaje 4 veces");
    }

    @Test
    void testValidarConsistencia() {
        ValidadorCrypto otroValidador = new ValidadorCrypto();
        
        boolean resultado1 = validadorCrypto.validar();
        boolean resultado2 = otroValidador.validar();
        
        assertEquals(resultado1, resultado2, "Diferentes instancias deben comportarse igual");
    }
}