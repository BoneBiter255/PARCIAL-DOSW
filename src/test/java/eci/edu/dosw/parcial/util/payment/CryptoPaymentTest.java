package eci.edu.dosw.parcial.util.payment;

import eci.edu.dosw.parcial.util.PagoCrypto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CryptoPaymentTest {

    private PagoCrypto pagoCrypto;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        pagoCrypto = new PagoCrypto();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testPagarMontoPositivo() {
        double monto = 0.0123;
        
        pagoCrypto.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con Criptomonedas: $" + monto, output, "Debe mostrar el mensaje correcto de pago con criptomonedas");
    }

    @Test
    void testPagarMontoCero() {
        double monto = 0.0;
        
        pagoCrypto.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con Criptomonedas: $" + monto, output, "Debe procesar pago con monto cero");
    }

    @Test
    void testPagarMontoGrande() {
        double monto = 50000.12345;
        
        pagoCrypto.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con Criptomonedas: $" + monto, output, "Debe manejar correctamente montos grandes");
    }

    @Test
    void testMultiplesPagos() {
        double monto1 = 1.5;
        double monto2 = 0.025;
        
        pagoCrypto.pagar(monto1);
        pagoCrypto.pagar(monto2);
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Pago con Criptomonedas: $" + monto1), "Debe contener el primer pago");
        assertTrue(output.contains("Pago con Criptomonedas: $" + monto2), "Debe contener el segundo pago");
    }
}