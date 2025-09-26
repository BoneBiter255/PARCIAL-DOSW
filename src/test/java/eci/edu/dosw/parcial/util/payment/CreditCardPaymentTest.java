package eci.edu.dosw.parcial.util.payment;

import eci.edu.dosw.parcial.util.PagoTarjeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardPaymentTest {

    private PagoTarjeta pagoTarjeta;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        pagoTarjeta = new PagoTarjeta();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testPagarMontoPositivo() {
        double monto = 100.50;
        
        pagoTarjeta.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con tarjeta: $" + monto, output, "Debe mostrar el mensaje correcto de pago con tarjeta");
    }

    @Test
    void testPagarMontoCero() {
        double monto = 0.0;
        
        pagoTarjeta.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con tarjeta: $" + monto, output, "Debe procesar pago con monto cero");
    }

    @Test
    void testPagarMontoNegativo() {
        double monto = -50.0;
        
        pagoTarjeta.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con tarjeta: $" + monto, output, "Debe procesar pago con monto negativo");
    }

    @Test
    void testPagarMontoDecimal() {
        double monto = 99.99;
        
        pagoTarjeta.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con tarjeta: $" + monto, output, "Debe manejar correctamente montos decimales");
    }

    @Test
    void testMultiplesPagos() {
        double monto1 = 50.0;
        double monto2 = 75.25;
        
        pagoTarjeta.pagar(monto1);
        pagoTarjeta.pagar(monto2);
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Pago con tarjeta: $" + monto1), "Debe contener el primer pago");
        assertTrue(output.contains("Pago con tarjeta: $" + monto2), "Debe contener el segundo pago");
    }
}