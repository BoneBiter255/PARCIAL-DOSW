package eci.edu.dosw.parcial.util.payment;

import eci.edu.dosw.parcial.util.PagoPayPal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PayPalPaymentTest {

    private PagoPayPal pagoPayPal;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        pagoPayPal = new PagoPayPal();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testPagarMontoPositivo() {
        double monto = 200.75;
        
        pagoPayPal.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con PayPal: $" + monto, output, "Debe mostrar el mensaje correcto de pago con PayPal");
    }

    @Test
    void testPagarMontoCero() {
        double monto = 0.0;
        
        pagoPayPal.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con PayPal: $" + monto, output, "Debe procesar pago con monto cero");
    }

    @Test
    void testPagarMontoDecimal() {
        double monto = 149.99;
        
        pagoPayPal.pagar(monto);
        
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Pago con PayPal: $" + monto, output, "Debe manejar correctamente montos decimales");
    }

    @Test
    void testMultiplesPagos() {
        double monto1 = 25.0;
        double monto2 = 87.50;
        
        pagoPayPal.pagar(monto1);
        pagoPayPal.pagar(monto2);
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Pago con PayPal: $" + monto1), "Debe contener el primer pago");
        assertTrue(output.contains("Pago con PayPal: $" + monto2), "Debe contener el segundo pago");
    }
}